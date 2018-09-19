package com.finance.recyclerviewdemo.reflect;

/**
 * Created by Jackie on 2018/8/16.
 */
public abstract class Singleton<T> {

    private T mInstance;

    protected abstract T create();

    public final T get(){
        synchronized (this){
            if (mInstance == null){
                mInstance = create();
            }
            return mInstance;
        }
    }


}
