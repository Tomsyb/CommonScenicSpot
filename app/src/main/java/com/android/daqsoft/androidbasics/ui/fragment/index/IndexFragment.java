package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.daqsoft.androidbasics.Main2Activity;
import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.CommonAdapter;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.base.ViewHolder;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.event.IndexYiBean;
import com.android.daqsoft.androidbasics.ui.ActivityWebView;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.android.daqsoft.androidbasics.utils.img.GlideImageLoader;
import com.android.daqsoft.androidbasics.utils.img.GlideUtils;
import com.android.daqsoft.androidbasics.view.CustomDialog;
import com.android.daqsoft.androidbasics.view.RoundImageView;
import com.android.daqsoft.androidbasics.view.suppertext.SuperTextView;
import com.daqi.spot.Config;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.CubeOutTransformer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Request;


/**
 * 首页
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-4-28.
 * @since JDK 1.8
 */
public class IndexFragment extends BaseFragment implements OnBannerListener, SwipeRefreshLayout
        .OnRefreshListener {
    @BindView(R.id.index_top_banner)
    Banner mBanner;
    @BindView(R.id.tab_index_top_scroview)
    ScrollView tabIndexTopScroview;
    Unbinder unbinder;

    /**
     * 数据
     */
    private List<String> mBannerImgs = new ArrayList<>();
    private List<String> mBannerTitles = new ArrayList<>();
    private List<String> mBannerHtml = new ArrayList<>();
    @BindView(R.id.index_scinic_all)
    TextView indexScinicAll;
    @BindView(R.id.index_stv_more)
    SuperTextView indexStvMore;
    @BindView(R.id.index_bottom_1)
    LinearLayout indexBottom1;
    @BindView(R.id.index_bottom_2)
    LinearLayout indexBottom2;
    @BindView(R.id.index_bottom_3)
    LinearLayout indexBottom3;
    @BindView(R.id.index_bottom_4)
    LinearLayout indexBottom4;
    @BindView(R.id.index_bottom_5)
    LinearLayout indexBottom5;
    @BindView(R.id.index_tv_toaday)
    TextView mTvToaday;
    @BindView(R.id.index_tv_max)
    TextView mTvMax;

    @BindView(R.id.index_sw)
    SwipeRefreshLayout mSwipLayout;
    @BindView(R.id.index_know_scenic)
    LinearLayout mKnowScenic;//了解景區

    @BindView(R.id.index_ll_bottom)
    LinearLayout mLLbottom;
    @BindView(R.id.index_rv)
    RecyclerView mRv;
    @BindView(R.id.item_va)
    ViewAnimator mVa;

    public static IndexFragment newInstance() {
        Bundle args = new Bundle();
        IndexFragment fragment = new IndexFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        getData();
        initRvAdapter();
    }

    /**
     * 初始化适配器
     */
    private List<IndexYiBean> mDatas;
    private CommonAdapter<IndexYiBean> mAdapter;

    private void initRvAdapter() {
        mDatas = new ArrayList<>();
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv.setHasFixedSize(true);
        mRv.setNestedScrollingEnabled(false);
        mAdapter = new CommonAdapter<IndexYiBean>(getActivity(), R.layout.item_index_yiqi, mDatas) {
            @Override
            protected void convert(ViewHolder holder, final IndexYiBean bean, int position) {
                holder.setText(R.id.item_index_title, bean.getDeviceName() + "(" + bean
                        .getDeviceAbbreviation() + ")");

                String[] split = bean.getDeviceTag().split(" ");
                if (split.length > 1) {
                    holder.setText(R.id.item_index_content, split[0]);
                    holder.setText(R.id.item_index_content2, split[1]);
                } else {
                    holder.setText(R.id.item_index_content, bean.getDeviceTag());
                }
                RoundImageView img = (RoundImageView) holder.getView(R.id.item_index_img);
                GlideUtils.GlideImg(getActivity(), bean.getDeviceImage(), img);
                holder.setText(R.id.item_index_tai, bean.getDeviceStationName());
                holder.setText(R.id.item_index_ta, "分项:" + bean.getDeviceItem());
                holder.setOnClickListener(R.id.index_ll_4, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((Main2Activity) getActivity()).startBrotherFragment(IndexScenicFragment
                                .newInstance(bean.getDeviceName()));
                    }
                });
            }
        };
        mRv.setAdapter(mAdapter);

    }

    @Override
    public void onStart() {
        super.onStart();
        mBanner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable
            Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, super.onCreateView(inflater, container,
                savedInstanceState));
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void getData() {
        mBannerImgs.clear();
        mBannerTitles.clear();
        mBannerHtml.clear();
        OkHttpUtils.get()
                .url(Constant.BASE_URL + "imec/getBannerList")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                        showLoadingDialog();
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        getBannerData();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mBanner.setVisibility(View.GONE);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject object = JSONObject.parseObject(response);
                            JSONArray dataArr = object.getJSONArray("data");
                            for (int i = 0; i < dataArr.size(); i++) {
                                JSONObject obj = dataArr.getJSONObject(i);
                                mBannerImgs.add(obj.getString("adImg"));
                                mBannerTitles.add(obj.getString("adTitle"));
                                mBannerHtml.add(obj.getString("adDesc"));
                            }
                            initBanner();
                        } catch (Exception e) {
                            mBanner.setVisibility(View.GONE);
                            e.printStackTrace();
                        }
                    }
                });
    }

    public void getBannerData() {
        mDatas.clear();
        OkHttpUtils.get()
                .url(Constant.BASE_URL + "imec/getDeviceList?stationID=51001")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                        showLoadingDialog();
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        dismissLoadingDialog();
                        mSwipLayout.setRefreshing(false);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showToastShort("请求错误");
                        mVa.setDisplayedChild(1);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject object = JSONObject.parseObject(response);
                            JSONArray dataArr = object.getJSONArray("data");
                            if (object.getIntValue("resultCode") == 0 && dataArr.size() > 0) {
                                for (int i = 0; i < dataArr.size(); i++) {
                                    JSONObject obj = dataArr.getJSONObject(i);
                                    IndexYiBean bean = new IndexYiBean();
                                    bean.setDeviceName(obj.getString("deviceName"));
                                    bean.setDeviceAbbreviation(obj.getString("deviceAbbreviation"));
                                    bean.setDeviceID(obj.getString("deviceID"));
                                    bean.setDeviceCode(obj.getString("deviceCode"));
                                    bean.setDeviceItem(obj.getString("deviceItem"));
                                    bean.setDeviceIP(obj.getString("deviceIP"));
                                    bean.setDeviceSubnetMask(obj.getString("deviceSubnetMask"));
                                    bean.setDeviceGateway(obj.getString("deviceGateway"));
                                    bean.setDeviceStationID(obj.getIntValue("deviceStationID"));
                                    bean.setDeviceLocationID(obj.getIntValue("deviceLocationID"));
                                    bean.setDeviceStartRun(obj.getString("deviceStartRun"));
                                    bean.setDeviceStartRun(obj.getString("deviceStopRun"));
                                    bean.setDeviceTag(obj.getString("deviceTag"));
                                    bean.setDeviceImage(obj.getString("deviceImage"));
                                    bean.setDeviceIsWarning(obj.getIntValue("deviceIsWarning"));
                                    bean.setDeviceStationName(obj.getString("deviceStationName"));
                                    mDatas.add(bean);
                                }
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mVa.setDisplayedChild(1);
                            }
                        } catch (Exception e) {
                            mVa.setDisplayedChild(1);
                            e.printStackTrace();
                        }
                    }
                });
    }


    private void initView() {
        if (Config.showIndexBottom) {
            mLLbottom.setVisibility(View.GONE);
        } else {
            mLLbottom.setVisibility(View.GONE);
        }
        mSwipLayout.setOnRefreshListener(this);
        indexStvMore.setOnSuperTextViewClickListener(new SuperTextView
                .OnSuperTextViewClickListener() {
            @Override
            public void onClickListener(SuperTextView superTextView) {

            }
        });


    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index;
    }


    /**
     * 设置banner
     */
    private void initBanner() {
        //简单使用
        mBanner.setImages(mBannerImgs)
                .setImageLoader(new GlideImageLoader())
                .setOnBannerListener(this)
                .setBannerTitles(mBannerTitles)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE)
                .setBannerAnimation(CubeOutTransformer.class)
                .start();

    }


    @Override
    public void OnBannerClick(int position) {
        ActivityUtils.startHtmlActivity(mBannerHtml.get(position), ActivityWebView.class);
    }


    private CustomDialog logDialog;

    /**
     * 下面是加载框
     */
    private void showLoadingDialog() {
        if (logDialog == null) {
            logDialog = new CustomDialog(_mActivity, "加载中...");
        }
        logDialog.show();
    }

    private void dismissLoadingDialog() {
        if (logDialog != null) {
            logDialog.dismiss();
        }
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.imtopen)
    public void onViewClicked() {
        ((Main2Activity)getActivity()).openDraw();
    }
}
