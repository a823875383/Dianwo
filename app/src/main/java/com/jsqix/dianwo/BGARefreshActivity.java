package com.jsqix.dianwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_bga_refresh)
public class BGARefreshActivity extends ToolBarActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("BGA刷新控件");
    }

    @Event(value = {R.id.refresh_common, R.id.refresh_meituan,
            R.id.refresh_jd, R.id.refresh_tmall,
            R.id.refresh_autohome, R.id.refresh_ctrip})
    private void click(View v) {
        switch (v.getId()) {
            case R.id.refresh_common:
                startActivity(new Intent(this, BGACommonActivity.class));
                break;
            case R.id.refresh_meituan:
                startActivity(new Intent(this, BGAMeiTuanActivity.class));
                break;
            case R.id.refresh_jd:
                startActivity(new Intent(this, BGAJDActivity.class));
                break;
            case R.id.refresh_tmall:
                startActivity(new Intent(this, BGATMallActivity.class));
                break;
            case R.id.refresh_autohome:
                startActivity(new Intent(this, BGAAutoHomeActivity.class));
                break;
            case R.id.refresh_ctrip:
                startActivity(new Intent(this, BGACtripActivity.class));
                break;
        }
    }


}
