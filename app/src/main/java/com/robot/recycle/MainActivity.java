package com.robot.recycle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.robot.recycle.adapter.NewsRecyclerAdapter;
import com.robot.recycle.common.TaskExecutor;
import com.robot.recycle.entity.NewsItem;
import com.robot.recycle.listener.IPullRefresh;
import com.robot.recycle.view.TRecycleView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private TRecycleView mTRecycleView;
    private NewsRecyclerAdapter mNewsRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTRecycleView = (TRecycleView) findViewById(R.id.t_recycle);

        mNewsRecyclerAdapter = new NewsRecyclerAdapter(this);

        mTRecycleView.setAdapter(mNewsRecyclerAdapter);
        mTRecycleView.setPullRefresh(new IPullRefresh() {
            @Override
            public void pullRefresh() {
                getDataFromNet();
            }
        });
        ((TextView)findViewById(R.id.load_data)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList data = getData(30);
                mNewsRecyclerAdapter.setData(data);

            }
        });

    }


    private  void getDataFromNet(){
        TaskExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    //模拟网络请求
                    Thread.sleep(800);
                    final ArrayList data = getData(30);
                    TaskExecutor.runInUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mNewsRecyclerAdapter.setData(data);
                            mTRecycleView.setRefresh(false);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        });
    }


    //自定义数据
    public ArrayList getData(int count) {
        ArrayList<NewsItem> list = new ArrayList<NewsItem>();
        for (int i = 0; i < count; i++) {
            NewsItem item = new NewsItem();
            item.mTitle = "新闻" + i;
            item.mContent = "今天晴：一年的第" + i  +"天...";
            list.add(item);
        }
        return  list;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }



}
