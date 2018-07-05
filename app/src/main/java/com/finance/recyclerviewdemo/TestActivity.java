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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jackie on 2018/7/5.
 */
public class TestActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private List<String> mList;
    private static final String TAG = "TestActivity";
    private Button btn;
    private Button btn_add;
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
        ItemTouchHelper.Callback callback = new CustomTouchHelper(ItemTouchHelper.UP|ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT,
                mList,recyclerView,adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.setMyClickListener(new RecyclerAdapter.OnMyClickListener() {
            @Override
            public void onItemClickListener(View view, int postion) {
                Toast.makeText(TestActivity.this,"---"+postion,Toast.LENGTH_SHORT).show();
            }
        });
    }




}
