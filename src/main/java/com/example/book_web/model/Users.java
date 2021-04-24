package com.example.book_web.model;

import com.example.book_web.dbconnection.DBConnector;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Getter @Setter
public class Users {
    public String user_phone;
    public String user_password;
    public String user_name;
    public String user_email;

    public void userLogin(String user_phone, String user_password) throws SQLException {
        String query, phone = null, password = null;
        DBConnector dbConnector = new DBConnector();
        DBConnector.connection = dbConnector.getConnectionDB();
        Statement statement = DBConnector.connection.createStatement();
        query = "SELECT user_phone, user_password FROM book.users WHERE user_phone = '" + user_phone + "';";
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()){
            phone = resultSet.getString("user_phone");
            password = resultSet.getString("user_password");
            if(!password.equals(user_password)){
                System.out.println("Incorrect password");
                return;
            }
        }else {
            System.out.println("Account number phone: " + user_phone + " not exist! \nLogin filed!");
            return;
        }
        System.out.println("Login Success!");
    }

    public boolean isValidRegister(Users user){
        if(!user.getUser_email().contains("@gmail.com")){
            System.out.println(user.getUser_email() + " invalid!");
            return false;
        }

        if(user.getUser_phone().length() != 10){
            System.out.println("Format number phone must contains 10 digit");
            return false;
        }else {
            for(int i = 0; i < user.getUser_phone().length(); i++){
                if(!Character.isDigit(user.getUser_phone().charAt(i))){
                    System.out.println("Exist charter '" + user.getUser_phone().charAt(i) + "' in number phone!");
                    return false;
                }
            }
        }

        if(user.user_password.length() < 8){
            System.out.println("Password contains least 8 character");
            return false;
        }
        return true;
    }

    public void userRegister(Users user) throws SQLException {
        String query;
        DBConnector dbConnector = new DBConnector();
        DBConnector.connection = dbConnector.getConnectionDB();
        Statement statement = DBConnector.connection.createStatement();
        query = "SELECT user_phone FROM book.users WHERE user_phone = '" + user.getUser_phone() +"';";
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()){
            System.out.println("Number phone has use by another account!");
        }else {
            query = "INSERT INTO `book`.`users` (`user_phone`, `user_name`, `user_password`, `user_email`) VALUES " +
                    "('" + user.getUser_phone() + "','" +
                    user.getUser_name() + "','" +
                    user.getUser_password() + "','" +
                    user.getUser_email() + "');";
            try {
                statement.executeUpdate(query);
                System.out.println("Register success!");
            }catch (SQLException e){
                System.out.println("Gmail has use by another account!");
                System.out.println(e.toString());
                System.out.println("Register failed!");
            }
        }
    }
}
