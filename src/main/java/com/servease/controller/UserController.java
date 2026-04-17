package com.servease.controller;

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
    }


