package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.CommonAdapter;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.base.ViewHolder;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.event.Basebean;
import com.android.daqsoft.androidbasics.ui.ActivityWebView;
import com.android.daqsoft.androidbasics.ui.fragment.other.OtherFragment;
import com.android.daqsoft.androidbasics.ui.fragment.text.TextFragment;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ObjectUtils;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.android.daqsoft.androidbasics.view.RxTextViewVertical;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 了解景区
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexScenicFragment extends BaseFragment {
    @BindView(R.id.index_scenic_img_back)
    ImageView imgBack;
    @BindView(R.id.fg_index_scenic_rv)
    RecyclerView mRv;
    @BindView(R.id.run_text)
    RxTextViewVertical mRunText;
    @BindView(R.id.title_yanb)
    TextView mTitle;

    private String huanjin= "";
    private String yuanli= "";
    private String guifan= "";
    /**
     * 数据
     */
    private ArrayList<String> titleList = new ArrayList<String>();
    private ArrayList<String> titleIDList = new ArrayList<String>();
    private CommonAdapter<Basebean> adapter;

    //单列
    public static IndexScenicFragment newInstance(String title,String huanjin,String yuanli,String guifan) {
        Bundle args = new Bundle();
        args.putString("TITLE",title);
        args.putString("huanjin",huanjin);
        args.putString("yuanli",yuanli);
        args.putString("guifan",guifan);
        IndexScenicFragment fragment = new IndexScenicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        initRecycleview();
        mTitle.setText(getArguments().getString("TITLE"));
        huanjin = getArguments().getString("huanjin");
        yuanli = getArguments().getString("yuanli");
        guifan = getArguments().getString("guifan");
        getData();
    }

    private void getData() {
        if (ObjectUtils.isNotEmpty(IApplication.SP.getString(Constant.JQGGLIST))){
            JSONObject object = JSONObject.parseObject(IApplication.SP.getString(Constant.JQGGLIST));
            JSONArray datasArray = object.getJSONArray("datas");
            for (int i = 0; i < 3; i++) {
                JSONObject obj = datasArray.getJSONObject(i);
                String title = obj.getString("title");
                titleIDList.add(obj.getString("id"));
            }
        }
        titleList.add("今日检测点位正常，未出现故障");
        titleList.add("机器需要维修");
        mRunText.setTextList(titleList);
        mRunText.setText(12, 2, Color.WHITE);//设置属性
        mRunText.setTextStillTime(3000);//设置停留时长间隔
        mRunText.setAnimTime(300);//设置进入和退出的时间间隔
        mRunText.setOnItemClickListener(new RxTextViewVertical.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        mRunText.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        mRunText.stopAutoScroll();
    }

    private void initRecycleview() {
        mRv.setLayoutManager(new GridLayoutManager(_mActivity, 3));
        adapter = new CommonAdapter<Basebean>(_mActivity, R.layout.item_common_img_text, Constant.getSinecList(_mActivity)) {
            @Override
            protected void convert(ViewHolder holder, Basebean bean, final int position) {
                holder.setText(R.id.item_common_tv, bean.getName());
                holder.setImageResource(R.id.item_common_img, bean.getImgId());
                holder.setOnClickListener(R.id.item_common_ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (position) {
                            case 0://景区简介
                                ActivityUtils.startHtmlActivity(huanjin, ActivityWebView.class);
                                //start(IndexPoliceXqFragment.newInstance());
                                break;
                            case 1://游览须知
                                ActivityUtils.startHtmlActivity(yuanli, ActivityWebView.class);
                                break;
                            case 2://票务政策
                                ActivityUtils.startHtmlActivity(guifan, ActivityWebView.class);
                                break;
                            case 3://开放时间
                                start(TextFragment.newInstance());
                                break;
                            case 4://景区交通
                                start(OtherFragment.newInstance());
                                break;
                            case 5://景区文化
                                ToastUtils.showToast("开发中...");
                                break;
                            case 6://景区公告
                                start(IndexPoliceXqFragment.newInstance());
                                break;
                            case 7://游记攻略
                                ToastUtils.showToast("开发中...");
                                //start(IndexScenicTravelsFragment.newInstance());
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
        };
        mRv.setAdapter(adapter);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_scenic;
    }


    @OnClick(R.id.index_scenic_img_back)
    public void onViewClicked() {
        _mActivity.onBackPressed();
    }

}
