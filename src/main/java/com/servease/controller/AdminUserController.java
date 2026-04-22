package com.servease.controller;

import com.servease.dao.AdminUserDAO;

import java.util.List;

public class AdminUserController {

    private AdminUserDAO dao = new AdminUserDAO();

    public List<Object[]> getUsers() {
        return dao.getAllUsers();
    }

    public void suspendUser(int id) {
        dao.updateStatus(id, "SUSPENDED");
    }

    public void activateUser(int id) {
        dao.updateStatus(id, "ACTIVE");
    }
}