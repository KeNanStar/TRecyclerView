package com.robot.recycle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.robot.recycle.view.TRecycleView;

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
                mTRecycleView.setDatas(30);

            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

    }
}
