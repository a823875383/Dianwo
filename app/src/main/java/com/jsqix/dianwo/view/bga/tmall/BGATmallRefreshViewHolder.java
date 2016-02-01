package com.jsqix.dianwo.view.bga.tmall;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AnimRes;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsqix.dianwo.R;

import cn.bingoogolapple.refreshlayout.BGAMeiTuanRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by dq on 2016/1/14.
 */
public class BGATmallRefreshViewHolder extends BGARefreshViewHolder {
    private TextView mTMallHeaderText;
    private ImageView mTMallRefreshView;

    private AnimationDrawable mChangeToReleaseRefreshAd;
    private AnimationDrawable mRefreshingAd;

    private int mPullDownImageResId = -1;
    private int mChangeToReleaseRefreshAnimResId = -1;
    private int mRefreshingAnimResId = -1;

    private String mPullDownRefreshText = "下拉刷新";
    private String mReleaseRefreshText = "松开立即更新";
    private String mRefreshingText = "正在刷新...";

    public BGATmallRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
    }

    public void setPullDownImageResource(@DrawableRes int resId) {
        this.mPullDownImageResId = resId;
    }

    public void setChangeToReleaseRefreshAnimResId(@AnimRes int resId) {
        this.mChangeToReleaseRefreshAnimResId = resId;
    }

    public void setRefreshingAnimResId(@AnimRes int resId) {
        this.mRefreshingAnimResId = resId;
    }

    @Override
    public View getRefreshHeaderView() {
        if (this.mRefreshHeaderView == null) {
            this.mRefreshHeaderView = View.inflate(this.mContext, R.layout.view_refresh_header_bga_tmall, (ViewGroup) null);
            this.mRefreshHeaderView.setBackgroundColor(0);
            if (this.mRefreshViewBackgroundColorRes != -1) {
                this.mRefreshHeaderView.setBackgroundResource(this.mRefreshViewBackgroundColorRes);
            }

            if (this.mRefreshViewBackgroundDrawableRes != -1) {
                this.mRefreshHeaderView.setBackgroundResource(this.mRefreshViewBackgroundDrawableRes);
            }

            this.mTMallRefreshView = (ImageView) this.mRefreshHeaderView.findViewById(R.id.pull_to_refresh_bike);
            this.mTMallHeaderText = (TextView) this.mRefreshHeaderView.findViewById(R.id.pull_to_refresh_text);
            if (this.mPullDownImageResId == -1) {
                throw new RuntimeException("请调用" + BGAMeiTuanRefreshViewHolder.class.getSimpleName() + "的setPullDownImageResource方法设置下拉过程中的图片资源");
            }

            this.mTMallRefreshView.setImageResource(this.mPullDownImageResId);
            if (this.mChangeToReleaseRefreshAnimResId == -1) {
                throw new RuntimeException("请调用" + BGAMeiTuanRefreshViewHolder.class.getSimpleName() + "的setChangeToReleaseRefreshAnimResId方法设置进入释放刷新状态时的动画资源");
            }

            this.mTMallRefreshView.setImageResource(this.mChangeToReleaseRefreshAnimResId);
            if (this.mRefreshingAnimResId == -1) {
                throw new RuntimeException("请调用" + BGAMeiTuanRefreshViewHolder.class.getSimpleName() + "的setRefreshingAnimResId方法设置正在刷新时的动画资源");
            }

            this.mTMallRefreshView.setImageResource(this.mRefreshingAnimResId);
            mTMallHeaderText.setText(mPullDownRefreshText);
        }

        return this.mRefreshHeaderView;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
//        if (scale <= 1.0F) {
//            scale = 0.1F + 0.9F * scale;
//            ViewCompat.setScaleX(this.mTMallRefreshView, scale);
//            ViewCompat.setPivotY(this.mTMallRefreshView, (float) this.mTMallRefreshView.getHeight());
//            ViewCompat.setScaleY(this.mTMallRefreshView, scale);
//        }
    }

    @Override
    public void changeToIdle() {
        this.stopChangeToReleaseRefreshAd();
        this.stopRefreshingAd();
        mTMallHeaderText.setText(mPullDownRefreshText);
    }

    @Override
    public void changeToPullDown() {
        mTMallHeaderText.setText(mPullDownRefreshText);
        this.mTMallRefreshView.setImageResource(this.mPullDownImageResId);
    }

    @Override
    public void changeToReleaseRefresh() {
        mTMallHeaderText.setText(mReleaseRefreshText);
        this.mTMallRefreshView.setImageResource(this.mChangeToReleaseRefreshAnimResId);
        this.mChangeToReleaseRefreshAd = (AnimationDrawable) this.mTMallRefreshView.getDrawable();
        this.mChangeToReleaseRefreshAd.start();
    }

    @Override
    public void changeToRefreshing() {
        mTMallHeaderText.setText(mRefreshingText);
        this.stopChangeToReleaseRefreshAd();
        this.mTMallRefreshView.setImageResource(this.mRefreshingAnimResId);
        this.mRefreshingAd = (AnimationDrawable) this.mTMallRefreshView.getDrawable();
        this.mRefreshingAd.start();
    }

    @Override
    public void onEndRefreshing() {
        this.stopChangeToReleaseRefreshAd();
        this.stopRefreshingAd();
        mTMallRefreshView.setImageResource(R.mipmap.tm_mui_bike_0);
    }

    private void stopRefreshingAd() {
        if (this.mRefreshingAd != null) {
            this.mRefreshingAd.stop();
            this.mRefreshingAd = null;
        }

    }

    private void stopChangeToReleaseRefreshAd() {
        if (this.mChangeToReleaseRefreshAd != null) {
            this.mChangeToReleaseRefreshAd.stop();
            this.mChangeToReleaseRefreshAd = null;
        }

    }
}
