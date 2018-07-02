package com.android.daqsoft.androidbasics.base;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.daqsoft.androidbasics.utils.StatusBarCompat;
import com.android.daqsoft.androidbasics.utils.Utils;
import com.android.daqsoft.androidbasics.view.CustomDialog;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * BaseActivity 基类Activity
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public abstract class BaseActivity extends SupportActivity {
    /**
     * 黄油刀绑定View
     */
    private Unbinder mUnbinder;
    /**
     * 请求管理者
     */
    protected RequestManager glideManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        Utils.getActivityList().add(this);
        IApplication.mActivity = this;
        glideManager = Glide.with(this);
        mUnbinder = ButterKnife.bind(this);
        init(savedInstanceState);
    }

    public abstract int getContentViewResId();

    public abstract void init(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
    private CustomDialog logDialog;

    /**
     * 下面是加载框
     */
    public void showLoadingDialog() {
        if (logDialog == null) {
            logDialog = new CustomDialog(this,"加载中...");
        }
        logDialog.show();
    }

    /**
     * 消失
     */
    public void dismissLoadingDialog() {
        if (logDialog != null) {
            logDialog.dismiss();
        }
    }

    /**
     * 沉浸状态栏（4.4以上系统有效）
     */
    protected void SetTranslanteBar(){
        StatusBarCompat.translucentStatusBar(this);
    }
}
