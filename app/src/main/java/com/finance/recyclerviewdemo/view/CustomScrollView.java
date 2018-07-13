package com.finance.recyclerviewdemo.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.widget.OverScroller;
import android.widget.TextView;

/**
 * Created by Jackie on 2018/7/13.
 */
public class CustomScrollView extends ViewGroup {
    private int SCREEN_WIDTH = 0;
    private int SCREEN_HEIGHT = 0;
    private int mWidth = 0;
    private int mHeight = 0;
    private Context context;
    private int mTouchSlop;
    public static final int SCROLL_STATE_IDLE = 0;  //空闲状态
    public static final int SCROLL_STATE_DRAGGING = 1;
    public static final int SCROLL_STATE_SETTLING = 2; //滑动后自然沉降的状态
    private int mScrollState;
    private int mLastTouchY;
    private static final int INVALID_POINTER = -1;
    private int mScrollPointerId = INVALID_POINTER;
    private static final String TAG = "CustomScrollView";
    private VelocityTracker mVelocityTracker;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private ViewFlinger mViewFlinger = new ViewFlinger();
    public CustomScrollView(Context context) {
        this(context,null);
    }

    public CustomScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        DisplayMetrics metric = context.getResources().getDisplayMetrics();
        SCREEN_WIDTH = metric.widthPixels;
        SCREEN_HEIGHT = metric.heightPixels;
        ViewConfiguration configuration = ViewConfiguration.get(context);
        //超过这个就算移动距离
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinFlingVelocity = configuration.getScaledMinimumFlingVelocity();
        //最大的fling速度
        mMaxFlingVelocity = configuration.getScaledMaximumFlingVelocity();



    }

    //多指触控
    //以新加入的手指的滑动为准
    //当有一个手指抬起时，以剩下的手指的滑动为准(第一次按下的点)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);
        int actionIndex = MotionEventCompat.getActionIndex(event);
        Log.i(TAG, "onTouchEvent: ---actionIndex---"+actionIndex);
        //获取速度
        if (mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
        }
        boolean eventAddedToVelocityTracker = false;
        MotionEvent vtev = MotionEvent.obtain(event);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                setScrollState(SCROLL_STATE_IDLE);
                mScrollPointerId = event.getPointerId(0);
                mLastTouchY = (int) (event.getY() + 0.5f);
//                Log.i(TAG, "onTouchEvent: ----按下的点--"+mLastTouchY);
                break;
            case MotionEventCompat.ACTION_POINTER_DOWN:
                mScrollPointerId = event.getPointerId(actionIndex);
                mLastTouchY = (int) (event.getY(actionIndex) + 0.5f);
//                Log.i(TAG, "onTouchEvent: ----多点触控有效的点----"+mLastTouchY);
                break;
            case MotionEvent.ACTION_MOVE:
                int index = event.findPointerIndex(mScrollPointerId);
                if (index < 0){
                    return false;
                }


                int y = (int) (event.getY(index) + 0.5f);
//                Log.i(TAG, "onTouchEvent: -----现在听谁的------"+y + "-----"+index+"-------mScrollPointerId-----"+mScrollPointerId);
                int dy = mLastTouchY - y;
                if (mScrollState != SCROLL_STATE_DRAGGING){
                    boolean startScroll = false;
                    if (Math.abs(dy) > mTouchSlop){
                        if (dy > 0){
                            dy -= mTouchSlop;
                        }else {
                            dy += mTouchSlop;
                        }
                        startScroll = true;
                    }
                    if (startScroll){
                        setScrollState(SCROLL_STATE_DRAGGING);
                    }
                }
                if (mScrollState == SCROLL_STATE_DRAGGING){
                    mLastTouchY = y;
                    contrainScrollBy(0,dy);
                }

                break;
            case MotionEventCompat.ACTION_POINTER_UP:
                if (event.getPointerId(actionIndex) == mScrollPointerId){  //这里的处理就是当最后一个点放开后（两个或三个），以第一次按下的点为准
                    int newIndex = actionIndex == 0 ? 1 : 0;
                    mScrollPointerId = event.getPointerId(newIndex);
                    mLastTouchY = (int) (event.getY(newIndex) + 0.5f);
                }

                break;


            case MotionEvent.ACTION_UP:
                mVelocityTracker.addMovement(vtev);
                eventAddedToVelocityTracker = true;
                mVelocityTracker.computeCurrentVelocity(1000,mMaxFlingVelocity);
                float yVelocity = -VelocityTrackerCompat.getYVelocity(mVelocityTracker,mScrollPointerId);
                if (Math.abs(yVelocity) < mMinFlingVelocity) {
                    yVelocity = 0F;
                } else {
                    yVelocity = Math.max(-mMaxFlingVelocity, Math.min(yVelocity, mMaxFlingVelocity));
                }
                if (yVelocity != 0) {
                    mViewFlinger.fling((int) yVelocity);
                } else {
                    setScrollState(SCROLL_STATE_IDLE);
                }
                resetTouch();
                break;

        }
        if (!eventAddedToVelocityTracker){
            mVelocityTracker.addMovement(vtev);
        }
        vtev.recycle();
        return true;
    }

    private void resetTouch(){
        if (mVelocityTracker != null){
            mVelocityTracker.clear();
        }
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int top = 0;
        for (int i = 0;i < 20;i++){
            int width = SCREEN_WIDTH;
            int height = SCREEN_HEIGHT/2;
            int left = 0;
            int right = left + width;
            int bottom = top + height;
            //撑大边界
            if (bottom > mHeight){
                mHeight = bottom;
            }
            if (right > mWidth){
                mWidth = right;
            }
            TextView textView = new TextView(context);
            if (i % 2 == 0){
                textView.setBackgroundColor(Color.CYAN);
            }else{
                textView.setBackgroundColor(Color.GREEN);
            }
            textView.setText("item  "+i);
            addView(textView);
            textView.layout(left,top,right,bottom);
            top = top + height;
            top += 20;
        }
    }

    //插值器   //f(x) = (x-1)^5 + 1
    private static final Interpolator sQuinticInterpolator = new Interpolator() {
        @Override
        public float getInterpolation(float t) {
            t -= 1.0f;
            return t * t * t * t * t + 1.0f;
        }
    };
    private void setScrollState(int state) {
        if (state == mScrollState) {
            return;
        }
        mScrollState = state;
        if (state != SCROLL_STATE_SETTLING) {
            mViewFlinger.stop();
        }
    }

    private class ViewFlinger implements Runnable{

        private int mLastFlingY = 0;
        private OverScroller mScroller;
        private boolean mEatRunOnAnimationRequest = false;
        private boolean mReSchedulePostAnimationCallback = false;

        public ViewFlinger(){
            mScroller = new OverScroller(getContext(),sQuinticInterpolator);
        }
        @Override
        public void run() {
            disableRunOnAnimationRequests();
            final OverScroller scroller = mScroller;
            if (scroller.computeScrollOffset()){
                int y = scroller.getCurrY();
                int dy = y - mLastFlingY;
                mLastFlingY = y;
                contrainScrollBy(0,dy);
                postOnAnimation();
            }
            enableRunOnAnimationRequests();

        }
        public void fling(int velocityY){
            mLastFlingY = 0;
            setScrollState(SCROLL_STATE_SETTLING);
            mScroller.fling(0,0,0,velocityY,Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            postOnAnimation();
        }

        public void stop(){
            removeCallbacks(this);
            mScroller.abortAnimation();
        }

        private void disableRunOnAnimationRequests() {
            mReSchedulePostAnimationCallback = false;
            mEatRunOnAnimationRequest = true;
        }

        private void enableRunOnAnimationRequests() {
            mEatRunOnAnimationRequest = false;
            if (mReSchedulePostAnimationCallback) {
                postOnAnimation();
            }
        }
        void postOnAnimation() {
            if (mEatRunOnAnimationRequest) {
                mReSchedulePostAnimationCallback = true;
            } else {
                removeCallbacks(this);
                ViewCompat.postOnAnimation(CustomScrollView.this, this);
            }
        }

    }
    private void contrainScrollBy(int dx,int dy){
        Rect viewPort = new Rect();
        getGlobalVisibleRect(viewPort);
        int height = viewPort.height();
        int width = viewPort.width();

        int scrollX = getScrollX();
        int scrollY = getScrollY();

        //右边界
        if (mWidth - scrollX - dx < width) {
            dx = mWidth - scrollX - width;
        }
        //左边界
        if (-scrollX - dx > 0) {
            dx = -scrollX;
        }
        //下边界
        Log.i(TAG, "contrainScrollBy: --1---------下边界----"+dy+"---scrollY:"+scrollY+"----mHeight:"+mHeight+"------height:"+height);
        if (mHeight - scrollY - dy < height) {      //
            //所有子View的总高度   -   getScrollY()  -  一个屏幕的高度            如果等于0，说明到底了，然后每次dy = 0,然后上拉的时候就不会再进入了，dy就一直等于0（除非下拉）
            dy = mHeight - scrollY - height;
            Log.i(TAG, "contrainScrollBy: --2--下边界----"+dy+"---scrollY:"+scrollY+"----mHeight:"+mHeight+"------height:"+height);
        }
        //上边界
        if (scrollY + dy < 0) {
            dy = -scrollY;
            Log.i(TAG, "contrainScrollBy: ----上边界----"+dy+"---scrollY:"+scrollY);
        }
        scrollBy(dx, dy);



    }




}
