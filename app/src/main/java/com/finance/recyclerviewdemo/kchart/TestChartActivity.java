package com.finance.recyclerviewdemo.kchart;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;

import com.finance.recyclerviewdemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Jackie on 2018/7/10.
 */
public class TestChartActivity extends AppCompatActivity {
    float[] points = new float[]{20,34,11,9,22,33};
    float[] ss = new float[4];
    private static final String TAG = "TestChartActivity";
    private ImageView imageView1,imageView2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        imageView1 = findViewById(R.id.img);
        imageView2 = findViewById(R.id.img2);

//        float[] mArr = new float[]{0,0};
//        Matrix mMatrixValue = new Matrix();
//        mMatrixValue.reset();
//        mMatrixValue.postTranslate(0,-70);
//        mMatrixValue.mapPoints(mArr);
//        Log.i(TAG, "onCreate: --1----"+Arrays.toString(mArr));   //输出（0，-70）
//
//
//        mMatrixValue.postScale(-2,-2);
//        mMatrixValue.mapPoints(mArr);
//        Log.i(TAG, "onCreate: ---2---"+Arrays.toString(mArr));  //输出（0，280）
//
//
//        mMatrixValue.postTranslate(300,300);
//        mMatrixValue.mapPoints(mArr);
//        Log.i(TAG, "onCreate: ---3---"+Arrays.toString(mArr));  //输出（300，-120）
//
//        float[] mArr1 = new float[]{0,0};
//        mMatrixValue.reset();
//        mMatrixValue.postTranslate(0,-70);
//        mMatrixValue.postScale(-2,-2);
//        mMatrixValue.postTranslate(300,300);
//        mMatrixValue.mapPoints(mArr1);
//        Log.i(TAG, "onCreate: ---4---"+Arrays.toString(mArr1));  //输出（300，440）




        final EntryData data = new EntryData();
        for (int i = 0; i < 567; i++) {
            float mult = 100;
            float val = (float) (Math.random() * 40) + mult;

            float high = (float) (Math.random() * 9) + 8f;
            float low = (float) (Math.random() * 9) + 8f;

            float open = (float) (Math.random() * 6) + 1f;
            float close = (float) (Math.random() * 6) + 1f;

            boolean even = i % 2 == 0;

            data.addEntry(new Entry(
                    val + high,
                    val - low,
                    even ? val + open : val - open,
                    even ? val - close : val + close,
                    (int) (Math.random() * 111),
                    ""));
        }

        final KLineChart chart = (KLineChart) findViewById(R.id.chart);
        chart.setData(data);
//        initViews();

//        Matrix matrix = new Matrix();
//        matrix.setScale(2,2);
//        //用当前矩阵改变pts中的值，然后存储在pts中，同上，pts也是存储点的坐标的数组
//        matrix.mapPoints(points);
//        Log.i(TAG, "onCreate: -----"+ Arrays.toString(points));
//        Matrix temp = new Matrix();
//        //矩阵求逆矩阵
//        matrix.invert(temp);
//        Log.i(TAG, "onCreate: ----------"+temp.toString());





    }
    private void initViews() {
        Bitmap bm = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        imageView1.setImageBitmap(bm);
        Bitmap modBm = Bitmap.createBitmap(bm.getWidth()+700, bm.getHeight()+700, bm.getConfig());
        Canvas canvas = new Canvas(modBm);
        canvas.translate(300,300);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAntiAlias(true);
        Matrix matrix = new Matrix();
        //matrix.setRotate(90, bm.getWidth()/2, bm.getHeight()/2);
//        matrix.setTranslate(50, 20);
        matrix.postTranslate(0, -100);
        matrix.postTranslate(100,100);

//        matrix.postScale(-1,-1);
        canvas.drawBitmap(bm, matrix, paint);
        float[] ll = new float[]{10,200,300,400};
        canvas.drawLines(ll,paint);
        imageView2.setImageBitmap(modBm);
    }
}
