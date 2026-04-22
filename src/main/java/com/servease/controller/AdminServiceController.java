package com.servease.controller;

import com.servease.dao.ServiceDAO;

import java.util.List;

public class AdminServiceController {

    private ServiceDAO dao = new ServiceDAO();

    public List<Object[]> getAllServices() {
        return dao.getAllServicesWithProvider();
    }

    public boolean deleteService(int id) {
        return dao.deleteService(id);
    }
}