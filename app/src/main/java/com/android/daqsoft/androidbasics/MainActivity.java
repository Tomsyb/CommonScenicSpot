package com.android.daqsoft.androidbasics;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.android.daqsoft.androidbasics.base.BaseActivity;
import com.android.daqsoft.androidbasics.ui.fragment.MainFragment;
import com.android.daqsoft.androidbasics.ui.fragment.index.IndexFragment;
import com.android.daqsoft.androidbasics.utils.BarUtils;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;
import me.yokeyword.fragmentation.anim.DefaultNoAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * @Author: YanBo.
 * @Date: 2018-4-28.
 * @Describe: 基础首页
 */
public class MainActivity extends BaseActivity {

    @BindView(R.id.fl_container)
    FrameLayout mFlContainer;
    @Override
    public int getContentViewResId() {
        return R.layout.activity_main;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        if (findFragment(IndexFragment.class) == null) {
            loadRootFragment(R.id.fl_container, IndexFragment.newInstance());
        }
    }


    @Override
    public void onBackPressedSupport() {
        // 对于4个类别的主Fragment内的回退back逻辑,已经在其onBackPressedSupport里各自处理了
        super.onBackPressedSupport();

    }

    /**
     * start other BrotherFragment
     * 就是和这个Fragment同级
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 设置横向(和安卓4.x动画相同)
        return new DefaultNoAnimator();
    }



}
