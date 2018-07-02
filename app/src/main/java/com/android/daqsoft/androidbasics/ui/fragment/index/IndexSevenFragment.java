package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.IndexSevenBean;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
import com.android.daqsoft.androidbasics.utils.LogUtils;
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
 * 720
 */

public class IndexSevenFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.include_img_back)
    ImageView ImgBack;
    @BindView(R.id.include_tv_title)
    TextView TvTitle;
    @BindView(R.id.seven_rv)
    RecyclerView mRv;
    @BindView(R.id.seven_swresh)
    SwipeRefreshLayout mSwipresh;
    @BindView(R.id.seven_animator)
    ViewAnimator animator;


    //数据
    private List<IndexSevenBean.DatasBean> mDatas = new ArrayList<>();
    private BaseQuickAdapter<IndexSevenBean.DatasBean,BaseViewHolder> mAdapter;
    private int page = 1;

    //单列
    public static IndexSevenFragment newInstance() {
        Bundle args = new Bundle();
        IndexSevenFragment fragment = new IndexSevenFragment();
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
        mAdapter = new BaseQuickAdapter<IndexSevenBean.DatasBean, BaseViewHolder>(R.layout.item_img_txt,mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, final IndexSevenBean.DatasBean item) {
                helper.setText(R.id.item_tv_bottom,item.getName());
                GlideUtils.GlideImg(_mActivity, item.getCoverpicture(), (ImageView) helper.getView(R.id.item_common_img));
                helper.setOnClickListener(R.id.item_common_img, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        };
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);//动画
        //上啦加载
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                //加载跟多
                loadMore();
            }
        },mRv);
        mRv.setAdapter(mAdapter);
    }


    private void initView() {
        TvTitle.setText("720虚拟体验");
        mSwipresh.setOnRefreshListener(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_seven;
    }


    @OnClick(R.id.include_img_back)
    public void onViewClicked() {
        _mActivity.onBackPressed();
    }

    @Override
    public void onRefresh() {
        refresh();
    }

    private void refresh() {
        IApplication.showLoadingDialog(_mActivity);
        page = 1;
        mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        getData(true);
    }

    private void loadMore() {
        getData(false);
    }

    private void getData(final boolean isRefresh) {
        RequestData.getSevenList(page, new RequestData.OnDataCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    IndexSevenBean bean = new Gson().fromJson(response, IndexSevenBean.class);
                    setData(isRefresh,bean.getDatas());
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
                if (mSwipresh.isRefreshing()){
                    mSwipresh.setRefreshing(false);
                }
                mAdapter.setEnableLoadMore(true);
                IApplication.dismissLoadingDialog();

            }
        });
    }
    //设置数据
    private void setData(boolean isRefresh,List<IndexSevenBean.DatasBean> mDatas){
        page++;
        final  int size = mDatas == null ?0:mDatas.size();
        if (isRefresh){
            mAdapter.setNewData(mDatas);
        }else {
            if (size>0){
                mAdapter.addData(mDatas);
            }
        }

        if (size<Constant.API.limitPage){
            ////第一页如果不够一页就不显示没有更多数据布局
            mAdapter.loadMoreEnd(isRefresh);
            ToastUtils.showToast("没有更多数据");
        }else {
            mAdapter.loadMoreComplete();
        }
    }
}
