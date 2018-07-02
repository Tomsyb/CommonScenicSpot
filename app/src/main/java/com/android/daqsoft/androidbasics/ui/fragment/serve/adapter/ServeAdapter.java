package com.android.daqsoft.androidbasics.ui.fragment.serve.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.event.Basebean;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by 严博 on 2018-4-13.
 * 预定服务的适配器
 */

public class ServeAdapter extends BaseQuickAdapter<Basebean,BaseViewHolder>{

    public ServeAdapter(int layoutResId, @Nullable List<Basebean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Basebean item) {
        helper.setImageResource(R.id.item_serve_img,item.getImgId());

    }
}
