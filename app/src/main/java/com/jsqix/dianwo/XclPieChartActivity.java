package com.jsqix.dianwo;

import android.os.Bundle;

import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_xcl_pie_chart)
public class XclPieChartActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("饼图");
    }
}
