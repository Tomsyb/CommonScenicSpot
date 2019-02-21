package com.android.daqsoft.androidbasics.ui.fragment.text;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/25.
 * 当前数据
 */

public class NowFragment extends BaseFragment {
    @BindView(R.id.tv_show)
    TextView tv_show;
    @BindView(R.id.include_tv_title)
    TextView includeTvTitle;
    private String deviceId = "";
    private String deviceIP = "";
    private String devicePort = "";
    private String devideUsr = "";
    private String devicePwd = "";


    //单列
    public static NowFragment newInstance(String deviceId,
                                          String deviceIP, String devicePort, String devideUsr,
                                          String devicePwd) {
        Bundle args = new Bundle();
        args.putString("deviceId", deviceId);
        args.putString("deviceIP", deviceIP);
        args.putString("devicePort", devicePort);
        args.putString("devideUsr", devideUsr);
        args.putString("devicePwd", devicePwd);
        NowFragment fragment = new NowFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        includeTvTitle.setText("当前数据");
        deviceId = getArguments().getString("deviceId");
        deviceIP = getArguments().getString("deviceIP");
        devicePort = getArguments().getString("devicePort");
        devideUsr = getArguments().getString("devideUsr");
        devicePwd = getArguments().getString("devicePwd");
        getData();
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_now;
    }



    @OnClick({R.id.include_img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.include_img_back:
                _mActivity.onBackPressed();
                break;
        }
    }

    private void getData(){
        showLoadingDialog();
        GetBuilder getBuilder = OkHttpUtils.get()
                .url(Constant.BASE_URL + "imec/getDeviceAttribute")
                .addParams("deviceID", deviceId)
                .addParams("deviceIP", deviceIP)
                .addParams("devicePort", devicePort)
                .addParams("devideUsr", devideUsr)
                .addParams("devicePwd", devicePwd)
                .addParams("firParameter", "dat")
                .addParams("secParameter","5");

        getBuilder.build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dismissLoadingDialog();
                        tv_show.setText("暂无数据!");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            dismissLoadingDialog();
                            JSONObject object = JSONObject.parseObject(response);
                            if (object.getIntValue("resultCode")==0){
                                String value = object.getString("data");
                                tv_show.setText(value);
                            }else {
                                tv_show.setText("暂无数据!");
                            }
                        }catch (Exception e){
                            tv_show.setText("暂无数据!");
                            e.printStackTrace();
                        }
                    }
                });
    }

}
