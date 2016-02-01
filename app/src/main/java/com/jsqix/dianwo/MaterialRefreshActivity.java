package com.jsqix.dianwo;

import android.os.Bundle;
import android.os.Handler;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_material_refresh)
public class MaterialRefreshActivity extends ToolBarActivity {
    @ViewInject(R.id.refreshLayout)
    private MaterialRefreshLayout mRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Material刷新控件");
        initRefreshLayout();
    }

    private void initRefreshLayout() {
        mRefreshLayout.setWaveColor(0x000);
        mRefreshLayout.setIsOverLay(true);
        mRefreshLayout.setWaveShow(true);
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.finishRefresh();
                    }
                }, 2000);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mRefreshLayout.finishRefreshLoadMore();
                    }
                }, 2000);
            }
        });
    }


}
