package com.jsqix.dianwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_diagram)
public class DiagramActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("图表绘制");
    }

    @Event(value = {R.id.mpandroid_chart_button, R.id.xcl_chart_button}, type = View.OnClickListener.class)
    private void click(View v) {
        switch (v.getId()) {
            case R.id.mpandroid_chart_button:
                startActivity(new Intent(this, MPAndroidChartActivity.class));
                break;
            case R.id.xcl_chart_button:
                startActivity(new Intent(this, XclChartsActivity.class));
                break;

            default:
                break;
        }
    }

}
