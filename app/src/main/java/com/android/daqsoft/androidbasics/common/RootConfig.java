package com.android.daqsoft.androidbasics.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * 机器人跳转带的参数
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-6.16:23
 * @since JDK 1.8
 */

public class RootConfig implements Serializable {
    private String baseUrl;
    private String wxAppId;
    private String imgMe;

    public RootConfig(String baseUrl, String wxAppId, String imgMe) {
        this.baseUrl = baseUrl;
        this.wxAppId = wxAppId;
        this.imgMe = imgMe;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getWxAppId() {
        return wxAppId;
    }

    public void setWxAppId(String wxAppId) {
        this.wxAppId = wxAppId;
    }

    public String getImgMe() {
        return imgMe;
    }

    public void setImgMe(String imgMe) {
        this.imgMe = imgMe;
    }
}
