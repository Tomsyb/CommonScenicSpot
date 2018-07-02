package com.android.daqsoft.androidbasics.ui.fragment.index.delagate;

import android.view.View;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.base.ItemViewDelegate;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.base.ViewHolder;
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.ActualBean;
import com.android.daqsoft.androidbasics.utils.LogUtils;

/**
 *实时天气
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class WeatherDelagate implements ItemViewDelegate<ActualBean>{
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_index_goser_actual;
    }

    @Override
    public boolean isForViewType(ActualBean item, int position) {
        return item.getItemType() == 0;
    }

    @Override
    public void convert(ViewHolder holder, ActualBean bean, int position) {
        holder.setText(R.id.item_actual_tv_top,bean.getTopName());
        holder.setOnClickListener(R.id.item_actual_tv_more, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.e("");
            }
        });
    }
}
