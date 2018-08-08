package com.finance.recyclerviewdemo.animator;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.finance.recyclerviewdemo.R;

import java.util.Hashtable;

/**
 * Created by Jackie on 2018/7/25.
 */
public class StartActivity extends Activity {
    private static final String TAG = "StartActivity";
    private ImageView first_hua,sec_hua,third_hua,four_hua,five_hua,six_hua;
    private static final int TIME = 2500;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            animatorSetFive.start();
            Log.i(TAG, "handleMessage: -------------");
        }
    };
    AnimatorSet animatorSetFive;
    private Interpolator accelerateInterpolator = new LinearInterpolator();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        init();

        ObjectAnimator animator = ObjectAnimator.ofFloat(four_hua,"rotation",0f,225f);
        animator.setDuration(TIME);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(four_hua, "alpha", 1f, 0f);
        animatorAlpha.setInterpolator(accelerateInterpolator);
        animatorAlpha.setDuration(TIME);



        ObjectAnimator animator1 = ObjectAnimator.ofFloat(first_hua,"rotation",0f,180f);
        animator1.setDuration(TIME);
        ObjectAnimator animator1Alpha = ObjectAnimator.ofFloat(first_hua, "alpha", 1f, 0f);
        animator1Alpha.setInterpolator(accelerateInterpolator);
        animator1Alpha.setDuration(TIME);


        ObjectAnimator animator2 = ObjectAnimator.ofFloat(sec_hua,"rotation",0f,135f);
        animator2.setDuration(TIME);
        ObjectAnimator animator2Alpha = ObjectAnimator.ofFloat(sec_hua, "alpha", 1f, 0f);
        animator2Alpha.setInterpolator(accelerateInterpolator);
        animator2Alpha.setDuration(TIME);

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(third_hua,"rotation",0f,90f);
        animator3.setDuration(TIME);
        ObjectAnimator animator3Alpha = ObjectAnimator.ofFloat(third_hua, "alpha", 1f, 0f);
        animator3Alpha.setInterpolator(accelerateInterpolator);
        animator3Alpha.setDuration(TIME);
//
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator,animatorAlpha,animator1,animator1Alpha,animator2,animator2Alpha,animator3,animator3Alpha);
//        animatorSet.playTogether(animator,animatorAlpha);
        animatorSet.start();
        handler.sendEmptyMessageDelayed(0x123,1800);


        ObjectAnimator fiveRotation = ObjectAnimator.ofFloat(five_hua,"rotation",0f,90f);
        fiveRotation.setDuration(1500);
        ObjectAnimator fiveAlpha = ObjectAnimator.ofFloat(five_hua, "alpha", 0f, 1f);
        fiveAlpha.setDuration(1500);
        animatorSetFive = new AnimatorSet();
        animatorSetFive.playTogether(fiveRotation,fiveAlpha);



//        animatorSet.addListener(new Animator.AnimatorListener() {
//            @Override
//            public void onAnimationStart(Animator animation) {
//                Log.i(TAG, "onAnimationStart: ----------------------");
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Log.i(TAG, "run: ----------------");
//                        
//                    }
//                },1800);
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                Log.i(TAG, "onAnimationEnd: --------------------------------");
////                animatorSetFive.start();
//            }
//
//            @Override
//            public void onAnimationCancel(Animator animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animator animation) {
//
//            }
//        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (handler != null){
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }

    public void init(){
        first_hua = findViewById(R.id.first_hua);
        sec_hua = findViewById(R.id.sec_hua);
        third_hua = findViewById(R.id.third_hua);
        four_hua = findViewById(R.id.four_hua);
        five_hua = findViewById(R.id.five_hua);
        six_hua = findViewById(R.id.six_hua);
    }
}
