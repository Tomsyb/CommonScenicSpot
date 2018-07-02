package com.android.daqsoft.androidbasics.ui.fragment.index.bean;

import java.util.List;

/**
 *
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class ScenicVideoBean {


    /**
     * responseTime : 1524192553787
     * message : success
     * code : 0
     * datas : [{"id":"125","title":"视频视频视频","mins":"","coverpicture":"http://file.geeker.com.cn/uploadfile/test/1523329518900/01-首页-2.jpg","upload":"http://file.geeker.com.cn/uploadfile/test/1523946247796/充气服咖喱咖喱完整版.mp4"},{"id":"121","title":"视频视频","mins":"2","coverpicture":"http://file.geeker.com.cn/uploadfile/test/1523431427186/搜狗截图20180411152232.png","upload":"http://file.geeker.com.cn/uploadfile/test/1524110386287/2017.7.10旅游微管家操作步骤_x264.mp4"},{"id":"115","title":"测试一则视频","mins":"","coverpicture":"","upload":"http://file.geeker.com.cn/uploadfile/test/1513841329169/1a3c75aae75a00df8db66c1082f3f1d7.mp4"}]
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
         * id : 125
         * title : 视频视频视频
         * mins :
         * coverpicture : http://file.geeker.com.cn/uploadfile/test/1523329518900/01-首页-2.jpg
         * upload : http://file.geeker.com.cn/uploadfile/test/1523946247796/充气服咖喱咖喱完整版.mp4
         */

        private String id;
        private String title;
        private String mins;
        private String coverpicture;
        private String upload;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getMins() {
            return mins;
        }

        public void setMins(String mins) {
            this.mins = mins;
        }

        public String getCoverpicture() {
            return coverpicture;
        }

        public void setCoverpicture(String coverpicture) {
            this.coverpicture = coverpicture;
        }

        public String getUpload() {
            return upload;
        }

        public void setUpload(String upload) {
            this.upload = upload;
        }
    }
}
