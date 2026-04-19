package com.servease.controller;

import com.servease.dao.UserDAO;
import com.servease.model.User;
import com.servease.service.UserService;
import com.servease.ui.dashboard.AdminDashboard;


import javax.swing.*;

public class UserController {

        private UserService userService = new UserService();

        public boolean registerUser(String name, String email, String password, String phone, String role) {
            User user = new User(name, email, password, phone, role);
            return userService.registerUser(user);
        }

        public User loginUser(String email, String password) {

            return userService.loginUser(email, password);
        }



        private UserDAO dao = new UserDAO();

        public boolean updateUser(User user, String password) {

            // If password empty → don’t change
            if (password == null || password.isEmpty()) {
                return dao.updateUserWithoutPassword(user);
            }

            // Hash password
            String hashed = hashPassword(password);
            return dao.updateUserWithPassword(user, hashed);
        }

        // SIMPLE HASH (production basic)
        private String hashPassword(String password) {
            return Integer.toHexString(password.hashCode());
        }

        public boolean updateProfileImage(int userId,String path){
            return dao.updateProfileImage(userId,path);
        }
    }


