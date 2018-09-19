package com.finance.recyclerviewdemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.zhangyue.we.x2c.X2C;
import com.zhangyue.we.x2c.ano.Xml;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jackie on 2018/8/28.
 */
//@Xml(layouts = R.layout.activity_x2c)
public class TestX2CActivity extends Activity {
    private static final String TAG = "TestX2CActivity";
    static long time = 0;
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
//        time = System.currentTimeMillis();
    }
    private Handler mHandler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        X2C.setContentView(this,R.layout.activity_x2c);
        setContentView(R.layout.activity_x2c);
        final ExecutorService executorService = Executors.newCachedThreadPool();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Message msg = mHandler.obtainMessage();
//                        msg.obj = "btn 子线程 2";
//                        mHandler.sendMessage(msg);
//                    }
//                }).start();
//            }
        });
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                Looper.prepare();
//                mHandler = new Handler(){
//                    @Override
//                    public void handleMessage(Message msg) {
//                        super.handleMessage(msg);
//                        Log.i(TAG, "handleMessage: -----"+msg.obj);
//                    }
//                };
//                Looper.loop();
//            }
//        }).start();




    }

    @Override
    protected void onResume() {
        super.onResume();
//        time = System.currentTimeMillis() - time;
//        Log.i(TAG, "onResume: --1---"+time);
    }
}
