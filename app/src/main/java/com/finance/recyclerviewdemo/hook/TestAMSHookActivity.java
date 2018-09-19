package com.finance.recyclerviewdemo.hook;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.finance.recyclerviewdemo.R;

/**
 * Created by Jackie on 2018/8/17.
 */
public class TestAMSHookActivity extends Activity {
    private Button button;
    @Override
    protected void attachBaseContext(Context newBase) {
//        HookHelper.hookActivityManager();
        HookPMSHelper.hookPackageManager(newBase);
        super.attachBaseContext(newBase);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
        button = findViewById(R.id.btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getPackageManager().getInstalledApplications(0);
            }
        });
    }
}
