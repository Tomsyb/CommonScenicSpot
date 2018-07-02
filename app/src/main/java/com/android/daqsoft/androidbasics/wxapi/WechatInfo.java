package com.android.daqsoft.androidbasics.wxapi;

import java.io.Serializable;

/**
 * @Author: YanBo.
 * @Date: 2018-4-28.
 * @Describe: 微信返回的字段
 */
public class WechatInfo implements Serializable {

    private String openid = "";
    private String nickname = "";
    private String sex = "";
    private String language = "";
    private String city = "";
    private String province = "";
    private String country = "";
    private String headimgurl = "";
    private String[] privilege = null;
    private String unionid = "";

    public String getOpenid() {
        return openid;
    }

    public String getNickname() {
        return nickname;
    }

    public String getSex() {
        return sex;
    }

    public String getLanguage() {
        return language;
    }

    public String getCity() {
        return city;
    }

    public String getProvince() {
        return province;
    }

    public String getCountry() {
        return country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public String[] getPrivilege() {
        return privilege;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public void setPrivilege(String[] privilege) {
        this.privilege = privilege;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
