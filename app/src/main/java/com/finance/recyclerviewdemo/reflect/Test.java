package com.finance.recyclerviewdemo.reflect;

import java.lang.reflect.Proxy;

/**
 * Created by Jackie on 2018/8/16.
 */
public class Test {
    final String className = "com.finance.recyclerviewdemo.binder.TestClassOO";
    public static void main(String[] args){
        Test test = new Test();
//        test.test1();
//        test.test2();
//        test.test3();
//        test.test4();
        AMN.getDefault().doSomethiing();
        test.test5();
        AMN.getDefault().doSomethiing();
    }

    public void test1(){
        try {
            Class r = Class.forName(className);
            //有参
            Class[] p1 = {int.class,String.class};
            Object[] o1 = {1,"pengzhiming"};
            Object obj = RefInvoke.createObject(className,p1,o1);
            //无参构造器
            Object obj2 = RefInvoke.createObject(className,null,null);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //调用实例方法
    public void test2(){
        Class[] p1 = {int.class,String.class};
        Object[] o1 = {1,"pengzhiming"};
        Object obj = RefInvoke.createObject(className,p1,o1);

        Class[] p3 = {String.class};
        Object[] v3 = {"ljc"};
        RefInvoke.invokeInstancemethod(obj,"doSomething",p3,v3);
    }

    //调用静态方法
    public void test3(){
        Class[] p4 = {};
        Object[] o4 = {};

        RefInvoke.invokeStatic(className,"work",p4,o4);
    }
    //获取一个字段并设置值
    public void test4(){
        Class[] p1 = {int.class,String.class};
        Object[] o1 = {1,"pengzhiming"};
        Object obj = RefInvoke.createObject(className,p1,o1);

        Object fieldObject = RefInvoke.getFieldObject(className,obj,"name");
        RefInvoke.setFieldObject(className,obj,"name","google or baidu");

        //获取静态字段
        Object fObject = RefInvoke.getFieldObject(className,null,"address");
        RefInvoke.setFieldObject(className,null,"address","ABDD");

    }
    //对泛型类的处理
    public void test5(){

        //获取AMD的gDefault单例，gDefault是静态的
        Object gDefault = RefInvoke.getFieldObject("com.finance.recyclerviewdemo.reflect.AMN",null,"gDefault");

        //gDefault是一个 单例对象，取出单例里面的mInstance字段
        Object rawB2Object = RefInvoke.getFieldObject("com.finance.recyclerviewdemo.reflect.Singleton"
                ,gDefault,"mInstance");
        try {
            Class<?> classB2Inteface = Class.forName("com.finance.recyclerviewdemo.reflect.ClassB2Interface");
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader()
                    ,new Class<?>[]{classB2Inteface},new ClassB2Mock(rawB2Object));

            //把Singleton的mInstance替换为proxy
            RefInvoke.setFieldObject("com.finance.recyclerviewdemo.reflect.Singleton"
                    ,gDefault,"mInstance",proxy);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }








}
