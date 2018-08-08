package com.finance.recyclerviewdemo.jihe;

/**
 * Created by Jackie on 2018/8/2.
 */
public class Student {

    private int num;
    private String name;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return num;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
