package com.android.daqsoft.androidbasics.ui.fragment.serve;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.ui.fragment.serve.adapter.ServeAdapter;
import com.android.daqsoft.androidbasics.utils.ActivityUtils;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.MD5Utils;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.android.daqsoft.androidbasics.utils.Utils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.daqi.spot.Config;

import butterknife.BindView;

/**
 * Created by 严博 on 2018/3/25.
 * 预定服务
 */

public class ServeFragment extends BaseFragment {
    @BindView(R.id.fg_serve_rv)
    RecyclerView fgServeRv;

    private ServeAdapter adapter;

    //单列
    public static ServeFragment newInstance() {
        Bundle args = new Bundle();
        ServeFragment fragment = new ServeFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        initRecycleview();
    }

    private void initRecycleview() {
        fgServeRv.setLayoutManager(new LinearLayoutManager(_mActivity));
        adapter = new ServeAdapter(R.layout.item_serve_list,Constant.getServeList());
        //点击事件
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.e("你点击了"+position);
                if(Utils.isnotNull(IApplication.SP.getString(Constant.API.WECHAT_OPENID))) {
                    String sign = MD5Utils.md5("gds=" + Config.JKB_APPKEY + IApplication.SP.getString(Constant.API.WECHAT_OPENID));
                    String wid = "";
                    if (position == 0) {//门票
                        wid = Config.JKB_SCENIC_ID;
                    } else if (position == 1) {//酒店
                        wid = Config.JKB_HOTEL_ID;
                    } else if (position == 2) {//特产
                        wid = Config.JKB_SPECIALTY_ID;
                    }
                    String url = Constant.API.JKB_BUY + "&wid=" +wid +
                            "&username="+IApplication.SP.getString(Constant.API.WECHAT_OPENID)
                            +"&sign="+sign;
                }else{
                    LogUtils.e("请登录");
                    ToastUtils.showToast("请到个人中心登录...");
                }
            }
        });
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        fgServeRv.setAdapter(adapter);
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_serve;
    }


}
