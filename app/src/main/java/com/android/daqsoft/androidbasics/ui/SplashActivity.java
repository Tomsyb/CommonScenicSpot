package com.android.daqsoft.androidbasics.ui;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.daqsoft.androidbasics.MainActivity;
import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseActivity;
import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.http.RequestData;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
import com.android.daqsoft.androidbasics.utils.BarUtils;

import butterknife.BindView;

/**
 * @Author: YanBo.
 * @Date: 2018-4-28.
 * @Describe: 引导页
 */
public class SplashActivity extends BaseActivity {
    @BindView(R.id.tv_title)
    TextView ivLogo;

    @Override
    public int getContentViewResId() {
        return R.layout.activity_splash;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initBaseData();
        BarUtils.setNavBarVisibility(this,false);
        PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.3f, 1f);
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.3f, 1f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.3f, 1f);
        ObjectAnimator objectAnimator2 = ObjectAnimator.ofPropertyValuesHolder(ivLogo, alpha, scaleX, scaleY);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(objectAnimator2);
        animatorSet.setInterpolator(new AccelerateInterpolator());
        animatorSet.setDuration(1000);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                ActivityUtils.startActivity(MainActivity.class);
                finish();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorSet.start();
    }

    private void initBaseData() {
        RequestData.getSiteNoticeList(1, "", "", new RequestData.OnDataCallBack() {
            @Override
            public void onSuccess(String response) {
                IApplication.SP.put(Constant.JQGGLIST,response);
            }

            @Override
            public void onFail() {
                IApplication.SP.put(Constant.JQGGLIST,"");
            }

            @Override
            public void onFinish() {
            }
        });
    }

}
