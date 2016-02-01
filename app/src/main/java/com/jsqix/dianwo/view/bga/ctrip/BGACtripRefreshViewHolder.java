package com.jsqix.dianwo.view.bga.ctrip;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsqix.dianwo.R;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * Created by dq on 2016/1/18.
 */
public class BGACtripRefreshViewHolder extends BGARefreshViewHolder {
    private TextView mHeaderText;
    private ImageView mRoundImage;
    private ImageView mFlightImage;

    private String mPullDownRefreshText = "下拉更新";
    private String mReleaseRefreshText = "松开立即更新";
    private String mRefreshingText = "更新中...";

    public BGACtripRefreshViewHolder(Context context, boolean isLoadingMoreEnabled) {
        super(context, isLoadingMoreEnabled);
    }

    @Override
    public View getRefreshHeaderView() {
        if (mRefreshHeaderView == null) {
            mRefreshHeaderView = View.inflate(mContext, R.layout.view_refresh_header_bga_ctrip, null);
            mRefreshHeaderView.setBackgroundColor(Color.TRANSPARENT);
            if (mRefreshViewBackgroundColorRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundColorRes);
            }
            if (mRefreshViewBackgroundDrawableRes != -1) {
                mRefreshHeaderView.setBackgroundResource(mRefreshViewBackgroundDrawableRes);
            }
            mRoundImage = (ImageView) mRefreshHeaderView.findViewById(R.id.pull_to_refresh_round);
            mFlightImage = (ImageView) mRefreshHeaderView.findViewById(R.id.pull_to_refresh_flight);
            mHeaderText = (TextView) mRefreshHeaderView.findViewById(R.id.pull_to_refresh_text);
        }
        return mRefreshHeaderView;
    }

    @Override
    public void handleScale(float scale, int moveYDistance) {

        //旋转动画
        ViewHelper.setPivotX(mRoundImage, mRoundImage.getMeasuredWidth() / 2);  // 设置中心点
        ViewHelper.setPivotY(mRoundImage, mRoundImage.getMeasuredHeight() / 2);
        ObjectAnimator animPX = ObjectAnimator.ofFloat(mRoundImage, "rotation", 0, 359).setDuration(500);
        animPX.setCurrentPlayTime((long) (scale * 250));

        ViewHelper.setPivotX(mFlightImage, mFlightImage.getMeasuredWidth() / 2);  // 设置中心点
        ViewHelper.setPivotY(mFlightImage, mFlightImage.getMeasuredHeight() / 2);
        ObjectAnimator animPY = ObjectAnimator.ofFloat(mFlightImage, "rotation", 0, -359).setDuration(500);
        animPY.setCurrentPlayTime((long) (scale * 250));
    }

    @Override
    public void changeToIdle() {
        mHeaderText.setText(mPullDownRefreshText);
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
    }

    @Override
    public void onEndRefreshing() {
        mHeaderText.setText(mPullDownRefreshText);
    }
}
