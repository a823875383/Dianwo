package com.jsqix.dianwo;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jsqix.dianwo.base.ToolBarActivity;
import com.jsqix.dianwo.view.ScaleImageView;
import com.youxiachai.onexlistview.XMultiColumnListView;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import me.maxwin.view.IXListViewLoadMore;
import me.maxwin.view.IXListViewRefreshListener;

@ContentView(R.layout.activity_xmulti_column)
public class XMultiColumnActivity extends ToolBarActivity implements IXListViewRefreshListener, IXListViewLoadMore {
    @ViewInject(R.id.mlist_view)
    private XMultiColumnListView listView;
    private List<DuitangInfo> data = new ArrayList<DuitangInfo>();
    private multiArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("瀑布流刷新");
        initView();
    }

    private void initView() {
        listView.setPullLoadEnable(this);
        listView.setPullRefreshEnable(this);
        for (int i = 0; i < 10; i++) {
            DuitangInfo info = new DuitangInfo();
            info.setSrcUrl("http://static.oschina.net/uploads/user/585/1171837_100.jpg?t=1371957136000");
            info.setWidth(300);
            if (i == 0) {
                info.setHeight(250);
            } else {
                info.setHeight(400);
            }
            data.add(info);
        }
        adapter = new multiArrayAdapter(this, data);
        listView.setAdapter(adapter);
    }

    @Override
    public void onLoadMore() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.stopLoadMore();
            }
        }, 2000);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                listView.stopRefresh("");
            }
        }, 2000);

    }

    class DuitangInfo {
        private String srcUrl;
        private int width;
        private int height;

        public String getSrcUrl() {
            return srcUrl;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public void setSrcUrl(String srcUrl) {
            this.srcUrl = srcUrl;
        }

        public void setWidth(int width) {
            this.width = width;
        }

        public void setHeight(int height) {
            this.height = height;
        }
    }

    class multiArrayAdapter extends ArrayAdapter<DuitangInfo> {
        List<DuitangInfo> data;
        Context context;

        public multiArrayAdapter(Context context, List<DuitangInfo> objects) {
            super(context, 0, 0, objects);
            this.context = context;
            this.data = objects;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.multi_column_list, null);
                holder = new ViewHolder();
                holder.imageView = (ScaleImageView) convertView.findViewById(R.id.news_pic);
                holder.contentView = (TextView) convertView.findViewById(R.id.news_title);
                holder.timeView = (TextView) convertView.findViewById(R.id.news_time);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            DuitangInfo info = data.get(position);
            holder.imageView.setImageWidth(info.getWidth());
            holder.imageView.setImageHeight(info.getHeight());
            x.image().bind(holder.imageView, info.getSrcUrl());
            return convertView;
        }

        class ViewHolder {
            ScaleImageView imageView;
            TextView contentView;
            TextView timeView;
        }
    }
}
