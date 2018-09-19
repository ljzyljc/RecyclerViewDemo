package com.finance.recyclerviewdemo.binder;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by Jackie on 2018/8/16.
 */
public class TestReflect {

    public static void main(String[] args){

        TestReflect testReflect = new TestReflect();
        try {
            testReflect.test2();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void test1() throws Exception{

        //----------------------------获取类型--------------------
        String str = "abc";
        Class cl = str.getClass();                     //根据对象获取类型

        Class c2 = Class.forName("java.lang.String");  //根据名字获取类型

        Class c3 = c2.getSuperclass();   //获取对象的父类类型，每个Class都有这个函数

        Class c4 = Boolean.TYPE;   //基本类型，如Boolean,都有TYPE属性，可以得到这个基本类型的类型

    }

    public void test2() throws Exception{

        TestClassOO testClassOO = new TestClassOO();
        Class temp = testClassOO.getClass();
        String className = temp.getName();
        System.out.println("获取所有构造器----------------");

        Constructor[] constructors = temp.getDeclaredConstructors(); //获取所有的构造器，不管私有的还是公有的
        for (int i = 0;i<constructors.length;i++){
            int mod = constructors[i].getModifiers();
            //输出修饰域和方法名称
            System.out.println(Modifier.toString(mod)+" "+className +"()");

            Class[] parameterTypes = constructors[i].getParameterTypes();
            //获取指定构造方法的参数集合
            for (int j = 0; j <parameterTypes.length ; j++) {
                //打印参数列表
                System.out.println(parameterTypes[j].getName());

            }


        }

//        //获取某个类的构造函数
//        Class ff = Class.forName("com.finance.recyclerviewdemo.binder.TestClassOO");
//        Class temp2 = ff.getClass();
        //获取无参构造器
        Constructor con1 = temp.getDeclaredConstructor();
        //获取有参构造器
        Class[] p1 = {int.class};
        Constructor con2 = temp.getDeclaredConstructor(p1);

        Class[] p2 = {int.class,String.class};
        Constructor con3 = temp.getConstructor(p2);

        //FIXME:--------调用构造函数----------
        //有参
        Class r = Class.forName("com.finance.recyclerviewdemo.binder.TestClassOO");
        Class[] p2d = {int.class,String.class};

        Object object = con3.newInstance(1,"Jackie");

        //无参
        Constructor cc1 = r.getDeclaredConstructor();
        Object obj2 = con1.newInstance();

        //调用一个private方法
        Class[] p4 ={String.class};
        Method method = r.getDeclaredMethod("doSomething",p4);
        method.setAccessible(true);
        Object [] argList = {"linjuncheng"};
        Object result = method.invoke(object,argList);


        //获取类的静态私有方法并调用它
        Class mm = Class.forName("com.finance.recyclerviewdemo.binder.TestClassOO");
        Method method1 = mm.getDeclaredMethod("work");
        method1.setAccessible(true);
        method1.invoke(null);

        //获取类的私有实例字段并修改它
        Class clz = Class.forName("com.finance.recyclerviewdemo.binder.TestClassOO");
        Class[] f = {int.class,String.class};
        Constructor constructor = clz.getDeclaredConstructor(f);
        Object obj = constructor.newInstance(2,"ljc");
        //获取name字段，private
        Field field = clz.getDeclaredField("name");
        field.setAccessible(true);
        Object fieldObject = field.get(obj);
        field.set(obj,"junchneg1992");  //只对Object有效

        //获取类的私有静态字段并修改它
        Class cm = Class.forName("com.finance.recyclerviewdemo.binder.TestClassOO");
        Field field1 = cm.getDeclaredField("address");
        field1.setAccessible(true);
        Object fieldObj = field1.get(null);
        field1.set(fieldObj,"ABCD");
        TestClassOO.printAddress();

        //对泛型类的反射




    }


}
