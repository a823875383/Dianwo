package com.jsqix.dianwo.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;
import com.jsqix.dianwo.R;


/**
 * 滑动返回 可以extends SlidingActivity 使用slidingmenu实现
 * 也可以用可以extends SwipeBackActivity 来实现
 */
public abstract class BaseSwipeBackActivity extends SlidingActivity implements SlidingMenu.OnOpenedListener {

    //SlidingMenu
    private SlidingMenu mSlidingMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //这里借用了SlidingMenu的setBehindContentView方法来设置一个透明菜单
        View behindView = new View(this);
        behindView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        behindView.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setBehindContentView(behindView);

        mSlidingMenu = getSlidingMenu();
        //设置阴影宽度为10个px
        mSlidingMenu.setShadowWidth(10);
        //设置阴影
        mSlidingMenu.setShadowDrawable(R.drawable.slide_shadow);
        //设置下面的布局，也就是我们上面定义的透明菜单离右边屏幕边缘的距离为0，也就是滑动开以后菜单会全屏幕显示
        mSlidingMenu.setBehindOffset(0);
        mSlidingMenu.setFadeDegree(0.35f);
        //菜单打开监听，因为菜单打开后我们要finish掉当前的Activity
        mSlidingMenu.setOnOpenedListener(this);

        //设置手势滑动方向，因为我们要实现微信那种右滑动的效果，这里设置成SlidingMenu.LEFT模式
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        //因为微信是只有边缘滑动，我们设置成TOUCHMODE_MARGIN模式，如果你想要全屏幕滑动，只需要把这个改成TOUCHMODE_FULLSCREEN就OK了
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

    }


    //滑动完全打开菜单后结束掉当前的Activity
    @Override
    public void onOpened() {
        this.finish();
    }
    public void setSwipeBackEnable(boolean enable) {
        if (enable == false) {
            getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        } else {
            getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        }
    }

}