package com.r4.matkapp;

public class Group {
    private int group_id;
    private String group_name;

    public Group(String group_name) {
        this.group_name = group_name;
    }

    public int getGroup_id() {
        return group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
    
}
