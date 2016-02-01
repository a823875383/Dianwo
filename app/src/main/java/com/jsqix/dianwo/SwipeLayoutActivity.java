package com.jsqix.dianwo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;
import me.maxwin.view.XListView;

@ContentView(R.layout.activity_swipe_layout)
public class SwipeLayoutActivity extends ToolBarActivity implements IXListViewRefreshListener, IXListViewLoadMore {
    @ViewInject(R.id.list_view)
    private XListView listView;
    private List<String> data = new ArrayList<String>();
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("SwipeLayout滑动菜单");
//        setSwipeBackEnable(false);
        initView();
    }

    private void initView() {
        listView.setPullLoadEnable(this);
        listView.setPullRefreshEnable(this);
        for (int i = 0; i < 15 + 1; i++) {
            data.add(i + "");
        }
        adapter = new ListViewAdapter(this, data);
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
    class ListViewAdapter extends BaseSwipeAdapter{
        List<String> data;
        Context mContext;

        public ListViewAdapter(Context context, List<String> objects) {
            this.mContext = context;
            this.data = objects;
        }

        @Override
        public View generateView(int position, ViewGroup parent) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.swipe_layout_item, null);
            SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
            swipeLayout.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {

                }
            });
            swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
                @Override
                public void onDoubleClick(SwipeLayout layout, boolean surface) {
                    Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
                }
            });
            v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();
                }
            });
            return v;
        }

        @Override
        public void fillValues(int position, View convertView) {

        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe_layout;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

}
