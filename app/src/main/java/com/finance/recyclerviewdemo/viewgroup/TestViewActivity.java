package com.finance.recyclerviewdemo.viewgroup;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.finance.recyclerviewdemo.R;

import java.io.IOException;
import java.net.URLClassLoader;

import dalvik.system.DexClassLoader;
import dalvik.system.PathClassLoader;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okio.Okio;

/**
 * Created by Jackie on 2018/8/8.
 */
public class TestViewActivity extends Activity {
    private static final String TAG = "TestViewActivity";

//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            Log.i(TAG, "handleMessage: "+Thread.currentThread().getName());
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    txt.setText("jackie");
//                }
//            }).start();
//        }
//    };
    private TextView txt;
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ui);
//        Okio
//        txt = findViewById(R.id.txt);
//        button = findViewById(R.id.btn);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0;i<10000;i++){
//                    Log.i(TAG, "onClick: ----"+i);
//                    Toast.makeText(TestViewActivity.this,"123456",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
//        ViewGroup
//        handler.sendEmptyMessageDelayed(0x123,5000);

//        URLClassLoaderi
//        PathClassLoader
//        DexClassLoader





    }

    public String getMsg(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("")
                .build();
        try {
            Response response = client.newCall(request).execute();
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
