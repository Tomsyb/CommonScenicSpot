package com.android.daqsoft.androidbasics.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.android.daqsoft.androidbasics.BuildConfig;
import com.android.daqsoft.androidbasics.common.Constant;
import com.android.daqsoft.androidbasics.utils.InitUtils;
import com.android.daqsoft.androidbasics.utils.LogUtils;
import com.android.daqsoft.androidbasics.utils.SPUtils;
import com.android.daqsoft.androidbasics.utils.Utils;
import com.android.daqsoft.androidbasics.view.CustomDialog;
import com.daqi.spot.Config;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.log.LoggerInterceptor;


import java.io.File;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

import me.yokeyword.fragmentation.Fragmentation;
import okhttp3.OkHttpClient;

/**
 *
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IApplication extends Application {
    /**
     * 当前Activity
     */
    public static Activity mActivity = null;

    /**
     *
     * @return 获取被选中的位置
     */
    public static int getTabSelceedPosition() {
        return tabSelceedPosition;
    }

    /**
     * 设置选中的位置
     * @param tabSelceedPosition
     */
    public static void setTabSelceedPosition(int tabSelceedPosition) {
        IApplication.tabSelceedPosition = tabSelceedPosition;
    }

    /**
     * 首页选中的 item
     */
    public static int tabSelceedPosition;
    /**
     * sppreence
     */
    public static SPUtils SP;
    /**
     * WXAPI
     */
    public static IWXAPI mWxApi;

    @Override
    public void onCreate() {
        super.onCreate();


        initWx();
//        // 建议在Application里初始化
//        Fragmentation.builder()
//                // 显示悬浮球 ; 其他Mode:SHAKE: 摇一摇唤出   NONE：隐藏
//                .stackViewMode(Fragmentation.BUBBLE)
//                .debug(BuildConfig.DEBUG)
//                .install();

        Utils.init(this);
        InitUtils.init(this);

        initLog();
        initHttpUtils();
        SP = SPUtils.getInstance("jingqu");
    }

    /**
     * 初始化微信
     */
    private void initWx() {
        //AppConst.WEIXIN.APP_ID是指你应用在微信开放平台上的AppID，记得替换。
        mWxApi = WXAPIFactory.createWXAPI(this, Config.APP_ID_WX, false);
        // 将该app注册到微信
        mWxApi.registerApp(Config.APP_ID_WX);
    }

    /**
     *
     * 初始化网络请求
     */
    private void initHttpUtils() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
               .addInterceptor(new LoggerInterceptor("yanb"))
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();

        OkHttpUtils.initClient(okHttpClient);
    }

    /**
     * 初始化LOG
     */
    private void initLog() {
        LogUtils.Config config = LogUtils.init(this)
                .setLogSwitch(BuildConfig.DEBUG)// 设置log总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(BuildConfig.DEBUG)// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为"alog"，即写入文件为"alog-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setConsoleFilter(LogUtils.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
                .setFileFilter(LogUtils.V)// log文件过滤器，和logcat过滤器同理，默认Verbose
                .setStackDeep(1);// log栈深度，默认为1
        LogUtils.d(config.toString());
    }

    /**
     * 加载框
     */
    public static CustomDialog logDialog;

    /**
     * 下面是加载框
     * @param context
     */
    public static void showLoadingDialog(Context context) {
        if (logDialog == null) {
            logDialog = new CustomDialog(context,"加载中...");
        }
        logDialog.show();
    }

    /**
     * 消失加载框
     */
    public static void dismissLoadingDialog() {
        if (logDialog != null) {
            logDialog.dismiss();
        }
    }

    /**
     * 避免出现java资源过多
     * @param base
     */
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    /**
     * finish所有list中的activity
     */
    public static void exit() {
        LinkedList<Activity> activityList = Utils.getActivityList();
        synchronized (activityList) {
            int size = activityList.size();
            for (int i = 0; i < size; i++) {
                if (activityList.get(0) != null) {
                    activityList.get(0).finish();
                }
            }
            System.gc();// 垃圾回收
            android.os.Process.killProcess(android.os.Process.myPid());
            System.runFinalization();
            System.exit(0);
        }
    }

}
