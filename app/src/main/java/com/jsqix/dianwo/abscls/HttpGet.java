package com.jsqix.dianwo.abscls;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jsqix.dianwo.api.ApiClient;
import com.jsqix.dianwo.inteface.InterfaceHttpGet;

import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public abstract class HttpGet extends AsyncTask<String, String, String> {
    // private final String PUSH_URL = "http://192.168.1.91:8888/api/";
    String response = "";
    int resultCode = 0;
    InterfaceHttpGet mListener;
    Context context;
    Map<String, Object> postMap;

    // Constructor
    public HttpGet(Context context, Map<String, Object> params,
                   InterfaceHttpGet listener) {
        this.mListener = listener;
        this.postMap = params;
        this.context = context;
    }

    public abstract void onPreExecute();

    public void onPostExecute(String response) {
        if (mListener != null)
            mListener.getCallback(resultCode, response);
    }

    public String doInBackground(String... urls) {
        return getJSON(urls);
    }

    public String getJSON(String... urls) {
        try {
            for (String url : urls) {
                String getUrl = ApiClient.makeGetMessage(ApiClient.IP + url, postMap);
                OkHttpRequest(getUrl);
//                HttpURLRequst(getUrl);
//                XutilsRequst(getUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.v("Exception", e.getMessage() == null ? "" : e.getMessage());
        } catch (Throwable t) {

        }
        return response;
    }

    private void OkHttpRequest(String url) throws IOException {
        int timeoutConnection = 20000;
        OkHttpClient client = new OkHttpClient();
        client.newBuilder().connectTimeout(timeoutConnection, TimeUnit.SECONDS);
        client.newBuilder().readTimeout(timeoutConnection, TimeUnit.SECONDS);
        Request request = new Request.Builder().url(url).build();
        Response okResponse = client.newCall(request).execute();
        if (okResponse.isSuccessful()) {
            response = okResponse.body().string();
        } else {
            response = "";
        }
    }

    private void HttpURLRequst(String url) throws IOException {
        int timeoutConnection = 20000;
        URL httpUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.setConnectTimeout(timeoutConnection);
        conn.setReadTimeout(timeoutConnection);
        InputStream is = conn.getInputStream();   //获取输入流，此时才真正建立链接
        response = ApiClient.convertStreamToString(is);
    }

    private void XutilsRequst(String url) throws Throwable {
        RequestParams params = new RequestParams(url);
        response = x.http().getSync(params, String.class);
    }

    public void setResultCode(int code) {
        this.resultCode = code;
    }


}
