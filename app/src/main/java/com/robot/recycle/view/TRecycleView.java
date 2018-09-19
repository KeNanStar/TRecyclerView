package com.robot.recycle.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v4.widget.ListViewCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.robot.recycle.R;
import com.robot.recycle.TRecycleUtils;
import com.robot.recycle.adapter.TRecyclerAdapter;
import com.robot.recycle.listener.IAnimListener;
import com.robot.recycle.listener.IPullRefresh;
import com.robot.recycle.listener.IPushRefresh;

import java.util.TreeMap;

/**
 * @author xing.hu
 * @since 2018/9/13, 下午7:43
 */
public class TRecycleView extends FrameLayout{
    private int mTouchSlop;

    // 托盘是否被拖动
    private boolean mIsDrag = false;

    private HeaderHolder mHeaderHolder;


    private float mInitX = -1f;
    private float mInitY = -1f;

    private RecyclerView mRecycleView;

    //private boolean mIntercept = false;

    private RelativeLayout mHeaderContainer;

    private TRecyclerAdapter mTAdapter;

    private LinearLayoutManager linearLayoutManager;

    private IPullRefresh mPullRefresh;
    private IPushRefresh mPushRefresh;

    private int mHeaderHeight = -1;
    private boolean mRefresh = false;
    private boolean mLoadMore = false;


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

        if(isUnIntercept()){
            return  false;
        }
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mIsDrag = false;
                mInitY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float y = ev.getY();
                if(y-mInitY >= mTouchSlop && !mIsDrag){
                    mIsDrag = true;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsDrag = false;
                break;
            default:
                break;
        }

        return mIsDrag;
    }




    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(isUnIntercept()){
            return false;
        }

        float dist = 0f;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsDrag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                dist = (y - mInitY)* TRecycleViewConst.PULL_DRAG_RATE;
                if(dist > 0){
                    mRecycleView.setTranslationY(getY() + dist);
                }
                if(mIsDrag){
                    //the distance of pull can trigger off refresh
                    if(mPullRefresh != null){
                        mPullRefresh.pullRefreshEnable(dist >= mHeaderHeight);
                    }
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
        mRecycleView.setVerticalScrollBarEnabled(true);
        initListener();

    }

    private void initListener(){
        mRecycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (targetInBottom()) {
                      if(mPushRefresh != null){
                          mLoadMore = true;
                          mPushRefresh.loadMore();
                      }
                    }

                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }


    private void createHeaderView() {
        mHeaderContainer = new RelativeLayout(mCtx);
        mHeaderHeight = (int)mCtx.getResources().getDimension(R.dimen.header_height);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mHeaderHeight);
        params.gravity = Gravity.TOP;
        addView(mHeaderContainer, params);
        setHeaderView();
    }


    private  void addTargetView(){
        mRecycleView = new RecyclerView(mCtx);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        addView(mRecycleView, params);
    }


    private void setHeaderView(){
        mHeaderHolder = new HeaderHolder(mCtx);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, mHeaderHeight);
        mHeaderContainer.addView(mHeaderHolder.getHeaderView(), params);
        mHeaderHolder.setAnimListener(mAnimListener);

    }


    /*public void setHeaderView(View headerView) {


    }*/



    public void setAdapter(RecyclerView.Adapter adapter){
        adapter.registerAdapterDataObserver(mDataObserver);
        mTAdapter = new TRecyclerAdapter(mCtx, adapter);
        mRecycleView.setAdapter(mTAdapter);



    }


    public void setRefresh(boolean refresh){
        mRefresh = refresh;
        if(!mRefresh){
            animToStart();
        }
    }


   public void setLoadMore(boolean loadMore){
        mLoadMore = loadMore;
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
                mRefresh = true;
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
        //the height of the Header
        //private final static int HEADER_CONTAINER_HEIGHT = 50;


    }


    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            mTAdapter.notifyDataSetChanged();
        }


        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mTAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mTAdapter.notifyItemRangeChanged(positionStart , itemCount, payload);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mTAdapter.notifyItemRangeInserted(positionStart , itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mTAdapter.notifyItemRangeRemoved(positionStart , itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mTAdapter.notifyItemMoved(fromPosition, toPosition );
        }
    };

    //if recycleView can scroll, TRecycleView doesn't intercept the event
    private boolean isUnIntercept(){
        return  !targetInTop();

    }

    //the recycleView has scrolled to the top position
    private boolean targetInTop(){
        return  !mRecycleView.canScrollVertically(-1) ;

    }

    //the recycleView has scrolled to the bottom position
    private boolean targetInBottom() {
        if (targetInTop()) {
            return false;
        }
        RecyclerView.LayoutManager layoutManager = mRecycleView.getLayoutManager();
        int count = mRecycleView.getAdapter().getItemCount();
        if (layoutManager instanceof LinearLayoutManager && count > 0) {
            LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
            if (linearLayoutManager.findLastVisibleItemPosition() == count - 1) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
            int[] lastItems = new int[2];
            staggeredGridLayoutManager
                    .findLastCompletelyVisibleItemPositions(lastItems);
            int lastItem = Math.max(lastItems[0], lastItems[1]);
            if (lastItem == count - 1) {
                return true;
            }
        }
        return false;
    }


    public HeaderHolder getHeaderHolder(){
        return mHeaderHolder;
    }
    public FooterHolder getFooterHolder(){
        return mTAdapter.getFooterHolder();
    }

    private IAnimListener mAnimListener = new IAnimListener() {
        @Override
        public void hideAnimEnd() {
            if(mPullRefresh != null){
                mPullRefresh.pullRefreshEnd();
            }
        }
    };

    public void showRefreshTip(String text){
        mHeaderHolder.showRefreshTips(text);
    }

}
