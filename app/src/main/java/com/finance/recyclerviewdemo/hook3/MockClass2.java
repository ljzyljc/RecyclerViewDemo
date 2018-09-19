package com.finance.recyclerviewdemo.hook3;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.finance.recyclerviewdemo.reflect.RefInvokePlus;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;


/**
 * Created by Jackie on 2018/8/20.
 */
public class MockClass2 implements Handler.Callback {

    Handler mBase;

    public MockClass2(Handler base){
        mBase = base;
    }

    @Override
    public boolean handleMessage(Message msg) {
        switch (msg.what){
            case 100:
                handleLaunchActivity(msg);
            break;
        }
        mBase.handleMessage(msg);
        return true;
    }
    private void handleLaunchActivity(Message message){
        Object obj = message.obj;
        Intent intent = (Intent) RefInvokePlus.getFieldObject(obj,"intent");
        Intent targetIntent = intent.getParcelableExtra(AMSHookHelper.EXTRA_TARGET_INTENT);
        intent.setComponent(targetIntent.getComponent());

    }








}
