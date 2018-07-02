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
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.ScenicGongGaoBean;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
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
 * 景区公告
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexScenicGongGaoFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    @BindView(R.id.include_img_back)
    ImageView ImgBack;
    @BindView(R.id.include_tv_title)
    TextView TvTitle;
    @BindView(R.id.gongl_rv)
    RecyclerView mRv;
    @BindView(R.id.gonggao_viewanimator)
    ViewAnimator mViewAnimator;
    @BindView(R.id.gongg_swresh)
    SwipeRefreshLayout mSwipresh;
    //数据
    private List<ScenicGongGaoBean.DatasBean> mDatas = new ArrayList<>();
    private BaseQuickAdapter<ScenicGongGaoBean.DatasBean, BaseViewHolder> mAdapter;
    private int mPage = 1;

    //单列
    public static IndexScenicGongGaoFragment newInstance() {
        Bundle args = new Bundle();
        IndexScenicGongGaoFragment fragment = new IndexScenicGongGaoFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        iniAdapter();
        getData(true,"","");
    }

    private void iniAdapter() {
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new BaseQuickAdapter<ScenicGongGaoBean.DatasBean, BaseViewHolder>(R.layout.item_common_lookmsg, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, final ScenicGongGaoBean.DatasBean item) {
                GlideUtils.GlideImg(mContext,item.getCover(), (ImageView) helper.getView(R.id.item_scenic_img));
                helper.setText(R.id.gonggao_tv_title,item.getTitle());
                helper.setText(R.id.gonggao_tv_content,item.getSummary());
                helper.setText(R.id.gonggao_tv_time,item.getCreateDate());
                helper.setText(R.id.gonggao_tv_people,item.getViewCount()+"");
                helper.setOnClickListener(R.id.look_msg_ll, new View.OnClickListener() {
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

    private void getData(final boolean isRefresh, String chanelId, String title) {
        IApplication.showLoadingDialog(_mActivity);
        if (isRefresh) {
            mPage = 1;
            mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        }
        RequestData.getSiteNoticeList(mPage, chanelId, title, new RequestData.OnDataCallBack() {
            @Override
            public void onSuccess(String response) {
                try {
                    ScenicGongGaoBean bean = new Gson().fromJson(response, ScenicGongGaoBean.class);
                    List<ScenicGongGaoBean.DatasBean> mList = new ArrayList<>();
                    for (int i = 0; i < bean.getDatas().size(); i++) {
                        mList.add(bean.getDatas().get(i));
                    }
                    setData(isRefresh, mList);
                }catch (Exception e){
                    if (ObjectUtils.isNotEmpty(mViewAnimator)){
                        mViewAnimator.setDisplayedChild(1);
                    };
                }
            }

            @Override
            public void onFail() {
                if (ObjectUtils.isNotEmpty(mViewAnimator)){
                    mViewAnimator.setDisplayedChild(1);
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
        TvTitle.setText("景区公告");
        mSwipresh.setOnRefreshListener(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_scenic_gonggao;
    }

    @OnClick(R.id.include_img_back)
    public void onViewClicked() {
        _mActivity.onBackPressed();
    }

    @Override
    public void onRefresh() {
        getData(true,"","");
    }

    /**
     * 加载跟多
     */
    private void loadmore() {
        getData(false,"","");
    }

    private void setData(boolean isrefresh,  List<ScenicGongGaoBean.DatasBean> mDatas) {
        mPage++;
        final int size = mDatas == null ? 0 : mDatas.size();
        if (isrefresh) {
            if (size>0){
                mViewAnimator.setDisplayedChild(0);
                mAdapter.setNewData(mDatas);
            }else {
                mViewAnimator.setDisplayedChild(1);
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
