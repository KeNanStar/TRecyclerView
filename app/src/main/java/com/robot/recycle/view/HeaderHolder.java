package com.robot.recycle.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.robot.recycle.R;
import com.robot.recycle.TRecycleUtils;
import com.robot.recycle.listener.IAnimListener;


public class HeaderHolder {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_LOADING = STATE_NORMAL + 1;
    public final static int STATE_TIPS = STATE_LOADING + 1;
    public final static int STATE_RELEASE = STATE_TIPS + 1;
    public final static int STATE_HIDE_LOAD = STATE_RELEASE + 1;
    public final static int STATE_SHOW_LOAD = STATE_HIDE_LOAD + 1;
    public final static int STATE_PULL = STATE_SHOW_LOAD + 1;


    private TextView mLoadText;
    private LoadingView mLoadingView;
    private String mLoadingTip = "";
    private String mNormalTip = "";
    private String mReleaseTip = "";
    private static final int FOOTER_HEIGHT = 55;

    private RelativeLayout mHeaderView;

    private Context mCtx;

    private  TextView mNewsNumTip;

    private AnimatorSet mTipShowAnimator;
    private ValueAnimator hideTipsAnimation;

    private IAnimListener mAnimListener;

    public HeaderHolder(Context context) {
        mCtx = context;
        initView();
    }

    public void setAnimListener(IAnimListener animListener){
        mAnimListener = animListener;
    }


    public void setState(int state,  Object... args) {
        switch (state) {
            case STATE_HIDE_LOAD:
                showLoad(false);
                break;
            case STATE_SHOW_LOAD:
                showLoad(true);
                break;
            case STATE_NORMAL:
                normal();
                break;
            case STATE_PULL:
                pullTip();
                break;
            case STATE_LOADING:
                loading();
                break;
            case STATE_RELEASE:
                release();
                break;
            case STATE_TIPS:
                if(args != null && args.length >=1){
                    if(args[0] instanceof  String){
                        showTips((String)args[0]);
                    }
                }
                break;

        }
    }

    public void showHeaderView(boolean isShow){
        if(mHeaderView != null ){
            if(isShow){
                if(mHeaderView.getVisibility() != View.VISIBLE){
                    mHeaderView.setVisibility(View.VISIBLE);
                }
            }else{
                if(mHeaderView.getVisibility() == View.VISIBLE){
                    mHeaderView.setVisibility(View.INVISIBLE);
                }
            }
        }
    }


    private  void showLoad(boolean isShow){
        if(mHeaderView != null ){
            if(isShow){
                if(mLoadingView.getVisibility() != View.VISIBLE){
                    mLoadingView.setVisibility(View.VISIBLE);
                }
                if(mLoadText.getVisibility() != View.VISIBLE){
                    mLoadText.setVisibility(View.VISIBLE);
                }
            }else{
                if(mLoadingView.getVisibility() == View.VISIBLE){
                    mLoadingView.setVisibility(View.INVISIBLE);
                }
                if(mLoadText.getVisibility() == View.VISIBLE){
                    mLoadText.setVisibility(View.INVISIBLE);
                }
            }
        }
    }



    private void normal() {
        showHeaderView(false);
        mLoadText.setText(mNormalTip);
        mLoadingView.stop();

    }



    private void pullTip() {
        showHeaderView(true);
        mLoadText.setText(mNormalTip);
        mLoadingView.stop();

    }

    private void loading() {
        setState(STATE_SHOW_LOAD);
        showHeaderView(true);
        mLoadText.setText(mLoadingTip);
        mLoadingView.start();

    }


    private void release() {
        setState(STATE_SHOW_LOAD);
        showHeaderView(true);
        mLoadText.setText(mReleaseTip);
        mLoadingView.stop();
    }

    private void showTips(String tip){
        if(!TextUtils.isEmpty(tip)) {
            setState(STATE_SHOW_LOAD);
            showHeaderView(true);
            mLoadText.setText(tip);
            mLoadingView.stop();
        }

    }


    private void initView() {
        mHeaderView = (RelativeLayout) LayoutInflater.from(mCtx).inflate(R.layout.item_header, null);
        mHeaderView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, TRecycleUtils.dip2px(mCtx, FOOTER_HEIGHT)));
        mLoadText = (TextView) mHeaderView.findViewById(R.id.pull_tv);
        mLoadingView = (LoadingView) mHeaderView.findViewById(R.id.pull_load);
        mNewsNumTip = (TextView)mHeaderView.findViewById(R.id.num_tip);
        mLoadingTip = mCtx.getResources().getString(R.string.pull_loading);
        mNormalTip =  mCtx.getResources().getString(R.string.pull_tip);
        mReleaseTip =  mCtx.getResources().getString(R.string.pull_release_tip);

    }


    public void showRefreshTips(String text) {
        mNewsNumTip.setVisibility(View.VISIBLE);
        mNewsNumTip.setText(text);
        showTipAnim();

    }

    protected void showTipAnim(){
        if(mNewsNumTip.getVisibility() == View.INVISIBLE){
            mNewsNumTip.setVisibility(View.VISIBLE);
        }

        float height = mCtx.getResources().getDimension(R.dimen.header_height);
        float width = TRecycleUtils.getScreenWidth(mCtx);

        mNewsNumTip.setPivotX(width / 2);
        mNewsNumTip.setPivotY(height / 2);

        float startScaleX = 0.7f;
        float startScaleY = 0.8f;
        float startAlpha = 0.8f;


        if(mTipShowAnimator != null && mTipShowAnimator.isRunning()){
            startScaleX =  mNewsNumTip.getScaleX();
            startScaleY =  mNewsNumTip.getScaleY();
            startAlpha =  mNewsNumTip.getAlpha();
            mTipShowAnimator.removeAllListeners();
            mTipShowAnimator.cancel();
        }

        if(hideTipsAnimation != null && hideTipsAnimation.isRunning()){
            startScaleX =  mNewsNumTip.getScaleX();
            startScaleY =  mNewsNumTip.getScaleY();
            startAlpha =  mNewsNumTip.getAlpha();
            hideTipsAnimation.removeAllListeners();
            hideTipsAnimation.cancel();
        }

        mTipShowAnimator = new AnimatorSet();
        mTipShowAnimator.playTogether(ObjectAnimator.ofFloat(mNewsNumTip, "scaleX", startScaleX, 1.f),
                ObjectAnimator.ofFloat(mNewsNumTip, "scaleY", startScaleY, 1.f),
                ObjectAnimator.ofFloat(mNewsNumTip, "alpha", startAlpha, 1.f));

        mTipShowAnimator.setInterpolator(new OvershootInterpolator());
        mTipShowAnimator.setDuration(500);
        mTipShowAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                hideTipAnim();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        mTipShowAnimator.start();

    }




    /**
     * 更新提示隐藏动画
     */
    private void hideTipAnim() {
        float height = mCtx.getResources().getDimension(R.dimen.header_height);


        hideTipsAnimation = ValueAnimator.ofFloat(0, -height);

        hideTipsAnimation.setDuration(500);
        hideTipsAnimation.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mNewsNumTip.setVisibility(View.INVISIBLE);
                if(mAnimListener != null){
                    mAnimListener.hideAnimEnd();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        hideTipsAnimation.start();
    }



    public RelativeLayout getHeaderView() {
        return mHeaderView;
    }
}
