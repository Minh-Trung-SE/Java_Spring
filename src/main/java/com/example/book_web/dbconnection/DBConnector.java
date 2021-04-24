package com.example.book_web.dbconnection;

import java.sql.*;

public class DBConnector {
    public static Connection connection;
    public static void getConnectionDB(){
        if(connection == null){
            try {
                String PASSWORD = "Minhtrung1772k1";
                String USER_NAME = "root";
                String DB_URL = "jdbc:mysql://localhost:3306/book";
                connection = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
                System.out.println("Connection Data Base Success!");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
