package com.finance.recyclerviewdemo.kchart;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.VelocityTrackerCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ScrollerCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.finance.recyclerviewdemo.kchart.bean.NewEntryData;

import static android.support.v4.view.ViewCompat.TYPE_NON_TOUCH;
import static android.support.v4.view.ViewCompat.TYPE_TOUCH;
import static android.support.v4.widget.ViewDragHelper.INVALID_POINTER;

/**
 * Created by Jackie on 2018/9/19.
 */
public class LineChart extends View {
    private RectF contentRect;
    private LineRender lineRender;
    private float contentLeftOffset;
    private NewEntryData mData;
    private VelocityTracker mVelocityTracker;
    private int mMinFlingVelocity;
    private int mMaxFlingVelocity;
    private int mTouchSlop;
    private int mScrollPointerId = INVALID_POINTER;
    private int mLastTouchX;
    private int mLastTouchY;
    private ViewFlinger mViewFlinger = new ViewFlinger();
    /**
     * The RecyclerView is not currently scrolling.
     * @see #getScrollState()
     */
    public static final int SCROLL_STATE_IDLE = 0;

    /**
     * The RecyclerView is currently being dragged by outside input such as user touch input.
     * @see #getScrollState()
     */
    public static final int SCROLL_STATE_DRAGGING = 1;

    /**
     * The RecyclerView is currently animating to a final position while not under
     * outside control.
     * @see #getScrollState()
     */
    public static final int SCROLL_STATE_SETTLING = 2;
    public LineChart(Context context) {
        this(context,null);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LineChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        ViewConfiguration vc = ViewConfiguration.get(this.getContext());
        mTouchSlop = vc.getScaledTouchSlop();
        mMaxFlingVelocity = vc.getScaledMaximumFlingVelocity();
        mMinFlingVelocity = vc.getScaledMinimumFlingVelocity();


        contentRect = new RectF();
        lineRender = new LineRender();
        contentLeftOffset = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15, context.getResources().getDisplayMetrics());

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        contentRect.set(contentLeftOffset ,contentLeftOffset,w - contentLeftOffset,h - contentLeftOffset*1.5f);
        notifyDataSetChanged(false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        lineRender.render(canvas);

    }

    private static final String TAG = "LineChart";
    @Override
    public boolean onTouchEvent(MotionEvent e) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        boolean eventAddedToVelocityTracker = false;
        final MotionEvent vtev = MotionEvent.obtain(e);
        final int action = MotionEventCompat.getActionMasked(e);
        final int actionIndex = MotionEventCompat.getActionIndex(e);
        switch (action){
            case MotionEvent.ACTION_DOWN:
                mScrollPointerId = e.getPointerId(0);
                mLastTouchX = (int) (e.getX() + 0.5f);
                mLastTouchY = (int) (e.getY() + 0.5f);
                if (mScrollState == SCROLL_STATE_SETTLING) {
                    setScrollState(SCROLL_STATE_DRAGGING);
                }
                Log.i(TAG, "onTouchEvent: ------down--------");
                break;
            case MotionEventCompat.ACTION_POINTER_DOWN: {
                mScrollPointerId = MotionEventCompat.getPointerId(e, actionIndex);
                mLastTouchX = (int) (MotionEventCompat.getX(e, actionIndex) + 0.5f);
                mLastTouchY = (int) (MotionEventCompat.getY(e, actionIndex) + 0.5f);
                break;
            }
            case MotionEvent.ACTION_MOVE:
                final int index = e.findPointerIndex(mScrollPointerId);
                final int x = (int) (e.getX(index) + 0.5f);
                final int y = (int) (e.getY(index) + 0.5f);
                int dx = mLastTouchX - x;
                int dy = mLastTouchY - y;
                if (mScrollState != SCROLL_STATE_DRAGGING) {
                    boolean startScroll = false;
                    if (Math.abs(dx) > mTouchSlop) {
                        if (dx > 0) {
                            dx -= mTouchSlop;
                        } else {
                            dx += mTouchSlop;
                        }
                        startScroll = true;
                    }
                    if (Math.abs(dy) > mTouchSlop) {
                        if (dy > 0) {
                            dy -= mTouchSlop;
                        } else {
                            dy += mTouchSlop;
                        }
                        startScroll = true;
                    }
                    if (startScroll) {
                        setScrollState(SCROLL_STATE_DRAGGING);
                    }
                }

                if (mScrollState == SCROLL_STATE_DRAGGING) {
                    mLastTouchX = x;
                    mLastTouchY = y;
                    scroll(dx,0);
                    Log.i(TAG, "onTouchEvent: ----------move---------------");
                }
                break;
            case MotionEventCompat.ACTION_POINTER_UP: {
                final int actionIndexDD = e.getActionIndex();
                if (e.getPointerId(actionIndexDD) == mScrollPointerId) {
                    // Pick a new pointer to pick up the slack.
                    final int newIndex = actionIndexDD == 0 ? 1 : 0;
                    mScrollPointerId = e.getPointerId(newIndex);
                    mLastTouchX = (int) (e.getX(newIndex) + 0.5f);
                    mLastTouchY = (int) (e.getY(newIndex) + 0.5f);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                mVelocityTracker.addMovement(vtev);
                eventAddedToVelocityTracker = true;
                mVelocityTracker.computeCurrentVelocity(1000, mMaxFlingVelocity);
                final float xvel =
                        -VelocityTrackerCompat.getXVelocity(mVelocityTracker, mScrollPointerId);
                final float yvel =
                        -VelocityTrackerCompat.getYVelocity(mVelocityTracker, mScrollPointerId);
                if (!((xvel != 0 || yvel != 0) && fling((int) xvel, (int) yvel))) {
                    setScrollState(SCROLL_STATE_IDLE);
                }
                resetTouch();
                Log.i(TAG, "onTouchEvent: ---------------up-------");
            }
            break;
            case MotionEvent.ACTION_CANCEL:
                resetTouch();
                setScrollState(SCROLL_STATE_IDLE);
                break;
        }
        if (!eventAddedToVelocityTracker) {
            mVelocityTracker.addMovement(vtev);
        }
        vtev.recycle();

        return true;
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
    private class ViewFlinger implements Runnable{
        private int mLastFlingX;
        private int mLastFlingY;
        private ScrollerCompat mScroller;
        final Interpolator sQuinticInterpolator = new Interpolator() {
            @Override
            public float getInterpolation(float t) {
                t -= 1.0f;
                return t * t * t * t * t + 1.0f;
            }
        };
        public ViewFlinger(){
            mScroller = ScrollerCompat.create(getContext(),sQuinticInterpolator);
        }
        @Override
        public void run() {
            final ScrollerCompat scroller = mScroller;
            if (scroller.computeScrollOffset()){
                final int x = mScroller.getCurrX();
                final int y = mScroller.getCurrY();
                int dx = x - mLastFlingX;
                int dy = y - mLastFlingY;
                mLastFlingX = x;
                mLastFlingY = y;
                scroll(dx,0);
                if (lineRender.canScroll() && !scroller.isFinished()){
                    postOnAnimation();
                }
            }
        }

        void postOnAnimation(){
            removeCallbacks(this);
            ViewCompat.postOnAnimation(LineChart.this,this);
        }
        public void fling(int velocityX, int velocityY){
            setScrollState(SCROLL_STATE_SETTLING);
            mLastFlingX = mLastFlingY = 0;
            mScroller.fling(0, 0, velocityX, velocityY,
                    Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE);
            postOnAnimation();
        }

        public void stop(){
            removeCallbacks(this);
            mScroller.abortAnimation();
        }


    }
    private void resetTouch() {
        if (mVelocityTracker != null) {
            mVelocityTracker.clear();
        }
    }
    public boolean fling(int velocityX, int velocityY) {

        if (Math.abs(velocityX) < mMinFlingVelocity) {
            velocityX = 0;
        }
        if (Math.abs(velocityY) < mMinFlingVelocity) {
            velocityY = 0;
        }
        if (velocityX == 0 && velocityY == 0) {
            // If we don't have any velocity, return false
            return false;
        }

        velocityX = Math.max(-mMaxFlingVelocity, Math.min(velocityX, mMaxFlingVelocity));
        velocityY = Math.max(-mMaxFlingVelocity, Math.min(velocityY, mMaxFlingVelocity));
        mViewFlinger.fling(velocityX, velocityY);
        return true;
    }


    public void scroll(float dx,float dy){
        Log.i(TAG, "scroll: --------"+dx +"---"+dy);
        lineRender.refreshTouchMatrix(dx,dy);
        invalidate();
    }

    private int mScrollState = SCROLL_STATE_IDLE;
    private static final int INVALID_POINTER = -1;
    public int getScrollState() {
        return mScrollState;
    }

    void setScrollState(int state) {
        if (state == mScrollState) {
            return;
        }

        mScrollState = state;
        if (state != SCROLL_STATE_SETTLING) {
            stopScrollersInternal();
        }
    }
    private void stopScrollersInternal() {
        mViewFlinger.stop();
    }
}
