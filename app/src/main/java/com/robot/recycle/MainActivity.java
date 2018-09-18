package com.robot.recycle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.robot.recycle.common.TaskExecutor;
import com.robot.recycle.listener.IPullRefresh;
import com.robot.recycle.view.TRecycleView;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private TRecycleView mTRecycleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTRecycleView = (TRecycleView) findViewById(R.id.t_recycle);
        ((TextView)findViewById(R.id.load_data)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList data = getData(30);
                mTRecycleView.setData(data);

            }
        });

        mTRecycleView.setPullRefresh(new IPullRefresh() {
            @Override
            public void pullRefresh() {
                getDataFromNet();
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
                            mTRecycleView.setData(data);
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
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < count; i++) {
            list.add("item " + i);
        }
        return  list;
    }


    @Override
    protected void onResume() {
        super.onResume();

    }



}
