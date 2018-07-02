package com.android.daqsoft.androidbasics.ui.fragment.index.bean;

import java.util.List;

/**
 *720bean
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class IndexSevenBean {
    /**
     * responseTime : 1523501866584
     * message : success
     * code : 0
     * datas : [{"name":"赛里木湖天湖古口","id":92,"coverpicture":"http://file.geeker.com.cn/uploadfile/test/1523431427186/搜狗截图20180411152232.png","url":"http://720.geeker.com.cn/rest/index/view.action?id=5761"},{"name":"赛里木湖景区","id":91,"coverpicture":"http://file.geeker.com.cn/uploadfile/test/1523430268265/搜狗截图20180411150340.png","url":"http://720.geeker.com.cn/rest/index/view.action?id=5760"},{"name":"cs","id":90,"coverpicture":"http://file.geeker.com.cn/uploadfile/test/1523263607030/01-登录注册.jpg","url":"http://www.facebook.com/"}]
     * page : {"total":3,"currPage":1,"pageSize":15,"totalPage":1}
     */

    private long responseTime;
    private String message;
    private int code;
    private PageBean page;
    private List<DatasBean> datas;

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<DatasBean> getDatas() {
        return datas;
    }

    public void setDatas(List<DatasBean> datas) {
        this.datas = datas;
    }

    public static class PageBean {
        /**
         * total : 3
         * currPage : 1
         * pageSize : 15
         * totalPage : 1
         */

        private int total;
        private int currPage;
        private int pageSize;
        private int totalPage;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getCurrPage() {
            return currPage;
        }

        public void setCurrPage(int currPage) {
            this.currPage = currPage;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public int getTotalPage() {
            return totalPage;
        }

        public void setTotalPage(int totalPage) {
            this.totalPage = totalPage;
        }
    }

    public static class DatasBean {
        /**
         * name : 赛里木湖天湖古口
         * id : 92
         * coverpicture : http://file.geeker.com.cn/uploadfile/test/1523431427186/搜狗截图20180411152232.png
         * url : http://720.geeker.com.cn/rest/index/view.action?id=5761
         */

        private String name;
        private int id;
        private String coverpicture;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCoverpicture() {
            return coverpicture;
        }

        public void setCoverpicture(String coverpicture) {
            this.coverpicture = coverpicture;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
