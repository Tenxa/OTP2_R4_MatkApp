/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.dao;

import java.util.List;

/**
 * @param <Entity>
 */
public interface DAO<Entity> {
    
    /**
     * 
     * @return List containing all entities (rows) in table or null if table is empty.
     */
    abstract List<Entity> getAll();
    
    /**
     * Persist the given transient entity, first assigning a generated identifier.
     * 
     * @param entity 
     */
    abstract void create(Entity entity);
    
    /**
     * Update the persistent entity with the identifier of the given detached instance.
     * 
     * @param entity 
     */
    abstract void update(Entity entity);
    
    /**
     * Remove a persistent entity.
     * 
     * @param entity 
     */
    abstract void delete(Entity entity);
    
    /**
     * Find Object with specified column data value.
     * Currently each concrete data access object specifies what column it is.
     * 
     * @param column Table column name.
     * @return Entity or null if row does not exits.
     */
    abstract Entity find(String column);
    
    /**
     * Find Entity with id value.
     * 
     * @param id Entity id attribute.
     * @return Entity or null if row does not exits. 
     */
    abstract Entity find(int id);
    
    /**
     * Re-read the state of the given entity from the underlying database.
     * 
     * @param entity 
     */
    abstract void refresh(Entity entity);
    
}
