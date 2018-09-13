package com.robot.recycle.view;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

/**
 * @author xing.hu
 * @since 2018/9/13, 下午7:43
 */
public  class RobotRecycleView extends FrameLayout {
   private RelativeLayout mHeaderView;
   private Context mCtx;


    public RobotRecycleView(Context context) {
        super(context);
        init(context);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {





    }

    private void init(Context ctx){
        mCtx = ctx;
        initView();
    }

    private void initView(){
        createHeaderView();
    }


    private void createHeaderView(){
        mHeaderView = new RelativeLayout(mCtx);
        addView(mHeaderView);
    }



}
