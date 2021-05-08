package com.example.book_web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.*;
@Configuration
public class DBConnector {
    @Bean
    public Connection getConnectionDB(){
        Connection connection = null;
        try {
            String PASSWORD = "Minhtrung1772k1";
            String USER_NAME = "root";
            String DB_URL = "jdbc:mysql://localhost:3306/book";
            connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
            System.out.println("Connection Data Base Success!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
