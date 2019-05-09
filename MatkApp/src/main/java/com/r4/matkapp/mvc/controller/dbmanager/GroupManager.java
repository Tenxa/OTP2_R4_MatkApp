/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.controller.dbmanager;

import com.r4.matkapp.mvc.controller.dbmanager.DatabaseManager;
import com.r4.matkapp.dao.GroupDAO;
import com.r4.matkapp.mvc.model.Group;

/**
 * Class for managing Group Database Access Object
 *
 * @author Eero
 */
public class GroupManager extends DatabaseManager<Group> {
    
    /**
     * Creates a Manager with a {@link ExpenseDAO}.
     */
    public GroupManager() {
        super(new GroupDAO(dbSession.getSessionFactory()));
    }
}
