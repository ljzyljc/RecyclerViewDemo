package com.finance.recyclerviewdemo.reflect;

/**
 * Created by Jackie on 2018/8/16.
 */
public class ClassB2 implements ClassB2Interface {
    public int id;

    @Override
    public void doSomethiing() {
        System.out.println("ClassB2 doSomething");
    }
}
