/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import com.r4.matkapp.mvc.model.User;
import java.util.List;

/**
 * @param <T>
 */
public interface DAO<T> {
    
    abstract List<T> getAll();
    
    abstract void create(T t);
    
    abstract void update(T t);
    
    abstract void delete(T t);
    
    abstract Object find(String column);
    
}
