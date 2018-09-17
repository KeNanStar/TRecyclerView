package com.robot.recycle.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.robot.recycle.R;
import com.robot.recycle.RobotUtils;

/**
 * @author xing.hu
 * @since 2018/9/13, 下午7:43
 */
public class RobotRecycleView extends FrameLayout {
    private final static int HEADER_CONTAINER_HEIGHT = 50;
    private float moveX;
    private float moveY;

    private RelativeLayout mHeaderContainer;
    private Context mCtx;


    public RobotRecycleView(Context context) {
        super(context);
        init(context);
    }

    public RobotRecycleView( Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public RobotRecycleView( Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);

    }

    public RobotRecycleView( Context context,  AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                moveX = event.getX();
                moveY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                setTranslationX(getX() + (event.getX() - moveX));
                setTranslationY(getY() + (event.getY() - moveY));
                break;
            case MotionEvent.ACTION_UP:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }
        return super.onTouchEvent(event);
    }


/* @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {


    }*/

    private void init(Context ctx) {
        mCtx = ctx;
        initView();
    }

    private void initView() {
        createHeaderView();
    }


    private void createHeaderView() {
        mHeaderContainer = new RelativeLayout(mCtx);
        mHeaderContainer.setBackgroundResource(R.color.red);
        int headerHeight = RobotUtils.dip2px(mCtx, HEADER_CONTAINER_HEIGHT);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, headerHeight);
        params.gravity = Gravity.TOP;
        addView(mHeaderContainer, params);
    }


    public void setHeaderView(View headerView) {


    }

}
