package com.finance.recyclerviewdemo.binder;

/**
 * Created by Jackie on 2018/8/16.
 */
public class TestClassOO {

    private String name;
    private static String address ;

    public static void printAddress(){
        System.out.println("address:    "+address);
    }
    public String getName() {
        return name;
    }

    public TestClassOO(){
        name = "Jackie";
    }

    private TestClassOO(int a){

    }

    public TestClassOO(int a,String b){

    }

    public TestClassOO(int a,double c){

    }


    private String doSomething(String d){
        System.out.println("TestClass00-->doSomeThing"+d);
        return "abcd";
    }

    private static void work(){
        System.out.println("静态方法--》work");
    }




}
