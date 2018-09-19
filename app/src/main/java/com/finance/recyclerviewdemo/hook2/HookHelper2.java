package com.finance.recyclerviewdemo.hook2;

import android.os.Handler;

import com.finance.recyclerviewdemo.reflect.RefInvokePlus;

/**
 * Created by Jackie on 2018/8/17.
 */
public class HookHelper2 {

    public static void attachBaseContext() throws Exception{

        Object currentAcitivyThread = RefInvokePlus.getStaticFieldObject("android.app.ActivityThread"
                ,"sCurrentActivityThread");
        Handler mH = (Handler) RefInvokePlus.getFieldObject(currentAcitivyThread,"mH");

        //把Handler的mCallBack字段，替换为new MockClass(mH)
        RefInvokePlus.setFieldObject(Handler.class,mH,"mCallback",new MockClass(mH));

    }

}
