package com.android.daqsoft.androidbasics.ui.fragment.text;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ObjectUtils;
import com.android.daqsoft.androidbasics.view.CustomDialog;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/25.
 * 网络测试
 */

public class TextFragment extends BaseFragment {
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
    public static TextFragment newInstance(String deviceId,
                                           String deviceIP, String devicePort, String devideUsr,
                                           String devicePwd) {
        Bundle args = new Bundle();
        args.putString("deviceId", deviceId);
        args.putString("deviceIP", deviceIP);
        args.putString("devicePort", devicePort);
        args.putString("devideUsr", devideUsr);
        args.putString("devicePwd", devicePwd);
        TextFragment fragment = new TextFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        includeTvTitle.setText("网络测试");
        deviceId = getArguments().getString("deviceId");
        deviceIP = getArguments().getString("deviceIP");
        devicePort = getArguments().getString("devicePort");
        devideUsr = getArguments().getString("devideUsr");
        devicePwd = getArguments().getString("devicePwd");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_text;
    }



    @OnClick({R.id.include_img_back, R.id.btn_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.include_img_back:
                _mActivity.onBackPressed();
                break;
            case R.id.btn_start:
                getData();
                break;
        }
    }

    private void getData() {
        showLoadingDialog();
        GetBuilder getBuilder = OkHttpUtils.get()
                .url(Constant.BASE_URL + "imec/getDeviceAttribute")
                .addParams("deviceID", deviceId)
                .addParams("deviceIP", deviceIP)
                .addParams("devicePort", devicePort)
                .addParams("devideUsr", devideUsr)
                .addParams("devicePwd", devicePwd)
                .addParams("firParameter", "ping");
        getBuilder
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dismissLoadingDialog();
                        tv_show.setText("接口请求错误!");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            dismissLoadingDialog();
                            JSONObject object = JSONObject.parseObject(response);
                            if (object.getIntValue("resultCode")==0){
                                tv_show.setText(object.getString("data"));
                            }else {
                                tv_show.setText("接口请求错误!");
                            }
                        }catch (Exception e){
                            tv_show.setText("接口请求错误!");
                            e.printStackTrace();
                        }
                    }
                });

    }

}
