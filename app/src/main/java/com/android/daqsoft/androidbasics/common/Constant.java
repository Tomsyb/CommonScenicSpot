package com.android.daqsoft.androidbasics.common;

import android.content.Context;

import com.android.daqsoft.androidbasics.R;
import com.android.daqsoft.androidbasics.base.IApplication;
import com.android.daqsoft.androidbasics.event.Basebean;
import com.android.daqsoft.androidbasics.ui.fragment.serve.bean.ServeListBean;
import com.android.daqsoft.androidbasics.view.sortbutton.model.ButtonModel;
import com.daqi.spot.Config;

import java.util.ArrayList;
import java.util.List;

/**
 *常量
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class Constant {
    public static String SCENERY1 = "scenery";
    public static String VIDEO1 = "video";
    public static String HOTEL1 = "hotel";
    public static String DINING1 = "dining";
    public static String SHOPPING1 = "shopping";
    public static String WXHEAD_URL = "WXHEAD_URL";
    public static String JQGGLIST = "JQGGLIST";
    public static String RobotXq = "RobotXq";
    public static String BASE_URL = "BASE_URL";
    //
    public static final int FIRST = 0;
    public static final int SECOND = 1;
    public static final int THIRD = 2;
    public static final int FOURTH = 3;
    public static  String VIDEO_PATH = "videoPath";
    public static  String VIDEO_TITLE = "videoTitle";
    public static  String VIDEO_IMG = "videoImg";
    public static  String HTML_URL = "HTMLURL";
    /**
     * 直接从手机相册选择图片
     */
    public static int REQUEST_IMAGE = 200;
    /**
     * 跳转到系统相机
     */
    public static int REQUEST_CAMERA = 201;
    /**
     * 签到拍照进入预览页面后回到拍照页面(提交成功)
     */
    public static int REQUEST_PREVIEW = 202;
    /**
     *重拍
     */
    public static int REQUEST_PREVIEWBACK = 204;
    /**
     * 获取定位信息后返回
     */
    public static int REQUEST_LOCATION = 203;
    /**
     * 导游导览传入的ID区分景区用 赛里木是8
     */
    public static String ID = "ID";

    /**
     * 接口
     */
    public static class API{
        /**
         * 版本更新地址
         */
        public static final String VERSION_URL = "http://app.daqsoft.com/appserives/Services.aspx";
        /**
         * 微信
         */
        public static String WECHAT_CODE_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
        public static String WECHAT_INFO_URL = "https://api.weixin.qq.com/sns/userinfo";
        /**
         * 保存微信用户登录后的信息
         */
        public static String WECHAT_OPENID = "wechat_openid";
        public static String WECHAT_ACCESS_TOKEN = "wechat_access_token";
        public static String WECHAT_USERINFO = "wechat_userinfo";
        public static String WECHAT_USERNICKNAME = "wechat_usernickname";
        public static String WECHAT_UNIONID = "unionid";
        public static String WECHAT_CODE = "wxcode";

        public static String BASE_URL = Config.BASE_URL;
        /**
         * 请求每页数量
         */
        public static int limitPage = 15;
        public static String lang = "cn";
        /**
         * 网页
         */

        /**
         * 景区简介、游览须知、票务政策、开放时间
         */
        public static String KnowScenicDetail = Config.HTML_BASE_URL+"detail.html?code=";
        /**
         * 景区交通
         */
        public static String TrafficDetail = Config.HTML_BASE_URL+"scenic-traffic.html?type=Android&lat="
                + IApplication.SP.getString("lastLat")+"&lon="+IApplication.SP.getString("lastLng");
        /**
         * 景区公告、详情item.
         */
        public static String HTML_JQGG_Detail = Config.HTML_BASE_URL+"notice-detail.html?id=";
        /**
         * 游记攻略详情item
         */
        public static String HTML_YJGL_Detail = Config.HTML_BASE_URL+"notes-detail.html?id=";
        /**
         * 出行服务
         */
        public static String HTML_GOTRAVEL = Config.HTML_BASE_URL+"weather.html";
        /**
         * 公交查询
         */
        public static String HTML_BUS = Config.HTML_BASE_URL+"bus-query.html";
        /**
         * 火车票
         */
        public static String HTML_TRAIN = Config.HTML_BASE_URL+"train-query.html";
        public static String HTML_MORE_SCENIC = Config.HTML_BASE_URL+"spot-desc.html?id=";
        /**
         * 网页不同的code
         */

        public static String Code_JQJJ = "jqjj";//景区简介
        public static String Code_YLXZ = "ylxz";//游览须知
        public static String Code_PWZC = "pwzc";//票务政策
        public static String Code_KFSJ = "kfsj";//开放时间
        public static String Code_JQWH = "jqwh";//景区文化
        public static String Code_MFMS = "mfms";//民风民俗

        /**
         * 站点区分项目用
         */
        public static String siteCode = Config.SITECODE;
        /**
         * 机器人
         */
        public static String WECHAT_HEADIMGURL = "wechat_headimgurl";
        /**
         * 问答回复
         */
        public static String FIND_QUESTION =BASE_URL+ "robotQuestion/findQuestion";
        /**
         * 机器人信息
         */
        public static String ROBOT_INFO = BASE_URL+"robotInfo/findRobotInfo";
        /**
         * 根据分类查询问答标签
         */
        public static String QUESTION_BY_TYPE = BASE_URL+"robotQuestion/findQuestionByType";
        /**
         * 业务分类列表
         */
        public static String ROBOT_TYPE_LIST = BASE_URL+"robotQuestion/findTypeList";
        /**
         * 景区最大承载量
         */
        public static String MAXCAPACITY = BASE_URL+"index/getMaxCapacity";
        /**
         * 景区实时人数
         */
        public static String RealTimeCount = BASE_URL+"index/getRealTimeCount";
        /**
         * 图片轮播
         */
        public static String SceneryIndexImg = BASE_URL+"index/getSceneryIndexImg";
        /**
         * 景区banner图http://localhost:12588/api/scapi/app/index/getSceneryBanner?lang=cn&siteCode=slmhjqgw
         */

        public static String getSceneryBanner = BASE_URL+"index/getSceneryBanner";
        /**
         * 天气
         */
        public static String SceneryWeatherInfo = BASE_URL+"index/getSceneryWeatherInfo";
        /**
         * 跟多不错过的景点
         */
        public static String MoreScenery = BASE_URL+"index/children";
        public static String SiteAdList = BASE_URL+"siteAd/getSiteAdList";
        /**
         * 游记攻略列表
         */
        public static String travelStrategy = BASE_URL+"travelStrategy/list";
        /**
         * 游记攻略列表详情
         */
        public static String travelStrategydetails = BASE_URL+"travelStrategy/detail";
        /**
         * 景区画册
         */
        public static String getSceneryImg = BASE_URL+"scenery/getSceneryImg";
        /**
         * 景区视频
         */
        public static String getSceneryVideo = BASE_URL+"scenery/getSceneryVideo";
        /**
         * 探风景
         */
        public static String getSceneryMonitor = BASE_URL+"scenery/getSceneryMonitor";
        /**
         * 景区公告
         */
        public static String getSiteNoticeList = BASE_URL+"siteNotice/list";
        /**
         * 720
         */
        public static String getSevenList = BASE_URL+"panorama/panoramaList";

        /**
         * 集客宝的相应数据
         */
        /**
         * 个人中心的地址
         */
        public static String JKB_USER_CENTER = Config.JKB_BASE_URL+"userCenter.jhtml?appkey="+Config.JKB_APPKEY;
        /**
         * 购买链接
         */
        public static String JKB_BUY = Config.JKB_BASE_URL+"index.jhtml?appkey="
                +Config.JKB_APPKEY+"&memberId="+Config.JKB_MEMBER_ID+"&source="+Config.JKB_SOURCE;


    }


    //***************************************************下面是死数据返回值********************************************

    /**
     *
     * @return 获取首页菜单列表
     */
    public static List<ButtonModel> getBtnList(Context context){
        Integer[] icons = new Integer[]{
                R.drawable.home_entrance_spot,
                R.drawable.home_entrance_book,
                R.drawable.home_entrance_camera,
                R.drawable.home_entrance_scenic,
                R.drawable.home_entrance_panorama,
                R.drawable.home_entrance_service,
                R.drawable.home_entrance_robot,
                R.drawable.home_entrance_report};
        String[] dataArr = context.getResources().getStringArray(R.array.index_menu);
        List<ButtonModel> data = new ArrayList<>();
        for (int i = 0; i < dataArr.length; i++) {
            ButtonModel bean = new ButtonModel();
            bean.setDrawableIcon(icons[i]);
            bean.setName(dataArr[i]);
            data.add(bean);
        }
        return data;
    }

    /**
     *
     * @return 了解景区菜单
     */
    public static List<Basebean> getSinecList(Context context){
        Integer[] icons = new Integer[]{
                R.drawable.home_entrance_spot,
                R.drawable.home_entrance_book,
                R.drawable.home_entrance_camera,
                R.drawable.home_entrance_scenic,
                R.drawable.home_entrance_panorama,
                R.drawable.home_entrance_service,
                R.drawable.home_entrance_robot,
                R.drawable.home_entrance_report};
        String[] dataArr = context.getResources().getStringArray(R.array.index_menu);
        List<Basebean> data = new ArrayList<>();
        for (int i = 0; i < dataArr.length; i++) {
            Basebean bean = new Basebean();
            bean.setName(dataArr[i]);
            bean.setImgId(icons[i]);
            data.add(bean);
        }
        return data;
    }

    /**
     * 获取预定服务的列表数据
     * @return
     */
    public static List<Basebean> getServeList(){
        List<Basebean> mDatas = new ArrayList<>();
        Integer[] icons = new Integer[]{
                R.drawable.book_index_tickets_normal,
                R.drawable.book_index_hotel_normal,
                R.drawable.book_index_goods_normal
//                R.drawable.book_index_food_normal,
//                R.drawable.book_index_fun_normal,
//                R.drawable.book_index_guide_normal
        };
        for (int i = 0; i < icons.length; i++) {
            Basebean bean = new Basebean();
            bean.setImgId(icons[i]);
            mDatas.add(bean);
        }
        return mDatas;
    }

    /**
     * 获取正如你所见的列表数据
     * @return
     */
    public static List<ServeListBean> getAsyouSayList(){
        List<ServeListBean> mDatas = new ArrayList<>();
        Integer[] icons = new Integer[]{
                R.drawable.spot_photograph_icon_plants,
                R.drawable.spot_photograph_icon_buildings,
                R.drawable.spot_photograph_icon_culture};

        ServeListBean bean = new ServeListBean();
        bean.setImgID(R.drawable.spot_photograph_icon_plants);
        bean.setTitle("识花草");
        bean.setContent("看点：白石海、墨海");
        mDatas.add(bean);

        bean = new ServeListBean();
        bean.setImgID(R.drawable.spot_photograph_icon_buildings);
        bean.setTitle("识建筑");
        bean.setContent("看点：白石海、墨海");
        mDatas.add(bean);

        bean = new ServeListBean();
        bean.setImgID(R.drawable.spot_photograph_icon_culture);
        bean.setTitle("识文化");
        bean.setContent("看点：白石海、墨海");
        mDatas.add(bean);
        return mDatas;
    }
}
