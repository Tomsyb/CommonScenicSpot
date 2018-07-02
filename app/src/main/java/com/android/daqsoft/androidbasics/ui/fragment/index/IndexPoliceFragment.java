package com.android.daqsoft.androidbasics.ui.fragment.index;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.android.daqsoft.androidbasics.view.dialog.RxDialogSureCancel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 一键报警
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexPoliceFragment extends BaseFragment {
    @BindView(R.id.include_img_back)
    ImageView mImgBack;
    @BindView(R.id.include_tv_title)
    TextView mTvTitle;
    @BindView(R.id.police_img_police)
    ImageView mImgPolice;
    @BindView(R.id.police_ll_report)
    LinearLayout policeLlReport;
    @BindView(R.id.police_ll_dangerous)
    LinearLayout policeLlDangerous;
    @BindView(R.id.police_ll_bad)
    LinearLayout policeLlBad;


    //单列
    public static IndexPoliceFragment newInstance() {
        Bundle args = new Bundle();
        IndexPoliceFragment fragment = new IndexPoliceFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        initView();
    }

    private void initView() {
        mTvTitle.setText("一键报警");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_index_police;
    }




    @OnClick({R.id.include_img_back, R.id.police_img_police, R.id.police_ll_report, R.id.police_ll_dangerous, R.id.police_ll_bad})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.include_img_back:
                _mActivity.onBackPressed();
                break;
            case R.id.police_img_police:
                break;
            case R.id.police_ll_report:
                start(IndexPoliceXqFragment.newInstance());
                break;
            case R.id.police_ll_dangerous:
                ToastUtils.showToast("待开发");
                break;
            case R.id.police_ll_bad:
                ToastUtils.showToast("待开发");
                //showDialog();
                break;
        }
    }

    private void showDialog() {
        final RxDialogSureCancel rxDialogSureCancel = new RxDialogSureCancel(_mActivity);//提示弹窗
        rxDialogSureCancel.getTitleView().setBackgroundResource(R.drawable.icon_person);
        rxDialogSureCancel.getSureView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogSureCancel.cancel();
            }
        });
        rxDialogSureCancel.getCancelView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rxDialogSureCancel.cancel();
            }
        });
        rxDialogSureCancel.show();
    }

}
