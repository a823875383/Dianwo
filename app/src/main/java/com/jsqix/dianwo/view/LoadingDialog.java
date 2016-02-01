package com.jsqix.dianwo.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.TextView;

import com.jsqix.dianwo.R;
import com.wang.avi.AVLoadingIndicatorView;


public class LoadingDialog extends Dialog {

    public final static int LOADING_STYLE_0 = 0, LOADING_STYLE_1 = 1, LOADING_STYLE_2 = 2;
    private Context mContext;
    private LayoutInflater inflater;
    private LayoutParams lp;
    private TextView loadtext;
    private AVLoadingIndicatorView avLoadingIndicatorView;
    private MetaballView metaballView;

    public LoadingDialog(Context context) {
        super(context, R.style.LoadingDialog);
        setCancelable(false);
        this.mContext = context;

        inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(R.layout.loadingdialog, null);
        avLoadingIndicatorView = (AVLoadingIndicatorView) layout.findViewById(R.id.loading_bar);
        metaballView = (MetaballView) layout.findViewById(R.id.metaball_view);
        loadtext = (TextView) layout.findViewById(R.id.loading_text);
        setContentView(layout);
        WindowManager m = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        // 设置window属性
        lp = getWindow().getAttributes();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        lp.gravity = Gravity.CENTER;
        lp.alpha = 1.0f; // 设置本身透明度
        lp.dimAmount = 0; // 设置黑暗度
        getWindow().setAttributes(lp);

    }

    public void setStyle(int styleId) {
        if (styleId == LOADING_STYLE_0) {
            avLoadingIndicatorView.setVisibility(View.VISIBLE);
            metaballView.setVisibility(View.GONE);
        } else if (styleId == LOADING_STYLE_1) {
            avLoadingIndicatorView.setVisibility(View.GONE);
            metaballView.setVisibility(View.VISIBLE);
        }

    }


    public void setLoadText(String content) {
        loadtext.setText(content);
    }

    public void show() {
        if (this != null && !isShowing())
            super.show();
    }

    public void dismiss() {
        if (this != null && isShowing())
            super.dismiss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dismiss();
        }
        return super.onKeyDown(keyCode, event);
    }
}