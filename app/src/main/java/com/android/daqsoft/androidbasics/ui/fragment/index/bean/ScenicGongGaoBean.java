package com.android.daqsoft.androidbasics.ui.fragment.index.bean;

import java.util.List;

/**
 *景区公告bean
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class ScenicGongGaoBean {

    /**
     * responseTime : 1523430826928
     * message : success
     * code : 0
     * datas : [{"id":8,"title":"赛里木湖景区官网开通啦","createDate":"2018-04-11","viewCount":0,"cover":"http://file.geeker.com.cn/uploadfile/ptisp/img/1523429246392/timg.jpg","summary":"赛里木湖景区官网开通啦"},{"id":9,"title":"赛里木湖景区微官网也开通拉","createDate":"2018-04-11","viewCount":1,"cover":"http://file.geeker.com.cn/uploadfile/ptisp/img/1523429309731/timg.jpg","summary":"赛里木湖景区微官网也开通拉"}]
     * page : {"total":2,"currPage":1,"pageSize":15,"totalPage":1}
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
         * total : 2
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
         * id : 8
         * title : 赛里木湖景区官网开通啦
         * createDate : 2018-04-11
         * viewCount : 0
         * cover : http://file.geeker.com.cn/uploadfile/ptisp/img/1523429246392/timg.jpg
         * summary : 赛里木湖景区官网开通啦
         */

        private int id;
        private String title;
        private String createDate;
        private int viewCount;
        private String cover;
        private String summary;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }

        public int getViewCount() {
            return viewCount;
        }

        public void setViewCount(int viewCount) {
            this.viewCount = viewCount;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }
    }
}
