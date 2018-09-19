package com.finance.recyclerviewdemo.kchart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.finance.recyclerviewdemo.kchart.bean.NewEntry;
import com.finance.recyclerviewdemo.kchart.bean.NewEntryData;

/**
 * Created by Jackie on 2018/9/19.
 */
public class LineRender {
    protected Paint mPaint;
    private NewEntryData data;
    private RectF contentRectF;
    protected float mBarSpace = 0.8f;
    // contain 2 points to draw a rect.
    protected float[] bodyBuffer = new float[4];
    protected float maxTouchOffset, minTouchOffset;
    public LineRender(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //看锯齿
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);
    }
    /**
     * matrix to map the values to the screen pixels
     * 矩阵将值映射到屏幕像素。
     */
    protected Matrix mMatrixValue = new Matrix();

    //和滚动相关的矩阵
    protected Matrix mMatrixTouch = new Matrix();
    private static final String TAG = "LineRender";
    public void setData(NewEntryData data){
        this.data = data;
        prepareMatrixTouch(5);
        prepareMatrixValue(data.mYMax,0);  //就按120 计算高度
    }


    //绘制所有
    public void render(Canvas canvas){

//        calc();
        Log.i(TAG, "render: ---："+contentRectF.left+"   top:"+contentRectF.top+"    right:"+contentRectF.right+"    bottom:"+contentRectF.bottom);
        canvas.clipRect(contentRectF.left,contentRectF.top,contentRectF.right,contentRectF.bottom);
        mPaint.setColor(Color.RED);
//        canvas.drawCircle(100,100,20f,mPaint);
        for (int i = 0;i<data.entries.size();i++){
//            Log.i(TAG, "render: "+data.entries.size());
            NewEntry entry = data.entries.get(i);

            bodyBuffer[0] = i+1 - mBarSpace;
            bodyBuffer[1] = entry.money;
            bodyBuffer[2] = i +mBarSpace;
            bodyBuffer[3] = 0;
//            Log.i(TAG, "render: "+bodyBuffer[0]+"-----"+bodyBuffer[1]+"----"+bodyBuffer[2]+"-----"+bodyBuffer[3]);
            mapPoints(bodyBuffer);
//            Log.i(TAG, "render:0000000000: "+bodyBuffer[0]+"-----"+bodyBuffer[1]+"----"+bodyBuffer[2]+"-----"+bodyBuffer[3]+"---------"+i);
            canvas.drawRect(bodyBuffer[0],bodyBuffer[1],bodyBuffer[2],bodyBuffer[3],mPaint);

        }

    }


    private void resetScrollRange(float scaleX){
        minTouchOffset = 0;
        //最大的可平移距离，因为已经有了一屏显示了，所以要减 1，可移动的距离
        maxTouchOffset = contentRectF.width() * (scaleX - 1f);

    }




    protected void calc(){
        float[] pixels = new float[]{
                contentRectF.right, 0
        };
        revertMapPoints(pixels);

        prepareMatrixValue(data.mYMax - data.mYMin,0);

    }

    protected void mapPoints(float[] pts){
        mMatrixValue.mapPoints(pts);
        mMatrixTouch.mapPoints(pts);
    }

    protected void revertMapPoints(float[] pixels){
        Matrix temp = new Matrix();
        // invert all matrices to convert back to the original value 反转所有矩阵以获取初始值
        mMatrixValue.invert(temp);//相当于求逆矩阵，tem就是逆矩阵
        temp.mapPoints(pixels);
    }




    public void prepareMatrixValue(float deltaY,float yMin){
        Log.i(TAG, "prepareMatrixValue: -----"+contentRectF.width()+"   "+contentRectF.height());
        float scaleX = contentRectF.width()/data.entries.size();
        float scaleY = contentRectF.height()/deltaY;
        mMatrixValue.reset();
        mMatrixValue.postScale(scaleX,-scaleY);
        mMatrixValue.postTranslate(0,contentRectF.height() + contentRectF.top);
    }
    //区域
    public void setContentRect(RectF contentRect){
        this.contentRectF = contentRect;
    }

    public void prepareMatrixTouch(float visableCount){
        float scaleX = data.entries.size()/visableCount;
        float scaleY = 1;
        mMatrixTouch.reset();
        mMatrixTouch.postScale(scaleX,scaleY);

        //把矩阵移动，向右移动，实现一开始显示最左边的数据，这里无需移动，因为一开始就在最左


    }



}
