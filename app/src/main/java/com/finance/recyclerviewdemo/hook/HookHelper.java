package com.finance.recyclerviewdemo.hook;

import com.finance.recyclerviewdemo.reflect.RefInvoke;
import com.finance.recyclerviewdemo.reflect.RefInvokePlus;

import java.lang.reflect.Proxy;

/**
 * Created by Jackie on 2018/8/17.
 */
public final class HookHelper {

    public static void hookActivityManager(){
        try {
            //获取AMN的gDefault单例 ,gDefault是静态的
            Object gDefault = RefInvokePlus.getStaticFieldObject("android.app.ActivityManagerNative", "gDefault");

            //gDefault是一个android.util.Singleton对象，我们取出这个单例的mInstance字段，IActivityManager类型
            Object rawIActivityManager = RefInvokePlus.getFieldObject("android.util.Singleton", gDefault, "mInstance");

            //创建一个对象的代理对象iActivityManagerInterface，然后替换mInstance字段，让代理对象帮忙干活
            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                    ,new Class<?>[]{iActivityManagerInterface}
                    ,new HookHandler(rawIActivityManager));

            RefInvokePlus.setFieldObject("android.util.Singleton",gDefault,"mInstance",proxy);

        }catch (Exception e){
            e.printStackTrace();
        }
    }







}
