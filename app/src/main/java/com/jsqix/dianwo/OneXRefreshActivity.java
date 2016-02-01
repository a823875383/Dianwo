package com.jsqix.dianwo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jsqix.dianwo.base.ToolBarActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;

@ContentView(R.layout.activity_one_xrefresh)
public class OneXRefreshActivity extends ToolBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("OneXList刷新控件");
    }

    @Event(value = {R.id.xlist_view, R.id.xmulti_view}, type = View.OnClickListener.class)
    private void click(View v) {
        switch (v.getId()) {
            case R.id.xlist_view:
                startActivity(new Intent(this, XListActivity.class));
                break;
            case R.id.xmulti_view:
                startActivity(new Intent(this, XMultiColumnActivity.class));
                break;
            default:
                break;
        }
    }
}
