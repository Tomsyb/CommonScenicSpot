package com.android.daqsoft.androidbasics.adapter.CommonAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * 通用的适配器
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public abstract class CommonAdapter<T> extends BaseAdapter {
    /**
     * 上下文
     */
    protected Context mContext;
    /**
     * 数据
     */
    protected List<T> mDatas;
    /**
     * 布局
     */
    protected LayoutInflater mInflater;
    /**
     * 布局id
     */
    private int layoutId;
    /**
     * 位置
     */
    private static int mPosition = 0;
    /**
     * 类型
     */
    private String type;

    public void setType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }

    public CommonAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = datas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        if (null == mDatas) {
            return 0;
        }
        return mDatas.size();
    }

    @Override
    public T getItem(int position) {
        if (null == mDatas) {
            return null;
        }
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent,
                layoutId, position);
        setPosition(position);
        convert(holder, getItem(position));
        return holder.getConvertView();
    }

    public static void setPosition(int position) {
        mPosition = position;
    }

    public static int getPosition() {
        return mPosition;
    }

    public abstract void convert(ViewHolder holder, T t);


}
