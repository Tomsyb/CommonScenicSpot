package com.android.daqsoft.androidbasics.adapter.CommonAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * 通用的适配器
 *Created by yulh on 2016/6/15.
 * @param <T>
 */
public abstract class RecycleAdapter<T> extends RecyclerView.Adapter<RecycleViewHolder> {
    protected Context mContext;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;
    private int layoutId;
    private static int mPosition = 0;

    public RecycleAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecycleViewHolder holder = RecycleViewHolder.get(mContext, null, parent,layoutId);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolder holder, int position) {
        convert(holder,mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

//    public int getCount() {
//        return mDatas.size();
//    }
//
//    public T getItem(int position) {
//        return mDatas.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder = ViewHolder.get(mContext, convertView, parent,layoutId, position);
//        setPosition(position);
//        convert(holder, getItem(position));
//        return holder.getConvertView();
//    }

    public static void setPosition(int position) {
        mPosition = position;
    }

    public static int getPosition() {
        return mPosition;
    }

    public abstract void convert(RecycleViewHolder holder, T t);
}
