/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import java.util.List;

/**
 * @param <T>
 */
public interface DAO<T> {
    
    abstract List<T> getAll();
    
    abstract void create(T t);
    
    abstract void update(T t);
    
    abstract void delete(T t);
    
    /**
     * Find Object with specified column data value.
     * Currently each concrete data access object specifies what column it is.
     * @param column
     * @return Object
     */
    abstract T find(String column);
    
    /**
     * Find Object with id value.
     * @param id
     * @return Object 
     */
    abstract T find(int id);
    
    abstract void refresh(T t);
    
}
