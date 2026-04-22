package com.servease.controller;

import com.servease.dao.AdminProviderDAO;

import java.util.List;

public class AdminProviderController {

    private AdminProviderDAO dao = new AdminProviderDAO();

    public List<Object[]> getProviders() {
        return dao.getProviders();
    }

    public void approve(int id) {
        dao.updateStatus(id, "ACTIVE");
    }

    public void reject(int id) {
        dao.updateStatus(id, "REJECTED");
    }

    public void suspend(int id) {
        dao.updateStatus(id, "SUSPENDED");
    }
}