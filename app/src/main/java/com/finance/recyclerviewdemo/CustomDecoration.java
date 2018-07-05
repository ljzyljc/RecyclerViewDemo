package com.finance.recyclerviewdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Jackie on 2018/7/5.
 */
public class CustomDecoration extends RecyclerView.ItemDecoration {
    private int mOrientation;
    private Drawable mDivider;
    private int [] attrs = new int[]{android.R.attr.listDivider};

    public CustomDecoration(Context context,int orientation){

        TypedArray typedArray = context.obtainStyledAttributes(attrs);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (mOrientation == LinearLayoutManager.VERTICAL){
            outRect.set(0,0,0,mDivider.getIntrinsicHeight());
        }else{//水平
            outRect.set(0,0,mDivider.getIntrinsicWidth(),0);
        }
    }
}
