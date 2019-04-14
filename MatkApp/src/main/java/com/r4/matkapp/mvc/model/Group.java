package com.r4.matkapp.mvc.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.transaction.Transactional;

@Entity
@Table(name = "groups")
public class Group implements Comparable{

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
    
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "expense_group",
            orphanRemoval = true)
    private Set<Expense> expenses = new HashSet<>();
    
    @Column(name = "budget")
    private double budget;

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
    
    public void addExpense(Expense e) {
        this.expenses.add(e);
    }

    /**
     * @return the users
     */
    
    public Set<User> getUsers() {
        return users;
    }

    /**
     * @return the budget
     */
    public double getBudget() {
        return budget;
    }

    /**
     * @param budget the budget to set
     */
    public void setBudget(double budget) {
        this.budget = budget;
    }

    @Override
    public int compareTo(Object o) {
        return name.compareTo(((Group)o).getGroup_name());  
    }
    

}
