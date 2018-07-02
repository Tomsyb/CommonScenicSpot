package com.android.daqsoft.androidbasics.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



import java.util.ArrayList;
import java.util.List;

/**
 * @Author: YanBo.
 * @Date: 2018-4-28.
 * @Describe: RecyclerView.Adapter 的基类
 * E 为集合数据类型泛型
 * T 为自定义RecycleViewHolder的子类
 */
public abstract class RecyclerBaseAdapter<E, T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    protected LayoutInflater inflater;
    protected Context context;
    protected List<E> list;

    public RecyclerBaseAdapter(Context context) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.list = new ArrayList<E>();
    }

    @Override
    public abstract T onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(T holder, int position);

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setData(List<E> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    public void add(E e) {
        this.list.add(e);
        this.notifyDataSetChanged();
    }

    public void add(E e, int index) {
        this.list.add(index, e);
        this.notifyItemChanged(index);
    }

    public void addAll(List<E> eList) {
        this.list.addAll(eList);
        this.notifyDataSetChanged();
    }

    public void delete(int index) {
        this.list.remove(index);
        this.notifyDataSetChanged();
    }

    public List<E> getList() {
        return list;
    }
}
