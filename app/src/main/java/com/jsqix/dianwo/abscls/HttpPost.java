package com.jsqix.dianwo.abscls;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jsqix.dianwo.api.ApiClient;
import com.jsqix.dianwo.inteface.InterfaceHttpPost;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class HttpPost extends AsyncTask<String, String, String> {
    String response = "";
    int resultCode = 0;

    Context context;
    InterfaceHttpPost mListener;
    Map<String, Object> postMap;

    /**
     * @param context
     * @param params
     * @param listener
     */
    public HttpPost(Context context, Map<String, Object> params,
                    InterfaceHttpPost listener) {
        this.postMap = params;
        String hmac = ApiClient.getSignAfter(params, ApiClient.ANDRID_SDK_KEY);
        postMap.put("hmac", hmac);
        this.mListener = listener;
        this.context = context;
    }

    public abstract void onPreExecute();

    public String doInBackground(String... urls) {
        return httpPost(urls);
    }

    public void onPostExecute(String response) {
        if (mListener != null) {
            mListener.postCallback(resultCode, response);
        }
    }

    @SuppressLint("NewApi")
    public String httpPost(String... urls) {
        try {
            for (String url : urls) {

                url = ApiClient.IP + url;
                OkHttpPost(url);
//                XutilsPost(url);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("Exception", e.getMessage() == null ? "" : e.getMessage());
        }
        catch (Throwable t){

        }
        return response;
    }

    private void OkHttpPost(String url)
            throws Exception {
        int timeoutConnection = 20000;
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(timeoutConnection, TimeUnit.SECONDS);
        client.newBuilder().readTimeout(timeoutConnection, TimeUnit.SECONDS);
        MultipartBody.Builder multipartEntity = new MultipartBody.Builder();
        Iterator<Entry<String, Object>> strings = postMap.entrySet().iterator();
        while (strings.hasNext()) {
            Entry<String, Object> entry = strings.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof List<?>) {
                List imgs = (List) value;
                for (Object img : imgs) {
                    File file = new File(img + "");
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                    multipartEntity.addFormDataPart(key, file.getName(), fileBody);
//                    multipartEntity.addPart(fileBody);
                }
            } else {
                if (key.contains("img")) {
                    File file = new File(value + "");
                    RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                    multipartEntity.addFormDataPart(key, file.getName(), fileBody);
//                    multipartEntity.addPart(fileBody);
                } else {
//                    RequestBody stringBody = RequestBody.create(null, value + "");
                    multipartEntity.addFormDataPart(key, value + "");
                }
            }
        }
        RequestBody requestBody = multipartEntity.build();
        Request request = new Request.Builder().url(url).post(requestBody).build();
        Response okResponse = client.newCall(request).execute();
        if (okResponse.isSuccessful()) {
            response = okResponse.body().string();
        } else {
            response = "";
        }

    }

    private void XutilsPost(String url) throws Throwable {
        RequestParams params = new RequestParams(url);
        Iterator<Entry<String, Object>> strings = postMap.entrySet().iterator();
        while (strings.hasNext()) {
            Entry<String, Object> entry = strings.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof List<?>) {
                List imgs = (List) value;
                for (Object img : imgs) {
                    File file = new File(img + "");
                    params.addBodyParameter(key, file, "image/*", file.getName());
                }
            } else {
                if (key.contains("img")) {
                    File file = new File(value + "");
                    params.addBodyParameter(key, file, "image/*", file.getName());
                } else {
                    params.addBodyParameter(key, value + "");
                }
            }
        }
        response = x.http().postSync(params, String.class);
    }

    private void HttpUrlPost(String url)
            throws Exception {
        int timeoutConnection = 20000;
        URL httpUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setRequestMethod("POST");
        conn.setConnectTimeout(timeoutConnection);
        conn.setReadTimeout(timeoutConnection);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("Charsert", "UTF-8");
//        conn.setRequestProperty("Content-Type", "multipart/form-data");
        Iterator<Entry<String, Object>> strings = postMap.entrySet().iterator();
        DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
        while (strings.hasNext()) {
            Entry<String, Object> entry = strings.next();
            String key = entry.getKey();
            Object value = entry.getValue();
            if (value instanceof List<?>) {
                List imgs = (List) value;
                for (Object img : imgs) {
                    File file = new File(img + "");
                    dos.writeBytes("Content-Disposition: form-data;name=\"" + key + "\";filename=\"" + file.getName() + "\"\r\n");
                    dos.writeBytes("Content-Type: " + "image/*" + "\r\n");
                    dos.write(getBytes(file));
                    dos.writeBytes("\r\n");
                }
            } else {
                if (key.contains("img")) {
                    File file = new File(value + "");
                    dos.writeBytes("Content-Disposition: form-data;name=\"" + key + "\";filename=\"" + file.getName() + "\"\r\n");
                    dos.writeBytes("Content-Type: " + "image/*" + "\r\n");
                    dos.write(getBytes(file));
                    dos.writeBytes("\r\n");
                } else {
                    dos.writeBytes("Content-Disposition: form-data; name=\"" + key
                            + "\"\r\n");
                    dos.writeBytes("Content-Type: text/plain; charset=" + "UTF-8" + "\"\r\n");
//                    dos.writeBytes("Content-Transfer-Encoding: 8bit" + "\"\r\n");
                    dos.writeBytes(value + "\r\n");
                }
            }
        }
        dos.writeBytes("\r\n");
        InputStream is = conn.getInputStream();   //获取输入流，此时才真正建立链接
        response = ApiClient.convertStreamToString(is);
    }

    //把文件转换成字节数组
    private byte[] getBytes(File f) throws Exception {
        FileInputStream in = new FileInputStream(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = in.read(b)) != -1) {
            out.write(b, 0, n);
        }
        in.close();
        return out.toByteArray();
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }


}
