package com.r4.matkapp.mvc.model;

import java.util.HashSet;
import java.util.Set;
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
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "expense_group",
            orphanRemoval = true)
    private Set<Expense> expenses = new HashSet<>();

    public Group() { 
        super();
        invite = generateInvite();
    }

    public Group(String group_name) {
        this.name = group_name;
        invite = generateInvite();
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
    
    public void sendInvite(User user){
        SendEmail email = new SendEmail();
        String subject = "MatkApp group invitation";
        String text = "Group: " + this.getGroup_name() + " has sent you an invitation. Here is the link: " + this.getInvite();
        email.Send(user.getEmail(), subject, text);
    }

}
