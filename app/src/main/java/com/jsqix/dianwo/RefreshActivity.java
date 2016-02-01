package com.jsqix.dianwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_refresh)
public class RefreshActivity extends ToolBarActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("刷新控件");
    }

    @Event(value = {R.id.bga_button, R.id.pull_button,
            R.id.onexlist_button, R.id.material_button}, type = View.OnClickListener.class)
    private void click(View v) {
        switch (v.getId()) {
            case R.id.bga_button:
                startActivity(new Intent(this, BGARefreshActivity.class));
                break;
            case R.id.pull_button:
                startActivity(new Intent(this, PullToRefreshActivity.class));
                break;
            case R.id.onexlist_button:
                startActivity(new Intent(this, OneXRefreshActivity.class));
                break;
            case R.id.material_button:
                startActivity(new Intent(this, MaterialRefreshActivity.class));
                break;
            default:
                break;
        }
    }

}
