package com.robot.recycle.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.robot.recycle.R;
import com.robot.recycle.RobotUtils;
import com.robot.recycle.adapter.NewsRecyclerAdapter;
import com.robot.recycle.listener.IPullRefresh;
import com.robot.recycle.listener.IPushRefresh;

import java.util.ArrayList;

/**
 * @author xing.hu
 * @since 2018/9/13, 下午7:43
 */
public class TRecycleView extends FrameLayout {
    private int mTouchSlop;

    // 托盘是否被拖动
    private boolean mIsDrag = false;



    private float mInitX = -1f;
    private float mInitY = -1f;

    private RecyclerView mRecycleView;

    //private boolean mIntercept = false;

    private RelativeLayout mHeaderContainer;

    private NewsRecyclerAdapter myAdapter;

    private LinearLayoutManager linearLayoutManager;

    private IPullRefresh mPullRefresh;
    private IPushRefresh mPushRefresh;

    private int mHeaderHeight = -1;
    private boolean mRefresh = false;


    private Context mCtx;


    public TRecycleView(Context context) {
        super(context);
        init(context);
    }

    public TRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public TRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public TRecycleView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();

        if(!isIntercept()){
            return  false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mInitY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float y = ev.getY();
                if(Math.abs(y-mInitY) >= mTouchSlop){
                    mIsDrag = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                break;
            default:
                break;
        }

        return mIsDrag;
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float dist = 0f;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                dist = (y - mInitY)* TRecycleViewConst.PULL_DRAG_RATE;
                if(dist > 0){
                    mRecycleView.setTranslationY(getY() + dist);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                dist = (event.getY() - mInitY) * TRecycleViewConst.PULL_DRAG_RATE;
                if(mIsDrag){
                    //开始回弹的动画
                     if(dist >= mHeaderHeight){
                         animToHeader();
                     }else{
                         animToStart();
                     }
                }
                mIsDrag = false;

                break;
        }
        return true;
    }

 /*@Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {


    }*/

    private void init(Context ctx) {
        mCtx = ctx;
        mTouchSlop = ViewConfiguration.get(mCtx).getScaledTouchSlop();

        initView();
    }

    private void initView() {
        createHeaderView();
        addTargetView();
        linearLayoutManager = new LinearLayoutManager(mCtx);
        mRecycleView.setLayoutManager(linearLayoutManager);
        myAdapter = new NewsRecyclerAdapter(mCtx);
        mRecycleView.setVerticalScrollBarEnabled(true);

        mRecycleView.setAdapter(myAdapter);
    }


    private void createHeaderView() {
        mHeaderContainer = new RelativeLayout(mCtx);
        mHeaderContainer.setBackgroundResource(R.color.red);
        mHeaderHeight = RobotUtils.dip2px(mCtx, TRecycleViewConst.HEADER_CONTAINER_HEIGHT);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeaderHeight);
        params.gravity = Gravity.TOP;
        addView(mHeaderContainer, params);
    }


    private  void addTargetView(){
        mRecycleView = new RecyclerView(mCtx);
        mRecycleView.setBackgroundResource(R.color.colorPrimary);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mRecycleView, params);
    }



    public void setHeaderView(View headerView) {


    }


    public void setData(ArrayList<String> data) {
        if(data != null){
            myAdapter.setData(data);
            myAdapter.setData(data);

        }
    }

    //托盘是否拦截事件
    private boolean isIntercept(){
       return  !targetInTop();

    }


    private boolean targetInTop(){
        return  false ;

    }

    public void setRefresh(boolean refresh){
        mRefresh = refresh;
        if(!mRefresh){
            animToStart();
        }

    }




    //回弹到初始位置
    private void animToStart(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mRecycleView,"translationY",  0);
        //animator.addListener(mToHeaderListener);
        animator.setDuration(AnimDurConst.ANIM_TO_HEADER_DUR);
        animator.start();
    }


    //回弹到header位置
    private void animToHeader(){
        ObjectAnimator animator = ObjectAnimator.ofFloat(mRecycleView,"translationY",  mHeaderHeight);
        animator.addListener(mToHeaderListener);
        animator.setDuration(AnimDurConst.ANIM_TO_HEADER_DUR);
        animator.start();

    }


    private Animator.AnimatorListener mToHeaderListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if(mPullRefresh != null){
                mPullRefresh.pullRefresh();
            }
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };


    public void setPullRefresh(IPullRefresh mPullRefresh) {
        this.mPullRefresh = mPullRefresh;
    }

    public void setPushRefresh(IPushRefresh mPushRefresh) {
        this.mPushRefresh = mPushRefresh;
    }


    private static class AnimDurConst {
        //回弹到header的动画时长
        private static final  int ANIM_TO_HEADER_DUR = 600;
        //回弹到初始位置的动画时长
        private static final  int ANIM_TO_START_DUR = 600;
    }

    private static class TRecycleViewConst{
        private static final float PULL_DRAG_RATE = .618f;
        private final static int HEADER_CONTAINER_HEIGHT = 50;


    }



}
