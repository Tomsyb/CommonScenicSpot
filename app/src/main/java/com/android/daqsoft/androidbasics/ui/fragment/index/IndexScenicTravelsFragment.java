package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.ScenicTravelsBean;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
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

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * 游记攻略
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexScenicTravelsFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener,View.OnKeyListener{
    @BindView(R.id.include_img_back)
    ImageView ImgBack;
    @BindView(R.id.include_tv_title)
    TextView TvTitle;
    @BindView(R.id.traves_rv)
    RecyclerView mRv;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @BindView(R.id.travel_viewanimator)
    ViewAnimator mViewAnimator;

    @BindView(R.id.include_search)
    EditText mEtSearch;
    /**
     * 当前默认页数
     */
    private int mPage = 1;
    /**
     * 搜索 关键字
     */
    private String searchName = "";
    /**
     * 数据
     */
    private List<ScenicTravelsBean> mDatas = new ArrayList<>();
    private BaseQuickAdapter<ScenicTravelsBean, BaseViewHolder> mAdapter;

    /**
     *
     * @return 单例
     */
    public static IndexScenicTravelsFragment newInstance() {
        Bundle args = new Bundle();
        IndexScenicTravelsFragment fragment = new IndexScenicTravelsFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        initView();
        iniAdapter();
        onRefresh();
    }

    /**
     * 初始适配器
     */
    private void iniAdapter() {
        mRv.setHasFixedSize(true);
        mRv.setLayoutManager(new LinearLayoutManager(_mActivity));
        mAdapter = new BaseQuickAdapter<ScenicTravelsBean, BaseViewHolder>(R.layout.item_common_bigimg, mDatas) {
            @Override
            protected void convert(BaseViewHolder helper, final ScenicTravelsBean item) {
                if (item.getRecommended().equals("0")){//1：推荐
                    helper.getView(R.id.tv_tuijain).setVisibility(View.GONE);
                }else {
                    helper.getView(R.id.tv_tuijain).setVisibility(View.VISIBLE);
                }
                if (ObjectUtils.isNotEmpty(item.getData())){
                    String eDate = CommonUtils.getEDate(item.getData());
                    String[] split = eDate.split(",");
                    helper.setText(R.id.bihimg_tv_data,split[0]);
                    helper.setText(R.id.bihimg_tv_year,split[2]);
                    helper.setText(R.id.bihimg_tv_mouth,split[1]);
                }else {
                    helper.setText(R.id.bihimg_tv_data,"0");
                    helper.setText(R.id.bihimg_tv_year,"0");
                    helper.setText(R.id.bihimg_tv_mouth,"0");
                }
                helper.setText(R.id.item_bigimg_tv_title,item.getTitle());
                helper.setText(R.id.bigima_tv_givePoint,item.getGoodNum());
                helper.setText(R.id.bigima_tv_collection,item.getXinNum());
                helper.setText(R.id.bigima_tv_viewCount,item.getMsgNum());
                helper.setText(R.id.item_bigimg_tv_content,item.getContent());
                GlideUtils.GlideImg(_mActivity,item.getImgPath(), (ImageView) helper.getView(R.id.item_bigimg_img_main));
                helper.setOnClickListener(R.id.item_bigimg_ll, new View.OnClickListener() {
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

    /**
     * 获取数据
     * @param isRefresh 是否刷新
     */
    private void getData(final boolean isRefresh) {
        IApplication.showLoadingDialog(_mActivity);
        if (isRefresh) {
            mPage = 1;
            mAdapter.setEnableLoadMore(false);//这里的作用是防止下拉刷新的时候还可以上拉加载
        }
        RequestData.gettravelStrategy(mPage, searchName, new RequestData.OnDataCallBack() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                try {
                    JSONObject object = JSONObject.parseObject(response);
                    JSONArray datasArray = object.getJSONArray("datas");
                    List<ScenicTravelsBean> mList = new ArrayList<>();
                    for (int i = 0; i < datasArray.size(); i++) {
                        JSONObject obj = datasArray.getJSONObject(i);
                        ScenicTravelsBean bean = new ScenicTravelsBean();
                        bean.setTitle(CommonUtils.isNotEmpty(obj.getString("title")));
                        bean.setContent(CommonUtils.isNotEmpty(obj.getString("digest")));
                        bean.setImgPath(CommonUtils.isNotEmpty(obj.getString("cover")));
                        bean.setGoodNum(CommonUtils.isNotEmpty(obj.getString("givePoint")));
                        bean.setXinNum(CommonUtils.isNotEmpty(obj.getString("collection")));
                        bean.setMsgNum(CommonUtils.isNotEmpty(obj.getString("viewCount")));
                        bean.setRecommended(CommonUtils.isNotEmpty(obj.getString("recommended")));
                        bean.setId(CommonUtils.isNotEmpty(obj.getString("id")));
                        bean.setData(CommonUtils.isNotEmpty(obj.getString("updateTime")));
                        mList.add(bean);
                    }
                    setData(isRefresh, mList);
                }catch (Exception e){
                    if (ObjectUtils.isNotEmpty(mViewAnimator)){
                        mViewAnimator.setDisplayedChild(1);
                    }
                    LogUtils.e(e.toString());
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
                if (ObjectUtils.isNotEmpty(mRefreshLayout) && mRefreshLayout.isRefreshing()) {
                    mRefreshLayout.setRefreshing(false);
                }
                mAdapter.setEnableLoadMore(true);
            }
        });
    }

    /**
     * 初始View
     */
    private void initView() {
        TvTitle.setText("游记攻略");
        mEtSearch.setOnKeyListener(this);
        mRefreshLayout.setOnRefreshListener(this);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_scenic_travels;
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

    /**
     * 设置数据
     * @param isrefresh 是否刷新
     * @param mDatas 数据
     */
    private void setData(boolean isrefresh,  List<ScenicTravelsBean> mDatas) {
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
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        String strSearch = mEtSearch.getText().toString().trim();
        if ((keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_ENTER) && event.getAction() == KeyEvent.ACTION_DOWN) {
            searchName = strSearch;
            getData(true);
            disMissKeyBorad();
            return true;
        }
        return false;
    }

    /**
     * 关闭软键盘
     */
    public void disMissKeyBorad() {
        try {
            InputMethodManager imm = (InputMethodManager) _mActivity.getSystemService(INPUT_METHOD_SERVICE);
            try {
                _mActivity.getCurrentFocus().getWindowToken();
            } catch (NullPointerException e) {
                return;
            }
            imm.hideSoftInputFromWindow(_mActivity.getCurrentFocus()
                    .getWindowToken(), 0);
        } catch (Exception e) {
            return;
        }
    }
}
