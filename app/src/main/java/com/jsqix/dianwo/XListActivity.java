package com.jsqix.dianwo;

import android.os.Bundle;
import android.os.Handler;

import com.jsqix.dianwo.base.ToolBarActivity;
import com.simple.commonadapter.ListViewAdapter;
import com.simple.commonadapter.viewholders.GodViewHolder;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

@ContentView(R.layout.activity_xlist)
public class XListActivity extends ToolBarActivity implements IXListViewRefreshListener, IXListViewLoadMore {
    @ViewInject(R.id.list_view)
    private XListView listView;
    private List<String> data = new ArrayList<String>();
    private ListViewAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("XList刷新控件");
        initView();
    }

    private void initView() {
        listView.setPullLoadEnable(this);
        listView.setPullRefreshEnable(this);
        for (int i = 0; i < 15 + 1; i++) {
            data.add(i + "");
        }
        adapter = new ListViewAdapter<String>(android.R.layout.simple_list_item_1, data) {
            @Override
            protected void onBindData(GodViewHolder viewHolder, int position, String item) {
                viewHolder.setText(android.R.id.text1, item);
            }
        };
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoadMore() {
        int size = data.size();
        for (int i = 0; i < new Random().nextInt(20) + 1; i++) {
            data.add((size + i) + "");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.stopLoadMore();
                adapter.notifyDataSetChanged();
            }
        }, 2000);
    }

    @Override
    public void onRefresh() {
        data.clear();
        for (int i = 0; i < new Random().nextInt(15) + 1; i++) {
            data.add(i + "");
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.stopRefresh("");
                adapter.notifyDataSetChanged();
            }
        }, 2000);

    }
}
