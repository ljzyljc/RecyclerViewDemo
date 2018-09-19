package com.finance.recyclerviewdemo.hook1;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.finance.recyclerviewdemo.R;

/**
 * Created by Jackie on 2018/8/17.
 */
public class Activity2 extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hook);
    }
}
