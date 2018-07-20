package com.android.daqsoft.androidbasics.ui.fragment.other;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.view.ChartEntity;
import com.android.daqsoft.androidbasics.view.LineChart;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/25.
 */

public class OtherFragment extends BaseFragment {
    @BindView(R.id.include_img_back)
    ImageView includeImgBack;
    @BindView(R.id.include_tv_title)
    TextView includeTvTitle;
    @BindView(R.id.chart)
    LineChart mLineChart;

    //单列
    public static OtherFragment newInstance() {
        Bundle args = new Bundle();
        OtherFragment fragment = new OtherFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        includeTvTitle.setText("数据曲线");
        List<ChartEntity> data = new ArrayList<>();
        for(int i =0;i<24;i++){
            data.add(new ChartEntity(String.valueOf(i), (float) (Math.random()*1000)));
        }
        mLineChart.setData(data);
        mLineChart.startAnimation(2000);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_other;
    }



    @OnClick(R.id.include_img_back)
    public void onViewClicked() {
        _mActivity.onBackPressed();
    }
}
