package com.jsqix.dianwo.base;

import android.app.Application;

import com.jsqix.dianwo.BuildConfig;
import com.jsqix.dianwo.utils.FrescoUtils;

import org.xutils.x;

/**
 * Created by wyouflf on 15/10/28.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        FrescoUtils.initConfig(this);
    }
}
