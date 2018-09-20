package com.robot.recycler.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.robot.recycler.R;
import com.robot.recycler.TRecyclerUtils;


public class FooterHolder {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_LOADING = STATE_NORMAL + 1;
    public final static int STATE_TIPS = STATE_LOADING + 1;


    private TextView mLoadText;
    private LoadingView mLoadingView;
    private String mLoadingTip = "";
    private String mNormalTip = "";
    private static final int FOOTER_HEIGHT = 55;

    private RelativeLayout mFooterView;

    private Context mCtx;

    public FooterHolder(Context context) {
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
            case STATE_TIPS:
                if(args != null && args.length >=1){
                    if(args[0] instanceof  String){
                        showTips((String)args[0]);
                    }
                }
                break;

        }
    }


    /**
     * hide footer when disable pull load more
     */
    private void normal() {
        showFooterView(false);
        mLoadText.setText(mNormalTip);
        mLoadingView.stop();
    }

    /**
     * show footer
     */
    private void loading() {
        showFooterView(true);
        mLoadText.setText(mLoadingTip);
        mLoadingView.start();

    }


    private void showTips(String tip){
        if(!TextUtils.isEmpty(tip)) {
            showFooterView(true);
            mLoadText.setText(tip);
            mLoadingView.stop();
        }

    }


    public void showFooterView(boolean isShow){
        if(mFooterView != null ){
            if(isShow){
                if(mFooterView.getVisibility() != View.VISIBLE){
                    mFooterView.setVisibility(View.VISIBLE);
                }
            }else{
                if(mFooterView.getVisibility() == View.VISIBLE){
                    mFooterView.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

    private void initView() {
        mFooterView = (RelativeLayout) LayoutInflater.from(mCtx).inflate(R.layout.item_footer, null);
        mFooterView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, TRecyclerUtils.dip2px(mCtx, FOOTER_HEIGHT)));
        mLoadText = (TextView) mFooterView.findViewById(R.id.upglide_tv);
        mLoadingView = (LoadingView) mFooterView.findViewById(R.id.upglide_load);
        mLoadingTip = mCtx.getResources().getString(R.string.pull_up_loading);
        mNormalTip =  mCtx.getResources().getString(R.string.pull_up_to_loading_more);

    }



    public RelativeLayout getFooterView() {
        return mFooterView;
    }
}
