package com.android.daqsoft.androidbasics.ui.fragment.guide;

import android.os.Bundle;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;

/**
 * @Author: YanBo.
 * @Date: 2018-4-28.
 * @Describe: 导游导览
 */
/**
 * 网页请求数据
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class GuideFragment extends BaseFragment {
    //单列
    public static GuideFragment newInstance(){
        Bundle args = new Bundle();
        GuideFragment fragment = new GuideFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_guide;
    }

}
