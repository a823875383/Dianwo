package com.jsqix.dianwo.view.bga.autohome;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsqix.dianwo.R;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by dq on 2016/1/18.
 */
public class BGAAutoHomeRefreshViewHolder extends BGARefreshViewHolder {

    private TextView mHeaderText;
    private TextView mSubHeaderText;
    private ImageView mDialImage;
    private ImageView mPointerImage;

    private Animation mAnimPointer;

    private String mPullDownRefreshText = "下拉刷新";
    private String mReleaseRefreshText = "松开后刷新";
    private String mRefreshingText = "正在加载中...";

    public BGAAutoHomeRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
        mAnimPointer = AnimationUtils.loadAnimation(context, R.anim.pointer_rotate);
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, R.layout.view_refresh_header_bga_autohome, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }
            mDialImage = (ImageView) mRefreshHeaderView.findViewById(R.id.pull_to_refresh_dial);
            mPointerImage = (ImageView) mRefreshHeaderView.findViewById(R.id.pull_to_refresh_pointer);
            mHeaderText = (TextView) mRefreshHeaderView.findViewById(R.id.pull_to_refresh_text);
            mSubHeaderText = (TextView) mRefreshHeaderView.findViewById(R.id.pull_to_refresh_sub_text);
        }
        return mRefreshHeaderView;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {
        if (scale < 0.7f) scale = 0.7f;
        if (scale > 1.0f) scale = 1.0f;

        //旋转动画
        ViewHelper.setPivotX(mPointerImage, mPointerImage.getMeasuredWidth() / 2);  // 设置中心点
        ViewHelper.setPivotY(mPointerImage, mPointerImage.getMeasuredHeight() / 2);
        ObjectAnimator animPY = ObjectAnimator.ofFloat(mPointerImage, "rotation", 0, 250).setDuration(300);
        animPY.setCurrentPlayTime((long) (scale * 1000 - 700));
    }

    @Override
    public void changeToIdle() {
        mHeaderText.setText(mPullDownRefreshText);
        mPointerImage.clearAnimation();
    }

    @Override
    public void changeToPullDown() {
        mHeaderText.setText(mPullDownRefreshText);
    }

    @Override
    public void changeToReleaseRefresh() {
        mHeaderText.setText(mReleaseRefreshText);
    }

    @Override
    public void changeToRefreshing() {
        mHeaderText.setText(mRefreshingText);
        mPointerImage.startAnimation(mAnimPointer);
    }

    @Override
    public void onEndRefreshing() {
        mHeaderText.setText(mPullDownRefreshText);
        mPointerImage.clearAnimation();
    }

}
