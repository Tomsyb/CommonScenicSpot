package com.android.daqsoft.androidbasics.ui.fragment.sbsx;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.fastjson.JSONObject;
import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.builder.GetBuilder;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;

/**
 * Created by Administrator on 2018/3/25.
 * 设备属性
 */

public class SbsxFragment extends BaseFragment {
    @BindView(R.id.include_tv_title)
    TextView includeTvTitle;
    @BindView(R.id.tv_tai)
    TextView tvTai;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_seid)
    TextView tvSeid;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_banbenno)
    TextView tvBanbenno;
    @BindView(R.id.tv_company)
    TextView tvCompany;
    Unbinder unbinder;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.tv_3)
    TextView tv3;
    @BindView(R.id.tv_4)
    TextView tv4;
    @BindView(R.id.tv_5)
    TextView tv5;
    @BindView(R.id.tv_6)
    TextView tv6;
    @BindView(R.id.tv_7)
    TextView tv7;
    @BindView(R.id.tv_8)
    TextView tv8;
    @BindView(R.id.tv_8_)
    TextView tv8_;
    @BindView(R.id.ll_8)
    LinearLayout ll8;
    @BindView(R.id.tv_9)
    TextView tv9;
    @BindView(R.id.tv_9_)
    TextView tv9_;
    @BindView(R.id.ll_9)
    LinearLayout ll9;
    @BindView(R.id.tv_10)
    TextView tv10;
    @BindView(R.id.tv_10_)
    TextView tv10_;
    @BindView(R.id.ll_10)
    LinearLayout ll10;
    @BindView(R.id.mva)
    ViewAnimator mVa;
    @BindView(R.id.ll_11)
    LinearLayout ll11;
    @BindView(R.id.ll_12)
    LinearLayout ll12;
    @BindView(R.id.ll_13)
    LinearLayout ll13;
    Unbinder unbinder2;


    private String deviceId = "";
    private String deviceIP = "";
    private String devicePort = "";
    private String devideUsr = "";
    private String devicePwd = "";
    private String name = "";
    private int mType = 0;

    private BaseQuickAdapter<KeyValues, BaseViewHolder> mAdapter;

    //单列
    public static SbsxFragment newInstance(String deviceId,
                                           String deviceIP, String devicePort, String devideUsr,
                                           String devicePwd, String name, int type) {
        Bundle args = new Bundle();
        args.putString("deviceId", deviceId);
        args.putString("deviceIP", deviceIP);
        args.putString("devicePort", devicePort);
        args.putString("devideUsr", devideUsr);
        args.putString("devicePwd", devicePwd);
        args.putString("name", name);
        args.putInt("mType", type);
        SbsxFragment fragment = new SbsxFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        deviceId = getArguments().getString("deviceId");
        deviceIP = getArguments().getString("deviceIP");
        devicePort = getArguments().getString("devicePort");
        devideUsr = getArguments().getString("devideUsr");
        devicePwd = getArguments().getString("devicePwd");
        name = getArguments().getString("name");
        mType = getArguments().getInt("mType");
        getData();
    }

    private void getData() {
        showLoadingDialog();
        String mtag = "";
        if (mType == 0) {
            mtag = "ppy";
            includeTvTitle.setText("设备属性");
        } else if (mType == 1) {
            mtag = "ste";
            includeTvTitle.setText("设备状态");
        }else if (mType == 2) {
            mtag = "pmr";
            includeTvTitle.setText("网络配置");
        }else if (mType == 3) {
            mtag = "pmr";
            includeTvTitle.setText("测量参数");
        }
        GetBuilder getBuilder = OkHttpUtils.get()
                .url(Constant.BASE_URL + "imec/getDeviceAttribute")
                .addParams("deviceID", deviceId)
                .addParams("deviceIP", deviceIP)
                .addParams("devicePort", devicePort)
                .addParams("devideUsr", devideUsr)
                .addParams("devicePwd", devicePwd)
                .addParams("firParameter", mtag);
        if (mType == 2){
            getBuilder.addParams("secParameter","n");
        }if (mType == 3){
            getBuilder.addParams("secParameter","m");
        }
        getBuilder
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        dismissLoadingDialog();
                        mVa.setDisplayedChild(1);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        dismissLoadingDialog();
                        try {
                            JSONObject object = JSONObject.parseObject(response);

                            if (object.getIntValue("resultCode") == 0) {
                                mVa.setDisplayedChild(0);
                                String result = object.getString("data");
                                String[] split = result.split(" ");
                                tvTai.setText(name);
                                tvSeid.setText(deviceId);
                                if (mType == 0) {
                                    ll8.setVisibility(View.GONE);
                                    ll9.setVisibility(View.GONE);
                                    ll10.setVisibility(View.GONE);

                                    tvName.setText(split[1]);
                                    tvType.setText(split[2]);
                                    tvDate.setText(split[5]);
                                    tvPhone.setText(split[6]);
                                    tvAddress.setText(split[4]);
                                    tvCompany.setText(split[3]);
                                    tvBanbenno.setText(split[7]);
                                } else if (mType == 1) {
                                    ll8.setVisibility(View.VISIBLE);
                                    ll9.setVisibility(View.VISIBLE);
                                    ll10.setVisibility(View.VISIBLE);
                                    tv1.setText("设备时钟");
                                    tv2.setText("时钟状态");
                                    tv3.setText("设备零点");
                                    tv4.setText("直流电源状态");
                                    tv5.setText("交流电源状态");
                                    tv6.setText("自校准开关状态");
                                    tv7.setText("调零开关状态");

                                    tv8.setText("事件触发个数");
                                    tv9.setText("异常告警状态");
                                    tv10.setText("自定义状态");

                                    tvName.setText(split[1]);
                                    if (split[2].equals("0")){
                                        tvType.setText("GPS授时");
                                    }else if (split[2].equals("1")){
                                        tvType.setText("SNTP授时");
                                    }else if (split[2].equals("2")){
                                        tvType.setText("内部时钟");
                                    }
                                    tvDate.setText(split[3]);
                                    if (split[4].equals("0")){
                                        tvPhone.setText("正常");
                                    }else if (split[4].equals("1")){
                                        tvPhone.setText("异常");
                                    }
                                    if (split[5].equals("0")){
                                        tvAddress.setText("正常");
                                    }else if (split[5].equals("1")){
                                        tvAddress.setText("异常");
                                    }
                                    if (split[6].equals("0")){
                                        tvCompany.setText("关");
                                    }else if (split[6].equals("1")){
                                        tvCompany.setText("开");
                                    }
                                    if (split[7].equals("0")){
                                        tvBanbenno.setText("关");
                                    }else if (split[7].equals("1")){
                                        tvBanbenno.setText("开");
                                    }
                                    tv8_.setText(split[8]);
                                    tv9_.setText(split[9]);
                                    tv10_.setText(split[10]);
                                }else if (mType == 2){
                                    ll8.setVisibility(View.VISIBLE);
                                    ll9.setVisibility(View.GONE);
                                    ll10.setVisibility(View.GONE);
                                    tv1.setText("IP地址");
                                    tv2.setText("子网掩码");
                                    tv3.setText("缺省网关");
                                    tv4.setText("服务端口数");
                                    tv5.setText("服务端口号");
                                    tv6.setText("管理端地址");
                                    tv7.setText("管理端端口号");
                                    tv8.setText("SNTP服务器地址");

                                    tvName.setText(split[1]);
                                    tvType.setText(split[2]);
                                    tvDate.setText(split[3]);
                                    tvPhone.setText(split[4]);
                                    String no = "";
                                    no = split[5]+" "+split[6]+" "+split[7];
                                    tvAddress.setText(no);
                                    tvCompany.setText(split[8]);
                                    tvBanbenno.setText(split[9]);
                                    tv8_.setText(split[10]);
                                }else if (mType == 3){
                                    ll8.setVisibility(View.GONE);
                                    ll9.setVisibility(View.GONE);
                                    ll10.setVisibility(View.GONE);
                                    ll11.setVisibility(View.GONE);
                                    ll12.setVisibility(View.GONE);
                                    ll13.setVisibility(View.GONE);
                                    tv1.setText("采样率");
                                    tv2.setText("通道数");
                                    tv3.setText("自定义参数个数");
                                    tv4.setText("自定义参数值");
                                    tvName.setText(split[1]);
                                    tvType.setText(split[2]);
                                    tvDate.setText(split[3]);
                                    String no = "";
                                    no = split[4]+" "+split[5]+" "+split[6]+" "+split[7]+" "+split[8]+" "+split[9];
                                    tvPhone.setText(no);
                                }
                            }else {
                                mVa.setDisplayedChild(1);
                            }
                        } catch (Exception e) {
                            mVa.setDisplayedChild(1);
                            e.printStackTrace();
                        }
                        LogUtils.e(response);
                    }
                });
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_sbsx;
    }


    @OnClick({R.id.include_img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.include_img_back:
                _mActivity.onBackPressed();
                break;
        }
    }

}
