package com.android.daqsoft.androidbasics.adapter.CommonAdapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.android.daqsoft.androidbasics.R;

import java.util.List;

/**
 * 首页菜单栏的适配器
 */
public class MygridviewSearchAdapter extends BaseAdapter {

    public List<String> data;
    private LayoutInflater _inflater;
    private Context context;

    public MygridviewSearchAdapter(Context context, List<String> data) {
        this.data = data;
        this.context = context;
        _inflater = LayoutInflater.from(context);
    }

    public void updateList(List<String> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (convertView == null) {
            convertView = _inflater.inflate(R.layout.item_search_style, null);
            holder = new ViewHolder();
            holder.tv_value = (TextView) convertView.findViewById(R.id.item_search_value);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.tv_value.setText(data.get(position));
        return convertView;
    }

    private class ViewHolder {
        private TextView tv_value;// 文字


    }
}
