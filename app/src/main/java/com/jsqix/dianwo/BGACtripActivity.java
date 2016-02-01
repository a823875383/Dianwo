package com.jsqix.dianwo;

import android.os.Bundle;
import android.os.Handler;

import com.jsqix.dianwo.base.ToolBarActivity;
import com.jsqix.dianwo.view.bga.ctrip.BGACtripRefreshViewHolder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

@ContentView(R.layout.activity_bga_ctrip)
public class BGACtripActivity extends ToolBarActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @ViewInject(R.id.refreshLayout)
    private BGARefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("携程旅游");
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setDelegate(this);
        mRefreshLayout.setRefreshViewHolder(new BGACtripRefreshViewHolder(this, true));
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
