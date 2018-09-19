package com.finance.recyclerviewdemo.hook;

import android.content.Context;
import android.content.pm.PackageManager;

import com.finance.recyclerviewdemo.reflect.RefInvoke;
import com.finance.recyclerviewdemo.reflect.RefInvokePlus;

import java.lang.reflect.Proxy;

/**
 * Created by Jackie on 2018/8/17.
 */
public final class HookPMSHelper {
    public static void hookPackageManager(Context context) {
        try {
            // 获取全局的ActivityThread对象
            Object currentActivityThread = RefInvokePlus.getStaticFieldObject("android.app.ActivityThread", "sCurrentActivityThread");

            // 获取ActivityThread里面原始的 sPackageManager
            Object sPackageManager = RefInvokePlus.getFieldObject(currentActivityThread, "sPackageManager");


            // 准备好代理对象, 用来替换原始的对象
            Class<?> iPackageManagerInterface = Class.forName("android.content.pm.IPackageManager");
            Object proxy = Proxy.newProxyInstance(iPackageManagerInterface.getClassLoader(),
                    new Class<?>[] { iPackageManagerInterface },
                    new HookHandler(sPackageManager));

            // 1. 替换掉ActivityThread里面的 sPackageManager 字段
            RefInvokePlus.setFieldObject(currentActivityThread, "sPackageManager", proxy);

            // 2. 替换 ApplicationPackageManager里面的 mPm对象
            PackageManager pm = context.getPackageManager();
            RefInvokePlus.setFieldObject(pm, "mPM", proxy);

        } catch (Exception e) {
            throw new RuntimeException("hook failed", e);
        }
    }
}
