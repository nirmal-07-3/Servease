package com.servease.dao;

import com.servease.config.DBConnection;
import com.servease.model.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class ServiceDAO {


    public boolean addService(Service service){
        boolean isAdded=false;

        try {
            Connection conn= DBConnection.getConnection();

            String query="INSERT INTO services (provider_id,name,description,price)VALUES(?,?,?,?)";
            PreparedStatement ps=conn.prepareStatement(query);

            ps.setInt(1,service.getProvider_id());
            ps.setString(2,service.getName());
            ps.setString(3,service.getDescription());
            ps.setDouble(4,service.getPrice());

            int row= ps.executeUpdate();

            if(row>0){
                isAdded=true;
            }


        } catch (Exception e) {
           e.printStackTrace();
        }
        return isAdded;
    }

    public List<Service> getAllServices(){

        List<Service> services =new ArrayList<>();

        try{
            Connection conn=DBConnection.getConnection();
            String query="SELECT * FROM services";
            Statement statement=conn.createStatement();
            ResultSet rs=statement.executeQuery(query);

            while (rs.next()){
                Service service=new Service();

                service.setId(rs.getInt("id"));
                service.setProvider_id(rs.getInt("provider_id"));
                service.setName(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                service.setPrice(rs.getDouble("price"));

                services.add(service);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    public List<Service> getServicesByProviderId(int provider_id){

        List<Service> services =new ArrayList<>();

        try{
            Connection conn=DBConnection.getConnection();
            String query="SELECT * FROM services WHERE provider_id=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1,provider_id);

            ResultSet rs= ps.executeQuery();

            while (rs.next()){
                Service service=new Service();

                service.setId(rs.getInt("id"));
                service.setProvider_id(rs.getInt("provider_id"));
                service.setName(rs.getString("name"));
                service.setDescription(rs.getString("description"));
                service.setPrice(rs.getDouble("price"));

                services.add(service);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    public Service getServiceById(int id) {

        try {
            Connection conn = DBConnection.getConnection();

            String sql = "SELECT * FROM services WHERE id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Service(
                        rs.getInt("id"),
                        rs.getInt("provider_id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price")
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public boolean deleteService(int service_id){

        boolean isDeleted=false;

        try{
            Connection conn=DBConnection.getConnection();
            String query="DELETE FROM services WHERE id=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setInt(1,service_id);

            int row=ps.executeUpdate();

            if(row>0){
                isDeleted=true;
            }


        }
        catch (Exception e){
            e.printStackTrace();
        }
        return isDeleted;
    }


    public boolean updateService(Service service){

        boolean isUpdated=false;
        try{
            Connection conn=DBConnection.getConnection();
            String query="UPDATE services SET name=?,description=?,price=?,imagePath=?  WHERE id=?";
            PreparedStatement ps=conn.prepareStatement(query);
            ps.setString(1, service.getName());
            ps.setString(2, service.getDescription());
            ps.setDouble(3, service.getPrice());
            ps.setString(4,service.getImagePath());
            ps.setInt(5,service.getId());

            int row=ps.executeUpdate();

            if(row>0){
                isUpdated=true;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return isUpdated;
    }
    


}
