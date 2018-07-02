package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 公告详情
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexScenicGongGaoXqFragment extends BaseFragment {
    @BindView(R.id.include_img_back)
    ImageView mImgBack;
    @BindView(R.id.include_tv_title)
    TextView mTvTitle;

    //单列
    public static IndexScenicGongGaoXqFragment newInstance() {
        Bundle args = new Bundle();
        IndexScenicGongGaoXqFragment fragment = new IndexScenicGongGaoXqFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        mTvTitle.setText("公告详情");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_scenic_gonggao_xq;
    }


    @OnClick(R.id.include_img_back)
    public void onViewClicked() {
        _mActivity.onBackPressed();
    }
}
