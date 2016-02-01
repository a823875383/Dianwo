package com.jsqix.dianwo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

@ContentView(R.layout.activity_mp_pie_chart)
public class MPPieChartActivity extends ToolBarActivity implements OnChartValueSelectedListener {
    @ViewInject(R.id.chart)
    private PieChart mChart;
    private Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("饼图");
        initView();
    }

    private void initView() {
        mChart.setUsePercentValues(true);
        mChart.setDescription("");
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        tf = Typeface.createFromAsset(getAssets(), "OpenSans-Regular.ttf");

        mChart.setCenterTextTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf")); // 设置圆盘中间区域的字体
        mChart.setCenterText(generateCenterSpannableText());// 设置中间的文字

        mChart.setDrawHoleEnabled(false); // 设置是否显示饼图中心的空白区 默认显示
        mChart.setHoleColorTransparent(true);

        mChart.setTransparentCircleColor(Color.WHITE); // 半透明圈的颜色
        mChart.setTransparentCircleAlpha(110); // 半透明圈的透明度

        mChart.setHoleRadius(58f); // 设置圆盘中间区域大小
        mChart.setTransparentCircleRadius(61f);// 设置中间透明圈的大小

        mChart.setDrawCenterText(false);// 是否显示圆盘中间文字 默认显示

        mChart.setRotationAngle(0);// 初始旋转角度
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true); // 圆盘是否可转动
        mChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");// 单位
        // mChart.setDrawUnitsInChart(true); // 设置是否使用单位 默认false

        // add a selection listener
        mChart.setOnChartValueSelectedListener(this);// 设置一个选中监听

        setData(3, 100);

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);//设置动画
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();//设置比例图
        l.setPosition(LegendPosition.RIGHT_OF_CHART);//最右边显示
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    private void setData(int count, float range) {

        float mult = range;

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();//每个饼块的实际数据

        // IMPORTANT: In a PieChart, no values (Entry) should have the same
        // xIndex (even if from different DataSets), since no values can be
        // drawn above each other.
        for (int i = 0; i < count + 1; i++) {
            yVals1.add(new Entry((float) (Math.random() * mult) + mult / 5, i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < count + 1; i++)
            xVals.add(mParties[i % mParties.length]);//每个饼块上的内容

        PieDataSet dataSet = new PieDataSet(yVals1, "Election Results");
        dataSet.setSliceSpace(2f);//设置个饼状图之间的距离
        dataSet.setSelectionShift(5f);// 选中态多出的长度

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);// 饼图颜色

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f); // 设置周围文字大小
        data.setValueTextColor(Color.WHITE);// 设置周围字体颜色
        data.setValueTypeface(tf);  // 设置圆盘百分比的字体
        mChart.setData(data);

        // undo all highlights
        mChart.highlightValues(null);

        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("MPAndroidChart\ndeveloped by Philipp Jahoda");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 14, s.length() - 15, 0);
        s.setSpan(new ForegroundColorSpan(Color.GRAY), 14, s.length() - 15, 0);
        s.setSpan(new RelativeSizeSpan(.8f), 14, s.length() - 15, 0);
        s.setSpan(new StyleSpan(Typeface.ITALIC), s.length() - 14, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), s.length() - 14, s.length(), 0);
        return s;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getVal() + ", xIndex: " + e.getXIndex()
                        + ", DataSet index: " + dataSetIndex);
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

}
