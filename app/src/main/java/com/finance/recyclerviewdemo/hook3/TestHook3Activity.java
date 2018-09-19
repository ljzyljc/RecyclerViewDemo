package com.finance.recyclerviewdemo.hook3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.finance.recyclerviewdemo.R;

/**
 * Created by Jackie on 2018/8/20.
 */
public class TestHook3Activity extends Activity {
    private static final String TAG = "TestHook3Activity";
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(newBase);
        try {
            AMSHookHelper.hookAMN();
            AMSHookHelper.hookActivityThread();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestHook3Activity.this,TargetAcitivity.class));
                Log.i(TAG, "onClick: -------ddd----");
            }
        });
    }



}
