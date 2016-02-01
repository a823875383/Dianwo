package com.jsqix.dianwo.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.jsqix.dianwo.R;
import com.jsqix.dianwo.view.ProgressWebView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * Created by dq on 2016/1/12.
 */
public class BaseWebActivity extends ToolBarActivity {
    @ViewInject(R.id.web_view)
    private ProgressWebView mWebView;
    @ViewInject(R.id.web_load_error)
    private LinearLayout webError;
    @ViewInject(R.id.network_exception)
    private RelativeLayout networkException;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, android.net.http.SslError error) {
                handler.proceed();
            }

            @Override
            public void onReceivedError(WebView view, int errorCode,
                                        String description, final String failingUrl) {
                // 加载页面报错时的处理
//                showNetWorkError(toolbar);
                networkException.setVisibility(View.VISIBLE);
                webError.setVisibility(View.VISIBLE);
                webError.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        loadUrl(failingUrl);
                    }
                });
            }
        });
    }

    @Event(R.id.network_exception)
    private void click(View v) {
        startActivity(new Intent(Settings.ACTION_SETTINGS));
    }

    public void loadUrl(String url) {
        webError.setVisibility(View.GONE);
        networkException.setVisibility(View.GONE);
        mWebView.loadUrl(url);
    }

    public WebSettings getSettings() {
        return mWebView.getSettings();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == mWebView.FILE_CHOOSER) {
            if (null == mWebView.mUploadMessage)
                return;
            Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
            mWebView.mUploadMessage.onReceiveValue(result);
            mWebView.mUploadMessage = null;

        } else if (requestCode == mWebView.FILE_CHOOSER_5) {
            if (null == mWebView.mFilePathCallback)
                return;
            Uri result = (intent == null || resultCode != RESULT_OK) ? null : intent.getData();
            if (result != null) {
                mWebView.mFilePathCallback.onReceiveValue(new Uri[]{result});
            } else {
                mWebView.mFilePathCallback.onReceiveValue(new Uri[]{});
            }
            mWebView.mFilePathCallback = null;
        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
}
