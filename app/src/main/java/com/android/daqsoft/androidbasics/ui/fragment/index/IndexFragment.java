package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.daqsoft.androidbasics.MainActivity;
import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.CommonAdapter;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.base.ViewHolder;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.event.IndexYiBean;
import com.android.daqsoft.androidbasics.http.RequestData;
import com.android.daqsoft.androidbasics.ui.fragment.MainFragment;
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.MoreScenicBean;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
import com.android.daqsoft.androidbasics.utils.CommonUtils;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.android.daqsoft.androidbasics.utils.img.GlideImageLoader;
import com.android.daqsoft.androidbasics.utils.img.GlideUtils;
import com.android.daqsoft.androidbasics.view.CustomDialog;
import com.android.daqsoft.androidbasics.view.RoundImageView;
import com.android.daqsoft.androidbasics.view.sortbutton.Interface.ViewControl;
import com.android.daqsoft.androidbasics.view.sortbutton.SoreButton;
import com.android.daqsoft.androidbasics.view.sortbutton.adapter.SortButtonAdapter;
import com.android.daqsoft.androidbasics.view.suppertext.SuperTextView;
import com.daqi.spot.Config;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.StackTransformer;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;


/**
 *首页
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexFragment extends BaseFragment implements OnBannerListener, ViewControl ,SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.index_top_banner)
    Banner mBanner;
    @BindView(R.id.soreButton)
    SoreButton mMenuButton;

    /**
     * 数据
     */
    private  List<String> mBannerImgs = new ArrayList<>();
    private  List<String> mBannerTitles = new ArrayList<>();
    private  List<MoreScenicBean> mScenicMoreList = new ArrayList<>();//了解景區數據
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
    @BindView(R.id.index_tv_weather_top)
    TextView mTvTopWether;
    @BindView(R.id.index_tv_weather_content)
    TextView mTvTopContent;
    @BindView(R.id.index_sw)
    SwipeRefreshLayout mSwipLayout;
    @BindView(R.id.index_know_scenic)
    LinearLayout mKnowScenic;//了解景區
    @BindView(R.id.index_ll_top)
    LinearLayout mLLtop;
    @BindView(R.id.index_ll_bottom)
    LinearLayout mLLbottom;
    @BindView(R.id.index_rv)
    RecyclerView mRv;
    @BindView(R.id.item_va)
    ViewAnimator mVa;

    /**
     * 标记网络请求全部完成
     */
    private int mFinshNum = 0;
    static final int REFRESH_COMPLETE = 0X1112;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case REFRESH_COMPLETE:
                    LogUtils.e("-->"+mFinshNum);
                   if (mFinshNum >3){
                       dismissLoadingDialog();
                       if (mSwipLayout.isRefreshing()){
                           mSwipLayout.setRefreshing(false);
                       }
                   }
                    break;
            }
        }
    };

    /**
     * 菜单数据
     */
    private List<Integer> menuList;

    public static IndexFragment newInstance() {
        Bundle args = new Bundle();
        IndexFragment fragment = new IndexFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        initMenu();
        getData();
        initRvAdapter();
    }

    /**
     * 初始化适配器
     */
    private List<IndexYiBean>  mDatas ;
    private CommonAdapter<IndexYiBean> mAdapter;
    private void initRvAdapter() {
        mDatas  = new ArrayList<>();
        mRv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRv.setHasFixedSize(true);
        mRv.setNestedScrollingEnabled(false);
        mAdapter= new CommonAdapter<IndexYiBean>(getActivity(),R.layout.item_index_yiqi,mDatas) {
            @Override
            protected void convert(ViewHolder holder, final IndexYiBean bean, int position) {
                holder.setText(R.id.item_index_title,bean.getName());
                holder.setText(R.id.item_index_content,bean.getFenxiang());
//                RoundImageView img = (RoundImageView) holder.getView(R.id.item_index_img);
//                img.setImageResource(bean.getResId());
                holder.setText(R.id.item_index_tai,bean.getZantai()+"观测");
                holder.setOnClickListener(R.id.index_ll_4, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((MainActivity)getActivity()).startBrotherFragment(IndexScenicFragment.newInstance(bean.getName()));
                    }
                });
            }
        };
        mRv.setAdapter(mAdapter);

        OkHttpUtils.get()
                .url(IApplication.SP.getString(Constant.BASE_URL)+"imec/getDeviceList?department=51001")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastUtils.showToastShort("请求错误");
                        mVa.setDisplayedChild(1);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            JSONObject object = JSONObject.parseObject(response);
                            if (object.getIntValue("resultCode")==0&&object.getJSONArray("data").size()>0){
                                for (int i = 0; i < object.getJSONArray("data").size(); i++) {
                                    IndexYiBean bean = new IndexYiBean();
                                    bean.setName(object.getString("deviceAbbreviation")+object.getString("deviceName")+"("+object.getString("deviceTag")+")");
                                    bean.setFenxiang("测量分项:"+object.getString("deviceItem"));
                                    bean.setZantai(object.getString("deviceStartRun"));
                                    mDatas.add(bean);
                                }
                                mAdapter.notifyDataSetChanged();
                            }else {
                                mVa.setDisplayedChild(1);
                            }
                        }catch (Exception e){
                            mVa.setDisplayedChild(1);
                            e.printStackTrace();
                        }
                    }
                });

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
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void getData() {
        showLoadingDialog();
        mBannerImgs.clear();
        mBannerTitles.clear();
        mBannerImgs.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1529041653392&di=b754108e12984c1a02793c256b7b9567&imgtype=0&src=http%3A%2F%2Fsrc.onlinedown.net%2Fd%2Ffile%2Fp%2F2017-02-21%2F686eb11f15b6db9f025e32b0973b1371.jpg");
        mBannerImgs.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=3355331080,1887045785&fm=27&gp=0.jpg");
        mBannerTitles.add("GM-4磁通门磁力仪");
        mBannerTitles.add("ZD88地电仪");
        initBanner();
        //
        RequestData.getToadayNum(new RequestData.OnDataCallBack() {
            @Override
            public void onSuccess(String data) {
                try {
                    JSONObject object = JSONObject.parseObject(data);
                    String datas = object.getString("data");
                    mTvToaday.setText(datas);

                } catch (Exception e) {
                    LogUtils.e(e.toString());
                    mTvToaday.setText("0");
                }
            }

            @Override
            public void onFail() {
                mTvToaday.setText("0");
            }

            @Override
            public void onFinish() {
                mFinshNum++;
                mHandler.sendEmptyMessage(REFRESH_COMPLETE);

            }
        });
        ///
        RequestData.getMaxNum(new RequestData.OnDataCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject object = JSONObject.parseObject(response);
                    String data = object.getString("data");
                    mTvMax.setText(data);

                } catch (Exception e) {
                    LogUtils.e(e.toString());
                    mTvMax.setText("0");
                }
            }

            @Override
            public void onFail() {
                mTvMax.setText("0");
            }

            @Override
            public void onFinish() {
                mFinshNum++;
                mHandler.sendEmptyMessage(REFRESH_COMPLETE);

            }
        });

        //
        RequestData.getSceneryWeatherInfo(new RequestData.OnDataCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject object = JSONObject.parseObject(response);
                    JSONObject dataObj = object.getJSONObject("data");

                    String min = CommonUtils.isNotEmpty(dataObj.getString("min"));
                    String max = CommonUtils.isNotEmpty(dataObj.getString("max"));
                    String txt_n = CommonUtils.isNotEmpty(dataObj.getString("txt_n"));
                    String txt_d = CommonUtils.isNotEmpty(dataObj.getString("txt_d"));
                    mTvTopWether.setText(min+"℃～"+max+"℃");
                    mTvTopContent.setText("今日"+" · "+txt_n+"转"+txt_d);
                } catch (Exception e) {
                    LogUtils.e(e.toString());
                }
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onFinish() {
                mFinshNum++;
                mHandler.sendEmptyMessage(REFRESH_COMPLETE);
            }
        });

        //
        RequestData.getMoreScenery("",1, new RequestData.OnDataCallBack() {
            @Override
            public void onSuccess(String response) {
                try{
                    mScenicMoreList.clear();
                    JSONObject object = JSONObject.parseObject(response);
                    JSONArray datasArray = object.getJSONArray("datas");
                    if (datasArray.size()>0){
                        for (int i = 0; i < datasArray.size(); i++) {
                            JSONObject obj = datasArray.getJSONObject(i);
                            MoreScenicBean bean =new MoreScenicBean();
                            bean.setAudioPath(CommonUtils.isNotEmpty(obj.getString("audioPath")));
                            bean.setContent(CommonUtils.isNotEmpty(obj.getString("describe")));
                            bean.setTitle(CommonUtils.isNotEmpty(obj.getString("name")));
                            bean.setImgPath(CommonUtils.isNotEmpty(obj.getString("img")));
                            bean.setVideoPath(CommonUtils.isNotEmpty(obj.getString("monitorPath")));
                            bean.setId(CommonUtils.isNotEmpty(obj.getString("id")));
                            mScenicMoreList.add(bean);
                        }
                    }else {//隱藏了解景區
                        mKnowScenic.setVisibility(View.GONE);
                    }
                }catch (Exception e){
                    LogUtils.e(e.toString());
                }
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onFinish() {
                mFinshNum++;
                mHandler.sendEmptyMessage(REFRESH_COMPLETE);
            }
        });
    }



    private void initView() {
        if (Config.showIndexBottom){
            mLLbottom.setVisibility(View.GONE);
        }else {
            mLLbottom.setVisibility(View.GONE);
        }
        mSwipLayout.setOnRefreshListener(this);
        indexStvMore.setOnSuperTextViewClickListener(new SuperTextView.OnSuperTextViewClickListener() {
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
     * 首页菜单栏
     */
    private void initMenu() {
        mMenuButton.setViewControl(this);
        //添加界面到list
        menuList = new ArrayList<>();
        menuList.add(R.layout.viewpager_page);
        mMenuButton.setView(menuList).init();
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
                .setBannerAnimation(StackTransformer.class)
                .start();

    }


    @Override
    public void OnBannerClick(int position) {
        LogUtils.e("你点击的是--》"+mBannerTitles.get(position));
    }

    /**
     * 菜单的回调(多个可以左右滑动)
     * @param view
     * @param type
     */
    @Override
    public void setView(View view, int type) {
        switch (type) {
            case 0://第一个界面
                GridView gridView = (GridView) view.findViewById(R.id.gridView);
                SortButtonAdapter adapter = new SortButtonAdapter(_mActivity, Constant.getBtnList(_mActivity));
                gridView.setAdapter(adapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = null;
                        LogUtils.e("你点击的是"+position);
                        switch (position) {
                            case 0://了解景区
                                ((MainActivity)getActivity()).startBrotherFragment(IndexScenicFragment.newInstance(""));
                                break;
                            case 1://预定服务
                                ToastUtils.showToast("功能正在研发中！");
                                //((MainFragment) getParentFragment()).setTab(1);
                                break;
                            case 2://识你所见
                                ToastUtils.showToast("功能正在研发中！");
                                //((MainFragment) getParentFragment()).startBrotherFragment(IndexAsyousayFragment.newInstance());
                                break;
                            case 3://探风景
                                ((MainActivity)getActivity()).startBrotherFragment(IndexLookSceneryFragment.newInstance());
                                break;
                            case 4://720
                                ((MainActivity)getActivity()).startBrotherFragment(IndexSevenFragment.newInstance());
                                break;
                            case 5://出行服务
                                ((MainActivity)getActivity()).startBrotherFragment(IndexGoServiceFragment.newInstance());
                                break;
                            case 6://智能机器人
                                ToastUtils.showToast("功能正在研发中！");
                                break;
                            case 7://一键报警
                                ToastUtils.showToast("功能正在研发中！");
                                //((MainFragment) getParentFragment()).startBrotherFragment(IndexPoliceFragment.newInstance());
                                break;
                            default:
                                break;

                        }
                    }
                });
                break;
            case 1://第二个界面
                break;
            case 2://第三个界面
                break;
        }
    }


    @OnClick({R.id.index_scinic_all, R.id.index_bottom_1, R.id.index_bottom_2, R.id.index_bottom_3, R.id.index_bottom_4, R.id.index_bottom_5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.index_scinic_all://景区查看全部

                ((MainFragment) getParentFragment()).startBrotherFragment(IndexScenicFragment.newInstance(""));
                break;

            case R.id.index_bottom_1://下面是底部4个快速入口
                break;
            case R.id.index_bottom_2:
                break;
            case R.id.index_bottom_3:
                break;
            case R.id.index_bottom_4:
                break;
            case R.id.index_bottom_5:
                break;
        }
    }
    private CustomDialog logDialog;

    /**
     * 下面是加载框
     */
    private  void showLoadingDialog() {
        if (logDialog == null) {
            logDialog = new CustomDialog(_mActivity,"加载中...");
        }
        logDialog.show();
    }

    private  void dismissLoadingDialog() {
        if (logDialog != null) {
            logDialog.dismiss();
        }
    }

    @Override
    public void onRefresh() {
        mFinshNum = 0;
        getData();
    }
}
