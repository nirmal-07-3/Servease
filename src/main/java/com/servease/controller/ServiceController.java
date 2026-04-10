package com.servease.controller;

import com.servease.model.Service;
import com.servease.service.ServiceManager;
import com.servease.service.UserService;

import javax.swing.*;
import java.util.List;



public class ServiceController {

    private ServiceManager serviceManager = new ServiceManager();

    public boolean addService(Service service) {


        return serviceManager.addService(service);


    }

    public List<Service> getServicesByProviderId(int provider_id) {
        return serviceManager.getServicesByProviderId(provider_id);
    }

    public boolean deleteService(int service_id) {

        return serviceManager.deleteService(service_id);
    }
    public boolean updateService(Service service){

        return serviceManager.updateService(service);
    }
}
