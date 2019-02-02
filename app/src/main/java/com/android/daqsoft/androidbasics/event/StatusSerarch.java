package com.android.daqsoft.androidbasics.event;

/**
 * 功能
 *
 * @author 严博
 * @version 1.0.0
 * @date 2019-2-2.15:53
 * @since JDK 1.8
 */

public class StatusSerarch {
    private String id;
    private String status;
    private String type;
    private String time;
    private String people;
    private int ischecked;

    public int getIschecked() {
        return ischecked;
    }

    public void setIschecked(int ischecked) {
        this.ischecked = ischecked;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }
}
