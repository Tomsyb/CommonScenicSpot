package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.CommonAdapter;
import com.android.daqsoft.androidbasics.adapter.recycleadapter.base.ViewHolder;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.ui.fragment.serve.bean.ServeListBean;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.android.daqsoft.androidbasics.utils.img.GlideImageLoader;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 *识你所见
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexAsyousayFragment extends BaseFragment implements OnBannerListener {
//    @BindView(R.id.index_asyousay_banner)
//    Banner mBanner;
    @BindView(R.id.index_sayousay_back)
    ImageView mBack;

    //banner数据
    public static List<?> images = new ArrayList<>();
    public static List<String> titles = new ArrayList<>();

    private CommonAdapter<ServeListBean> adapter;
    private List<ServeListBean> mDatas = new ArrayList<>();
    @BindView(R.id.index_sayousay_rv)
    RecyclerView mRv;

    //单列
    public static IndexAsyousayFragment newInstance() {
        Bundle args = new Bundle();
        IndexAsyousayFragment fragment = new IndexAsyousayFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        //setBanner();
        initRecycleview();
    }

    private void initRecycleview() {
        mRv.setNestedScrollingEnabled(false);
        mRv.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new CommonAdapter<ServeListBean>(_mActivity,R.layout.item_asyousay_list, Constant.getAsyouSayList()) {
            @Override
            protected void convert(ViewHolder holder, ServeListBean bean, final int position) {
                holder.setImageResource(R.id.item_serve_img,bean.getImgID());
                holder.setText(R.id.item_serve_title,bean.getTitle());
                holder.setText(R.id.item_serve_content,bean.getContent());
                holder.setOnClickListener(R.id.item_serve_list_ll, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ToastUtils.showToast("你点击"+position);
                    }
                });
            }
        };
        mRv.setAdapter(adapter);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_asyousay;
    }


    @OnClick(R.id.index_sayousay_back)
    public void onViewClicked() {
        _mActivity.onBackPressed();
    }

//    /**
//     * 设置banner
//     */
//    private void setBanner() {
//        String[] bannerUrl = getResources().getStringArray(R.array.banner_url);
//        String[] title = getResources().getStringArray(R.array.title);
//        List list = Arrays.asList(bannerUrl);
//        images = new ArrayList(list);
//        List list1 = Arrays.asList(title);
//        titles = new ArrayList(list1);
//        //简单使用
//        mBanner.setImages(images)
//                .setImageLoader(new GlideImageLoader())
//                .setOnBannerListener(this)
//                .start();
//    }

    @Override
    public void OnBannerClick(int position) {
        ToastUtils.showToast("你点击" + position);
    }

}
