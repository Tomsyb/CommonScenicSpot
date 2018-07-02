package com.android.daqsoft.androidbasics.ui.fragment.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseFragment;
import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.MD5Utils;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.android.daqsoft.androidbasics.utils.Utils;
import com.android.daqsoft.androidbasics.utils.img.GlideUtils;
import com.android.daqsoft.androidbasics.view.MWebChromeClient;
import com.android.daqsoft.androidbasics.view.suppertext.SuperTextView;
import com.daqi.spot.Config;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by yanbo on 2018/3/25.
 */

public class MineFragment extends BaseFragment {
    @BindView(R.id.fg_mine_qure)
    SuperTextView mBtnQure;
    @BindView(R.id.fg_mine_img_chat)
    ImageView mImgHead;
    @BindView(R.id.webView_mine)
    WebView webViewMine;
    @BindView(R.id.animator_mine)
    ViewAnimator animatorMine;
    Unbinder unbinder;
    @BindView(R.id.include_img_back)
    ImageView includeImgBack;
    @BindView(R.id.include_tv_title)
    TextView includeTvTitle;


    //单列
    public static MineFragment newInstance() {
        Bundle args = new Bundle();
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void init(Bundle savedInstanceState) {
        includeTvTitle.setText("个人中心");
    }

    @Override
    public int getLayoutResId() {
        return R.layout.fg_mine;
    }

    @OnClick({R.id.fg_mine_qure,R.id.include_img_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fg_mine_qure:
                if (!IApplication.mWxApi.isWXAppInstalled()) {
                    ToastUtils.showToast("您还未安装微信客户端");
                    return;
                }
                final SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "diandi_wx_login";
                IApplication.mWxApi.sendReq(req);
                break;
            case R.id.include_img_back:
                if (webViewMine.canGoBack()) {
                    webViewMine.goBack();
                }
                break;
        }
    }



    @Override
    public void onResume() {
        super.onResume();
       /* LogUtils.e("onResume: 执行与否" + IApplication.SP.getString(Constant.API.WECHAT_HEADIMGURL));
        GlideUtils.GlideImg(_mActivity, IApplication.SP.getString(Constant.API.WECHAT_HEADIMGURL), mImgHead);*/
        //http://jkb66.com/wap/common/userCenter.jhtml?
        //&appkey=0939809b-82e3-4e62-a41f-2b761461dc16&username=o_xc2xEBLLL8h5m3QrlmUzqdYBA4&sign=b8188d1ff999304a3e63e7ff71910ee1

        /**
         * 通过根据OpenID来判断是否第三方登录
         */
        if (Utils.isnotNull(IApplication.SP.getString(Constant.API.WECHAT_OPENID))) {
            animatorMine.setDisplayedChild(1);
            String sign = MD5Utils.md5("gds=" + Config.JKB_APPKEY + IApplication.SP.getString(Constant.API.WECHAT_OPENID));
            String url = Constant.API.JKB_USER_CENTER + "&username=" + IApplication.SP.getString(Constant.API.WECHAT_OPENID) + "&sign=" + sign+"&headImgUrl="+ IApplication.SP.getString(Constant.API.WECHAT_HEADIMGURL);
            LogUtils.e(sign);
            LogUtils.e(url);
            setWebInfo(url);
        } else {
            animatorMine.setDisplayedChild(0);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 0) {
            String headUrl = data.getStringExtra("headUrl");
            LogUtils.e("url:" + headUrl);
            GlideUtils.GlideImg(_mActivity, headUrl, mImgHead);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void initWebView() {
        WebSettings webSettings = webViewMine.getSettings();
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheMaxSize(1024 * 1024 * 8);//设置缓冲大小，我设的是8M
    }

    /**
     * 设置网页相关属性
     */
    @SuppressLint("SetJavaScriptEnabled")
    protected void setWebInfo(String url) {
        initWebView();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webViewMine.setWebContentsDebuggingEnabled(true);
        }
        webViewMine.requestFocus();
        webViewMine.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (webViewMine.canGoBack()) {
                    includeImgBack.setVisibility(View.VISIBLE);
                }else {
                    includeImgBack.setVisibility(View.GONE);
                }
            }

            //覆盖shouldOverrideUrlLoading 方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        // 如果访问的页面中有Javascript，则webview必须设置支持Javascript。
        webViewMine.getSettings().setJavaScriptEnabled(true);
        webViewMine.getSettings().setPluginState(WebSettings.PluginState.ON);
        webViewMine.requestFocusFromTouch();//设置WebView支持手势
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            webViewMine.getSettings().setAllowFileAccessFromFileURLs(true);
        }
        webViewMine.getSettings().setUseWideViewPort(true);
        webViewMine.getSettings().setAllowFileAccess(true);//设置js可访问文件
        webViewMine.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        String userAgent = webViewMine.getSettings().getUserAgentString();
        webViewMine.getSettings().setUserAgentString(userAgent + " mobile daqsoft.com");
        CookieManager.getInstance().setAcceptCookie(true);

        webViewMine.getSettings().setDomStorageEnabled(true);
//        mWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);//开启硬件加速
        //mWebView.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        webViewMine.getSettings().setAllowFileAccess(true);
        webViewMine.getSettings().setAppCacheEnabled(true);
        //mWebView.getSettings().setSupportMultipleWindows(true);
        webViewMine.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webViewMine.getSettings().setUseWideViewPort(true);
        webViewMine.getSettings().setLoadWithOverviewMode(true);
        //mWebView.getSettings().setSavePassword(true);
        //mWebView.getSettings().setSaveFormData(true);
        //mWebView.getSettings().setJavaScriptEnabled(true);     // enable navigator.geolocation     s.setGeolocationEnabled(true);     s.setGeolocationDatabasePath("/data/data/org.itri.html5webview/databases/");     // enable Web Storage: localStorage, sessionStorage     s.setDomStorageEnabled(true);  wb.requestFocus();  wb.setScrollBarStyle(0);
        webViewMine.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });
        webViewMine.loadUrl(url);


    }
}
