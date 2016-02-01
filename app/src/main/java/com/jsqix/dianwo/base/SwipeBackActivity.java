package com.jsqix.dianwo.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jsqix.dianwo.utils.DensityUtil;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;

/**
 * Created by dq on 2016/1/19.
 */
public class SwipeBackActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Slidr.attach(this);
    }

    public void setSwipeBackEnable(boolean enable) {
        SlidrConfig mConfig;
        SlidrConfig.Builder builder = new SlidrConfig.Builder();
        if (enable == false) {
            builder.touchSize(0);
        } else {
            builder.touchSize(DensityUtil.dip2px(this, 50));
        }
        mConfig = builder.build();
        Slidr.attach(this, mConfig);
    }
}
