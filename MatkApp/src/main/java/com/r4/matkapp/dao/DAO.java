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
    
    List<T> getAll();
    
    abstract void create(T t);
    
    abstract void update(T t, String[] params);
    
    abstract void delete(T t);
    
    abstract void close();
    
}
