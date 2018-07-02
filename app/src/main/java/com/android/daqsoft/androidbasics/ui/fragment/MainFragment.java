package com.android.daqsoft.androidbasics.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.event.TabSelectedEvent;
import com.android.daqsoft.androidbasics.ui.fragment.guide.GuideFragment;
import com.android.daqsoft.androidbasics.ui.fragment.index.IndexFragment;
import com.android.daqsoft.androidbasics.ui.fragment.index.IndexPoliceFragment;
import com.android.daqsoft.androidbasics.ui.fragment.mine.MineFragment;
import com.android.daqsoft.androidbasics.ui.fragment.serve.ServeFragment;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.view.bottombar.BottomBar;
import com.android.daqsoft.androidbasics.view.bottombar.BottomBarTab;
import com.daqi.spot.Config;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.eventbusactivityscope.EventBusActivityScope;
import me.yokeyword.fragmentation.SupportFragment;

import static com.android.daqsoft.androidbasics.common.Constant.FIRST;
import static com.android.daqsoft.androidbasics.common.Constant.FOURTH;
import static com.android.daqsoft.androidbasics.common.Constant.SECOND;
import static com.android.daqsoft.androidbasics.common.Constant.THIRD;

/**
 * Created by 严博 on 2018/3/23.
 * 首页fragment
 */

public class MainFragment extends BaseFragment {
    //再点击一次推出程序
    private static final long WAIT_TIME = 2000L;
    private long TOUCH_TIME = 0;

    @BindView(R.id.bottomBar)
    BottomBar mBottomBar;
    private int mPosition;

    private SupportFragment[] mFrgList = new SupportFragment[4];//fg首页集合

    //单列
    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void init(Bundle savedInstanceState) {
        mBottomBar
                .addItem(new BottomBarTab(_mActivity,R.drawable.tab_home_normal,getString(R.string.home)))
                .addItem(new BottomBarTab(_mActivity,R.drawable.tab_book_normal,getString(R.string.service)))
                .addItem(new BottomBarTab(_mActivity,R.drawable.tab_guide_normal,getString(R.string.guide)))
                .addItem(new BottomBarTab(_mActivity,R.drawable.tab_personal_normal,getString(R.string.mine)));
        mBottomBar.setOnTabSelectedListener(new BottomBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position, int prePosition) {
                mPosition = prePosition;
                LogUtils.e(prePosition);
                showHideFragment(mFrgList[position],mFrgList[prePosition]);
                BottomBarTab tab = mBottomBar.getItem(FIRST);
                //下面设置红点数量
//                if (position == FIRST) {
//                    tab.setUnreadCount(0);
//                } else {
//                    tab.setUnreadCount(tab.getUnreadCount() + 1);
//                }
            }

            @Override
            public void onTabUnselected(int position) {

            }

            @Override
            public void onTabReselected(int position) {
                // 在FirstPagerFragment,FirstHomeFragment中接收, 因为是嵌套的Fragment
                // 主要为了交互: 重选tab 如果列表不在顶部则移动到顶部,如果已经在顶部,则刷新
                EventBusActivityScope.getDefault(_mActivity).post(new TabSelectedEvent(position));
            }
        });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_main;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SupportFragment firstFg = findChildFragment(IndexFragment.class);
        if (firstFg == null) {
            mFrgList[FIRST] = IndexFragment.newInstance();
            mFrgList[SECOND] = ServeFragment.newInstance();
            mFrgList[THIRD] = GuideFragment.newInstance();
            mFrgList[FOURTH] = MineFragment.newInstance();
            loadMultipleRootFragment(R.id.fl_tab_container, FIRST,
                    mFrgList[FIRST],
                    mFrgList[SECOND],
                    mFrgList[THIRD],
                    mFrgList[FOURTH]);
        } else {
            // 这里库已经做了Fragment恢复,所有不需要额外的处理了, 不会出现重叠问题
            // 这里我们需要拿到mFragments的引用
            mFrgList[FIRST] = firstFg;
            mFrgList[SECOND] = findChildFragment(ServeFragment.class);
            mFrgList[THIRD] = findChildFragment(GuideFragment.class);
            mFrgList[FOURTH] = findChildFragment(MineFragment.class);
        }
    }

    /**
     * start other BrotherFragment
     * 就是和这个Fragment同级
     */
    public void startBrotherFragment(SupportFragment targetFragment) {
        start(targetFragment);
    }

    public void setTab(int mPosition){
        mBottomBar.setCurrentItem(mPosition);
    }


    /**
     * 处理回退事件
     *
     * @return
     */
    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - TOUCH_TIME < WAIT_TIME) {
            IApplication.exit();
        } else {
            TOUCH_TIME = System.currentTimeMillis();
            Toast.makeText(_mActivity, R.string.press_again_exit, Toast.LENGTH_SHORT).show();
        }
        return true;
    }

}
