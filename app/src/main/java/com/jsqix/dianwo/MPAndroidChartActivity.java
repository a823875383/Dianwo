package com.jsqix.dianwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_mpandroid_chart)
public class MPAndroidChartActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("MPAndroidChart");
    }
    @Event(value = {R.id.line_chart_button, R.id.pie_chart_button}, type = View.OnClickListener.class)
    private void click(View v) {
        switch (v.getId()) {
            case R.id.line_chart_button:
                startActivity(new Intent(this, MPLineChartActivity.class));
                break;
            case R.id.pie_chart_button:
                startActivity(new Intent(this, MPPieChartActivity.class));
                break;

            default:
                break;
        }
    }
}
