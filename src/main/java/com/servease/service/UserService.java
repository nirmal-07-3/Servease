package com.servease.service;

import com.servease.dao.UserDAO;
import com.servease.model.User;

public class UserService {

    private UserDAO userDAO=new UserDAO();

    public boolean registerUser(User user){
       if(user.getName()==null||user.getName().isEmpty()||user.getEmail()==null||user.getEmail().isEmpty()|| user.getPassword()==null||user.getPassword().isEmpty()){
           System.out.println("Fill All Details");
           return false;
       }
       if(!user.getEmail().contains("@")){
           System.out.println("Email Address Is Not Valid");
           return false;
       }
       if (user.getPassword().length()<4){
           System.out.println("Password must be at least 4 character");
           return false;
       }
       return userDAO.registerUser(user);
    }

    public User loginUser(String email,String password){

        if(email==null||email.isEmpty()||password==null||password.isEmpty()){
            System.out.println("Fill All Details");
            return null;
        }
        return userDAO.loginUser(email,password);
    }
}
