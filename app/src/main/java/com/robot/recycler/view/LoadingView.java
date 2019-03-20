package com.robot.recycler.view;

import android.animation.Animator;
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
    private float mAnimateRotate = 0;
    private ValueAnimator mPopAnimator;
    private ValueAnimator mRotateAnimator;
    private boolean mIsAnimationStarted = false;

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

    private void init() {
        mMaxRadius = TRecyclerUtils.dip2px(getContext(), 4);
        mMiniRadius = TRecyclerUtils.dip2px(getContext(), 2);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        reset();
        setAnimateRadius(0);
        setAnimateRotate(0);
        applyTheme();
    }

    private void reset() {
        mAnimateRadius = mMiniRadius;
        mAnimateRotate = 0;
    }

    public void setAnimateRadius(float radius) {
        if (radius != mAnimateRadius) {
            mAnimateRadius = radius;
            invalidate();
        }
    }

    public void setAnimateRotate(float rotate) {
        if (rotate != mAnimateRotate) {
            mAnimateRotate = rotate;
            invalidate();
        }
    }

    public void start() {
        if (!mIsAnimationStarted) {
            mIsAnimationStarted = true;
            startPop();
        }
    }

    private void startPop() {
        if (mPopAnimator != null && mPopAnimator.isRunning()) {
            mPopAnimator.cancel();
        }
        if (mRotateAnimator != null && mRotateAnimator.isRunning()) {
            mRotateAnimator.cancel();
        }
        mPopAnimator = ObjectAnimator.ofFloat(this, "animateRadius", mMiniRadius, mMaxRadius, mMiniRadius);
        mPopAnimator.setDuration(500);
        mPopAnimator.setRepeatCount(0);
        mPopAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mIsAnimationStarted) {
                    startRotate();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mPopAnimator.start();
    }

    private void startRotate() {
        if (mPopAnimator != null && mPopAnimator.isRunning()) {
            mPopAnimator.cancel();
        }
        if (mRotateAnimator != null && mRotateAnimator.isRunning()) {
            mRotateAnimator.cancel();
        }
        mRotateAnimator = ObjectAnimator.ofFloat(this, "animateRotate", mAnimateRotate, mAnimateRotate + 180);
        mRotateAnimator.setDuration(200);
        mRotateAnimator.setRepeatCount(0);
        mRotateAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (mIsAnimationStarted) {
                    if (mAnimateRotate >= 360) {
                        mAnimateRotate -= 360;
                    }
                    startPop();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mRotateAnimator.start();
    }

    public void stop() {
        mIsAnimationStarted = false;
        if (mPopAnimator != null && mPopAnimator.isRunning()) {
            mPopAnimator.cancel();
        }
        if (mRotateAnimator != null && mRotateAnimator.isRunning()) {
            mRotateAnimator.cancel();
        }
        reset();
    }

    @Override
    protected void onDraw(Canvas canvas) {
      /*  if (mPopAnimator == null && mRotateAnimator == null) {
            // animation never started, dont draw
            return;
        }*/
        int width = canvas.getWidth();
        if (width != 0) {
            mFirstCenterX = width / 2;
            mSecondCenterX = width / 2;
        }
        super.onDraw(canvas);
        try {
            int saveCount = canvas.save();
            canvas.rotate(mAnimateRotate, (mFirstCenterX + mSecondCenterX) / 2, (mFirstCenterY + mSecondCenterY) / 2);
            canvas.drawCircle(mFirstCenterX, mFirstCenterY, mAnimateRadius, mPaint);
            canvas.drawCircle(mSecondCenterX, mSecondCenterY, mMiniRadius + mMaxRadius - mAnimateRadius, mPaint);
            canvas.restoreToCount(saveCount);
        } catch (Exception e) {

        }
    }

    public void applyTheme() {
        int mColor = getContext().getResources().getColor(R.color.red, null);
        mPaint.setColor(mColor);
//        invalidate();
    }

    public void applyTheme(int color) {
        int mColor = getContext().getResources().getColor(color, null);
        mPaint.setColor(mColor);
//        invalidate();
    }



}
