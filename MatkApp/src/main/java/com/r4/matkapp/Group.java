package com.r4.matkapp;

import javax.persistence.*;

@Entity
@Table(name = "group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id", updatable = false, nullable = false)
    private int group_id;
    
    @Column(name="group_name")
    private String group_name;
    
    public Group() {
        super();
    }

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
