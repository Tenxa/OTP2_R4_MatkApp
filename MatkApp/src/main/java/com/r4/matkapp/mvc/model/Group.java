package com.r4.matkapp.mvc.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.transaction.Transactional;

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
    
    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "user_group")
    private Set<User> users = new HashSet<User>();
    
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
    

    /**
     * @return the expenses
     */
    public Set<Expense> getExpenses() {
        return expenses;
    }

    /**
     * @param expenses the expenses to set
     */
    public void setExpenses(Set<Expense> expenses) {
        this.expenses = expenses;
    }

    /**
     * @return the users
     */
    
    public Set<User> getUsers() {
        return users;
    }
    

}
