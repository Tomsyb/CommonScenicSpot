package com.android.daqsoft.androidbasics.ui.fragment.text;

import android.os.Bundle;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;

/**
 * Created by Administrator on 2018/3/25.
 */

public class TextFragment extends BaseFragment {
    //单列
    public static TextFragment newInstance(){
        Bundle args = new Bundle();
        TextFragment fragment = new TextFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {

    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_text;
    }


}
