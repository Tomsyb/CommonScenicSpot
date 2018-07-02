package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.http.RequestData;
import com.android.daqsoft.androidbasics.utils.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 游记详情
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexScenicTravelsXqFragment extends BaseFragment {
    @BindView(R.id.include_img_back)
    ImageView mImgBack;
    @BindView(R.id.include_tv_title)
    TextView mTvTitle;
    private String id = "";

    /**
     * 单例
     * @param id 条目ID
     * @return
     */
    public static IndexScenicTravelsXqFragment newInstance(String id) {
        Bundle args = new Bundle();
        args.putString("ID",id);
        IndexScenicTravelsXqFragment fragment = new IndexScenicTravelsXqFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        mTvTitle.setText("游记攻略详情");
        id = getArguments().getString("ID");
        getData();
    }

    private void getData() {

        RequestData.gettravelStrategyDetail(id, new RequestData.OnDataCallBack() {
            @Override
            public void onSuccess(String response) {

            }

            @Override
            public void onFail() {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_scenic_travels_xq;
    }


    @OnClick(R.id.include_img_back)
    public void onViewClicked() {
        _mActivity.onBackPressed();
    }
}
