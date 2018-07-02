package com.android.daqsoft.androidbasics.ui.fragment.index.delagate;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.base.ItemViewDelegate;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.base.ViewHolder;
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.ActualBean;

/**
 *停车场
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class CarDelagate implements ItemViewDelegate<ActualBean>{
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_index_goser_car;
    }

    @Override
    public boolean isForViewType(ActualBean item, int position) {
        return item.getItemType() == 2;
    }

    @Override
    public void convert(ViewHolder holder, ActualBean bean, int position) {
        holder.setText(R.id.item_car_tv_top,bean.getTopName());
    }

}
