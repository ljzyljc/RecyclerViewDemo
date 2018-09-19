package com.finance.recyclerviewdemo.viewgroup;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by Jackie on 2018/8/8.
 */
public class SimpleViewGroup extends ViewGroup {

    private static final String TAG = "SimpleViewGroup";
    
    public SimpleViewGroup(Context context) {
        super(context);
    }

    public SimpleViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.i(TAG, "SimpleViewGroup: ----------------");
        setWillNotDraw(false);
    }

    public SimpleViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int with = MeasureSpec.getSize(widthMeasureSpec);
//        int height =  MeasureSpec.getSize(heightMeasureSpec);
//        int withMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heigthMode = MeasureSpec.getMode(heightMeasureSpec);
//        if (getChildCount() == 0) {//如果没有子View,当前ViewGroup没有存在的意义，不用占用空间
//            setMeasuredDimension(0, 0);
//            return;
//        }
//        if (withMode == MeasureSpec.AT_MOST && heigthMode == MeasureSpec.AT_MOST){
//            //高度累加，宽度取最大
//            setMeasuredDimension(getMaxChildWidth(),getTotleHeight());
//        }else if (heigthMode == MeasureSpec.AT_MOST){
//            setMeasuredDimension(with,getTotleHeight());
//        }else if (withMode == MeasureSpec.AT_MOST){
//            setMeasuredDimension(getMaxChildWidth(),height);
//        }
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0;i<count;i++){
            measureChild(getChildAt(i),MeasureSpec.EXACTLY,MeasureSpec.EXACTLY);
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        int currentHeight = 0;
        for (int i = 0;i < count;i++){
            Log.i(TAG, "onLayout: ------------"+count);
            View child = getChildAt(i);

            int childHeight = child.getMeasuredHeight();
            Log.i(TAG, "onLayout: -------"+child.getMeasuredHeight()+"----"+child.getHeight());
            Log.i(TAG, "onLayout: -------left:"+l+"top:"+currentHeight+"right:"+child.getMeasuredWidth()+"bottom:"+currentHeight + childHeight);
            child.layout(l,currentHeight,l+child.getMeasuredWidth(),currentHeight + childHeight);
//            child.layout(l,currentHeight,150,60);
            currentHeight += childHeight;
        }
//        int count = getChildCount();
//        int currentHeigth = 0;
//        //将子View逐个摆放
//        for (int i = 0; i < count; i++) {
//            View child = getChildAt(i);
//
//            int childHeigth = child.getMeasuredHeight();
//            child.layout(l, currentHeigth, r + child.getMeasuredWidth(), currentHeigth + childHeigth);
//            currentHeigth += childHeigth;
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.i(TAG, "onDraw: -------");
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        Log.i(TAG, "draw: ----------");
    }
}
