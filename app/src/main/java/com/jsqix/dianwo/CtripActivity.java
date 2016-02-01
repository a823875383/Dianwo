package com.jsqix.dianwo;

import android.os.Bundle;
import android.os.Handler;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.jsqix.dianwo.base.ToolBarActivity;
import com.jsqix.dianwo.view.pulltorefresh.CtripHeaderLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_ctrip)
public class CtripActivity extends ToolBarActivity implements PullToRefreshBase.OnRefreshListener {
    @ViewInject(R.id.main_act_scrollview)
    PullToRefreshScrollView mPullToRefreshScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("携程旅游");
        initView();
    }

    private void initView() {
        mPullToRefreshScrollView.setHeaderLayout(new CtripHeaderLayout(this));
        mPullToRefreshScrollView.setOnRefreshListener(this);
    }


    @Override
    public void onRefresh(PullToRefreshBase refreshView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullToRefreshScrollView.onRefreshComplete();
            }
        }, 2000);
    }
}
