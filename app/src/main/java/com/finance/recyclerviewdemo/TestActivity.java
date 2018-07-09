package com.finance.recyclerviewdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jackie on 2018/7/5.
 */
public class TestActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ListView listView;
    private RecyclerAdapter adapter;
    private List<String> mList;
    private static final String TAG = "TestActivity";
    private Button btn;
    private Button btn_add;
//    String str="{"status":"1","errMsg":"查询成功","data":[{"typeaddtime":"2015-09-09T11:56:11","typeid":1,"typeimages":"http:\/\/192.168.0.188:8080\/msx\/images\/msx.jpg","typename":"蔬菜","typesequence":1,"typesimple":"http:\/\/192.168.0.188:8080\/msx\/images\/msx.jpg","typestate":"1"}]}"
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "setContentView: ----");
        recyclerView = findViewById(R.id.recycler);
        btn_add = findViewById(R.id.btn_add);
        btn = findViewById(R.id.btn);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addData(0);
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.removeData(0);
            }
        });
        mList = new ArrayList<>();
        for (int i=0;i<10;i++){
            mList.add("--jackie--"+i);
        }
        Log.i(TAG, "setContentView: --"+mList.size());
        adapter = new RecyclerAdapter(mList);
        LinearLayoutManager linearLayoutManager
                = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
//        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
//        defaultItemAnimator.setAddDuration(1000);
//        defaultItemAnimator.setRemoveDuration(1000);
//        recyclerView.setItemAnimator(defaultItemAnimator);

//        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);


        //多个RecyclerView公用一个RecycledViewPool
//        RecyclerView.RecycledViewPool recycledViewPool = recyclerView.getRecycledViewPool();
//        RecyclerView recyclerView1 = new RecyclerView(this);
//        recyclerView1.setRecycledViewPool(recycledViewPool);
//        RecyclerView recyclerView2 = new RecyclerView(this);
//        recyclerView2.setRecycledViewPool(recycledViewPool);

        adapter.setMyClickListener(new RecyclerAdapter.OnMyClickListener() {
            @Override
            public void onItemClickListener(View view, int postion) {
                Toast.makeText(TestActivity.this,"---"+postion,Toast.LENGTH_SHORT).show();
            }
        });

//        Gson gson = new Gson();
////        TestUser<User> user = new TestUser<>();
////        user.age = 18;
////        user.name = "jackie";
////        user.number = "007";
////        User user1 = new User();
////        user.t = user1;
////        //生成一个json字符串
////        String myJson = gson.toJson(user);
////        Log.i(TAG, "onCreate: ------"+myJson);
//
//        String json = "{\"age\":18,\"name\":\"jackie\",\"number\":\"007\",\"t\":{\"money\":12306}}";
//
//        //fastjson
////        TestUser<User> testUser = JSON.parseObject(json,new TypeReference<TestUser<User>>(){});
////        Log.i(TAG, "onCreate: ----t.money----"+ testUser.t.money);
//
//        //gson方式
//        TestUser<User> testUser = gson.fromJson(json,new TypeToken<TestUser<User>>(){}.getType());
//        Log.i(TAG, "onCreate: ---gson-----"+testUser.t.money);
//
//        Log.i(TAG, "onCreate: "+testUser.age);   //解析正确，得到的值为 18
//        Log.i(TAG, "onCreate: ------"+testUser.t.money);


        //------------------解析泛型--------------------------
//        BaseBean<CategoryBean> beanBaseBean = new BaseBean<>();
//        beanBaseBean.setErrMsg("00000");
//        beanBaseBean.setStatus(200);
//        List<CategoryBean> list = new ArrayList<>();
//        for (int i=0;i<3;i++){
//            CategoryBean categoryBean = new CategoryBean();
//            categoryBean.setTypeaddtime("A"+i);
//            categoryBean.setTypeimages("B"+i);
//            categoryBean.setTypename("C"+i);
//            list.add(categoryBean);
//        }
//        beanBaseBean.setData(list);
//        String str = gson.toJson(beanBaseBean);
////            String str = "";
//        Log.i(TAG, "onCreate: ---------"+str);
//            BaseBean<CategoryBean> cat = JSON.parseObject(str,new TypeReference<BaseBean<CategoryBean>>(){});
//            List<CategoryBean> list1 = cat.getData();
//        Log.i(TAG, "onCreate: ----"+list1.size());




    }




}
