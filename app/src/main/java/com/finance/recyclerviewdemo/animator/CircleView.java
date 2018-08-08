package com.finance.recyclerviewdemo.animator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

import java.lang.ref.WeakReference;

/**
 * Created by Jackie on 2018/7/25.
 */
public class CircleView extends View {
    private Paint paint;
    private int mRadious = 0;
    private int SCREEN_WIDTH;
    private int SCREEN_HEIGHT;
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            handler.removeMessages(0x123);
//            handler.sendEmptyMessageDelayed(0x123,10);
//            invalidate();
//        }
//    };

//    private static class MyHandler extends Handler{
//        private WeakReference<CircleView> weakReference;
//        public MyHandler(CircleView circleView){
//
//        }
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//        }
//    }


    public CircleView(Context context) {
        this(context,null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        SCREEN_WIDTH = metric.widthPixels;
        SCREEN_HEIGHT = metric.heightPixels;
        paint = new Paint();
        paint.setColor(Color.parseColor("#408DDC"));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(680,720,mRadious,paint);
        if (mRadious == 0) {
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRadious = mRadious + 20;
                    invalidate();
                }
            },3000);
        }else{
            this.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRadious = mRadious + 20;
                    invalidate();
                }
            },10);
        }
////        if (mRadious == 0){
////            handler.sendEmptyMessageDelayed(0x123,3500);
////        }
//        mRadious = mRadious + 20;
    }

//    @Override
//    protected void onDetachedFromWindow() {
//        super.onDetachedFromWindow();
//        if (handler != null) {
//            handler.removeCallbacksAndMessages(null);
//            handler = null;
//        }
//
//    }
}
