package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.CommonAdapter;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.MultiItemTypeAdapter;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.base.ViewHolder;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.ui.fragment.index.adapter.GoServiceAdapter;
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.ActualBean;
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.GongGaoBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *出行服务实时信息
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexGoServiceActualFragment extends BaseFragment {
    @BindView(R.id.include_img_back)
    ImageView ImgBack;
    @BindView(R.id.include_tv_title)
    TextView TvTitle;
    @BindView(R.id.actual_rv)
    RecyclerView mRv;
    List<ActualBean> mDatas = new ArrayList<>();

    /**
     *
     * @return 单列
     */
    public static IndexGoServiceActualFragment newInstance() {
        Bundle args = new Bundle();
        IndexGoServiceActualFragment fragment = new IndexGoServiceActualFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initData();
        initView();
    }

    private void initData() {
        ActualBean bean = null;
        bean = new ActualBean();
        bean.setItemType(0);
        bean.setWeather("10°");
        bean.setTravelNum("89");
        bean.setContent("今天星期五");
        bean.setTopName("实时天气");
        mDatas.add(bean);

        bean = new ActualBean();
        bean.setItemType(1);
        bean.setTopName("景区客流量");
        bean.setShushiNum("非常舒适");
        bean.setKeNum("555");
        bean.setToadayNum("788");
        bean.setMaxNum("74874");
        mDatas.add(bean);

        bean = new ActualBean();
        bean.setItemType(2);
        bean.setTopName("停车场");
        bean.setBottomContent("南门停车场");
        bean.setCarshen("110");
        bean.setCarAll("777");
        mDatas.add(bean);
    }


    private void initView() {
        TvTitle.setText("实时信息");
        mRv.setLayoutManager(new LinearLayoutManager(_mActivity));
        mRv.setAdapter(new GoServiceAdapter(_mActivity,mDatas));
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_goser_actual;
    }


    @OnClick(R.id.include_img_back)
    public void onViewClicked() {
        _mActivity.onBackPressed();
    }

}
