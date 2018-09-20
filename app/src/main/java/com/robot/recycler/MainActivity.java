package com.robot.recycler;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.robot.recycler.adapter.NewsRecyclerAdapter;
import com.robot.recycler.common.TaskExecutor;
import com.robot.recycler.entity.NewsItem;
import com.robot.recycler.listener.IPullRefresh;
import com.robot.recycler.listener.IPushRefresh;
import com.robot.recycler.view.FooterHolder;
import com.robot.recycler.view.HeaderHolder;
import com.robot.recycler.view.TRecyclerView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private TRecyclerView mTRecyclerView;
    private NewsRecyclerAdapter mNewsRecyclerAdapter;


    private static  final int  INIT_COUNT = 25;
    private static  final int  PULL_COUNT = 10;
    private static  final int  PUSH_COUNT = 20;

    private static  final int  INIT_MODE = 0;
    private static  final int  PULL_MODE = 1;
    private static  final int  PUSH_MODE = 2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTRecyclerView = (TRecyclerView) findViewById(R.id.t_recycle);

        initTRecycleView();
        ((TextView)findViewById(R.id.load_data)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFromNet(INIT_MODE);

            }
        });
        initData();

    }

    private void initData(){
        getDataFromNet(INIT_MODE);
    }


    private void initTRecycleView(){
        mNewsRecyclerAdapter = new NewsRecyclerAdapter(this);
        mTRecyclerView.setAdapter(mNewsRecyclerAdapter);
        mTRecyclerView.setPullRefresh(mPullRefresh);
        mTRecyclerView.setPushRefresh(mPushRefresh);
    }


    private  void getDataFromNet(final int mode){
        TaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //模拟网络请求
                    Thread.sleep(800);
                    final ArrayList data = getData(mode);
                    TaskExecutor.runInUiThread(new Runnable() {
                        @Override
                        public void run() {
                            switch (mode){
                                case INIT_MODE:
                                    mNewsRecyclerAdapter.setData(data);
                                    break;
                                case PULL_MODE:
                                    String tip = "为你更新了" + data.size()+ "条新闻";
                                    mTRecyclerView.showRefreshTip(tip);
                                    mNewsRecyclerAdapter.insertData(data);
                                    mTRecyclerView.getHeaderHolder().setState(HeaderHolder.STATE_HIDE_LOAD);
                                    break;
                                case PUSH_MODE:
                                    mNewsRecyclerAdapter.addData(data);
                                    mTRecyclerView.getFooterHolder().setState(FooterHolder.STATE_NORMAL);
                                    mTRecyclerView.setLoadMore(false);
                                    break;
                                 default:

                                     break;

                            }

                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    private  IPullRefresh mPullRefresh = new IPullRefresh() {
        @Override
        public void pullRefresh() {
            mTRecyclerView.getHeaderHolder().setState(HeaderHolder.STATE_LOADING);
            getDataFromNet(PULL_MODE);
        }

        @Override
        public void pullRefreshEnable(boolean enable) {
            mTRecyclerView.getHeaderHolder().setState(enable ?HeaderHolder.STATE_RELEASE: HeaderHolder.STATE_PULL);
        }

        @Override
        public void pullRefreshEnd() {
            mTRecyclerView.setRefresh(false);

        }
    };


    private IPushRefresh mPushRefresh = new IPushRefresh() {
        @Override
        public void loadMore() {
            mTRecyclerView.getFooterHolder().setState(FooterHolder.STATE_LOADING);
            getDataFromNet(PUSH_MODE);
        }
    };




    //自定义数据
    public ArrayList getData(int mode) {
        int count = 0;
        ArrayList<NewsItem> list = new ArrayList<NewsItem>();
        switch (mode){
            case INIT_MODE:
                count = INIT_COUNT - TRecyclerUtils.getRandomNum(10);
                break;
            case PULL_MODE:
                count = PULL_COUNT - TRecyclerUtils.getRandomNum(5);
                break;
            case PUSH_MODE:
                count = PUSH_COUNT - TRecyclerUtils.getRandomNum(10);
                break;
        }

        for (int i = 0; i < count; i++) {
            NewsItem item = new NewsItem();
            list.add(item);
        }
        return  list;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

}
