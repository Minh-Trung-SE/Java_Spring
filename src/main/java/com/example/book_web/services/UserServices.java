package com.example.book_web.services;

import com.example.book_web.dbconnection.DBConnector;
import com.example.book_web.entity.Users;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;
@Service
public class UserServices {

    private final Connection connection;
    public UserServices(Connection connection) {
        this.connection = connection;
    }

    //Method to check valid form register
    public boolean isValidRegister(Users user){

        if(Pattern.matches("[A-Za-z0-9!#$%&'*+-/=?^_`{|}~]+@gmail.com", user.getUserEmail())){
            System.out.println(user.getUserEmail() + " invalid!");
            return false;
        }
        if(Pattern.matches("[0][0-9]{9}", user.getUserPhone())){
            System.out.println("Format number phone must contains 10 digit");
            return false;
        }
        if(user.userPassword.length() < 8){
            System.out.println("Password contains least 8 character");
            return false;
        }
        return true;
    }

    //Method to login
    public void userLogin(String userPhone, String userPassword) throws SQLException {
        Statement statement = connection.createStatement();
        String query, phone = null, password = null;
        query = "SELECT user_phone, user_password FROM book.users WHERE user_phone = '" + userPhone + "';";
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()){
            phone = resultSet.getString("userPhone");
            password = resultSet.getString("userPassword");
            if(!password.equals(userPassword)){
                System.out.println("Incorrect password");
                return;
            }
        }else {
            System.out.println("Account number phone: " + userPhone + " not exist! \nLogin filed!");
            return;
        }
        System.out.println("Login Success!");
    }

    //Method register user
    public void userRegister(Users user) throws SQLException {
        String query;
        Statement statement = connection.createStatement();

        query = "SELECT user_phone FROM book.users WHERE user_phone = '" + user.getUserPhone() +"';";
        ResultSet resultSet = statement.executeQuery(query);
        if(resultSet.next()){
            System.out.println("Number phone has use by another account!");
        }else {
            query = "INSERT INTO `book`.`users` (`user_phone`, `user_name`, `user_password`, `user_email`) VALUES " +
                    "('" + user.getUserPhone() + "','" +
                    user.getUserName() + "','" +
                    user.getUserPassword() + "','" +
                    user.getUserEmail() + "');";
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

    //Method will return all information of user
    public Users getInformationUser(String userPhone){
        String query;
        Users user = new Users();
        try {
            Statement statement = connection.createStatement();
            query = "SELECT * FROM `book`.`users` WHERE user_phone = '" + userPhone + "';";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                user.setUserPhone(resultSet.getString("user_phone"));
                user.setUserEmail(resultSet.getString("user_email"));
                user.setUserName(resultSet.getString("user_name"));
                user.setUserPassword(resultSet.getString("user_password"));
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return user;
    }

    //Method to change user password
    public void changePassword(String userPhone, String userPassword, String newPassword ){
        String oldPassword = null, query;
        try {
            Statement statement = connection.createStatement();
            query = "SELECT `user_password` FROM `book`.`users` WHERE `user_phone` =  `" + userPhone + "';";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                oldPassword = resultSet.getString("user_password");
            }
            if(userPassword.equals(oldPassword)){
                query = "UPDATE `book`.`users` SET `user_password` = '" + newPassword + "' WHERE (`user_phone` = '" +userPhone+ "');";
                statement.executeUpdate(query);
                System.out.println("Change password success!");
            }else {
                System.out.println("Change password failed!");
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
    }

}