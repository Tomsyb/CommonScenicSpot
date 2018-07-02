package com.android.daqsoft.androidbasics.utils;

import com.andview.refreshview.XRefreshView;

/**
 * Created by yanbo on 2018-4-10.
 * 下拉刷新工具类
 */

public class XrefreshUtils {
    public void setInitFresh(XRefreshView xRefreshView, boolean isLoadMore) {
        //设置刷新完成以后，headerview固定的时间
        xRefreshView.setPinnedTime(1000);
        xRefreshView.setPullLoadEnable(isLoadMore);
        xRefreshView.setMoveForHorizontal(true);
        xRefreshView.setAutoLoadMore(true);
    }
}
