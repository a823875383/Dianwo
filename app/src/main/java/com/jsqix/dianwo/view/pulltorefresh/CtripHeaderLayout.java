package com.jsqix.dianwo.view.pulltorefresh;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;
import com.jsqix.dianwo.R;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.view.ViewHelper;

/**
 * Created by dq on 2016/1/18.
 */
public class CtripHeaderLayout extends LoadingLayoutBase {

    private FrameLayout mInnerLayout;
    private TextView mHeaderText;
    private ImageView mRoundImage;
    private ImageView mFlightImage;

    private CharSequence mPullLabel = "下拉刷新";
    private CharSequence mReleaseLabel = "松开立即更新";
    private CharSequence mRefreshingLabel = "正在刷新...";

    public CtripHeaderLayout(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_refresh_header_bga_ctrip, this);
        mInnerLayout = (FrameLayout) findViewById(R.id.fl_inner);
        mHeaderText = (TextView) mInnerLayout.findViewById(R.id.pull_to_refresh_text);
        mRoundImage = (ImageView) mInnerLayout.findViewById(R.id.pull_to_refresh_round);
        mFlightImage = (ImageView) mInnerLayout.findViewById(R.id.pull_to_refresh_flight);

        LayoutParams lp = (LayoutParams) mInnerLayout.getLayoutParams();
        lp.gravity = Gravity.BOTTOM;
        reset();
    }

    @Override
    public int getContentSize() {
        // 设置未完全显示的时候就促发刷新动作
        return mInnerLayout.getHeight() * 7 / 10;
    }

    @Override
    public void pullToRefresh() {
        mHeaderText.setText(mPullLabel);
    }

    @Override
    public void releaseToRefresh() {
        mHeaderText.setText(mReleaseLabel);
    }

    @Override
    public void onPull(float scale) {
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
    public void refreshing() {
        mHeaderText.setText(mRefreshingLabel);
    }

    @Override
    public void reset() {

    }

    @Override
    public void setPullLabel(CharSequence pullLabel) {
        mPullLabel = pullLabel;
    }

    @Override
    public void setRefreshingLabel(CharSequence refreshingLabel) {
        mRefreshingLabel = refreshingLabel;
    }

    @Override
    public void setReleaseLabel(CharSequence releaseLabel) {
        mReleaseLabel = releaseLabel;
    }
}
