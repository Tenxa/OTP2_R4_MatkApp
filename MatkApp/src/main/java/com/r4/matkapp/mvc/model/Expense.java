package com.r4.matkapp.mvc.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

/**
 *
 * @author Eero & Mika
 */
@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private int id;
    
    //Olettaen, että rakkenne kulut liitetään aina johonkin ryhmään
    
    
    
    @Column(name = "expense_description")
    private String expense_description;
    @Column(name = "expense_amount")
    private double expense_amount;
    /** ei nykysuunnitelmalla tarpeellinen kenttä
     * @Column(name = "expense_currency")
     * private String expense_currency
     */
    
    @ManyToOne
    private Group expense_group;
    
    //Ajattelumalli:
    //True = kaikki osallistuu, tasajako
    //False = ei kaikki osallistu tai ei tasajako
    @Column(name = "equal_split")
    private boolean equal_split;
    
    @ManyToMany(fetch=FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_expenses",
        joinColumns = @JoinColumn(name = "expense_id"),
        inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> joined_users = new HashSet<>();
    
    public Expense() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExpense_description() {
        return expense_description;
    }

    public void setExpense_description(String expense_description) {
        this.expense_description = expense_description;
    }

    public double getExpense_amount() {
        return expense_amount;
    }

    public void setExpense_amount(double expense_amount) {
        this.expense_amount = expense_amount;
    }

    public boolean isEqual_split() {
        return equal_split;
    }

    public void setEqual_split(boolean equal_split) {
        this.equal_split = equal_split;
    }

    /**
     * @return the expense_group
     */
    public Group getExpense_group() {
        return expense_group;
    }

    /**
     * @param expense_group the expense_group to set
     */
    public void setExpense_group(Group expense_group) {
        this.expense_group = expense_group;
    }
    
    public void addUser(User u) {
        getUsers().add(u);
    }

    /**
     * @return the users
     */
    public Set<User> getUsers() {
        return joined_users;
    }

    /**
     * @param users the users to set
     */
    public void setUsers(Set<User> users) {
        this.joined_users = users;
    }
    
}
