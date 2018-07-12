package com.finance.recyclerviewdemo.kchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by Jackie on 2018/7/10.
 */
public class KLineChart extends View {

    private RectF contentRect;

    private Renderer renderer;
    private EntryData mData;
    private float contentMinOffset;
    public KLineChart(Context context) {
        this(context,null);
    }

    public KLineChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public KLineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //最小的边距
        contentMinOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, context.getResources().getDisplayMetrics());
        renderer = new Renderer();
        contentRect = new RectF();

    }

    public void setData(EntryData entryData){
        this.mData = entryData;
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        contentRect.set(contentMinOffset * 2,contentMinOffset,w - contentMinOffset,h - contentMinOffset);
        notifyDataSetChanged(false);
    }

    public void notifyDataSetChanged(boolean invaliadate){
        mData.calcMinMax(0,mData.entries.size());
        renderer.setContentRect(contentRect);
        renderer.setData(mData);
        if (invaliadate){
            invalidate();
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        renderer.render(canvas);
    }
}
