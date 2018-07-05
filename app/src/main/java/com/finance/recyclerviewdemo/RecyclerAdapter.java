package com.finance.recyclerviewdemo;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Jackie on 2018/7/5.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private static final String TAG = "RecyclerAdapter";
    private List<String> mList;
    public RecyclerAdapter(List<String> list){
        this.mList = list;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        if (myClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    myClickListener.onItemClickListener(v,position);
                }
            });
        }
        holder.textView.setText(mList.get(position));
    }









    public void removeData(int position){
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void addData(int position){
        mList.add(position,"add");
        notifyItemInserted(position);
    }


    @Override
    public int getItemCount() {
        Log.i(TAG, "getItemCount: -----"+mList.size());
        return mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text);
        }
    }

    private OnMyClickListener myClickListener;

    public void setMyClickListener(OnMyClickListener myClickListener) {
        this.myClickListener = myClickListener;
    }

    public interface OnMyClickListener{
        void onItemClickListener(View view,int postion);
    }

}
