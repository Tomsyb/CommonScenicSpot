package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.http.RequestData;
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.LookScenicBean;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ObjectUtils;
import com.android.daqsoft.androidbasics.utils.img.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @Author: YanBo.
 * @Date: 2018-4-28.
 * @Describe: 探风景
 */
public class IndexLookSceneryFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.include_img_back)
    ImageView ImgBack;
    @BindView(R.id.include_tv_title)
    TextView TvTitle;
    @BindView(R.id.scenery_rv)
    RecyclerView mRv;
    @BindView(R.id.scenery_swresh)
    SwipeRefreshLayout mSwipresh;
    @BindView(R.id.scenery_animator)
    ViewAnimator mAnimator;


    //数据
    private List<LookScenicBean.DatasBean> mDatas = new ArrayList<>();
    private BaseQuickAdapter<LookScenicBean.DatasBean, BaseViewHolder> mAdapter;
    private int mPage = 1;

    //单列
    public static IndexLookSceneryFragment newInstance() {
        Bundle args = new Bundle();
        IndexLookSceneryFragment fragment = new IndexLookSceneryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        initAdapter();
        onRefresh();
    }

    private void initAdapter() {
        mRv.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new BaseQuickAdapter<LookScenicBean.DatasBean, BaseViewHolder>(R.layout.item_scenery_list,mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, final LookScenicBean.DatasBean item) {
                GlideUtils.GlideImg(_mActivity,item.getCover(), (ImageView) helper.getView(R.id.iv_video_logo));
                helper.setText(R.id.item_scenery_tv_bigtitle,item.getName());
                helper.setText(R.id.item_list_viewnum,item.getViewNum());
                helper.setOnClickListener(R.id.iv_icon_openvideo, new View.OnClickListener() {
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

    private void getData(final boolean isRefresh, String name) {
        IApplication.showLoadingDialog(_mActivity);
        if (isRefresh) {
            mPage = 1;
            mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        }
        RequestData.getSceneryMonitor(name, mPage, new RequestData.OnDataCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    LookScenicBean bean = new Gson().fromJson(response, LookScenicBean.class);
                    List<LookScenicBean.DatasBean> mList = new ArrayList<>();
                    for (int i = 0; i < bean.getDatas().size(); i++) {
                        mList.add(bean.getDatas().get(i));
                    }
                    setData(isRefresh, mList);
                } catch (Exception e) {
                    if (ObjectUtils.isNotEmpty(mAnimator)){
                        mAnimator.setDisplayedChild(1);
                    }
                    LogUtils.e(e.toString());
                }
            }

            @Override
            public void onFail() {
                if (ObjectUtils.isNotEmpty(mAnimator)){
                    mAnimator.setDisplayedChild(1);
                }
            }

            @Override
            public void onFinish() {
                IApplication.dismissLoadingDialog();
                if (ObjectUtils.isNotEmpty(mSwipresh) && mSwipresh.isRefreshing()) {
                    mSwipresh.setRefreshing(false);
                }
                mAdapter.setEnableLoadMore(true);
            }
        });
    }

    private void initView() {
        TvTitle.setText("探风景");
        mSwipresh.setOnRefreshListener(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_scenery;
    }


    @OnClick(R.id.include_img_back)
    public void onViewClicked() {
        _mActivity.onBackPressed();
    }

    @Override
    public void onRefresh() {
        getData(true,"");
    }


    /**
     * 加载跟多
     */
    private void loadmore() {
        getData(false,"");
    }

    private void setData(boolean isrefresh, List<LookScenicBean.DatasBean> mDatas) {
        mPage++;
        final int size = mDatas == null ? 0 : mDatas.size();
        if (isrefresh) {
            if (size>0){
                mAnimator.setDisplayedChild(0);
                mAdapter.setNewData(mDatas);
            }else {
                mAnimator.setDisplayedChild(1);
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
