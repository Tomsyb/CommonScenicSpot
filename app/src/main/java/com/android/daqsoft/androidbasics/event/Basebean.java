package com.android.daqsoft.androidbasics.event;

/**
 * 基础bean
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class Basebean {
    /**
     * item 的名字
     */
    private String name;
    /**
     * item的图片ID
     */
    private int ImgId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImgId() {
        return ImgId;
    }

    public void setImgId(int imgId) {
        ImgId = imgId;
    }
}
