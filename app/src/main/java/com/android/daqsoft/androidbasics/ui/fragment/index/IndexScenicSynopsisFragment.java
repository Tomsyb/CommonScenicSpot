package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.utils.img.GlideImageLoader;
import com.android.daqsoft.androidbasics.utils.img.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 简介景区
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexScenicSynopsisFragment extends BaseFragment {
    @BindView(R.id.img_scenic_synopsis_top)
    ImageView imgTop;
    @BindView(R.id.img_scenic_synopsis_back)
    ImageView imgBack;

    //单列
    public static IndexScenicSynopsisFragment newInstance() {
        Bundle args = new Bundle();
        IndexScenicSynopsisFragment fragment = new IndexScenicSynopsisFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        GlideUtils.GlideImg(_mActivity,"http://ww4.sinaimg.cn/large/006uZZy8jw1faic1xjab4j30ci08cjrv.jpg",imgTop);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_scenic_synopsis;
    }




    @OnClick({R.id.img_scenic_synopsis_top, R.id.img_scenic_synopsis_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_scenic_synopsis_top:
                break;
            case R.id.img_scenic_synopsis_back:
                _mActivity.onBackPressed();
                break;
        }
    }
}
