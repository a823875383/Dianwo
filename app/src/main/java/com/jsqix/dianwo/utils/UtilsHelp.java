package com.jsqix.dianwo.utils;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.jsqix.dianwo.R;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;

import me.drakeet.materialdialog.MaterialDialog;

/**
 * Created by dq on 2016/1/12.
 */
public class UtilsHelp {

    /**
     * 提示对话框
     */
    public static void showDialogTip(Context context, String msg) {
        final MaterialDialog alertDialog = new MaterialDialog(context);
        alertDialog.setTitle("提示").setMessage(msg + "\n");
        alertDialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    /**
     * 提示确认框
     */
    public static void showDialogConfirm(Context context, String msg) {
        final MaterialDialog alertDialog = new MaterialDialog(context);
        alertDialog.setTitle("提示").setMessage(msg + "\n");
        alertDialog.setPositiveButton("确认", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setPositiveButton("取消", new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    /**
     * 提示弹出框
     */
    public static void showPopTip(Context context, String msg, View view) {
        ToolTipRelativeLayout mToolTip = new ToolTipRelativeLayout(context);
        ToolTip toolTip = new ToolTip()
                .withText(msg)
                .withColor(Color.RED)
                .withShadow()
                .withAnimationType(ToolTip.AnimationType.FROM_TOP);
        mToolTip.showToolTipForView(toolTip, view);
    }

    /**
     * 提示Toast
     */
    public static void showToast(Context context, String msg) {
        ToastHelper.makeText(context, msg, ToastHelper.LENGTH_LONG).setAnimation(R.style.PopToast).show();
//        SuperToast toast = new SuperToast(context);
//        toast.setText(msg);
//        toast.setDuration(SuperToast.Duration.VERY_SHORT);
//        toast.setBackground(SuperToast.Background.BLACK);
//        toast.setAnimations (SuperToast.Animations.POPUP);
//        toast.setTextColor(Color.WHITE);
//        toast.show();
    }

    /**
     * 显示加载对话框
     */
    public static void showLoading(Context context) {
        LoadingManage.getLoadingInstance(context).show();
    }

    /**
     * 隐藏加载对话框
     */
    public static void hideLoading(Context context) {
        LoadingManage.getLoadingInstance(context).hide();
    }

    /**
     * 检查网络提示
     */
    public static PopupWindow showNetWorkError(final Context context, View anchor) {
        View view = LayoutInflater.from(context).inflate(R.layout.network_exception, null);
        PopupWindow pop = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context, 40), true);
        pop.setBackgroundDrawable(context.getResources().getDrawable(R.mipmap.network_exception_bg));
        pop.setOutsideTouchable(true);
        pop.setTouchable(true);
        pop.setFocusable(true);
        pop.showAsDropDown(anchor);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //打开设置界面
                context.startActivity(new Intent(Settings.ACTION_SETTINGS));
            }
        });
//        pop.showAtLocation(anchor, Gravity.LEFT | Gravity.TOP, 0, anchor.getMeasuredHeight());
        return pop;
    }

    //是否为手机、平板或电视
    public static boolean isTablet(Context context) {
        boolean isTablet = (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_LARGE;
        boolean isPhone = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getPhoneType() == TelephonyManager.PHONE_TYPE_NONE;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.getDefaultDisplay().getRealMetrics(dm);//包括虚拟键
        } else {
            wm.getDefaultDisplay().getMetrics(dm);
        }
        double diagonalPixels = Math.sqrt(Math.pow(dm.widthPixels / dm.xdpi, 2) + Math.pow(dm.heightPixels / dm.ydpi, 2));
        boolean screenInches = diagonalPixels > 10;
        if (isTablet && isPhone && screenInches)
            return true;
        return false;
    }

}
