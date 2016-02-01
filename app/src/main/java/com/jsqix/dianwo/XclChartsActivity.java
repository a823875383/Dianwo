package com.jsqix.dianwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
@ContentView(R.layout.activity_xcl_charts)
public class XclChartsActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("XCL-Charts");
    }
    @Event(value = {R.id.line_chart_button, R.id.pie_chart_button}, type = View.OnClickListener.class)
    private void click(View v) {
        switch (v.getId()) {
            case R.id.line_chart_button:
                startActivity(new Intent(this, XclLineChartActivity.class));
                break;
            case R.id.pie_chart_button:
                startActivity(new Intent(this, XclPieChartActivity.class));
                break;

            default:
                break;
        }
    }
}
