/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.r4.matkapp.mvc.model.dbmanager;

import com.r4.matkapp.mvc.model.Group;

/**
 *
 * @author Eero
 */
public class GroupManager extends DatabaseManager<Group> {

    @Override
    public void create(Group entity) {
        GROUP_DAO.create(entity);
    }

    @Override
    public void refresh(Group entity) {
        GROUP_DAO.refresh(entity);
    }

    @Override
    public void update(Group entity) {
        GROUP_DAO.update(entity);
    }

    @Override
    public void delete(Group entity) {
        GROUP_DAO.delete(entity);
    }

    @Override
    public Group find(int id) {
        return GROUP_DAO.find(id);
    }

    @Override
    public Group find(String col) {
        return GROUP_DAO.find(col);
    }
}
