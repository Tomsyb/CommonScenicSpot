package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewAnimator;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.http.RequestData;
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.CamerBean;
import com.android.daqsoft.androidbasics.utils.CommonUtils;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ObjectUtils;
import com.android.daqsoft.androidbasics.utils.img.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 景区画册
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexScenicPictureFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.include_img_back)
    ImageView ImgBack;
    @BindView(R.id.include_tv_title)
    TextView TvTitle;
    @BindView(R.id.picture_rv)
    RecyclerView mRv;
    @BindView(R.id.pivture_swresh)
    SwipeRefreshLayout mSwipresh;
    @BindView(R.id.animator)
    ViewAnimator animator;


    /**
     * 数据
     */
    private List<CamerBean> mDatas = new ArrayList<>();
    private BaseQuickAdapter<CamerBean, BaseViewHolder> mAdapter;
    private int mPage = 1;

    public static IndexScenicPictureFragment newInstance() {
        Bundle args = new Bundle();
        IndexScenicPictureFragment fragment = new IndexScenicPictureFragment();
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
        mRv.setLayoutManager(new GridLayoutManager(_mActivity, 2));
        mAdapter = new BaseQuickAdapter<CamerBean, BaseViewHolder>(R.layout.item_common_img, mDatas) {
            @Override
            protected void convert(final BaseViewHolder helper, final CamerBean item) {
                GlideUtils.GlideImg(_mActivity, item.getImgPath(), (ImageView) helper.getView(R.id.item_common_img));
                helper.setOnClickListener(R.id.item_common_img, new View.OnClickListener() {
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


    private void getData(final boolean isRefresh) {
        IApplication.showLoadingDialog(_mActivity);
        if (isRefresh) {
            mPage = 1;
            mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        }
        RequestData.getSceneryImg(mPage + "", new RequestData.OnDataCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject object = JSONObject.parseObject(response);
                    JSONArray datasArray = object.getJSONArray("datas");
                    List<CamerBean> mList = new ArrayList<>();
                    for (int i = 0; i < datasArray.size(); i++) {
                        JSONObject obj = datasArray.getJSONObject(i);
                        CamerBean bean = new CamerBean();
                        bean.setImgPath(CommonUtils.isNotEmpty(obj.getString("path")));
                        bean.setName(CommonUtils.isNotEmpty(obj.getString("name")));
                        mList.add(bean);
                    }
                    setData(isRefresh, mList);
                } catch (Exception e) {
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
                if (ObjectUtils.isNotEmpty(mSwipresh) && mSwipresh.isRefreshing()) {
                    mSwipresh.setRefreshing(false);
                }
                mAdapter.setEnableLoadMore(true);

            }
        });
    }

    private void initView() {
        TvTitle.setText("景区画册");
        mSwipresh.setOnRefreshListener(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_scenic_picture;
    }


    @OnClick(R.id.include_img_back)
    public void onViewClicked() {
        _mActivity.onBackPressed();
    }

    @Override
    public void onRefresh() {
        getData(true);
    }


    /**
     * 加载跟多
     */
    private void loadmore() {
        getData(false);
    }

    private void setData(boolean isrefresh, List<CamerBean> mDatas) {
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
