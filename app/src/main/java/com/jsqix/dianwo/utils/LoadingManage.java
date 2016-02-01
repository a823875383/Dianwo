package com.jsqix.dianwo.utils;

import android.content.Context;
import android.util.Log;

import com.jsqix.dianwo.view.LoadingDialog;

/**
 * Created by dq on 2016/1/12.
 */
public class LoadingManage {

    static LoadingDialog loadingDialog;
    static LoadingManage loadingManage = new LoadingManage();

    public static LoadingManage getLoadingInstance(Context context) {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(context);
        }
        return loadingManage;
    }

    public void show() {
        try {
            if (loadingDialog != null) {
                loadingDialog.setStyle(0);
                loadingDialog.show();
            }
        } catch (Exception e) {
            Log.e(LoadingManage.class.getSimpleName(),"");
        }
    }

    public void hide() {
        try {
            if (loadingDialog != null) {
                loadingDialog.dismiss();
            }
        } catch (Exception e) {
            Log.e(LoadingManage.class.getSimpleName(),"");
        }
    }
}
