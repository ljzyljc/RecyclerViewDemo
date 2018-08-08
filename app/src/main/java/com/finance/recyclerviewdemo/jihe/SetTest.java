package com.finance.recyclerviewdemo.jihe;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jackie on 2018/8/2.
 */
public class SetTest {

    private static HashSet<Student> hashSet = new HashSet<>();

    public static void main(String[] args){
//        Student student = new Student();
//        student.setNum(12);
//        hashSet = new HashSet<>();
//        hashSet.add(student);
//        student.setNum(20);
//        hashSet.add(student);
//        System.out.println(hashSet.size()+"-----------");
//        ExecutorService executorService = Executors.newScheduledThreadPool()
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(0);
        linkedList.add(3);
        linkedList.add(5);
        linkedList.add(7);
        linkedList.add(6);
        linkedList.add(77);
        linkedList.add(2);
        linkedList.add(1);
        for (int i=0;i<linkedList.size();i++){
            System.out.println(linkedList.get(i));
        }


//        LinkedHashMap<Integer,Integer> map = new LinkedHashMap<>(0,0.75f,false);
//        map.put(0,0);
//        map.put(1,1);
//        map.put(2,2);
//        map.put(3,3);
//        map.put(4,4);
//        map.put(5,5);
//        map.put(6,6);
//        map.put(7,7);
//
//        map.get(1);
//        map.get(2);
//
//        for (Map.Entry<Integer,Integer> entry : map.entrySet()){
//            System.out.println(entry.getKey()+":-----value:"+entry.getValue());
//        }

        //---------------------最近最少次序------true-------
//        0:-----value:0
//        3:-----value:3
//        4:-----value:4
//        5:-----value:5
//        6:-----value:6
//        7:-----value:7
//        1:-----value:1
//        2:-----value:2

        //--------------------插入顺序-------------false------------
//        0:-----value:0
//        1:-----value:1
//        2:-----value:2
//        3:-----value:3
//        4:-----value:4
//        5:-----value:5
//        6:-----value:6
//        7:-----value:7

    }




}
