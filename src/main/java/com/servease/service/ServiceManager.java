package com.servease.service;

import com.servease.dao.ServiceDAO;
import com.servease.model.Service;

import java.util.List;

public class ServiceManager {

    private ServiceDAO serviceDAO = new ServiceDAO();

    //Validation

    public boolean addService(Service service) {


        if (service.getName() == null || service.getName().isEmpty() || service.getDescription() == null || service.getDescription().isEmpty() || service.getPrice() < 0
                || service.getProvider_id() <= 0) {
            System.out.println("Details are Not Valid");
            return false;
        }
        List<Service> existingServices =
                serviceDAO.getServicesByProviderId(service.getProvider_id());

        for (Service s : existingServices) {
            if (s.getName().equalsIgnoreCase(service.getName())) {
                System.out.println("Service already exists!");
                return false;
            }
        }


        return serviceDAO.addService(service);
    }

    public List<Service> getAllServices() {
        ServiceDAO serviceDAO=new ServiceDAO();
        return serviceDAO.getAllServices();
    }



   public List<Service> getServicesByProviderId(int provider_id){
        ServiceDAO servicedao=new ServiceDAO();
        if (provider_id<=0){
            return null;
        }
       return servicedao.getServicesByProviderId(provider_id);
   }

    public boolean deleteService(int service_id){

        if(service_id<=0){
            System.out.println("Invalid Service Id");
            return false;
        }
        return serviceDAO.deleteService(service_id);
    }

    public boolean updateService(Service service){
        if(service.getName().isEmpty()||service.getPrice()<=0){
            System.out.println("Invalid data!");
            return false;
        }
        return serviceDAO.updateService(service);
    }

}





