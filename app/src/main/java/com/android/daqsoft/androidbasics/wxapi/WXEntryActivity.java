package com.android.daqsoft.androidbasics.wxapi;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.BaseActivity;
import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.http.RequestData;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ObjectUtils;
import com.android.daqsoft.androidbasics.utils.ToastUtils;
import com.daqi.spot.Config;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * @Author: YanBo.
 * @Date: 2018-4-28.
 * @Describe: 微信登录或者分享成功后回调此页面
 */
public class WXEntryActivity extends SupportActivity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //如果没回调onResp，八成是这句没有写
        IApplication.mWxApi.handleIntent(getIntent(), this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
//		switch (req.getType()) {
//		case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
//			break;
//		case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
//			break;
//		default:
//			break;
//		}
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    //app发送消息给微信，处理返回消息的回调
    @Override
    public void onResp(BaseResp resp) {
        LogUtils.e(resp.errStr);
        LogUtils.e("错误码 : " + resp.errCode + "");
        int result = 0;
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK://用户同意
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        LogUtils.e("code = " + code);
                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求
                        RequestData.getWechatCode(code);//根据微信登录返回的code,获取access_token
                        IApplication.SP.put(Constant.API.WECHAT_CODE, code);
                        break;
                    case RETURN_MSG_TYPE_SHARE:
                        ToastUtils.showToast("微信分享成功");
                        finish();
                        break;
                }

//                if(ObjectUtils.isNotEmpty(IApplication.SP.getString(Constant.API.WECHAT_UNIONID))) {
//                    IApplication.showLoadingDialog(this);
//                    OkHttpUtils.get()
//                            .url()
//                    RequestData.bindingInfo(SpFile.getString(Constant.WECHAT_UNIONID), "1", new org.xutils.common.Callback.CacheCallback<String>() {
//                        @Override
//                        public boolean onCache(String result) {
//                            return false;
//                        }
//
//                        @Override
//                        public void onSuccess(String result) {
//                            Log.e(result);
//                            try {
//                                JSONObject jsonObject = new JSONObject(result);
//                                if (jsonObject.getInt("code") == 0) {
//                                    JSONObject json = jsonObject.getJSONObject("data");
//                                    if (json.getInt("status") == 0) {//已经绑定，
//                                        SpFile.putString("token", json.getString("token"));
//                                    } else {//未绑定
//                                        Intent intent = new Intent(WXEntryActivity.this, RegisterActivity.class);
//                                        intent.putExtra("type", 2);
//                                        intent.putExtra("wechat","1");
//                                        goToOtherClass(intent);//跳转到绑定页面
//                                    }
//                                    finish();
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable ex, boolean isOnCallback) {
//
//                        }
//
//                        @Override
//                        public void onCancelled(CancelledException cex) {
//
//                        }
//
//                        @Override
//                        public void onFinished() {
//                            dismiss();
//                        }
//                    });
//                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                if (RETURN_MSG_TYPE_SHARE == resp.getType()) ToastUtils.showToast("分享失败");
                else ToastUtils.showToast("登录失败");
                LogUtils.e("*********用户取消**********");
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                LogUtils.e("-------------用户拒绝授权-----------");
                break;
            default:
                LogUtils.e("--&&&&&&&&&&&&&&&&&&&&&&-");
                break;
        }
        WXEntryActivity.this.finish();
    }




}