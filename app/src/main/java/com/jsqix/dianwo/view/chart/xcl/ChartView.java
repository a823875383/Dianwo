package com.jsqix.dianwo.view.chart.xcl;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import org.xclcharts.common.DensityUtil;
import org.xclcharts.event.touch.ChartTouch;
import org.xclcharts.renderer.XChart;

import java.util.ArrayList;
import java.util.List;

/**
 * @author XiongChuanLiang<br/>(xcl_168@aliyun.com) QQ群: 374780627
 * @ClassName ChartView
 * @Description 含手势操作的XCL-Charts图表View基类
 */
public abstract class ChartView extends GraphicalView {

    //private ChartTouch mChartTouch[];
    private List<ChartTouch> mTouch = new ArrayList<ChartTouch>();

    public ChartView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub

    }

    public ChartView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public ChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


    @Override
    public void render(Canvas canvas) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        //touchChart(event);
        touchEvent(event);
        return true;
    }


    //////////////////////////////////////////////////////

    //////////////////////////////////////////////////////
    //用于手势操作来平移或放大缩小图表
    //////////////////////////////////////////////////////

    /**
     * 用于绑定需要手势滑动的图表
     *
     * @param view  视图
     * @param chart 图表类
     */
    public void bindTouch(View view, XChart chart) {
        mTouch.add(new ChartTouch(this, chart));
    }

    /**
     * 用于绑定需要手势滑动的图表，及指定可滑动范围
     *
     * @param view     视图
     * @param chart    图表类
     * @param panRatio 需大于0
     */
    public void bindTouch(View view, XChart chart, float panRatio) {
        mTouch.add(new ChartTouch(this, chart, panRatio));
    }

    /**
     * 清空绑定类
     */
    public void restTouchBind() {
        mTouch.clear();
    }

    /**
     * 触发手势操作
     *
     * @param event
     * @return
     */
    private boolean touchEvent(MotionEvent event) {
        for (ChartTouch c : mTouch) {
            c.handleTouch(event);
        }
        return true;
    }
    //Demo中bar chart所使用的默认偏移值。
    //偏移出来的空间用于显示tick,axistitle....
    protected int[] getBarLnDefaultSpadding()
    {
        int [] ltrb = new int[4];
        ltrb[0] = DensityUtil.dip2px(getContext(), 40); //left
        ltrb[1] = DensityUtil.dip2px(getContext(), 60); //top
        ltrb[2] = DensityUtil.dip2px(getContext(), 20); //right
        ltrb[3] = DensityUtil.dip2px(getContext(), 40); //bottom
        return ltrb;
    }

    protected int[] getPieDefaultSpadding()
    {
        int [] ltrb = new int[4];
        ltrb[0] = DensityUtil.dip2px(getContext(), 20); //left
        ltrb[1] = DensityUtil.dip2px(getContext(), 65); //top
        ltrb[2] = DensityUtil.dip2px(getContext(), 20); //right
        ltrb[3] = DensityUtil.dip2px(getContext(), 20); //bottom
        return ltrb;
    }
}

