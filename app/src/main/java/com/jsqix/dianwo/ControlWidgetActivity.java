package com.jsqix.dianwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_control_widget)
public class ControlWidgetActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("控件使用");
    }

    @Event(value = {R.id.refresh_button, R.id.swipe_button, R.id.enlarge_button,
            R.id.date_button, R.id.diagram_button, R.id.picker_button,
            R.id.webview_button}, type = View.OnClickListener.class)
    private void click(View v) {
        switch (v.getId()) {
            case R.id.refresh_button:
                startActivity(new Intent(this, RefreshActivity.class));
                break;
            case R.id.swipe_button:
                startActivity(new Intent(this, SwipeActivity.class));
                break;
            case R.id.enlarge_button:
                startActivity(new Intent(this, ViewPhotoActivity.class));
                break;
            case R.id.date_button:
                startActivity(new Intent(this, DateRangeActivity.class));
                break;
            case R.id.diagram_button:
                startActivity(new Intent(this, DiagramActivity.class));
                break;
            case R.id.picker_button:
                startActivity(new Intent(this, PickViewActivity.class));
                break;
            case R.id.webview_button:
                startActivity(new Intent(this, WebViewActivity.class));
                break;
            default:
                break;
        }
    }

}
