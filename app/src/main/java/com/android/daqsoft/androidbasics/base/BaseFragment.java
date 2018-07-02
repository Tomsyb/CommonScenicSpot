package com.android.daqsoft.androidbasics.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.daqsoft.androidbasics.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 基础Fragment 集成Fragmentmation框架
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public abstract class BaseFragment extends SupportFragment{
    /**
     * 标题栏头部
     */
    private TextView mTitle;
    /**
     * 本类tag
     */
    public static final String TAG = BaseFragment.class.getSimpleName();
    /**
     * 根View
     */
    private View mRootView;
    /**
     * 黄油刀
     */
    private Unbinder mUnbinder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutResId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        init(savedInstanceState);
        return mRootView;
    }


    /**
     *  必须重写的方法 初始化
     * @param savedInstanceState
     */
    public abstract void init(Bundle savedInstanceState);

    /**
     * 获取资源ID
     * @return
     */
    public abstract int getLayoutResId();
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }

    /**
     * toorbar
     * @param toolbar
     */
    protected void initToolbarNav(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _mActivity.onBackPressed();
            }
        });
    }
}
