package com.finance.recyclerviewdemo.hook3;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.finance.recyclerviewdemo.R;

/**
 * Created by Jackie on 2018/8/20.
 */
public class TargetAcitivity extends Activity {
    private static final String TAG = "TargetAcitivity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.i(TAG, "onCreate: ----------");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_target);
    }
}
