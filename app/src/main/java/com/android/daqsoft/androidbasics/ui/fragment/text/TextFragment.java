package com.android.daqsoft.androidbasics.ui.fragment.text;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ObjectUtils;
import com.android.daqsoft.androidbasics.view.CustomDialog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/3/25.
 * 网络测试
 */

public class TextFragment extends BaseFragment {
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.tv_show)
    TextView mTvShow;
    @BindView(R.id.include_tv_title)
    TextView includeTvTitle;



    //单列
    public static TextFragment newInstance() {
        Bundle args = new Bundle();
        TextFragment fragment = new TextFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void init(Bundle savedInstanceState) {
        includeTvTitle.setText("网络测试");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_text;
    }

    public String Ping(String str) {
        String resault = "";
        Process p;
        try {
            //ping -c 3 -w 100  中  ，-c 是指ping的次数 3是指ping 3次 ，-w 100  以秒为单位指定超时间隔，是指超时时间为100秒
            p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + str);
            int status = p.waitFor();

            InputStream input = p.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(input));
            StringBuffer buffer = new StringBuffer();
            String line = "";
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            System.out.println("Return ============" + buffer.toString());
            if (status == 0) {
                resault = "连接成功:" + buffer.toString();
            } else {
                resault = "faild";
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return resault;
    }



    private String ping = "10.51.26.16";


    @OnClick({R.id.include_img_back, R.id.btn_start})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.include_img_back:
                _mActivity.onBackPressed();
                break;
            case R.id.btn_start:
                String trim = etMobile.getText().toString().trim();
                showLoadingDialog();
                if (ObjectUtils.isNotEmpty(trim)) {
                    ping = trim;
                    new NetPing().execute();
                } else {
                    new NetPing().execute();
                }
                break;
        }
    }


    private class NetPing extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            String s = "";
            s = Ping(ping);
            LogUtils.e("ping", s);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mTvShow.setText(s);
            dismissLoadingDialog();
        }
    }



}
