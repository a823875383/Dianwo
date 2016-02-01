package com.jsqix.dianwo.view.bga.jd;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.AnimRes;
import android.support.annotation.DrawableRes;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsqix.dianwo.R;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by dq on 2016/1/14.
 */
public class BGAJDRefreshViewHolder extends BGARefreshViewHolder {
    private ImageView mPullDownView;
    private ImageView mReleaseRefreshingView;
    private TextView mHeaderHintView;

    private AnimationDrawable mChangeToReleaseRefreshAd;
    private AnimationDrawable mRefreshingAd;

    private int mPullDownImageResId;
    private int mChangeToReleaseRefreshAnimResId;
    private int mRefreshingAnimResId;

    private String mPullDownRefreshText = "下拉可以刷新";
    private String mReleaseRefreshText = "松开后刷新";
    private String mRefreshingText = "正在加载中...";

    /**
     * @param context
     * @param isLoadingMoreEnabled 上拉加载更多是否可用
     */
    public BGAJDRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
    }

    /**
     * 设置下拉过程中的图片资源
     *
     * @param resId
     */
    public void setPullDownImageResource(@DrawableRes int resId) {
        mPullDownImageResId = resId;
    }

    /**
     * 设置进入释放刷新状态时的动画资源
     *
     * @param resId
     */
    public void setChangeToReleaseRefreshAnimResId(@AnimRes int resId) {
        mChangeToReleaseRefreshAnimResId = resId;
    }

    /**
     * 设置正在刷新时的动画资源
     *
     * @param resId
     */
    public void setRefreshingAnimResId(@AnimRes int resId) {
        mRefreshingAnimResId = resId;
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, R.layout.view_refresh_header_bga_jd, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }
            mPullDownView = (ImageView) mRefreshHeaderView.findViewById(R.id.pull_to_refresh_people);
            mReleaseRefreshingView = (ImageView) mRefreshHeaderView.findViewById(R.id.pull_to_refresh_goods);
            mHeaderHintView = (TextView) mRefreshHeaderView.findViewById(R.id.tv_jd_header_hint);
            mHeaderHintView.setText(mPullDownRefreshText);
           /* if (mPullDownImageResId != -1) {
                mPullDownView.setImageResource(R.mipmap.app_refresh_people_0);
            } else {
                throw new RuntimeException("请调用" + BGAJDRefreshViewHolder.class.getSimpleName() + "的setPullDownImageResource方法设置下拉过程中的图片资源");
            }
            if (mChangeToReleaseRefreshAnimResId != -1) {
                mReleaseRefreshingView.setImageResource(R.mipmap.app_refresh_goods_0);
            } else {
                throw new RuntimeException("请调用" + BGAJDRefreshViewHolder.class.getSimpleName() + "的setChangeToReleaseRefreshAnimResId方法设置进入释放刷新状态时的动画资源");
            }*/
            if (mRefreshingAnimResId != -1) {

            } else {
                throw new RuntimeException("请调用" + BGAJDRefreshViewHolder.class.getSimpleName() + "的setRefreshingAnimResId方法设置正在刷新时的动画资源");
            }
        }
        return mRefreshHeaderView;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
//        if (scale <= 1.0f) {
//            scale = 0.1f + 0.9f * scale;
//            ViewCompat.setScaleX(mPullDownView, scale);
//            ViewCompat.setPivotY(mPullDownView, mPullDownView.getHeight());
//            ViewCompat.setScaleY(mPullDownView, scale);
//        }
        scale = scale > 1.0f ? 1.0f : scale;
        mPullDownView.setImageResource(R.mipmap.app_refresh_people_0);
        mReleaseRefreshingView.setImageResource(R.mipmap.app_refresh_goods_0);
        if (mReleaseRefreshingView.getVisibility() != View.VISIBLE) {
            mReleaseRefreshingView.setVisibility(View.VISIBLE);
        }

        //透明度动画
        ObjectAnimator animAlphaP = ObjectAnimator.ofFloat(mPullDownView, "alpha", -1, 1).setDuration(300);
        animAlphaP.setCurrentPlayTime((long) (scale * 300));
        ObjectAnimator animAlphaG = ObjectAnimator.ofFloat(mReleaseRefreshingView, "alpha", -1, 1).setDuration(300);
        animAlphaG.setCurrentPlayTime((long) (scale * 300));

        //缩放动画
        ViewHelper.setPivotX(mPullDownView, 0);  // 设置中心点
        ViewHelper.setPivotY(mPullDownView, 0);
        ObjectAnimator animPX = ObjectAnimator.ofFloat(mPullDownView, "scaleX", 0, 1).setDuration(300);
        animPX.setCurrentPlayTime((long) (scale * 300));
        ObjectAnimator animPY = ObjectAnimator.ofFloat(mPullDownView, "scaleY", 0, 1).setDuration(300);
        animPY.setCurrentPlayTime((long) (scale * 300));

        ViewHelper.setPivotX(mReleaseRefreshingView, mReleaseRefreshingView.getMeasuredWidth());
        ObjectAnimator animGX = ObjectAnimator.ofFloat(mReleaseRefreshingView, "scaleX", 0, 1).setDuration(300);
        animGX.setCurrentPlayTime((long) (scale * 300));
        ObjectAnimator animGY = ObjectAnimator.ofFloat(mReleaseRefreshingView, "scaleY", 0, 1).setDuration(300);
        animGY.setCurrentPlayTime((long) (scale * 300));
    }

    @Override
    public void changeToIdle() {
        stopChangeToReleaseRefreshAd();
        stopRefreshingAd();

        mPullDownView.setVisibility(View.VISIBLE);
        mReleaseRefreshingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void changeToPullDown() {
        mHeaderHintView.setText(mPullDownRefreshText);
        mPullDownView.setVisibility(View.VISIBLE);
        mReleaseRefreshingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void changeToReleaseRefresh() {
        mHeaderHintView.setText(mReleaseRefreshText);
//        mReleaseRefreshingView.setImageResource(mChangeToReleaseRefreshAnimResId);
//        mChangeToReleaseRefreshAd = (AnimationDrawable) mReleaseRefreshingView.getDrawable();

        mPullDownView.setVisibility(View.VISIBLE);
        mReleaseRefreshingView.setVisibility(View.VISIBLE);

//        mChangeToReleaseRefreshAd.start();
    }

    @Override
    public void changeToRefreshing() {
        stopChangeToReleaseRefreshAd();
        mHeaderHintView.setText(mRefreshingText);
        mReleaseRefreshingView.setImageResource(mRefreshingAnimResId);
        mRefreshingAd = (AnimationDrawable) mReleaseRefreshingView.getDrawable();

        mReleaseRefreshingView.setVisibility(View.VISIBLE);
        mPullDownView.setVisibility(View.INVISIBLE);

        mRefreshingAd.start();
    }

    @Override
    public void onEndRefreshing() {
        mReleaseRefreshingView.setVisibility(View.VISIBLE);
        mPullDownView.setVisibility(View.INVISIBLE);
//        mHeaderHintView.setText(mPullDownRefreshText);
        stopChangeToReleaseRefreshAd();
        stopRefreshingAd();
    }

    private void stopRefreshingAd() {
        if (mRefreshingAd != null) {
            mRefreshingAd.stop();
            mRefreshingAd = null;
        }
    }

    private void stopChangeToReleaseRefreshAd() {
        if (mChangeToReleaseRefreshAd != null) {
            mChangeToReleaseRefreshAd.stop();
            mChangeToReleaseRefreshAd = null;
        }
    }
}
