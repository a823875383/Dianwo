package com.jsqix.dianwo;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjection;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jsqix.dianwo.abscls.HttpPost;
import com.jsqix.dianwo.api.ApiClient;
import com.jsqix.dianwo.api.Md5;
import com.jsqix.dianwo.banner.NetworkImageHolderView;
import com.jsqix.dianwo.base.BaseActivity;
import com.jsqix.dianwo.obj.AdvObj;
import com.jsqix.dianwo.utils.FileUtils;
import com.jsqix.dianwo.utils.FrescoUtils;
import com.jsqix.dianwo.utils.ScreenRecorder;
import com.jsqix.dianwo.utils.StringUtils;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ContentView(R.layout.activity_test)
public class TestActivity extends BaseActivity {
    @ViewInject(R.id.button_record)
    private Button btn;
    @ViewInject(R.id.head)
    private SimpleDraweeView head;
    @ViewInject(R.id.title_text)
    private ShimmerTextView titleView;
    @ViewInject(R.id.banner)
    private ConvenientBanner banner;
    private List<AdvObj> advList = new ArrayList<AdvObj>();

    private final String IMAGE_FILE_ROOT = FileUtils.getUserFileRoot();
    private String IMAGE_FILE_PATH = IMAGE_FILE_ROOT + "/head.png";

    private ScreenRecorder mRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSwipeBackEnable(false);
        requestPermissions();
        initView();
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, RESULT_CODE_0);
        } else {
            getAdv();
        }
    }

    private void initView() {
        titleView.setReflectionColor(R.color.colorPrimary);
        Shimmer shimmer = new Shimmer();
        shimmer.start(titleView);
    }

    @Event(value = {R.id.button, R.id.button_login, R.id.button_capture, R.id.button_record, R.id.head, R.id.right_btn}, type = View.OnClickListener.class)
    private void click(View v) {
        switch (v.getId()) {
            case R.id.button:
                startActivity(new Intent(this, ControlWidgetActivity.class));
                break;
            case R.id.button_login:
                login();
                break;
            case R.id.button_capture:
                startCapture();
                break;
            case R.id.button_record:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请录音权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, RESULT_CODE_1);
                } else {
                    recordScreen();
                }
                break;
            case R.id.head:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请CAMERA权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, RESULT_CODE_2);
                } else {
                    IMAGE_FILE_PATH = IMAGE_FILE_ROOT + "/head_" + System.currentTimeMillis() + ".png";
                    showCamera(IMAGE_FILE_PATH);
                }
                break;
            case R.id.right_btn:
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请CAMERA权限
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, RESULT_CODE_3);
                } else {
                    Intent intent = new Intent(this, CaptureActivity.class);
                    startActivity(intent);
                }
                break;
        }
    }


    private void recordScreen() {
        if (mRecorder != null) {
            mRecorder.quit();
            mRecorder = null;
            btn.setText("Restart recorder");
        } else {
            screenRecord();
        }
    }

    private void login() {
        Map map = new HashMap<String, Object>();
        map.put("acct", "test1");
        map.put("pwd", Md5.getMD5("123456" + ApiClient.SECRET_KEY, "utf-8"));
        HttpPost api = new HttpPost(this, map, this) {
            @Override
            public void onPreExecute() {
                showLoading();
            }
        };
        api.execute("userLogin");
        api.setResultCode(RESULT_CODE_0);
    }

    private void getAdv() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", 9302);
        map.put("type", "0");
        map.put("p_size", 5);
        map.put("p_num", 1);
        HttpPost api = new HttpPost(this, map, this) {

            @Override
            public void onPreExecute() {
                showLoading();
            }
        };
        api.setResultCode(RESULT_CODE_2);
        api.execute("queryAdvertAddList");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                // 本地图库
                case SELECT_PICTURE:
                    startPhotoZoom(data.getData(), SELECT_PICTURE, IMAGE_FILE_PATH);
                    break;
                // 拍照图片
                case SELECT_CAMER:
                    if (FileUtils.isSdcardAvailable()) {
                        File tempFile = new File(IMAGE_FILE_PATH);
                        startPhotoZoom(Uri.fromFile(tempFile), SELECT_CAMER, IMAGE_FILE_PATH);
                    } else {
                        showToast("未找到存储卡，无法存储照片！");
                    }
                    break;
                //显示图片并上传
                case SELECT_RESULT:
                    if (data != null) {
                        getImageToView(data);
                    }
                    break;
                //录制屏幕
                case REQUEST_MEDIA_PROJECTION:
                    MediaProjection mediaProjection = getMediaProjectionManager().getMediaProjection(resultCode, data);
                    if (mediaProjection == null) {
                        Log.e("@@", "media projection is null");
                        return;
                    }
                    // video size
                    final int width = 1280;
                    final int height = 720;
                    File file = new File(FileUtils.getRecordFileRoot(),
                            "record-" + width + "x" + height + "-" + System.currentTimeMillis() + ".mp4");
                    final int bitrate = 6000000;
                    mRecorder = new ScreenRecorder(width, height, bitrate, 1, mediaProjection, file.getAbsolutePath());
                    mRecorder.start();
                    btn.setText("Stop Recorder");
                    showToast("Screen recorder is running...");
                    moveTaskToBack(true);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RESULT_CODE_2) {
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                IMAGE_FILE_PATH = IMAGE_FILE_ROOT + "/head_" + System.currentTimeMillis() + ".png";
                showCamera(IMAGE_FILE_PATH);
            } else {
                showDialogTip("请允许打开相机");
            }
        } else if (requestCode == RESULT_CODE_3) {
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(this, CaptureActivity.class);
                startActivity(intent);
            } else {
                showDialogTip("请允许打开相机");
            }
        } else if (requestCode == RESULT_CODE_0) {
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getAdv();
            } else {
                showDialogTip("请允许访问内存卡");
            }
        } else if (requestCode == RESULT_CODE_1) {
            // Check if the only required permission has been granted
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                recordScreen();
            } else {
                showDialogTip("请允许录音");
            }
        } else super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    /**
     * 设置头像
     */
    private void getImageToView(Intent data) {
        FrescoUtils.setImageURI(head, "file://" + IMAGE_FILE_PATH);
        FileUtils.RecursionDeleteFile(new File(IMAGE_FILE_ROOT), new File(IMAGE_FILE_PATH).getName());
        upImage();
    }

    /**
     * 上传图像
     */
    private void upImage() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("uid", "9302");
        List<String> imgs = new ArrayList<String>();
        imgs.add(IMAGE_FILE_PATH);
        map.put("imgUrl", imgs);
        HttpPost post = new HttpPost(this, map, this) {

            @Override
            public void onPreExecute() {
            }
        };
        post.execute("uptUserHeadImg");
        post.setResultCode(RESULT_CODE_1);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showAppExit();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void postCallback(int resultCode, String result) {
        super.postCallback(resultCode, result);
        if (resultCode == RESULT_CODE_2) {
            getAdvDone(result);
        } else {
            showToast(result);
        }
    }

    @Override
    public void getCallback(int resultCode, String result) {
        super.getCallback(resultCode, result);
        showToast(result);
}

    private void getAdvDone(String result) {
        try {
            JSONObject ret = new JSONObject(result);
            String code = ret.getString("code");
            if ("000".equals(code)) {
                String imgAdRootUrl = ret.getString("url");
                JSONArray adData = ret.getJSONArray("obj");
                advList = new Gson().fromJson(adData.toString(),
                        new TypeToken<List<AdvObj>>() {
                        }.getType());
                for (AdvObj listObj : advList) {
                    listObj.setIMG_URL(imgAdRootUrl + listObj.getIMG_URL());
                    listObj.setIMG_MOD(imgAdRootUrl + listObj.getIMG_MOD());
                }

                initAdvPager();
            } else {
                showToast(ret.getString("msg"));
            }
        } catch (JSONException e) {
            if (StringUtils.isEmpty(result)) {
                showToast("服务器未响应");
            } else {
                showToast("数据异常");
            }
            e.printStackTrace();
        }
    }

    private void initAdvPager() {
        List<String> networkImages = new ArrayList<String>();
        for (int i = 0; i < advList.size(); i++)
            networkImages.add(advList.get(i).getIMG_URL());
        banner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
            @Override
            public NetworkImageHolderView createHolder() {
                return new NetworkImageHolderView();
            }
        }, networkImages)
                .setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        showToast("您点击的是" + position + "");
                    }
                })
                .setPageIndicator(new int[]{R.mipmap.dot_normal, R.mipmap.dot_focused})
                .startTurning(2000);
    }
}
