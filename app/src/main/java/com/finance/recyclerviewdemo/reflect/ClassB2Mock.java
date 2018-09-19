package com.finance.recyclerviewdemo.reflect;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by Jackie on 2018/8/16.
 */
public class ClassB2Mock implements InvocationHandler {

    Object mBase;
    public ClassB2Mock(Object object){
        mBase = object;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("---method:  "+method.getName());
        if ("doSomethiing".equals(method.getName())){
            System.out.println("---method:  hello");
        }
        return method.invoke(mBase,args);
    }
}
