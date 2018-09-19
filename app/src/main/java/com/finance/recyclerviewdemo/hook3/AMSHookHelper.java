package com.finance.recyclerviewdemo.hook3;

import android.os.Handler;

import com.finance.recyclerviewdemo.reflect.RefInvokePlus;

import java.lang.reflect.Proxy;

/**
 * Created by Jackie on 2018/8/20.
 */
public class AMSHookHelper {
    public static final String EXTRA_TARGET_INTENT = "extra_target_intent";
    private static final String TAG = "AMSHookHelper";
    //-------------------------第1步----------------------------
    //主要完成的操作是  "把真正要启动的Activity临时替换为在AndroidManifest.xml中声明的替身Activity",进而骗过AMS
    public static void hookAMN() throws Exception{

        Object gDefault = RefInvokePlus.getStaticFieldObject("android.app.ActivityManagerNative","gDefault");

        //gDefault 是一个android.util.Singleton<T>对象，我们取出这个单例里面 mInstance字段
        Object mInstance = RefInvokePlus.getFieldObject("android.util.Singleton", gDefault, "mInstance");

        Class<?> classBInterface = Class.forName("android.app.IActivityManager");

        Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                ,new Class<?>[]{classBInterface}
                ,new MockClass1(mInstance));

//        Class class1 = gDefault.getClass();
        RefInvokePlus.setFieldObject("android.util.Singleton",gDefault,"mInstance",proxy);

    }
    /**-----------------第5步
     * 由于之前我们用替身欺骗了AMS; 现在我们要换回我们真正需要启动的Activity
     * 不然就真的启动替身了, 狸猫换太子...
     * 到最终要启动Activity的时候,会交给ActivityThread 的一个内部类叫做 H 来完成
     * H 会完成这个消息转发; 最终调用它的callback
     */
    public static void hookActivityThread() throws Exception{

        //获取到当前的ActivityThread对象
        Object currentActivityThread = RefInvokePlus.getStaticFieldObject("android.app.ActivityThread", "sCurrentActivityThread");
        //由于ActivityThread一个进程只有一个，我们获取这个对象的mH；
        Handler mH = (Handler) RefInvokePlus.getFieldObject(currentActivityThread,"mH");

        RefInvokePlus.setFieldObject(Handler.class,mH,"mCallback",new MockClass2(mH));

    }





}
