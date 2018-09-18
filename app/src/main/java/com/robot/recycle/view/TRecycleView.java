package com.robot.recycle.view;

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

import java.util.ArrayList;
import java.util.List;

/**
 * @author xing.hu
 * @since 2018/9/13, 下午7:43
 */
public class TRecycleView extends FrameLayout {
    private final static int HEADER_CONTAINER_HEIGHT = 50;
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
    public void requestDisallowInterceptTouchEvent(boolean b) {
        // Nope.
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
       /* if(!isIntercept()){
            return  false;
        }*/
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //mMoveX = event.getX();
                //mMoveY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                //mRecycleView.setTranslationX(getX() + (event.getX() - mMoveX));
                //mRecycleView.setTranslationY(getY() + (event.getY() - mMoveY));
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
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
        int headerHeight = RobotUtils.dip2px(mCtx, HEADER_CONTAINER_HEIGHT);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerHeight);
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

    public void setDatas(int count) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            list.add("item " + i);
        }
        myAdapter.setData(list);
    }

    //托盘是否拦截事件
    private boolean isIntercept(){
       return  !targetInTop();

    }


    private boolean targetInTop(){
        return  false ;

    }


}
