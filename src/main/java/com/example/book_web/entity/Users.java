package com.example.book_web.entity;

import com.example.book_web.dbconnection.DBConnector;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Getter @Setter
public class Users {
    public String userPhone;
    public String userPassword;
    public String userName;
    public String userEmail;
}
