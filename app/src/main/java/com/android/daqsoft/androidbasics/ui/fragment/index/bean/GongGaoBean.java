package com.android.daqsoft.androidbasics.ui.fragment.index.bean;

/**
 * Created by Daqsoft on 2018-4-3.
 */
/**
 *公告bean
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class GongGaoBean {
    /**
     * 图片地址
     */
    private String imgUrl;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
