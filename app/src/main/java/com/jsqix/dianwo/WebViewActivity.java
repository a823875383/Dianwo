package com.jsqix.dianwo;

import android.os.Bundle;

import com.jsqix.dianwo.base.BaseWebActivity;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_web_view)
public class WebViewActivity extends BaseWebActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        loadUrl("https://m.taobao.com/");
        loadUrl("file:///android_asset/webpage/fileChooser.html");
    }
}
