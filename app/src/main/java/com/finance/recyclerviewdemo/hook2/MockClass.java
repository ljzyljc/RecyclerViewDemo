package com.finance.recyclerviewdemo.hook2;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Jackie on 2018/8/17.
 */
public class MockClass implements Handler.Callback {
    private static final String TAG = "MockClass";
    Handler mBase;

    public MockClass(Handler mBase) {
        this.mBase = mBase;
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

    public void handleLaunchActivity(Message msg){
        //简单起见，直接取出TargetActivity
        Object obj = msg.obj;
        Log.i(TAG, "handleLaunchActivity: ----"+obj.toString());
    }

}
