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
import com.android.daqsoft.androidbasics.event.StatusSerarch;
import com.android.daqsoft.androidbasics.http.RequestData;
import com.android.daqsoft.androidbasics.ui.fragment.index.bean.ScenicTravelsBean;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
import com.android.daqsoft.androidbasics.utils.CommonUtils;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ObjectUtils;
import com.android.daqsoft.androidbasics.utils.Utils;
import com.android.daqsoft.androidbasics.utils.img.GlideUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;

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
     * 搜索 关键字
     */
    private String searchName = "";

    private BaseQuickAdapter<StatusSerarch,BaseViewHolder> mAdapter;
    private String deviceId = "";

    /**
     *
     * @return 单例
     */
    public static IndexScenicTravelsFragment newInstance(String deviceId_) {
        Bundle args = new Bundle();
        args.putString("deviceId",deviceId_);
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
        mAdapter = new BaseQuickAdapter<StatusSerarch, BaseViewHolder>(R.layout.item_guide_enfor_note,null) {
            @Override
            protected void convert(BaseViewHolder helper, StatusSerarch item) {
                helper.setText(R.id.tv_name,item.getId());
                if (item.getIschecked()==0){
                    helper.setText(R.id.tv_statuss,"待处理");
                    helper.setBackgroundRes(R.id.tv_statuss,R.drawable.bg_common_solid_orange);
                }else {
                    helper.setBackgroundRes(R.id.tv_statuss,R.drawable.bg_common_solid_green);
                    helper.setText(R.id.tv_statuss,"已处理");
                }
                helper.setText(R.id.tv_namesf,item.getStatus());
                helper.setText(R.id.tv_mechanism,item.getType());
                helper.setText(R.id.tv_place,item.getPeople()+"于 "+item.getTime()+"  上报");
            }
        };
        mRv.setAdapter(mAdapter);

    }


    private void getData(){
        mRefreshLayout.setRefreshing(true);
        OkHttpUtils.get()
                .url("http://2h15419d06.51mypc.cn:24420/imec/getLastSixEvent")
                .addParams("device_ID",deviceId)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        mViewAnimator.setDisplayedChild(1);
                        mRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        try {
                            mRefreshLayout.setRefreshing(false);
                            JSONObject object = JSONObject.parseObject(response);
                            if (object.getIntValue("resultCode")==0&&object.getJSONArray("data").size()>0){
                                mViewAnimator.setDisplayedChild(0);
                                JSONArray data = object.getJSONArray("data");
                                List<StatusSerarch> mlist = new ArrayList<>();
                                for (int i = 0; i < data.size(); i++) {
                                    JSONObject obj = data.getJSONObject(i);
                                    StatusSerarch bean = new StatusSerarch();
                                    bean.setId(obj.getString("deviceID"));
                                    bean.setPeople(obj.getString("submitPerson"));
                                    bean.setStatus(obj.getString("eventStatus"));
                                    bean.setTime(obj.getString("submitTime"));
                                    bean.setType(obj.getString("eventType"));
                                    mlist.add(bean);
                                }
                                mAdapter.setNewData(mlist);
                            }else {
                                mViewAnimator.setDisplayedChild(1);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            mViewAnimator.setDisplayedChild(1);
                        }
                    }
                });
    }
    /**
     * 初始View
     */
    private void initView() {
        try {
            deviceId = getArguments().getString("deviceId");
        }catch (Exception e){
            e.printStackTrace();
        }
        TvTitle.setText("故障检索");
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
        getData();
    }


    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        String strSearch = mEtSearch.getText().toString().trim();
        if ((keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_ENTER) && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (Utils.isnotNull(strSearch)){
                deviceId = strSearch;
                getData();
            }else {
                deviceId = getArguments().getString("deviceId");
                getData();
            }
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
