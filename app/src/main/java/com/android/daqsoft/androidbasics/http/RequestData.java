package com.android.daqsoft.androidbasics.http;

import com.alibaba.fastjson.JSONObject;
import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.ObjectUtils;
import com.android.daqsoft.androidbasics.wxapi.WechatInfo;
import com.daqi.spot.Config;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;

import java.util.concurrent.CopyOnWriteArraySet;

import okhttp3.Call;
import okhttp3.Request;

/**
 * 数据请求
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class RequestData {
    public interface OnDataCallBack{
        void onSuccess(String response);
        void onFail();
        void onFinish();
    }
    //更新
    public static void checkUpdata(String version,final OnDataCallBack listener){
        OkHttpUtils.get()
                .url(Constant.API.VERSION_URL)
                .addParams("AppId",Config.APPID)
                .addParams("Method","AppVersion")
                .addParams("token","daqsoft")
                .addParams("AppType","1")
                .addParams("VersionCode",version)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                    }

                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        listener.onFinish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        listener.onSuccess(response);
                        LogUtils.e(response);
                    }
                });
    }

    //微信登录
    public static void getWechatCode(String code){
        OkHttpUtils.post()
                .url(Constant.API.WECHAT_CODE_URL)
                .addParams("appid", Config.APP_ID_WX)
                .addParams("secret", Config.APP_SECRET_WX)
                .addParams("code", code)
                .addParams("grant_type", "authorization_code")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("获取微信access_token = " + response);
                        try {
                            org.json.JSONObject jsonObject = new org.json.JSONObject(response);
                            if (ObjectUtils.isNotEmpty(jsonObject) && ObjectUtils.isNotEmpty(jsonObject.get("openid"))&& ObjectUtils.isNotEmpty(jsonObject.get("access_token"))) {
                                IApplication.SP.put(Constant.API.WECHAT_ACCESS_TOKEN, jsonObject.getString("access_token"));
                                IApplication.SP.put(Constant.API.WECHAT_OPENID, jsonObject.getString("openid"));
                                IApplication.SP.put(Constant.API.WECHAT_UNIONID, jsonObject.getString("unionid"));
                                getWechatUserInfo(jsonObject.getString("openid"), jsonObject.getString("access_token"));
                            } else {
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LogUtils.e(e.toString());
                        }
                    }
                });
    }

    public static void getWechatUserInfo (String openid, String accessToken) {
        OkHttpUtils.post()
                .url(Constant.API.WECHAT_INFO_URL)
                .addParams("openid",openid)
                .addParams("access_token",accessToken)
                .addParams("lang","zh_CN")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e("用户的个人信息 = " + response);
                        if (ObjectUtils.isNotEmpty(response)) {
                            try {
                                org.json.JSONObject jsonObject = new org.json.JSONObject(response);
                                WechatInfo wechatInfo = new WechatInfo();
                                wechatInfo.setOpenid(jsonObject.getString("openid"));
                                wechatInfo.setNickname(jsonObject.getString("nickname"));
                                wechatInfo.setSex(jsonObject.getInt("sex") + "");
                                wechatInfo.setLanguage(jsonObject.getString("language"));
                                wechatInfo.setCity(jsonObject.getString("city"));
                                wechatInfo.setProvince(jsonObject.getString("province"));
                                wechatInfo.setCountry(jsonObject.getString("country"));
                                wechatInfo.setHeadimgurl(jsonObject.getString("headimgurl"));
                                wechatInfo.setPrivilege(null);
                                wechatInfo.setUnionid(jsonObject.getString("unionid"));
                                IApplication.SP.put(Constant.API.WECHAT_USERINFO, response);
                                IApplication.SP.put(Constant.API.WECHAT_USERNICKNAME, wechatInfo.getNickname());
                                IApplication.SP.put(Constant.API.WECHAT_HEADIMGURL, wechatInfo.getHeadimgurl());
                                // loginWithWechat(wechatInfo);//登录（注册到服务器）
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }
    public static void findQuestionByType(String typeId,final OnDataCallBack listener) {
        OkHttpUtils.get()
                .url(Constant.API.QUESTION_BY_TYPE)
                .addParams("lang",Constant.API.lang)
                .addParams("typeId",typeId)
                .addParams("siteCode", Constant.API.siteCode)
                .addParams("type","1")//类型（必填 0 PC端，1 APP 端）
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        listener.onFinish();
                    }

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e(response);
                        if (isSuccess(response)){
                            listener.onSuccess(response);
                        }else {
                            listener.onFail();
                        }
                    }
                });
    }
    //机器人http://192.168.0.33:12588/api/scapi/app/robotQuestion/findTypeList?lang=cn&siteCode=slmhjqapp
    public static void findRobotTypeList(final OnDataCallBack listener) {
        OkHttpUtils.get()
                .url(Constant.API.ROBOT_TYPE_LIST)
                .addParams("lang",Constant.API.lang)
                .addParams("siteCode", Constant.API.siteCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        listener.onFinish();
                    }

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e(response);
                        listener.onSuccess(response);
                    }
                });
    }
    /**
     * 极客机器人
     *
     */
    public static void robotInfo(final OnDataCallBack listener) {
        OkHttpUtils.get()
                .url(Constant.API.ROBOT_INFO)
                .addParams("lang",Constant.API.lang)
                .addParams("siteCode", Constant.API.siteCode)
                .addParams("type","1")//类型（必填 0 PC端，1 APP 端）
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        listener.onFinish();
                    }

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e(response);
                        listener.onSuccess(response);
                    }
                });
    }
    /**
     * 根据问答回复
     *
     * @param question 提问
     */
    public static void findQuestion(String question, final OnDataCallBack listener) {
        OkHttpUtils.get()
                .url(Constant.API.FIND_QUESTION)
                .addParams("lang",Constant.API.lang)
                .addParams("siteCode", Constant.API.siteCode)
                .addParams("type","1")//类型（必填 0 PC端，1 APP 端）
                .addParams("question",question)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        listener.onFinish();
                    }

                    @Override
                    public void onBefore(Request request, int id) {
                        super.onBefore(request, id);
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e(response);
                        listener.onSuccess(response);
                    }
                });
    }
    //----------------------------------------------------------------------------------------首页

    /**
     * 获取banner数据
     */
    public static void getTopBanner(final OnDataCallBack listener) {
        OkHttpUtils.get()
                .url(Constant.API.SceneryIndexImg)
                .addParams("siteCode", Constant.API.siteCode)
                .addParams("lang","cn")
                .addParams("num","5")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        listener.onFinish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e(response);
                        if (isSuccess(response)){
                            listener.onSuccess(response);
                        }else {
                            listener.onFail();
                        }

                    }
                });
    }

    /**
     * 景区实时人数
     */
    public static void getToadayNum(final OnDataCallBack listener) {
        //愿你在迷茫时坚信你的珍贵，不忘对生命的思索，对自己的真实?sceneryId=945843598164993597&siteCode=qdnzxw
        OkHttpUtils.get()
                .url(Constant.API.RealTimeCount)
                .addParams("siteCode", Constant.API.siteCode)
                .addParams("lang", Constant.API.lang)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        listener.onFinish();
                    }
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        listener.onFail();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        LogUtils.e(response);
                        if (isSuccess(response)){
                            listener.onSuccess(response);
                        }else {
                            listener.onFail();
                        }
                    }
                });
    }

    /**
     * 景区最大承载量
     */
    public static void getMaxNum(final OnDataCallBack callBack) {
        OkHttpUtils.get()
                .url(Constant.API.MAXCAPACITY)
                .addParams("siteCode", Constant.API.siteCode)
                .addParams("lang", Constant.API.lang)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        callBack.onFinish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onFail();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (isSuccess(response)){
                            callBack.onSuccess(response);
                        }else {
                            callBack.onFail();
                        }
                        LogUtils.e(response);

                    }
                });
    }


    /**
     * 获取景区实时天气
     */
    public static void getSceneryWeatherInfo(final OnDataCallBack callBack) {
        OkHttpUtils.get()
                .url(Constant.API.SceneryWeatherInfo)
                .addParams("lang", Constant.API.lang)
                .addParams("siteCode", Constant.API.siteCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        callBack.onFinish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onFail();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (isSuccess(response)){
                            callBack.onSuccess(response);
                        }else {
                            callBack.onFail();
                        }

                        LogUtils.e(response);

                    }
                });
    }


    /**
     * 不能错过的景点sceneryId=945843598164993597&lang=cn&siteCode=qdnzxw&page=1&limitPage=10
     */
    public static void getMoreScenery(String name,int page,final OnDataCallBack callBack) {
        OkHttpUtils.get()
                .url(Constant.API.MoreScenery)
                .addParams("lang", Constant.API.lang)
                .addParams("name", name)
                .addParams("siteCode", Constant.API.siteCode)
                .addParams("page", page+"")
                .addParams("limitPage", Constant.API.limitPage+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        callBack.onFinish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onFail();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (isSuccess(response)){
                            callBack.onSuccess(response);
                        }else {
                            callBack.onFail();
                        }

                        LogUtils.e(response);

                    }
                });
    }
    /**
     * 广告lang=cn&
     * siteCode=slmhjqapp&
     * posCode=&
     * title=&
     * page=1&
     * limitPage=10
     * http://192.168.0.33:12588/api/scapi/app/siteAd/getSiteAdList?lang=cn&siteCode=slmhjqapp&posCode=&title=&page=1&limitPage=10
     */
    public static void getSiteAdList(String posCode,String title,int page,final OnDataCallBack callBack) {
        OkHttpUtils.get()
                .url(Constant.API.SiteAdList)
                .addParams("lang", Constant.API.lang)
                .addParams("title", title)
                .addParams("posCode", posCode)
                .addParams("siteCode", Constant.API.siteCode)
                .addParams("page", page+"")
                .addParams("limitPage", Constant.API.limitPage+"")
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        callBack.onFinish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onFail();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (isSuccess(response)){
                            callBack.onSuccess(response);
                        }else {
                            callBack.onFail();
                        }

                        LogUtils.e(response);

                    }
                });
    }

    /**
     * 获取景区banner
     * @param callBack
     * lang=cn&siteCode=slmhjqgw
     */
    public static void getSceneryBanner(final OnDataCallBack callBack) {
        OkHttpUtils.get()
                .url(Constant.API.getSceneryBanner)
                .addParams("lang", Constant.API.lang)
                .addParams("siteCode", Constant.API.siteCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        callBack.onFinish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onFail();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (isSuccess(response)){
                            callBack.onSuccess(response);
                        }else {
                            callBack.onFail();
                        }

                        LogUtils.e(response);

                    }
                });
    }
    /**
     * 游记攻略列表http://localhost:12588/api/scapi/app/travelStrategy/list?page=1&lang=cn&chanelId=&limitPage=10&title=&siteCode=test
     */
    public static void gettravelStrategy(int page,String search,final OnDataCallBack callBack) {
        OkHttpUtils.get()
                .url(Constant.API.travelStrategy)
                .addParams("page", page+"")
                .addParams("lang", "cn")
                .addParams("chanelId", "")
                .addParams("limitPage", Constant.API.limitPage+"")
                .addParams("title", search)
                .addParams("siteCode", Constant.API.siteCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        callBack.onFinish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onFail();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (isSuccess(response)){
                            callBack.onSuccess(response);
                        }else {
                            callBack.onFail();
                        }

                        LogUtils.e(response);

                    }
                });
    }

    /**
     * 游记攻略xaingq
     */
    public static void gettravelStrategyDetail(String id,final OnDataCallBack callBack) {
        OkHttpUtils.get()
                .url(Constant.API.travelStrategydetails)
                .addParams("id", id)
                .addParams("lang", "cn")
                .addParams("siteCode", Constant.API.siteCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        callBack.onFinish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onFail();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (isSuccess(response)){
                            callBack.onSuccess(response);
                        }else {
                            callBack.onFail();
                        }

                        LogUtils.e(response);

                    }
                });
    }
    /**
     * 景区画册http://localhost:12588/api/scapi/app/scenery/getSceneryImg?sceneryId=945843598164993597&lang=cn&siteCode=qdnzxw&page=1&limitPage=10
     */
    public static void getSceneryImg(String page,final OnDataCallBack callBack) {
        OkHttpUtils.get()
                .url(Constant.API.getSceneryImg)
                .addParams("page", page)
                .addParams("lang", Constant.API.lang)
                .addParams("limitPage", Constant.API.limitPage+"")
                .addParams("siteCode", Constant.API.siteCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        callBack.onFinish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onFail();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (isSuccess(response)){
                            callBack.onSuccess(response);
                        }else {
                            callBack.onFail();
                        }

                        LogUtils.e(response);

                    }
                });
    }


    /**
     * 景区视频
     */
    public static void getSceneryVideo(String name,int page,final OnDataCallBack callBack) {
        OkHttpUtils.get()
                .url(Constant.API.getSceneryVideo)
                .addParams("name", name)
                .addParams("page", page+"")
                .addParams("lang", Constant.API.lang)
                .addParams("limitPage", Constant.API.limitPage+"")
                .addParams("siteCode", Constant.API.siteCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        callBack.onFinish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onFail();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (isSuccess(response)){
                            callBack.onSuccess(response);
                        }else {
                            callBack.onFail();
                        }

                        LogUtils.e(response);

                    }
                });
    }

    /**
     * 探风景http://localhost:12588/api/scapi/app/scenery/getSceneryMonitor?sceneryId=945843598164993597&lang=cn&siteCode=qdnzxw&page=1&limitPage=10&name=
     */
    public static void getSceneryMonitor(String name,int page,final OnDataCallBack callBack) {
        OkHttpUtils.get()
                .url(Constant.API.getSceneryMonitor)
                .addParams("name", name)
                .addParams("page", page+"")
                .addParams("lang", Constant.API.lang)
                .addParams("limitPage", Constant.API.limitPage+"")
                .addParams("siteCode", Constant.API.siteCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        callBack.onFinish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onFail();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (isSuccess(response)){
                            callBack.onSuccess(response);
                        }else {
                            callBack.onFail();
                        }

                        LogUtils.e(response);

                    }
                });
    }


    /**
     * 景区公告siteNotice/list?
     * page=1
     * &lang=cn
     * &chanelId=
     * &limitPage=10
     * &title=
     * &siteCode=slmhjqgw
     */
    public static void getSiteNoticeList(int page,String chanelId,String title,final OnDataCallBack callBack) {
        OkHttpUtils.get()
                .url(Constant.API.getSiteNoticeList)
                .addParams("page", page+"")
                .addParams("lang", Constant.API.lang)
                .addParams("chanelId", chanelId)
                .addParams("title", title)
                .addParams("limitPage", Constant.API.limitPage+"")
                .addParams("siteCode", Constant.API.siteCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        callBack.onFinish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onFail();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (isSuccess(response)){
                            callBack.onSuccess(response);
                        }else {
                            callBack.onFail();
                        }

                        LogUtils.e(response);

                    }
                });
    }

    //720page=1&limitPage=20&siteCode=slmhjqgw&lang=cn
    public static void getSevenList(int page,final OnDataCallBack callBack) {
        OkHttpUtils.get()
                .url(Constant.API.getSevenList)
                .addParams("page", page+"")
                .addParams("limitPage", Constant.API.limitPage+"")
                .addParams("lang", Constant.API.lang)
                .addParams("siteCode", Constant.API.siteCode)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onAfter(int id) {
                        super.onAfter(id);
                        callBack.onFinish();
                    }

                    @Override
                    public void onError(Call call, Exception e, int id) {
                        callBack.onFail();

                    }

                    @Override
                    public void onResponse(String response, int id) {
                        if (isSuccess(response)){
                            callBack.onSuccess(response);
                        }else {
                            callBack.onFail();
                        }

                        LogUtils.e(response);

                    }
                });
    }
    /**
     * 判断code是否=0是否成功返回数据
     * @param response
     * @return
     */
    public static boolean isSuccess(String response){
        int success = 0;
        try {
            JSONObject object = JSONObject.parseObject(response);
            String data = object.getString("code");
            if (data.equals("0")){
                success = 0;
            }else {
               success = 1;
            }
        } catch (Exception e) {
            success = 1;
            LogUtils.e(e.toString());
        }
        if (success == 0){
            return true;
        }else {
            return false;
        }
    }

}
