package com.jsqix.dianwo;

import android.os.Bundle;
import android.os.Handler;

import com.jsqix.dianwo.base.ToolBarActivity;
import com.jsqix.dianwo.view.bga.jd.BGAJDRefreshViewHolder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

@ContentView(R.layout.activity_bga_jd)
public class BGAJDActivity extends ToolBarActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @ViewInject(R.id.refreshLayout)
    private BGARefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("京东样式");
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        // 为BGARefreshLayout设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGAJDRefreshViewHolder refreshViewHolder = new BGAJDRefreshViewHolder(this, true);
        refreshViewHolder.setRefreshingAnimResId(R.anim.bg_refresh_jd_refreshing);
        // 设置下拉刷新和上拉加载更多的风格
        mRefreshLayout.setRefreshViewHolder(refreshViewHolder);
    }

    @Override
    public void onBGARefreshLayoutBeginRefreshing(BGARefreshLayout bgaRefreshLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.endRefreshing();
            }
        }, 2000);

    }

    @Override
    public boolean onBGARefreshLayoutBeginLoadingMore(BGARefreshLayout bgaRefreshLayout) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.endLoadingMore();
            }
        }, 2000);
        return true;
    }
}
