package com.finance.recyclerviewdemo.hook1;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.finance.recyclerviewdemo.reflect.RefInvokePlus;

/**
 * Created by Jackie on 2018/8/17.
 */
public class EvliInstrumentation extends Instrumentation {

    private static final String TAG = "EvliInstrumentation";

    private Instrumentation mBase;
    public EvliInstrumentation(Instrumentation base){
        mBase = base;
    }

    public ActivityResult execStartActivity(
            Context who, IBinder contextThread, IBinder token, Activity target,
            Intent intent, int requestCode, Bundle options) {

        Log.i(TAG, "execStartActivity: ----today is qixi(China romantic day)");
        //要调用原生的方法，不调用的话，所有的startActivity都失效了，
        //由于这个方法是隐藏的，因此需要使用反射调用，首先要找到这个方法
        Class[] p1 = {Context.class, IBinder.class,
                IBinder.class, Activity.class,
                Intent.class, int.class, Bundle.class};
        Object[] v1 = {who, contextThread, token, target,
                intent, requestCode, options};
        return (ActivityResult) RefInvokePlus.invokeInstanceMethod(mBase, "execStartActivity", p1, v1);


    }
}
