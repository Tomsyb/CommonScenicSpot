package com.android.daqsoft.androidbasics.event;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2018-6-21.17:07
 * @since JDK 1.8
 */

public class IndexYiBean {
    private String name;
    private int resId;
    private String content;
    private String zantai;
    private String fenxiang;

    public String getZantai() {
        return zantai;
    }

    public void setZantai(String zantai) {
        this.zantai = zantai;
    }

    public String getFenxiang() {
        return fenxiang;
    }

    public void setFenxiang(String fenxiang) {
        this.fenxiang = fenxiang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
