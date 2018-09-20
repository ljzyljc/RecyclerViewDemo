package com.finance.recyclerviewdemo.kchart;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.Log;

import com.finance.recyclerviewdemo.kchart.bean.NewEntry;
import com.finance.recyclerviewdemo.kchart.bean.NewEntryData;

import java.text.DecimalFormat;
import java.util.Arrays;

/**
 * Created by Jackie on 2018/9/19.
 */
public class LineRender {
    protected Paint mPaint,mLabelPaint;
    private NewEntryData data;
    private RectF contentRectF;
    protected float mBarSpace = 0.4f;
    // contain 2 points to draw a rect.
    protected float[] bodyBuffer = new float[4];
    private float[] calcTemp = new float[]{0, 0};
    protected float maxTouchOffset, minTouchOffset;
    private float defaultTextSize;
    public LineRender(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG); //看锯齿
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(1);

        mLabelPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLabelPaint.setColor(Color.BLACK);

    }
    /**
     * matrix to map the values to the screen pixels
     * 矩阵将值映射到屏幕像素。
     */
    protected Matrix mMatrixValue = new Matrix();

    //和滚动相关的矩阵
    protected Matrix mMatrixTouch = new Matrix();
    /**
     * matrix to map the chart offset
     * 映射图表偏移的矩阵
     */
    protected Matrix mMatrixOffset = new Matrix();
    private static final String TAG = "LineRender";
    private Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    public void setData(NewEntryData data){
        this.data = data;
        prepareMatrixTouch(visibleCount);
        prepareMatrixValue(data.mYMax,0);  //就按120 计算高度
        //FIXME：变换后再进行平移，比较准确
        prepareMatrixOffset(contentRectF.left,contentRectF.top);
    }


    //绘制所有
    public void render(Canvas canvas){
        calc();
        renderLabels(canvas);
        Log.i(TAG, "render: ---："+contentRectF.left+"   top:"+contentRectF.top+"    right:"+contentRectF.right+"    bottom:"+contentRectF.bottom);
        canvas.save();
        canvas.clipRect(contentRectF.left,contentRectF.top,contentRectF.right,contentRectF.bottom);
        mPaint.setColor(Color.RED);
        Log.i(TAG, "render: ----"+ data.entries.size());
//        canvas.drawCircle(100,100,20f,mPaint);
        for (int i = 0;i<data.entries.size();i++){
            if (i == 0){
                mPaint.setColor(Color.BLUE);
            }else {
                mPaint.setColor(Color.RED);
            }
//            Log.i(TAG, "render: "+data.entries.size());
            NewEntry entry = data.entries.get(i);

            bodyBuffer[0] = entry.month - mBarSpace;
            bodyBuffer[1] = entry.money;
            bodyBuffer[2] = entry.month;
            bodyBuffer[3] = 0;
//            Log.i(TAG, "render: "+bodyBuffer[0]+"-----"+bodyBuffer[1]+"----"+bodyBuffer[2]+"-----"+bodyBuffer[3]);
            mapPoints(bodyBuffer);
            Log.i(TAG, "render:0000000000: "+bodyBuffer[0]+"-----"+bodyBuffer[1]+"----"+bodyBuffer[2]+"-----"+bodyBuffer[3]+"---------"+i);
            canvas.drawRect(bodyBuffer[0],bodyBuffer[1],bodyBuffer[2],bodyBuffer[3],mPaint);

        }
        canvas.restore();

    }
    protected DecimalFormat decimalFormatter = new DecimalFormat("0.00");
    /**
     * Draw x and y labels.
     */
    protected void renderLabels(Canvas canvas) {
        // DRAW Y LABELS
        mLabelPaint.setTextAlign(Paint.Align.RIGHT);

        //draw max y value
        calcTemp[1] = contentRectF.top;
        revertMapPoints(calcTemp);
        String value = decimalFormatter.format(calcTemp[1]);
        mLabelPaint.setTextSize(10);
        mLabelPaint.setTextSize(contentRectF.left * 9 / mLabelPaint.measureText(value));
        mLabelPaint.getFontMetrics(fontMetrics);
        canvas.drawText(
                value,
                contentRectF.left * 9 / 10,
                contentRectF.top - fontMetrics.top - fontMetrics.bottom,
                mLabelPaint);

        // draw min y value
        calcTemp[1] = contentRectF.bottom;
        revertMapPoints(calcTemp);
        value = decimalFormatter.format(calcTemp[1]);
        mLabelPaint.setTextSize(10);
        mLabelPaint.setTextSize(contentRectF.left * 10 / mLabelPaint.measureText(value));
        mLabelPaint.getFontMetrics(fontMetrics);
        canvas.drawText(
                value,
                contentRectF.left * 9 / 10,
                contentRectF.bottom - fontMetrics.bottom,
                mLabelPaint);

        calcTemp[1] = contentRectF.height() / 3 + contentRectF.top;
        revertMapPoints(calcTemp);
        value = decimalFormatter.format(calcTemp[1]);
        mLabelPaint.setTextSize(10);
        mLabelPaint.setTextSize(contentRectF.left * 10 / mLabelPaint.measureText(value));
        mLabelPaint.getFontMetrics(fontMetrics);
        canvas.drawText(
                value,
                contentRectF.left * 9 / 10,
                contentRectF.height() / 3 + contentRectF.top + fontMetrics.bottom,
                mLabelPaint);

        calcTemp[1] = contentRectF.height() * 2 / 3 + contentRectF.top;
        revertMapPoints(calcTemp);
        value = decimalFormatter.format(calcTemp[1]);
        mLabelPaint.setTextSize(10);
        mLabelPaint.setTextSize(contentRectF.left * 10 / mLabelPaint.measureText(value));
        mLabelPaint.getFontMetrics(fontMetrics);
        canvas.drawText(
                value,
                contentRectF.left * 9 / 10,
                contentRectF.height() * 2 / 3 + contentRectF.top + fontMetrics.bottom,
                mLabelPaint);

        canvas.save();
        canvas.clipRect(contentRectF.left, contentRectF.bottom, contentRectF.right, contentRectF.bottom + 30);
        mLabelPaint.setTextAlign(Paint.Align.CENTER);
        mLabelPaint.setTextSize(defaultTextSize);
        mLabelPaint.getFontMetrics(fontMetrics);
        for (int i = visibleXMin; i < 12; i++) {
            if (i % 2 == 0) {
                calcTemp[0] = i + 0.5f;
                mapPoints(calcTemp);
                canvas.drawText(
                        i + "",
                        calcTemp[0],
                        contentRectF.bottom + 15,
                        mLabelPaint);
//                canvas.drawLine(calcTemp[0], contentRectF.top, calcTemp[0], contentRectF.bottom, mLabelPaint);
            }
        }
        canvas.restore();
    }

    protected float[] matrixValues = new float[9];
    //刷新矩阵
    public void refreshTouchMatrix(float dx,float dy){
        isOnBorder = true;
        mMatrixTouch.getValues(matrixValues);
        matrixValues[Matrix.MTRANS_X] += -dx;
        matrixValues[Matrix.MTRANS_Y] += dy;

        if (matrixValues[Matrix.MTRANS_X] < -maxTouchOffset) {
            matrixValues[Matrix.MTRANS_X] = -maxTouchOffset;
            isOnBorder = false;
        }
        if (matrixValues[Matrix.MTRANS_X] > 0) {
            matrixValues[Matrix.MTRANS_X] = 0;
            isOnBorder = false;
        }

        mMatrixTouch.setValues(matrixValues);


    }
    private boolean isOnBorder = true;
    public boolean canScroll() {
        return isOnBorder;
    }


    //坐标点的映射
    protected void mapPoints(float[] pts){
        mMatrixValue.mapPoints(pts);
        mMatrixTouch.mapPoints(pts);
        mMatrixOffset.mapPoints(pts);
    }

    protected void revertMapPoints(float[] pixels){
        Matrix temp = new Matrix();
        // invert all matrices to convert back to the original value 反转所有矩阵以获取初始值
        mMatrixValue.invert(temp);//相当于求逆矩阵，tem就是逆矩阵
        temp.mapPoints(pixels);
        mMatrixTouch.invert(temp);
        temp.mapPoints(pixels);
        Log.i(TAG, "revertMapPoints: ---2---" + Arrays.toString(pixels));
        mMatrixValue.invert(temp);
        temp.mapPoints(pixels);
    }




    public void prepareMatrixValue(float deltaY,float yMin){
        Log.i(TAG, "prepareMatrixValue: -----"+contentRectF.width()+"   "+contentRectF.height());
        float scaleX = contentRectF.width()/data.entries.size();
        float scaleY = contentRectF.height()/deltaY;
        mMatrixValue.reset();
        mMatrixValue.postScale(scaleX,-scaleY);
        mMatrixValue.postTranslate(0,contentRectF.height());
    }

    public void prepareMatrixOffset(float leftOffset,float topOffset){
        mMatrixOffset.reset();
        mMatrixOffset.postTranslate(leftOffset,topOffset);
    }

    //区域
    public void setContentRect(RectF contentRect){
        this.contentRectF = contentRect;
        defaultTextSize = contentRectF.top * 3 / 4;
    }

    public void prepareMatrixTouch(float visableCount){
        float scaleX = data.entries.size()/visableCount;
        float scaleY = 1;
        mMatrixTouch.reset();
        mMatrixTouch.postScale(scaleX,scaleY);
        resetScrollRange(scaleX);
        //把矩阵移动，向右移动，实现一开始显示最左边的数据，这里无需移动，因为一开始就在最左
//        mMatrixTouch.postTranslate(-maxTouchOffset,0);

    }
    private void resetScrollRange(float scaleX){
        minTouchOffset = 0;
        //最大的可平移距离，因为已经有了一屏显示了，所以要减 1，可移动的距离
        maxTouchOffset = contentRectF.width() * (scaleX - 1f);

    }

    protected int visibleCount = 6;
    protected int visibleXMin, visibleXMax;
    protected void calc(){
            // calc step 0: calc min&max x index
            float[] pixels = new float[]{
                    contentRectF.right, 0
            };
            revertMapPoints(pixels);
            visibleXMin = (pixels[0] <= 0) ? 0 : (int) (pixels[0]);
            visibleXMax = visibleXMin + visibleCount;// plus visibleCount+1 for smooth disappear both side.
            Log.i(TAG, "calc: ========" + Arrays.toString(pixels));
            if (visibleXMax > data.entries.size()) {
                visibleXMax = data.entries.size();
            }

            // calc step 1: calc min&max y value
            data.calcMinMax(visibleXMin, visibleXMax);
            prepareMatrixValue(data.mYMax, 0);
//            prepareMatrixBar(data.mMaxYVolume);


    }


}
