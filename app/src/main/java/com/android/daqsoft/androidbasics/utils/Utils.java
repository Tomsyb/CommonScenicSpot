package com.android.daqsoft.androidbasics.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;

import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.common.Constant;

import java.util.LinkedList;

/**
 * <pre>
 *     author:
 *                                      ___           ___           ___         ___
 *         _____                       /  /\         /__/\         /__/|       /  /\
 *        /  /::\                     /  /::\        \  \:\       |  |:|      /  /:/
 *       /  /:/\:\    ___     ___    /  /:/\:\        \  \:\      |  |:|     /__/::\
 *      /  /:/~/::\  /__/\   /  /\  /  /:/~/::\   _____\__\:\   __|  |:|     \__\/\:\
 *     /__/:/ /:/\:| \  \:\ /  /:/ /__/:/ /:/\:\ /__/::::::::\ /__/\_|:|____    \  \:\
 *     \  \:\/:/~/:/  \  \:\  /:/  \  \:\/:/__\/ \  \:\~~\~~\/ \  \:\/:::::/     \__\:\
 *      \  \::/ /:/    \  \:\/:/    \  \::/       \  \:\  ~~~   \  \::/~~~~      /  /:/
 *       \  \:\/:/      \  \::/      \  \:\        \  \:\        \  \:\         /__/:/
 *        \  \::/        \__\/        \  \:\        \  \:\        \  \:\        \__\/
 *         \__\/                       \__\/         \__\/         \__\/
 *     blog  : http://blankj.com
 *     time  : 16/12/08
 *     desc  : utils about initialization
 * </pre>
 */
public final class Utils {

    /**
     * 带多个参数进行跳转
     *
     * @param context
     * @param targetActivity
     * @param bundle
     */
    public static void startActivity(Activity context, Activity targetActivity,
                                     Bundle bundle) {
        Intent intent = new Intent(context, targetActivity.getClass());
        intent.putExtras(bundle);
        context.startActivity(intent);
        context.overridePendingTransition(0, 0);// 取消Activity跳转效果
    }

    @SuppressLint("StaticFieldLeak")
    private static Application sApplication;

    private static LinkedList<Activity> sActivityList = new LinkedList<>();

    private static ActivityLifecycleCallbacks mCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityStarted(Activity activity) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityResumed(Activity activity) {
            setTopActivity(activity);
        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            sActivityList.remove(activity);
        }
    };

    private Utils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Init utils.
     * <p>Init it in the class of Application.</p>
     *
     * @param context context
     */
    public static void init(@NonNull final Context context) {
        Utils.sApplication = (Application) context.getApplicationContext();
        Utils.sApplication.registerActivityLifecycleCallbacks(mCallbacks);
    }

    /**
     * Return the context of Application object.
     *
     * @return the context of Application object
     */
    public static Application getApp() {
        if (sApplication != null) return sApplication;
        throw new NullPointerException("u should init first");
    }

    static void setTopActivity(final Activity activity) {
        if (activity.getClass() == PermissionUtils.PermissionActivity.class) return;
        if (sActivityList.contains(activity)) {
            if (!sActivityList.getLast().equals(activity)) {
                sActivityList.remove(activity);
                sActivityList.addLast(activity);
            }
        } else {
            sActivityList.addLast(activity);
        }
    }

    public static LinkedList<Activity> getActivityList() {
        return sActivityList;
    }

    /**
     * 将类型转换成汉字
     *
     * @param type
     * @return
     */
    public static String type2Chinese(String type) {
        String str = "";
        str = type.equals(Constant.SCENERY1) ? "景区" : type.equals(Constant.SHOPPING1) ? "购物" : type.equals(Constant.DINING1) ? "美食" : type.equals(Constant.HOTEL1) ? "酒店" : "娱乐";
        return str;
    }
    /**
     * 判断对象内容是否为空
     * @param obj 判断的对象
     * @return 不为空返回true。 为空返回false
     */
    public static boolean isnotNull(Object obj) {
        boolean b = false;
        if (null != obj && !obj.toString().equals("")
                && !obj.toString().toLowerCase().equals("null")
                && "undefined" != obj) {
            b = true;
        }
        return b;
    }
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
    /**
     * 跳转到下一个页面（不带参数）
     * @param activity
     * @param clss
     */
    public static void goToOtherClass(Activity activity, Class<?> clss){
        Intent intent = new Intent(activity,clss);
        activity.startActivity(intent);
        //activity.overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    /**
     * 跳转到下一个页面（带参数的跳转）
     * @param activity
     * @param clss
     * @param bundle
     */
    public static void goToOtherClass(Activity activity, Class<?> clss, Bundle bundle){
        Intent intent = new Intent(activity,clss);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }
    /**
     * 跳转到拨号界面，手动拨打
     *
     * @param phoneNumber
     */
    public static void justCall(String phoneNumber) {
        if (isnotNull(phoneNumber)) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
            IApplication.mActivity.startActivity(intent);
        } else {
            ToastUtils.showToast("暂无联系电话");
        }
    }
    public static String getKeyWord(String url){
        String value = "";
        String key = "";
        try {
            value = url.split("\\?")[1].split("&")[0].split("=")[1];
            key = url.split("\\?")[1].split("&")[0].split("=")[0];
            if (!key.equals("keyword")){
                value = "";
            }
        }catch (Exception e){
            value = "";
        }
        return  value;
    }
    public static String getURLval(String url,String key){
        if (url == null || url.equals(""))return null;
        String[] s = url.split("\\?");
        if (s.length > 1){
            url = s[1];
            s = url.split("&");
            // id = 12323 & 123 =222;
            String[] temp;
            for (String v : s) {
                temp = v.split("=");
                if (temp[0].trim().equals(key)){
                    return temp[1];
                }
            }
        }
        return null;
    }

}
