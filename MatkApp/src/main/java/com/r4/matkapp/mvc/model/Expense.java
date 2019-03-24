package com.r4.matkapp.mvc.model;

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
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_group")
    private Group user_group;
    @Column(name = "expense_description")
    private String expense_description;
    @Column(name = "expense_amount")
    private double expense_amount;
    /** ei nykysuunnitelmalla tarpeellinen kenttä
     * @Column(name = "expense_currency")
     * private String expense_currency
     */
    
    //Ajattelumalli:
    //True = kaikki osallistuu, tasajako
    //False = ei kaikki osallistu tai ei tasajako
    @Column(name = "equal_split")
    private boolean equal_split;
    
    public Expense() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Group getUser_group() {
        return user_group;
    }

    public void setUser_group(Group user_group) {
        this.user_group = user_group;
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
    
    
    
}
