package com.android.daqsoft.androidbasics.ui.fragment.other;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by Administrator on 2018/3/25.
 */

public class OtherFragment extends BaseFragment {
    //单列
    public static OtherFragment newInstance(){
        Bundle args = new Bundle();
        OtherFragment fragment = new OtherFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_other;
    }


}
