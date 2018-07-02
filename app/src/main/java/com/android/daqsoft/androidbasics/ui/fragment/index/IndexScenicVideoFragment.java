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
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.ScenicVideoBean;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ObjectUtils;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.android.daqsoft.androidbasics.utils.img.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by yanbo on 2018/3/25.
 * 宣传视频
 */

public class IndexScenicVideoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.include_img_back)
    ImageView ImgBack;
    @BindView(R.id.include_tv_title)
    TextView TvTitle;
    @BindView(R.id.video_rv)
    RecyclerView mRv;
    @BindView(R.id.video_animator)
    ViewAnimator animator;
    @BindView(R.id.video_swlayout)
    SwipeRefreshLayout mSwip;
    //数据
    private List<ScenicVideoBean.DatasBean> mDatas = new ArrayList<>();
    private BaseQuickAdapter<ScenicVideoBean.DatasBean, BaseViewHolder> mAdapter;

    private int mPage = 1;



    //单列
    public static IndexScenicVideoFragment newInstance() {
        Bundle args = new Bundle();
        IndexScenicVideoFragment fragment = new IndexScenicVideoFragment();
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
        mAdapter = new BaseQuickAdapter<ScenicVideoBean.DatasBean, BaseViewHolder>(R.layout.item_video_list, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, final ScenicVideoBean.DatasBean item) {
                GlideUtils.GlideImg(_mActivity,item.getCoverpicture(), (ImageView) helper.getView(R.id.iv_video_logo));
                helper.setText(R.id.tv_Title,item.getTitle());
                helper.setText(R.id.tv_mins,item.getMins());
                helper.setOnClickListener(R.id.iv_icon_openvideo, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (ObjectUtils.isNotEmpty(item.getUpload())){
                        }else {
                            ToastUtils.showToast("无视频播放");
                        }
                        LogUtils.e("--"+item.getUpload());
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
        RequestData.getSceneryVideo(name, mPage, new RequestData.OnDataCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    ScenicVideoBean bean = new Gson().fromJson(response, ScenicVideoBean.class);
                    List<ScenicVideoBean.DatasBean> mList = new ArrayList<>();
                    for (int i = 0; i < bean.getDatas().size(); i++) {
                        mList.add(bean.getDatas().get(i));
                    }
                    setData(isRefresh, mList);
                }catch (Exception e){
                    animator.setDisplayedChild(1);
                    LogUtils.e(e.toString());
                }
            }

            @Override
            public void onFail() {
                animator.setDisplayedChild(1);
            }

            @Override
            public void onFinish() {
                IApplication.dismissLoadingDialog();
                if (ObjectUtils.isNotEmpty(mSwip) && mSwip.isRefreshing()) {
                    mSwip.setRefreshing(false);
                }
                mAdapter.setEnableLoadMore(true);
            }
        });
    }

    private void initView() {
        TvTitle.setText("宣传视频");
        mSwip.setOnRefreshListener(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_scenic_video;
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
    private void setData(boolean isrefresh, List<ScenicVideoBean.DatasBean> mDatas) {
        mPage++;
        final int size = mDatas == null ? 0 : mDatas.size();
        if (isrefresh) {
            if (size>0){
                animator.setDisplayedChild(0);
                mAdapter.setNewData(mDatas);
            }else {
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
