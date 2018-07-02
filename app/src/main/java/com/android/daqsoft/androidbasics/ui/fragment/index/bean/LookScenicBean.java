package com.android.daqsoft.androidbasics.ui.fragment.index.bean;

import java.util.List;


/**
 *
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class LookScenicBean {

    /**
     * responseTime : 1523420508796
     * message : success
     * code : 0
     * datas : [{"id":"47","name":"Phale","cover":"http://file.geeker.com.cn/uploadfile/test/1513407101578/594a6c1e32307.jpg","monitorPath":"http://183.221.61.239:83/Movies/pazhihua49/pazhihua49.m3u8","viewNum":"","viewNumToDay":""},{"id":"46","name":"嘉宾港","cover":"http://file.geeker.com.cn/uploadfile/ptisp/img/1513132686368/Tulips","monitorPath":"http://218.70.231.253:83/SEM/Movies/cqkjbxx/cqkjbxx.m3u8","viewNum":"","viewNumToDay":""}]
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
         * id : 47
         * name : Phale
         * cover : http://file.geeker.com.cn/uploadfile/test/1513407101578/594a6c1e32307.jpg
         * monitorPath : http://183.221.61.239:83/Movies/pazhihua49/pazhihua49.m3u8
         * viewNum :
         * viewNumToDay :
         */

        private String id;
        private String name;
        private String cover;
        private String monitorPath;
        private String viewNum;
        private String viewNumToDay;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCover() {
            return cover;
        }

        public void setCover(String cover) {
            this.cover = cover;
        }

        public String getMonitorPath() {
            return monitorPath;
        }

        public void setMonitorPath(String monitorPath) {
            this.monitorPath = monitorPath;
        }

        public String getViewNum() {
            return viewNum;
        }

        public void setViewNum(String viewNum) {
            this.viewNum = viewNum;
        }

        public String getViewNumToDay() {
            return viewNumToDay;
        }

        public void setViewNumToDay(String viewNumToDay) {
            this.viewNumToDay = viewNumToDay;
        }
    }
}
