package com.finance.recyclerviewdemo.hook1;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.finance.recyclerviewdemo.R;
import com.finance.recyclerviewdemo.hook2.HookHelper2;
import com.finance.recyclerviewdemo.reflect.RefInvoke;
import com.finance.recyclerviewdemo.reflect.RefInvokePlus;

/**
 * Created by Jackie on 2018/8/17.
 */
public class Activity1 extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
//        Instrumentation mInstrumentation = (Instrumentation) RefInvokePlus.getFieldObject(Activity.class,
//                this,"mInstrumentation");
//        Instrumentation evilInstrumentation = new EvliInstrumentation(mInstrumentation);
//
//        RefInvokePlus.setFieldObject(Activity.class,this,"mInstrumentation",evilInstrumentation);


        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity1.this,Activity2.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {

        super.attachBaseContext(newBase);
        try {
            HookHelper2.attachBaseContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
