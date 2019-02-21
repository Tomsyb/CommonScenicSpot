package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.CommonAdapter;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.base.ViewHolder;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.event.Basebean;
import com.android.daqsoft.androidbasics.ui.ActivityWebView;
import com.android.daqsoft.androidbasics.ui.fragment.other.OtherFragment;
import com.android.daqsoft.androidbasics.ui.fragment.sbsx.SbsxFragment;
import com.android.daqsoft.androidbasics.ui.fragment.text.NowFragment;
import com.android.daqsoft.androidbasics.ui.fragment.text.TextFragment;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ObjectUtils;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.android.daqsoft.androidbasics.view.RxTextViewVertical;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 了解景区
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexScenicFragment extends BaseFragment {
    @BindView(R.id.index_scenic_img_back)
    ImageView imgBack;
    @BindView(R.id.fg_index_scenic_rv)
    RecyclerView mRv;
    @BindView(R.id.run_text)
    RxTextViewVertical mRunText;
    @BindView(R.id.title_yanb)
    TextView mTitle;

    private String huanjin= "";
    private String yuanli= "";
    private String guifan= "";


    private String stationName = "";
    private String deviceId = "";
    private String deviceIP = "";
    private String devicePort = "";
    private String devideUsr = "";
    private String devicePwd = "";

    /**
     * 数据
     */
    private ArrayList<String> titleList = new ArrayList<String>();
    private ArrayList<String> titleIDList = new ArrayList<String>();
    private CommonAdapter<Basebean> adapter;

    //单列
    public static IndexScenicFragment newInstance(String title,String huanjin,String yuanli,
                                                  String guifan,String stationName,String deviceId,
                                                  String deviceIP,String devicePort,String devideUsr,
                                                  String devicePwd) {
        Bundle args = new Bundle();
        args.putString("TITLE",title);
        args.putString("huanjin",huanjin);
        args.putString("yuanli",yuanli);
        args.putString("guifan",guifan);
        args.putString("stationName",stationName);
        args.putString("deviceId",deviceId);
        args.putString("deviceIP",deviceIP);
        args.putString("devicePort",devicePort);
        args.putString("devideUsr",devideUsr);
        args.putString("devicePwd",devicePwd);
        IndexScenicFragment fragment = new IndexScenicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initRecycleview();
        mTitle.setText(getArguments().getString("TITLE"));
        huanjin = getArguments().getString("huanjin");
        yuanli = getArguments().getString("yuanli");
        guifan = getArguments().getString("guifan");
        stationName = getArguments().getString("stationName");
        deviceId = getArguments().getString("deviceId");
        deviceIP = getArguments().getString("deviceIP");
        devicePort = getArguments().getString("devicePort");
        devideUsr = getArguments().getString("devideUsr");
        devicePwd = getArguments().getString("devicePwd");
        getData();
    }

    private void getData() {
        titleList.add("今日检测点位正常，未出现故障");
        titleList.add("机器需要维修");
        mRunText.setTextList(titleList);
        mRunText.setText(12, 2, Color.WHITE);//设置属性
        mRunText.setTextStillTime(3000);//设置停留时长间隔
        mRunText.setAnimTime(300);//设置进入和退出的时间间隔
        mRunText.setOnItemClickListener(new RxTextViewVertical.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mRunText.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        mRunText.stopAutoScroll();
    }

    private void initRecycleview() {
        mRv.setLayoutManager(new GridLayoutManager(_mActivity, 3));
        adapter = new CommonAdapter<Basebean>(_mActivity, R.layout.item_common_img_text, Constant.getSinecList(_mActivity)) {
            @Override
            protected void convert(ViewHolder holder, Basebean bean, final int position) {
                holder.setText(R.id.item_common_tv, bean.getName());
                holder.setImageResource(R.id.item_common_img, bean.getImgId());
                holder.setOnClickListener(R.id.item_common_ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (position) {
                            case 0://景区简介
                                ActivityUtils.startHtmlActivity(huanjin, ActivityWebView.class);
                                //start(IndexPoliceXqFragment.newInstance());
                                break;
                            case 1://游览须知
                                ActivityUtils.startHtmlActivity(yuanli, ActivityWebView.class);
                                break;
                            case 2://票务政策
                                ActivityUtils.startHtmlActivity(guifan, ActivityWebView.class);
                                break;
                            case 3://开放时间
                                start(TextFragment.newInstance(deviceId,deviceIP,devicePort,devideUsr,devicePwd));
                                break;
                            case 4://景区交通
                                start(OtherFragment.newInstance(deviceId,deviceIP,devicePort,devideUsr,devicePwd));
                                break;
                            case 5://当前数据
                                start(NowFragment.newInstance(deviceId,deviceIP,devicePort,devideUsr,devicePwd));
                                break;
                            case 6://景区公告
                                start(IndexPoliceXqFragment.newInstance(stationName,deviceId));
                                break;
                            case 7://游记攻略
                                start(IndexScenicTravelsFragment.newInstance(deviceId));
                                break;
                            case 8://设备属性
                                start(SbsxFragment.newInstance(deviceId,deviceIP,devicePort,devideUsr,devicePwd,stationName,0));
                                break;
                            case 9://设备状态
                                start(SbsxFragment.newInstance(deviceId,deviceIP,devicePort,devideUsr,devicePwd,stationName,1));
                                break;
                            case 10://网络配置
                                start(SbsxFragment.newInstance(deviceId,deviceIP,devicePort,devideUsr,devicePwd,stationName,2));
                                break;
                            case 11://测量参数
                                start(SbsxFragment.newInstance(deviceId,deviceIP,devicePort,devideUsr,devicePwd,stationName,3));
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        };
        mRv.setAdapter(adapter);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_scenic;
    }


    @OnClick(R.id.index_scenic_img_back)
    public void onViewClicked() {
        _mActivity.onBackPressed();
    }

}
