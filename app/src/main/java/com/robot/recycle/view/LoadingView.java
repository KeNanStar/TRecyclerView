package com.robot.recycle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.robot.recycle.R;
import com.robot.recycle.TRecycleUtils;

/**
 *
 *
 * @author xing.hu
 * @since 2018/7/19, 下午5:16
 *  下拉刷新的LoadingView
 */

public class LoadingView extends View {
    private Context mCtx;


    private static final int SCALE_TYPE_INCREASE = 0;//递增变化
    private static final int SCALE_TYPE_DECREASE = 1;//递减变化
    private Paint mPaint;
    private float mMaxRadius = 0;//动画最大半径
    private float mMiniRadius = 0;//动画最小半径
    private float VELOCITY = 0.5f; //画笔移动像素速度
    private boolean mIsStop = true;//停止

    private boolean mIsShowAnimation = false;//是否展示动画

    private float mFirstRadius = mMiniRadius; //第一个圆默认最小状态
    private float mSecondRadius = mMaxRadius;    //第二个圆默认最大状态

    private int mFirstScaleType = SCALE_TYPE_INCREASE;//第一个圆形默认递增，放大
    private int mSecondScaleType = SCALE_TYPE_DECREASE;//第二个圆形默认递减，缩小

    //两个圆的圆心位置
    private int mFirstCenterX;
    private int mFirstCenterY = 15;
    private int mSecondCenterX;
    private int mSecondCenterY = 45;



    public LoadingView(Context context) {
        super(context);
        init(context);
    }

    public LoadingView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public LoadingView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mCtx =  context;
        mMaxRadius = TRecycleUtils.dip2px(getContext(), 4);
        mMiniRadius = TRecycleUtils.dip2px(getContext(), 2);
        mFirstRadius = mMiniRadius;
        mSecondRadius = mMaxRadius;
        mPaint = new Paint();
        mPaint.setColor(mCtx.getColor(R.color.red));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setAntiAlias(true);
    }

    /**
     * 动画停止
     */
    public void start(){
        invalidate();
        mIsStop = false;
        mIsShowAnimation = true;
    }

    /**
     * 动画停止
     */
    public void stop(){
        mIsStop = true;
        mIsShowAnimation = false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int width = canvas.getWidth();
        //将圆心X值放在画布中间，解决居中问题
        if(width != 0){
            mFirstCenterX = width / 2;
            mSecondCenterX = width / 2;
        }
        super.onDraw(canvas);
        if(mIsShowAnimation){
            if(mIsStop){
                return;
            }
            drawFirstCircle(canvas);
            drawSecondCircle(canvas);
            invalidate();
        }else {
            canvas.drawCircle(mFirstCenterX, mFirstCenterY, mFirstRadius, mPaint);
            canvas.drawCircle(mSecondCenterX, mSecondCenterY, mSecondRadius, mPaint);
        }
    }

    /**
     * 画第一个圆
     * @param canvas
     */
    private void drawFirstCircle(Canvas canvas){
        if(mFirstScaleType == SCALE_TYPE_INCREASE){
            if(mFirstRadius < mMaxRadius){
                mFirstRadius += VELOCITY;
            }else {
                mFirstScaleType = SCALE_TYPE_DECREASE;
            }
        }else {
            if(mFirstRadius > mMiniRadius){
                mFirstRadius -= VELOCITY;
            }else {
                mFirstScaleType = SCALE_TYPE_INCREASE;
            }
        }
        canvas.drawCircle(mFirstCenterX, mFirstCenterY, mFirstRadius, mPaint);
    }

    /**
     * 画第二个圆
     * @param canvas
     */
    private void drawSecondCircle(Canvas canvas){
        if(mSecondScaleType == SCALE_TYPE_INCREASE){
            if(mSecondRadius < mMaxRadius){
                mSecondRadius += VELOCITY;
            }else {
                mSecondScaleType = SCALE_TYPE_DECREASE;
            }
        }else {
            if(mSecondRadius > mMiniRadius){
                mSecondRadius -= VELOCITY;
            }else {
                mSecondScaleType = SCALE_TYPE_INCREASE;
            }
        }
        canvas.drawCircle(mSecondCenterX, mSecondCenterY, mSecondRadius, mPaint);
    }


}
