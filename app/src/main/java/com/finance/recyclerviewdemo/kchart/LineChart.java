package com.finance.recyclerviewdemo.kchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.finance.recyclerviewdemo.kchart.bean.NewEntryData;

/**
 * Created by Jackie on 2018/9/19.
 */
public class LineChart extends View {
    private RectF contentRect;
    private LineRender lineRender;
    private float contentLeftOffset;
    private NewEntryData mData;

    public LineChart(Context context) {
        this(context,null);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        contentRect = new RectF();
        lineRender = new LineRender();
        contentLeftOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, context.getResources().getDisplayMetrics());

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        contentRect.set(contentLeftOffset * 2,contentLeftOffset,w - contentLeftOffset,h - contentLeftOffset);
        notifyDataSetChanged(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        lineRender.render(canvas);

    }

    public void setmData(NewEntryData mData) {
        this.mData = mData;
    }

    public void notifyDataSetChanged(boolean invalitate){
        mData.calcMinMax(0,mData.entries.size());
        lineRender.setContentRect(contentRect);
        lineRender.setData(mData);
        if (invalitate){
            invalidate();
        }
    }
}
