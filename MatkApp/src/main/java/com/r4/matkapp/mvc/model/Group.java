package com.r4.matkapp.mvc.model;

import javax.persistence.*;

@Entity
@Table(name = "groups")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "invite")
    private String invite;

    public Group() { 
        super();
        invite = generateInvite();
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
    
    public String getInvite() {
        return invite;
    }

    private String generateInvite() {
        RandomString rs = new RandomString(10);  
        return rs.nextString();
    }

}
