package com.jsqix.dianwo;

import android.os.Bundle;

import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;

@ContentView(R.layout.activity_xcl_line_chart)
public class XclLineChartActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("折线图");
        initView();
    }

    private void initView() {
//        //线图基类
//        LineChart chart = new LineChart();
//        //图所占范围大小
//        chart.setChartRange(0, 0, this.mScrWidth  , this.mScrHeight );
//
//        //标签1对应的数据集
//        LinkedList<Double> value1= new LinkedList<Double>();
//        value1.add((double)25);
//        value1.add((double)21);
//        value1.add((double)31);
//        value1.add((double)40);
//        value1.add((double)35);
//        //标签2对应的数据集
//        LinkedList<Double> value2= new LinkedList<Double>();
//        value2.add((double)30);
//        value2.add((double)42);
//        value2.add((double)50);
//        value2.add((double)50);
//        value2.add((double)40);
//
//        //将标签与对应的数据集分别绑定
//        LineData lineData1 = new LineData("小熊",value1,(int)Color.rgb(234, 83, 71));
//        LineData lineData2 = new LineData("小小熊",value2,(int)Color.rgb(75, 166, 51));
//
//        //标签集合
//        LinkedList<String> lables = new LinkedList<String>();
//        lables.add("2010");
//        lables.add("2011");
//        lables.add("2012");
//        lables.add("2013");
//        lables.add("2014");
//        chart.setLineLables(lables);
//
//        //设定数据源
//        LinkedList<LineData> chartData = new LinkedList<LineData>();
//        chartData.add(lineData1);
//        chartData.add(lineData2);
//        chart.setDataSource(chartData);
//
//        //图标题
//        chart.setTitle("线图(Line Chart)");
//        //图例
//        chart.setLegend("XCL-Charts");
//
//        //数据轴最大值
//        chart.setDataAxisMax(100);
//        //数据轴刻度间隔
//        chart.setDataAxisSteps(10);
//
//        //设置标签轴颜色
//        chart.getLablesAxisPaint().setColor((int)Color.rgb(22, 107, 164));
//        //设置数据轴颜色
//        chart.getDataAxisPaint().setColor((int)Color.rgb(252, 210, 9));
//
//        //显示分隔色
//        chart.isShowInnerLineInterval(false);
//        //设置分隔色
//        //chart.setInnerLineIntervalColor((int)Color.rgb(239, 239, 239));
//
//        //显示横向分隔网线
//        chart.isShowInnerHorizontalLine(true);
//        //显示竖向分隔网线
//        chart.isShowInnerVerticalLine(true);
//
//        //显示横向分隔网线颜色
//        chart.setInnerHorizontalLineStyle(1,(int) Color.rgb(218, 218, 218));
//        //显示竖向分隔网线颜色
//        chart.setInnerVerticalLineStyle(1,(int)Color.rgb(218, 218, 218));
//
//        //线条粗细
//        chart.setLineStyle(5);
//        //点上显示圆心
//        chart.setDotStyle(XTypes.LineDotStyle.Circle);
//        //点上圆心半径
//        chart.setLineDotRadius(8);
//        //点上显示标签
//        chart.isShowDotLabel(true);
//
//        //设置标签轴标签 偏移量,旋转角度
//        chart.setPaintStyle(XTypes.LineTextPaintType.LABLESAXIS_LABLES,10,-45f);
    }
}
