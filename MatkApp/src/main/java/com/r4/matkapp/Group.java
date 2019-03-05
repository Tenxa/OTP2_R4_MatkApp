package com.r4.matkapp;

import javax.persistence.*;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    
    @Column(name="name")
    private String name;
    
    public Group() {
        super();
    }

    public Group(String group_name) {
        this.name = group_name;
    }

    public int getId() {
        return id;
    }

    public String getGroup_name() {
        return name;
    }

    public void setGroup_name(String group_name) {
        this.name = group_name;
    }

}
