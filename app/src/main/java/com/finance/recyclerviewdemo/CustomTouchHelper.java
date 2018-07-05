package com.finance.recyclerviewdemo;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.widget.BaseAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Created by Jackie on 2018/7/5.
 */
public class CustomTouchHelper extends ItemTouchHelper.SimpleCallback{
    private List<String> mList;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    public CustomTouchHelper(int dragDirs, int swipeDirs, List<String> list, RecyclerView recyclerView,RecyclerView.Adapter adapter) {
        super(dragDirs, swipeDirs);
        this.mList = list;
        this.recyclerView = recyclerView;
        this.adapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();  //得到拖动ViewHolder的position
        int toPosition = target.getAdapterPosition();
        if (fromPosition < toPosition) {
            for (int i= fromPosition;i<toPosition;i++) {
                Collections.swap(mList,i,i+1);
            }
        }else {
            for (int i= fromPosition;i > toPosition;i--) {
                Collections.swap(mList,i,i-1);
            }
        }
        adapter.notifyItemMoved(fromPosition,toPosition);

        return true;
    }
    //移除
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int postion = viewHolder.getAdapterPosition();
        mList.remove(postion);
        adapter.notifyItemRemoved(postion);
    }

    //绘制Item,可以设置都透明度，位移
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE){
            //滑动时改变Item的透明度
            float alpha = 1 - Math.abs(dX)/viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        }
    }
}
