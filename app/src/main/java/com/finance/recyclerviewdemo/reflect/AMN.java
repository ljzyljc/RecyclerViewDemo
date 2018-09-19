package com.finance.recyclerviewdemo.reflect;

/**
 * Created by Jackie on 2018/8/16.
 */
public class AMN {

    private static final Singleton<ClassB2Interface> gDefault = new Singleton<ClassB2Interface>() {
        @Override
        protected ClassB2Interface create() {
            ClassB2 b2 = new ClassB2();
            b2.id = 2;
            return b2;
        }
    };

    static public ClassB2Interface getDefault(){
        return gDefault.get();
    }


}
