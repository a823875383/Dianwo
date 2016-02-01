package com.jsqix.dianwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_swipe)
public class SwipeActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("滑动菜单");
    }

    @Event(value = {R.id.menu_button, R.id.layout_button}, type = View.OnClickListener.class)
    private void click(View v) {
        switch (v.getId()) {
            case R.id.menu_button:
                startActivity(new Intent(this, SwipeMenuActivity.class));
                break;
            case R.id.layout_button:
                startActivity(new Intent(this, SwipeLayoutActivity.class));
                break;

            default:
                break;
        }
    }
}
