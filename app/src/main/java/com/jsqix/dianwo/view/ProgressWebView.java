package com.jsqix.dianwo.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.jsqix.dianwo.R;
import com.jsqix.dianwo.utils.DensityUtil;
import com.jsqix.dianwo.utils.UtilsHelp;

/**
 * 带进度条的webview
 * Created by dq on 2015/12/31.
 */
public class ProgressWebView extends WebView {
    private ProgressBar progressbar;
    public String TAG = getClass().getSimpleName();
    public final static int FILE_CHOOSER = 1;
    public final static int FILE_CHOOSER_5 = 2;
    public ValueCallback<Uri> mUploadMessage;
    public ValueCallback<Uri[]> mFilePathCallback;

    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        progressbar = new ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal);
        progressbar.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context, 3), 0, 0));
        progressbar.setProgressDrawable(getResources().getDrawable(R.drawable.progressbar_color));
        WebSettings settings = getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setAppCacheEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setDatabaseEnabled(true);
        addView(progressbar);
        setWebViewClient(new WebViewClient());
        setWebChromeClient(new WebChromeClient());
    }

    public class WebViewClient extends android.webkit.WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                progressbar.setVisibility(GONE);
            } else {
                if (progressbar.getVisibility() == GONE)
                    progressbar.setVisibility(VISIBLE);
                progressbar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            UtilsHelp.showDialogTip(getContext(), message);
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            UtilsHelp.showDialogTip(getContext(), message);
            return super.onJsAlert(view, url, message, result);
        }

        // For Android <3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            mUploadMessage = uploadMsg;
            openFile(FILE_CHOOSER);
        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType) {
            openFileChooser(uploadMsg);
        }

        // For Android 4.1+
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType, String capture) {
            openFileChooser(uploadMsg, acceptType);
        }

        // For Android 5.0+
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            mFilePathCallback = filePathCallback;
            openFile(FILE_CHOOSER_5);
            return true;
        }
    }

    private void openFile(int ResutCode) {
        Log.i(TAG, "openFileChooser enter");
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);

        i.setType("*/*");
        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, i);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "请选择");
        ((Activity) (getContext())).startActivityForResult(chooserIntent
                /*Intent.createChooser(
                        i, "choose files")*/, ResutCode);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
        lp.x = l;
        lp.y = t;
        progressbar.setLayoutParams(lp);
        super.onScrollChanged(l, t, oldl, oldt);
    }

}
