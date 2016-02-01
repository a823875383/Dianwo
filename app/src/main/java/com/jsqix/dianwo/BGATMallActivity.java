package com.jsqix.dianwo;

import android.os.Bundle;
import android.os.Handler;

import com.jsqix.dianwo.base.ToolBarActivity;
import com.jsqix.dianwo.view.bga.tmall.BGATmallRefreshViewHolder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import cn.bingoogolapple.refreshlayout.BGARefreshLayout;

@ContentView(R.layout.activity_bga_tmall)
public class BGATMallActivity extends ToolBarActivity implements BGARefreshLayout.BGARefreshLayoutDelegate {
    @ViewInject(R.id.refreshLayout)
    private BGARefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("天猫样式");
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        // 为BGARefreshLayout设置代理
        mRefreshLayout.setDelegate(this);
        // 设置下拉刷新和上拉加载更多的风格     参数1：应用程序上下文，参数2：是否具有上拉加载更多功能
        BGATmallRefreshViewHolder refreshViewHolder = new BGATmallRefreshViewHolder(this, true);
        refreshViewHolder.setPullDownImageResource(R.mipmap.tm_mui_bike_0);
        refreshViewHolder.setRefreshingAnimResId(R.anim.refresh_tmall_header_refreshing);
        refreshViewHolder.setChangeToReleaseRefreshAnimResId(R.anim.refresh_tmall_header_refreshing);
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
