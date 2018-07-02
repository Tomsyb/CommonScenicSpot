package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
import com.android.daqsoft.androidbasics.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: YanBo.
 * @Date: 2018-4-28.
 * @Describe: 出行服务
 */

public class IndexGoServiceFragment extends BaseFragment {
    @BindView(R.id.go_service_img_back)
    ImageView mImgBack;
    @BindView(R.id.go_service_tv_msg)
    TextView mTvMsg;
    @BindView(R.id.go_service_tv_bus)
    TextView mTvBus;
    @BindView(R.id.go_service_tv_titck)
    TextView mTvTitck;


    public static IndexGoServiceFragment newInstance() {
        Bundle args = new Bundle();
        IndexGoServiceFragment fragment = new IndexGoServiceFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_go_service;
    }



    @OnClick({R.id.go_service_img_back, R.id.go_service_tv_msg, R.id.go_service_tv_bus, R.id.go_service_tv_titck})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.go_service_img_back:
                _mActivity.onBackPressed();
                break;
            case R.id.go_service_tv_msg://实时信息

                break;
            case R.id.go_service_tv_bus://公交查询

                break;
            case R.id.go_service_tv_titck://火车票查询

                break;
        }
    }
}
