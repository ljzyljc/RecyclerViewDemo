package com.finance.recyclerviewdemo.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by Jackie on 2018/8/16.
 */
public class RefInvoke {

    /**
     * 反射出一个构造函数
     * @param className  类的名字
     * @param pareTyples 构造器参数的类型数组 such as:  int.class,String.class
     * @param pareValues 构造器参数的具体值数组
     * @return
     */
    public static Object createObject(String className,Class[] pareTyples,Object[] pareValues){
        try {
            Class r = Class.forName(className);
            Constructor ctor = r.getDeclaredConstructor(pareTyples);
            ctor.setAccessible(true);
            return ctor.newInstance(pareValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
    }


    /**
     * 调用实例方法
     * @param obj         具体对象
     * @param methodName  方法名字
     * @param pareTyples    方法的参数类型数组
     * @param pareValues    方法的具体值数组
     * @return
     */
    public static Object invokeInstancemethod(Object obj, String methodName,
                                              Class[] pareTyples,Object[] pareValues){
        if (obj == null){
            return null;
        }
        //调用一个private方法
        try {
            Method method = obj.getClass().getDeclaredMethod(methodName,pareTyples);
            method.setAccessible(true);
            return  method.invoke(obj,pareValues);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 调用静态方法
     * @param className
     * @param methodName
     * @param pareTyples
     * @param pareValues
     * @return
     */
    public static Object invokeStatic(String className,String methodName,
                                      Class[] pareTyples,Object[] pareValues){

        try {
            Class clazz = Class.forName(className);
            Method method = clazz.getDeclaredMethod(methodName,pareTyples);
            method.setAccessible(true);
            return method.invoke(null,pareValues);
        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
    }

    /**
     * 获取一个字段的值
     * @param className
     * @param obj
     * @param fieldName
     * @return
     */
    public static Object getFieldObject(String className,Object obj,String fieldName){
        try {
            Class cls = Class.forName(className);
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
            return null;
    }
    /**
     * 设置一个字段的值
     * @param className
     * @param obj
     * @param fieldName
     * @return
     */
    public static void setFieldObject(String className,Object obj,String fieldName,Object fieldValue){
        try {
            Class cls = Class.forName(className);
            Field field = cls.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(obj,fieldValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
