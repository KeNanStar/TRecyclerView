package com.robot.recycle.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.robot.recycle.R;
import com.robot.recycle.TRecycleUtils;


public class HeaderHolder {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_LOADING = STATE_NORMAL + 1;
    public final static int STATE_TIPS = STATE_LOADING + 1;
    public final static int STATE_RELEASE = STATE_TIPS + 1;


    private TextView mLoadText;
    private LoadingView mLoadingView;
    private String mLoadingTip = "";
    private String mNormalTip = "";
    private String mReleaseTip = "";
    private static final int FOOTER_HEIGHT = 55;

    private RelativeLayout mHeaderView;

    private Context mCtx;

    public HeaderHolder(Context context) {
        mCtx = context;
        initView();
    }



    public void setState(int state,  Object... args) {
        switch (state) {
            case STATE_NORMAL:
                normal();
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


    /**
     * hide footer when disable pull load more
     */
    private void normal() {
        showHeaderView(false);
        mLoadText.setText(mNormalTip);
        mLoadingView.stop();

    }

    /**
     * show footer
     */
    private void loading() {
        showHeaderView(true);
        mLoadText.setText(mLoadingTip);
        mLoadingView.start();

    }


    private void release() {
        showHeaderView(true);
        mLoadText.setText(mReleaseTip);
        mLoadingView.stop();
    }

    private void showTips(String tip){
        if(!TextUtils.isEmpty(tip)) {
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
        mLoadingTip = mCtx.getResources().getString(R.string.pull_loading);
        mNormalTip =  mCtx.getResources().getString(R.string.pull_tip);
        mReleaseTip =  mCtx.getResources().getString(R.string.pull_release_tip);

    }





    public RelativeLayout getHeaderView() {
        return mHeaderView;
    }
}
