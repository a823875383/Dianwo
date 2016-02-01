package com.jsqix.dianwo.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.jsqix.dianwo.R;
import com.jsqix.dianwo.utils.ToolBarHelper;
import com.romainpiel.shimmer.Shimmer;
import com.romainpiel.shimmer.ShimmerTextView;

public class ToolBarActivity extends BaseActivity {

    private ToolBarHelper mToolBarHelper;
    public Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(int layoutResID) {
        mToolBarHelper = new ToolBarHelper(this, layoutResID);
        toolbar = mToolBarHelper.getToolBar();
        setContentView(mToolBarHelper.getContentView());
        /*把 toolbar 设置到Activity 中*/
        setSupportActionBar(toolbar);
        /*自定义的一些操作*/
        onCreateCustomToolBar(toolbar);
    }

    public void onCreateCustomToolBar(Toolbar toolbar) {
        toolbar.setContentInsetsRelative(0, 0);
        toolbar.setNavigationIcon(R.mipmap.icon_back);
        toolbar.showOverflowMenu();
        getLayoutInflater().inflate(R.layout.toobar_custom, toolbar);
    }

    public void setTitle(String str) {
        ShimmerTextView title = (ShimmerTextView) toolbar.findViewById(R.id.title_text);
        title.setText(str);
        title.setReflectionColor(R.color.colorPrimary);
        Shimmer shimmer=new Shimmer();
        shimmer.start(title);
    }

    public void setRightText(String str) {
        TextView right = (TextView) toolbar.findViewById(R.id.right_btn);
        right.setText(str);
    }

    public void setRightDrawble(int sid) {
        TextView right = (TextView) toolbar.findViewById(R.id.right_btn);
        right.setBackgroundResource(sid);
    }

    public void setRightClick(View.OnClickListener ocl) {
        TextView right = (TextView) toolbar.findViewById(R.id.right_btn);
        right.setOnClickListener(ocl);
    }

    /**
     * 返回事件
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
