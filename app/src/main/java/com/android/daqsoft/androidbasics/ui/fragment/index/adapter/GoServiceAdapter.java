package com.android.daqsoft.androidbasics.ui.fragment.index.adapter;

import android.content.Context;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.MultiItemTypeAdapter;
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.ActualBean;
import com.android.daqsoft.androidbasics.ui.fragment.index.delagate.CarDelagate;
import com.android.daqsoft.androidbasics.ui.fragment.index.delagate.SenicKeDelagate;
import com.android.daqsoft.androidbasics.ui.fragment.index.delagate.WeatherDelagate;


import java.util.List;


/**
 * 出行服务实时信息适配器
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class GoServiceAdapter extends MultiItemTypeAdapter<ActualBean>{

    public GoServiceAdapter(Context context, List<ActualBean> datas) {
        super(context, datas);
        //多少布局就加多少
        addItemViewDelegate(new WeatherDelagate());
        addItemViewDelegate(new SenicKeDelagate());
        addItemViewDelegate(new CarDelagate());
    }
}
