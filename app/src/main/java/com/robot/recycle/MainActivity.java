package com.robot.recycle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.robot.recycle.adapter.NewsRecyclerAdapter;
import com.robot.recycle.common.TaskExecutor;
import com.robot.recycle.entity.NewsItem;
import com.robot.recycle.listener.IPullRefresh;
import com.robot.recycle.listener.IPushRefresh;
import com.robot.recycle.view.FooterHolder;
import com.robot.recycle.view.HeaderHolder;
import com.robot.recycle.view.TRecycleView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private TRecycleView mTRecycleView;
    private NewsRecyclerAdapter mNewsRecyclerAdapter;


    private static  final int  INIT_COUNT = 25;
    private static  final int  PULL_COUNT = 10;
    private static  final int  PUSH_COUNT = 20;

    private static  final int  INIT_MODE = 0;
    private static  final int  PULL_MODE = 1;
    private static  final int  PUSH_MODE = 2;


    private static final String[] TITLES = {"互联网金融", "云计算", "机器学习", "人工智能"};
    private static final String[] CONTENTS = {"互联网金融不是互联网和金融业的简单结合，而是在实现安全、移动等网络技术水平上，被用户熟悉接受后（尤其是对电子商务的接受），自然而然为适应新的需求而产生的新模式及新业务",
            "云计算（cloudcomputing）是基于互联网的相关服务的增加、使用和交付模式，通常涉及通过互联网来提供动态易扩展且经常是虚拟化的资源。"
            , "机器学习(Machine Learning, ML)是一门多领域交叉学科，涉及概率论、统计学、逼近论、凸分析、算法复杂度理论等多门学科。"
            , "人工智能（Artificial Intelligence），英文缩写为AI。它是研究、开发用于模拟、延伸和扩展人的智能的理论、方法、技术及应用系统的一门新的技术科学。"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTRecycleView = (TRecycleView) findViewById(R.id.t_recycle);

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
        mTRecycleView.setAdapter(mNewsRecyclerAdapter);
        mTRecycleView.setPullRefresh(mPullRefresh);
        mTRecycleView.setPushRefresh(mPushRefresh);
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
                                    mTRecycleView.showRefreshTip(tip);
                                    mNewsRecyclerAdapter.insertData(data);
                                    mTRecycleView.getHeaderHolder().setState(HeaderHolder.STATE_HIDE_LOAD);
                                    break;
                                case PUSH_MODE:
                                    mNewsRecyclerAdapter.addData(data);
                                    mTRecycleView.getFooterHolder().setState(FooterHolder.STATE_NORMAL);
                                    mTRecycleView.setLoadMore(false);
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
            mTRecycleView.getHeaderHolder().setState(HeaderHolder.STATE_LOADING);
            getDataFromNet(PULL_MODE);
        }

        @Override
        public void pullRefreshEnable(boolean enable) {
            mTRecycleView.getHeaderHolder().setState(enable ?HeaderHolder.STATE_RELEASE: HeaderHolder.STATE_PULL);
        }

        @Override
        public void pullRefreshEnd() {
            mTRecycleView.setRefresh(false);

        }
    };


    private IPushRefresh mPushRefresh = new IPushRefresh() {
        @Override
        public void loadMore() {
            mTRecycleView.getFooterHolder().setState(FooterHolder.STATE_LOADING);
            getDataFromNet(PUSH_MODE);
        }
    };




    //自定义数据
    public ArrayList getData(int mode) {
        int count = 0;
        ArrayList<NewsItem> list = new ArrayList<NewsItem>();
        switch (mode){
            case INIT_MODE:
                count = INIT_COUNT - TRecycleUtils.getRandomNum(10);
                break;
            case PULL_MODE:
                count = PULL_COUNT - TRecycleUtils.getRandomNum(5);
                break;
            case PUSH_MODE:
                count = PUSH_COUNT - TRecycleUtils.getRandomNum(10);
                break;
        }

        for (int i = 0; i < count; i++) {
            NewsItem item = new NewsItem();
            item.mTitle = TITLES[TRecycleUtils.getRandomNum(4)];
            item.mContent = CONTENTS[TRecycleUtils.getRandomNum(4)];
            list.add(item);
        }
        return  list;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

}
