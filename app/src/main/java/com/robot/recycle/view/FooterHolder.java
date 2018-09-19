package com.robot.recycle.view;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.robot.recycle.R;
import com.robot.recycle.TRecycleUtils;


public class FooterHolder {
    public final static int STATE_NORMAL = 0;
    public final static int STATE_LOADING = STATE_NORMAL + 1;
    public final static int STATE_TIPS = STATE_LOADING + 1;


    private View mContentView;
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
        mContentView.setVisibility(View.VISIBLE);
        mLoadText.setText(mNormalTip);
        mLoadingView.stop();
        mLoadingView.setVisibility(View.GONE);
    }

    /**
     * show footer
     */
    private void loading() {
        mContentView.setVisibility(View.VISIBLE);
        mLoadText.setText(mLoadingTip);
        mLoadingView.setVisibility(View.VISIBLE);
        mLoadingView.start();

    }


    private void showTips(String tip){
        if(!TextUtils.isEmpty(tip)) {
            mContentView.setVisibility(View.VISIBLE);
            mLoadText.setText(tip);
            mLoadingView.stop();
            mLoadingView.setVisibility(View.GONE);
        }

    }


    private void initView() {
        mFooterView = (RelativeLayout) LayoutInflater.from(mCtx).inflate(R.layout.item_footer, null);
        mFooterView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, TRecycleUtils.dip2px(mCtx, FOOTER_HEIGHT)));
        mContentView = mFooterView.findViewById(R.id.footer_content);
        mLoadText = (TextView) mFooterView.findViewById(R.id.upglide_tv);
        mLoadingView = (LoadingView) mFooterView.findViewById(R.id.upglide_load);
        mLoadingTip = mCtx.getResources().getString(R.string.pull_up_loading);
        mNormalTip =  mCtx.getResources().getString(R.string.pull_up_to_loading_more);

    }





    public RelativeLayout getFooterView() {
        return mFooterView;
    }
}
