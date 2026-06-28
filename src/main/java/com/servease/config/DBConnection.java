package com.servease.config;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    public static Connection getConnection() {

        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            Properties props = new Properties();

            InputStream input = DBConnection.class
                    .getClassLoader()
                    .getResourceAsStream("config.properties");



            props.load(input);

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");



            conn = DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println("❌ Connection Failed");
            e.printStackTrace();
        }

        return conn;
    }
}