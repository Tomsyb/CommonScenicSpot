package com.android.daqsoft.androidbasics.event;

/**
 * 基础bean
 * @author 严博
 * @date 2018-4-28.
 * @version 1.0.0
 * @since JDK 1.8
 */
public class TabSelectedEvent {
    public int position;
    public TabSelectedEvent(int position) {
        this.position = position;
    }
}
