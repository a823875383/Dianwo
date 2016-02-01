package com.jsqix.dianwo;

import android.os.Bundle;
import android.os.Handler;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.jsqix.dianwo.base.ToolBarActivity;
import com.jsqix.dianwo.view.pulltorefresh.TmallFooterLayout;
import com.jsqix.dianwo.view.pulltorefresh.TmallHeaderLayout;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_tmall)
public class TMallActivity extends ToolBarActivity implements PullToRefreshBase.OnRefreshListener2 {
    @ViewInject(R.id.main_act_scrollview)
    PullToRefreshScrollView mPullToRefreshScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("天猫");
        initView();
    }

    private void initView() {
        mPullToRefreshScrollView.setHeaderLayout(new TmallHeaderLayout(this));
        mPullToRefreshScrollView.setFooterLayout(new TmallFooterLayout(this));
        mPullToRefreshScrollView.setOnRefreshListener(this);
    }

    @Override
    public void onPullDownToRefresh(PullToRefreshBase refreshView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullToRefreshScrollView.onRefreshComplete();
            }
        }, 2000);
    }

    @Override
    public void onPullUpToRefresh(PullToRefreshBase refreshView) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mPullToRefreshScrollView.onRefreshComplete();
            }
        }, 2000);
    }
}
