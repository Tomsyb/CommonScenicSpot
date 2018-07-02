package com.android.daqsoft.androidbasics.ui.fragment.index.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.android.daqsoft.androidbasics.ui.fragment.index.bean.IndexVPBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexPagerAdapter extends PagerAdapter {
    private List<IndexVPBean> list;
    private Context context;
    private List<View> viewList = new ArrayList<View>();

    public IndexPagerAdapter(List<IndexVPBean> list, Context context) {
        this.list = list;
        this.context = context;
        if (list!=null &&list.size()>0){
            IndexPagerWindow window;
            for (int i = 0; i < list.size(); i++) {
                window = new IndexPagerWindow(context);
                window.setModel(list.get(i), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                viewList.add(window);
            }
        }
    }


    @Override
    public int getCount() {
        return list == null?0:list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position % viewList.size()));
    }
    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position % viewList.size()));
        return viewList.get(position % viewList.size());
    }


}
