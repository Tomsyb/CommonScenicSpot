package com.android.daqsoft.androidbasics.ui.fragment.index.delagate;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.base.ItemViewDelegate;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.base.ViewHolder;
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.ActualBean;

/**
 *景区客流量
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class SenicKeDelagate implements ItemViewDelegate<ActualBean>{
    @Override
    public int getItemViewLayoutId() {
        return R.layout.item_index_goser_senic;
    }

    @Override
    public boolean isForViewType(ActualBean item, int position) {
        return item.getItemType() == 1;
    }

    @Override
    public void convert(ViewHolder holder, ActualBean bean, int position) {
        holder.setText(R.id.item_scenic_tv_top,bean.getTopName());
    }

}
