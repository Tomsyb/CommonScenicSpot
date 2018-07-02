package com.daqi.spot;
/**
 * 配置信息
 *
 * @author MouJunFeng
 * @version 1.0.0
 * @time 2018-3-14
 * @since JDK 1.8
 */
public class Config {

    /**
     * APPID更新
     */
    public static final String APPID = "";
    public static String BASE_URL = "http://ptisp.exp.daqsoft.com/scapi/api/scapi/app/";//测试8012
    public static String HTML_BASE_URL = "http://ptisp-ui.exp.daqsoft.com/exp-jq-mob/view/";//网页url
    /**
     * app_id是从微信官网申请到的合法APPid
     */
    public static final String APP_ID_WX = "wxb363a9ff53731258";
    /**
     * 微信AppSecret值
     */
    public static final String  APP_SECRET_WX = "2b0d0325bb7c8383bff52e0900b7f56c";


    /**
     * 集客宝的瓦屋山的购买的appkey以及相应参数
     */
    public static final String JKB_BASE_URL = "http://jkb66.com/wap/common/";
    public static final String JKB_APPKEY = "0939809b-82e3-4e62-a41f-2b761461dc16";

    //特产的ID
    public static final String JKB_SPECIALTY_ID = "736";

    //酒店预订的ID
    public static final String JKB_HOTEL_ID = "735";

    //门票预订的ID
    public static final String JKB_SCENIC_ID = "721";

    //赛里木湖的memberId
    public static final String JKB_MEMBER_ID = "15389";

    //赛里木湖的source
    public static final String JKB_SOURCE = "shelves";


    //是否显示首页底部
    public static boolean showIndexBottom = true;
    /**
     * 站点编码
     */
    public static String SITECODE = "wwsapp";//qdnzxwApp,slmhjqapp
    /**
     * 地区编码
     */
    public static String REGION = "522600";
    /**
     * 地区名字
     */
    public static String CITY_NAME = "瓦屋山";
    /**
     * 当前地区的经纬度 lat lng
     */
    public static String COMMON_LAT = "26.517456607";
    public static String COMMON_LNG = "107.8052189117";
    /**
     * 关于信息
     */
    public static String about = ("瓦屋山");
    public static int ID = 34;
}