package com.jsqix.dianwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_pull_to_refresh)
public class PullToRefreshActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("PullToRefresh");
    }

    @Event(value = {R.id.tmall_button, R.id.ctrip_button}, type = View.OnClickListener.class)
    private void click(View v) {
        switch (v.getId()) {
            case R.id.tmall_button:
                startActivity(new Intent(this, TMallActivity.class));
                break;
            case R.id.ctrip_button:
                startActivity(new Intent(this, CtripActivity.class));
                break;
            default:
                break;
        }
    }
}
