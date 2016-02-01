package com.jsqix.dianwo.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

import com.jsqix.dianwo.inteface.InterfaceHttpGet;
import com.jsqix.dianwo.inteface.InterfaceHttpPost;
import com.jsqix.dianwo.utils.FileUtils;
import com.jsqix.dianwo.utils.StringUtils;
import com.jsqix.dianwo.utils.UtilsHelp;
import com.jsqix.dianwo.view.ActionSheetDialog;
import com.jsqix.dianwo.view.ActionSheetDialog.SheetItemColor;
import com.jsqix.dianwo.view.IOSDialog;
import com.jsqix.dianwo.view.LoadingDialog;

import org.xutils.x;

import java.io.File;
import java.text.SimpleDateFormat;

import me.imid.swipebacklayout.lib.app.SwipeBackActivity;

//import android.media.projection.MediaProjectionManager;

public class BaseActivity extends SwipeBackActivity implements InterfaceHttpGet, InterfaceHttpPost {
    protected static final int RESULT_CODE_0 = 0, RESULT_CODE_1 = 1, RESULT_CODE_2 = 2,
            RESULT_CODE_3 = 3, RESULT_CODE_4 = 4, RESULT_CODE_5 = 5, RESULT_CODE_6 = 6;
    protected static final int INTENT_CODE_0 = 0x100, INTENT_CODE_1 = 0x1001, INTENT_CODE_2 = 0x1002,
            INTENT_CODE_3 = 0x1003, INTENT_CODE_4 = 0x1004, INTENT_CODE_5 = 0x1005, INTENT_CODE_6 = 0x1006;
    protected static final int SELECT_CAMER = 0x1000, SELECT_PICTURE = 0x1001,
            SELECT_RESULT = 0x1002, REQUEST_MEDIA_PROJECTION = 0x1003;
    private LoadingDialog loadingDialog;
    private MediaProjectionManager mMediaProjectionManager;

    protected String[] mMonths = new String[]{
            "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dec"
    };

    protected String[] mParties = new String[]{
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        /*沉浸式状态栏*/
        StatusBarCompat.compat(this);
    }

    /**
     * 退出APP对话框
     */
    public void showAppExit() {
        IOSDialog alertDialog = new IOSDialog(this);
        alertDialog.builder().setCancelable(true).setTitle("提示")
                .setMsg("亲，你确定要退出了么\n");
        alertDialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
                finish();
            }
        });
        alertDialog.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        alertDialog.show();
    }

    /**
     * 提示对话框
     */
    public void showDialogTip(String msg) {
        UtilsHelp.showDialogTip(this, msg);
    }

    /**
     * 提示确认框
     */
    public void showDialogConfirm(String msg) {
        UtilsHelp.showDialogConfirm(this, msg);
    }

    /**
     * 提示弹出框
     */
    public void showPopTip(String msg, View view) {
        UtilsHelp.showPopTip(this, msg, view);
    }

    /**
     * 提示Toast
     */
    public void showToast(String msg) {
        UtilsHelp.showToast(this, msg);
    }


    /**
     * 显示加载对话框
     */
    public void showLoading() {
        showLoading("正在处理,请稍候...", 0);
    }

    public void showLoading(String text, int styleId) {
        try {
            if (text != null) {
                loadingDialog = new LoadingDialog(this);
                loadingDialog.setStyle(styleId);
                loadingDialog.setTitle(text);
                loadingDialog.show();
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public void showLoading(int id, int styleId) {
        if (id != 0) {
            String text = getResources().getString(id);
            if (text != null) {
                loadingDialog = new LoadingDialog(this);
                loadingDialog.setStyle(styleId);
                loadingDialog.setLoadText(text);
                loadingDialog.show();
            }
        }
    }

    /**
     * 隐藏加载对话框
     */
    public void hideLoading() {
        try {
            if (loadingDialog != null) {
                loadingDialog.dismiss();
            }
        } catch (Exception e) {

        }
    }

    /**
     * 检查网络提示
     */
    public PopupWindow showNetWorkError(View anchor) {
        return UtilsHelp.showNetWorkError(this, anchor);
    }

    /**
     * 图片选择器对话框
     */
    public void showCamera(final String imgPath) {
        final ActionSheetDialog sheetDialog = new ActionSheetDialog(this).builder();
        sheetDialog.setTitle("选项");
        sheetDialog.addSheetItem("拍摄照片", SheetItemColor.Red,
                new ActionSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        Intent intent = new Intent(
                                MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri
                                .fromFile(new File(imgPath)));
                        startActivityForResult(intent, SELECT_CAMER);
                    }
                });
        sheetDialog.addSheetItem("选取本地", ActionSheetDialog.SheetItemColor.Red,
                new ActionSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        startActivityForResult(
                                Intent.createChooser(intent, "选择图片"),
                                SELECT_PICTURE);
                    }
                });
        sheetDialog.show();
    }

    /**
     * 裁剪图片
     */
    public void startPhotoZoom(Uri uri, int requestCode, String path) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        if (requestCode == SELECT_PICTURE && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            String url = FileUtils.getPath(this, uri);
            intent.setDataAndType(Uri.fromFile(new File(url)), "image/*");
        } else {
            intent.setDataAndType(uri, "image/*");
        }
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(path)));//保存路径

        startActivityForResult(intent, SELECT_RESULT);

    }


    /**
     * 切换用户对话框
     */
    public void showUserExit() {
        final ActionSheetDialog sheetDialog = new ActionSheetDialog(this).builder();
        sheetDialog.setTitle("选项");
        sheetDialog.addSheetItem("退出账户", SheetItemColor.Red,
                new ActionSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {

                    }
                });
        sheetDialog.addSheetItem("切换用户", ActionSheetDialog.SheetItemColor.Red,
                new ActionSheetDialog.OnSheetItemClickListener() {

                    @Override
                    public void onClick(int which) {

                    }
                });
        sheetDialog.show();
    }

    /**
     * 截图
     */
    public String startCapture() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
        String strDate = dateFormat.format(new java.util.Date());
        String pathImage = FileUtils.getImageFileRoot();
        String nameImage = pathImage + "/" + strDate + ".png";
        View viewScreen = getWindow().getDecorView();
        viewScreen.setDrawingCacheEnabled(true);
        viewScreen.buildDrawingCache();
        int windowWidth = getWindowManager().getDefaultDisplay().getWidth();
        int windowHeight = getWindowManager().getDefaultDisplay().getHeight();
        Bitmap bitmap = Bitmap.createBitmap(viewScreen.getDrawingCache(), 0, 0, windowWidth, windowHeight);
        Log.i("Snapshot", "image data captured");
        viewScreen.destroyDrawingCache();
        nameImage = FileUtils.saveCache(nameImage, bitmap);
        if (!isNull(nameImage)) {
            showToast("截屏成功");
        }
        return nameImage;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void screenRecord() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mMediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
            Intent captureIntent = mMediaProjectionManager.createScreenCaptureIntent();
            startActivityForResult(captureIntent, REQUEST_MEDIA_PROJECTION);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MediaProjectionManager getMediaProjectionManager() {
        return mMediaProjectionManager;
    }


    public boolean isNull(Object obj) {
        return StringUtils.isNull(obj);
    }

    @Override
    public void getCallback(int resultCode, String result) {
        hideLoading();
        Log.v("请求的结果", result);
//        showToast(result);
    }

    @Override
    public void postCallback(int resultCode, String result) {
        hideLoading();
        Log.v("请求的结果", result);
//        showToast(result);
    }
}
