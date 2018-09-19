package com.finance.recyclerviewdemo.hook;

import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Jackie on 2018/8/17.
 */
public class HookHandler implements InvocationHandler{

    private static final String TAG = "HookHandler";

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Log.i(TAG, "invoke: ---hey,baby;you are hooked");
        Log.i(TAG, "invoke: ---"+method.getName()+"     "+ Arrays.toString(args));
        return method.invoke(mBase,args);
    }

    private Object mBase;

    public HookHandler(Object mBase) {
        this.mBase = mBase;
    }
}
