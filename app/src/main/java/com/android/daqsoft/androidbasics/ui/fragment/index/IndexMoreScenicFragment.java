package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ViewAnimator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.http.RequestData;
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.MoreScenicBean;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
import com.android.daqsoft.androidbasics.utils.CommonUtils;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ObjectUtils;
import com.android.daqsoft.androidbasics.utils.img.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 更多不容错过的景点
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexMoreScenicFragment extends BaseFragment implements OnBannerListener, SwipeRefreshLayout.OnRefreshListener {
//    @BindView(R.id.index_more_banner)
//    Banner mBanner;
    @BindView(R.id.index_more_back)
    ImageView mBack;
    @BindView(R.id.index_more_rv)
    RecyclerView mRv;
    @BindView(R.id.more_sp)
    SwipeRefreshLayout mSwp;
    @BindView(R.id.more_animator)
    ViewAnimator animator;
    @BindView(R.id.more_img)
    ImageView mImgTop;

    /**
     * banner数据
     */
    private List<String> mBannerImages = new ArrayList<>();
    private List<String> mBannerTitles = new ArrayList<>();

    private BaseQuickAdapter<MoreScenicBean, BaseViewHolder> mAdapter;
    private int mPage = 1;
    private List<MoreScenicBean> mDatas = new ArrayList<>();

    /**
     *
     * @return 单列
     */
    public static IndexMoreScenicFragment newInstance() {
        Bundle args = new Bundle();
        IndexMoreScenicFragment fragment = new IndexMoreScenicFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        initRecycleview();
        onRefresh();
    }

    private void getBannerData() {

        RequestData.getSceneryBanner(new RequestData.OnDataCallBack() {
            @Override
            public void onSuccess(String response) {
                JSONObject object = JSONObject.parseObject(response);
                String img = object.getString("data");
                LogUtils.e("图片"+img);
                GlideUtils.GlideImg(_mActivity,img,mImgTop);
            }

            @Override
            public void onFail() {

            }

            @Override
            public void onFinish() {

            }
        });
    }

    private void getData(final boolean isRefresh, String name) {
        IApplication.showLoadingDialog(_mActivity);
        if (isRefresh) {
            mPage = 1;
            mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        }
        RequestData.getMoreScenery(name, mPage, new RequestData.OnDataCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject object = JSONObject.parseObject(response);
                    JSONArray datasArray = object.getJSONArray("datas");
                    List<MoreScenicBean> mList = new ArrayList<>();
                    for (int i = 0; i < datasArray.size(); i++) {
                        JSONObject obj = datasArray.getJSONObject(i);
                        MoreScenicBean bean = new MoreScenicBean();
                        bean.setAudioPath(CommonUtils.isNotEmpty(obj.getString("audioPath")));
                        bean.setContent(CommonUtils.isNotEmpty(obj.getString("describe")));
                        bean.setTitle(CommonUtils.isNotEmpty(obj.getString("name")));
                        bean.setImgPath(CommonUtils.isNotEmpty(obj.getString("img")));
                        bean.setVideoPath(CommonUtils.isNotEmpty(obj.getString("monitorPath")));
                        bean.setSevenPath(CommonUtils.isNotEmpty(obj.getString("panoramaPath")));
                        bean.setId(CommonUtils.isNotEmpty(obj.getString("id")));
                        bean.setIntroduce(CommonUtils.isNotEmpty(obj.getString("introduce")));
                        mList.add(bean);
                    }
                    setData(isRefresh, mList);
                } catch (Exception e) {
                    if (ObjectUtils.isNotEmpty(animator)){
                        animator.setDisplayedChild(1);
                        LogUtils.e(e.toString());
                    }
                }
            }

            @Override
            public void onFail() {
                if (ObjectUtils.isNotEmpty(animator)){
                    animator.setDisplayedChild(1);
                }
            }

            @Override
            public void onFinish() {
                IApplication.dismissLoadingDialog();
                if (ObjectUtils.isNotEmpty(mSwp) && mSwp.isRefreshing()) {
                    mSwp.setRefreshing(false);
                }
                mAdapter.setEnableLoadMore(true);
            }
        });
    }

    private void initRecycleview() {
        mSwp.setOnRefreshListener(this);
        mRv.setNestedScrollingEnabled(false);
        mRv.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new BaseQuickAdapter<MoreScenicBean, BaseViewHolder>(R.layout.item_l_img_r_text, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, final MoreScenicBean item) {
                helper.setText(R.id.l_img_r_tv_title, CommonUtils.delHTMLTag(item.getTitle()));
                helper.setText(R.id.l_img_r_tv_content, CommonUtils.delHTMLTag(item.getIntroduce()));
                GlideUtils.GlideImg(_mActivity, item.getImgPath(), (ImageView) helper.getView(R.id.item_scenery_iv_logo));
                helper.setOnClickListener(R.id.more_ll_720, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                //视频
                helper.setOnClickListener(R.id.more_ll_video, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                //语音
                helper.setOnClickListener(R.id.more_ll_audio, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        start(IndexAudioFragment.newInstance(item.getTitle(), item.getAudioPath(), item.getImgPath()));
                    }
                });
                //栏目
                helper.setOnClickListener(R.id.item_scenery_ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        };
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadmore();
            }
        }, mRv);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        mRv.setAdapter(mAdapter);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_more_scenic;
    }


    @OnClick(R.id.index_more_back)
    public void onViewClicked() {
        _mActivity.onBackPressed();
    }

//    /**
//     * 设置banner
//     */
//    private void setBanner() {
//        //简单使用
//        mBanner.setImages(mBannerImages)
//                .setImageLoader(new GlideImageLoader())
//                .setOnBannerListener(this)
//                .setIndicatorGravity(BannerConfig.CENTER)
//                .setBannerAnimation(StackTransformer.class)
//                .start();
//    }

    @Override
    public void OnBannerClick(int position) {
        LogUtils.e("你点击" + position);
    }

    @Override
    public void onRefresh() {
        getBannerData();
        getData(true, "");
    }

    /**
     * 加载跟多
     */
    private void loadmore() {
        getData(false, "");
    }

    private void setData(boolean isrefresh, List<MoreScenicBean> mDatas) {
        mPage++;
        final int size = mDatas == null ? 0 : mDatas.size();
        if (isrefresh) {
            if (size > 0) {
                animator.setDisplayedChild(0);
                mAdapter.setNewData(mDatas);
            } else {
                animator.setDisplayedChild(1);
            }
        } else {//上啦加载
            if (size > 0) {
                mAdapter.addData(mDatas);
            }
        }
        if (size < Constant.API.limitPage) {
            //第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isrefresh);
        } else {
            mAdapter.loadMoreComplete();
        }
    }
}
