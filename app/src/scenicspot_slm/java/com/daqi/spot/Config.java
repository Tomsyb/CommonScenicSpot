package com.daqi.spot;

public class Config {
    //APPID更新
    public static final String APPID = "94762";//94762
    public static String BASE_URL = "http://192.168.0.33:8012/api/scapi/app/";//测试8012
    public static String HTML_BASE_URL = "http://p.ued.daqsoft.com/slmh-app/view/";//网页url
    /**
     * app_id是从微信官网申请到的合法APPid
     */
    public static final String APP_ID_WX = "wxffc9df981b7a71db";
    /**
     * 微信AppSecret值
     */
    public static final String  APP_SECRET_WX = "9ac8f369e79b32909f0d878d5bb405a7";

    /**
     * 集客宝的赛里木湖的购买的appkey以及相应参数
     */
    //public static final String JKB_BASE_URL = "http://jkb66.com/wap/common/";//正式
    public static final String JKB_BASE_URL = "http://test.wxtest.daqsoft.com/wap/common/";//测试
    //public static final String JKB_APPKEY = "0939809b-82e3-4e62-a41f-2b761461dc16";
    public static final String JKB_APPKEY = "b8eba468-cc2d-41be-9a4b-10d7c2b9865a";//测试

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
    public static String SITECODE = "qdnzxwApp";//qdnzxwApp,slmhjqapp
    /**
     * 地区编码
     */
    public static String REGION = "522600";
    /**
     * 地区名字
     */
    public static String CITY_NAME = "赛里木";
    /**
     * 当前地区的经纬度 lat lng
     */
    public static String COMMON_LAT = "26.517456607";
    public static String COMMON_LNG = "107.8052189117";
    /**
     * 关于信息
     */
    public static String about = ("赛里木");
    public static int ID = 36;
}