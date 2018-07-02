package com.android.daqsoft.androidbasics.ui.fragment.index.bean;


/**
 *出行服务实时信息bean
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class ActualBean {
    /**
     * 0:实时天气。1：景区客流量，2：停车场
     */
    private int ItemType;
    /**
     * 顶部名字
     */
    private String topName;
    /**
     * 天气
     */
    private String weather;
    /**
     * 旅游数量
     */
    private String travelNum;
    /**
     * 内容
     */
    private String content;
    /**
     * 实时人数
     */
    private String shushiNum;
    /**
     * 客流量
     */
    private String keNum;
    /**
     * 今日数量
     */
    private String toadayNum;
    /**
     * 最大人数
     */
    private String maxNum;

    /**
     * 底部内容
     */
    private String bottomContent;
    /**
     * 车流量
     */
    private String carshen;
    /**
     * 车流量总计
     */
    private String carAll;

    public int getItemType() {
        return ItemType;
    }

    public void setItemType(int itemType) {
        ItemType = itemType;
    }

    public String getTopName() {
        return topName;
    }

    public void setTopName(String topName) {
        this.topName = topName;
    }

    public String getWeather() {
        return weather;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

    public String getTravelNum() {
        return travelNum;
    }

    public void setTravelNum(String travelNum) {
        this.travelNum = travelNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getShushiNum() {
        return shushiNum;
    }

    public void setShushiNum(String shushiNum) {
        this.shushiNum = shushiNum;
    }

    public String getKeNum() {
        return keNum;
    }

    public void setKeNum(String keNum) {
        this.keNum = keNum;
    }

    public String getToadayNum() {
        return toadayNum;
    }

    public void setToadayNum(String toadayNum) {
        this.toadayNum = toadayNum;
    }

    public String getMaxNum() {
        return maxNum;
    }

    public void setMaxNum(String maxNum) {
        this.maxNum = maxNum;
    }

    public String getBottomContent() {
        return bottomContent;
    }

    public void setBottomContent(String bottomContent) {
        this.bottomContent = bottomContent;
    }

    public String getCarshen() {
        return carshen;
    }

    public void setCarshen(String carshen) {
        this.carshen = carshen;
    }

    public String getCarAll() {
        return carAll;
    }

    public void setCarAll(String carAll) {
        this.carAll = carAll;
    }
}
