package com.robot.recycler.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.robot.recycler.R;
import com.robot.recycler.TRecyclerUtils;

/**
 *
 *
 * @author xing.hu
 * @since 2018/7/19, 下午5:16
 *  下拉刷新的LoadingView
 */

public class LoadingView extends View {
    private Paint mPaint;
    private float mAnimateRadius = 0;
    private float mMaxRadius = 0;
    private float mMiniRadius = 0;
    private ValueAnimator mAnimator;

    private int mFirstCenterX;
    private int mFirstCenterY = 15;
    private int mSecondCenterX;
    private int mSecondCenterY = 45;
    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init(){
        mMaxRadius = TRecyclerUtils.dip2px(getContext(), 4);
        mMiniRadius = TRecyclerUtils.dip2px(getContext(), 2);
        mAnimateRadius = mMiniRadius;
        mPaint = new Paint();
        int mColor = getContext().getResources().getColor(R.color.red, null);
        mPaint.setColor(mColor);
        mPaint.setAntiAlias(true);
        mAnimator = ValueAnimator.ofFloat(mMiniRadius, mMaxRadius, mMiniRadius);
    }

    public void setAnimateRadius(float radius) {
        if (radius != mAnimateRadius) {
            mAnimateRadius = radius;
            invalidate();
        }
    }

    public void start(){
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
        mAnimator = ObjectAnimator.ofFloat(this, "animateRadius", mMiniRadius, mMaxRadius, mMiniRadius);
        mAnimator.setDuration(500).setRepeatCount(ValueAnimator.INFINITE);
        mAnimator.start();
    }

    /**
     * 动画停止
     */
    public void stop(){
        if (mAnimator != null && mAnimator.isRunning()) {
            mAnimator.cancel();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mAnimator == null) {
            // animation never started dont draw
            return;
        }
        int width = canvas.getWidth();
        if(width != 0){
            mFirstCenterX = width / 2;
            mSecondCenterX = width / 2;
        }
        super.onDraw(canvas);
        canvas.drawCircle(mFirstCenterX, mFirstCenterY, mAnimateRadius, mPaint);
        canvas.drawCircle(mSecondCenterX, mSecondCenterY, mMiniRadius + mMaxRadius - mAnimateRadius, mPaint);
    }

    public void applyTheme(){
        int mColor = getContext().getResources().getColor(R.color.red, null);
        mPaint.setColor(mColor);
        if (mAnimator == null || !mAnimator.isRunning()) {
            invalidate();
        }
    }




}
