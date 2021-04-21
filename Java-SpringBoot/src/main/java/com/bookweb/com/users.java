package com.bookweb.com;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

@Component
public class users {
    String user_phone;
    String user_name;
    String user_password;
    String user_email;


    public boolean login(Connection connection, String user_phone, String user_password) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String phone, password;
        String query = "SELECT user_phone, user_password FROM `book`.`users` WHERE `user_phone` = " + user_phone + ";";
        Statement statement = connection.createStatement();
        ResultSet result = statement.executeQuery(query);
        if(result.next()){
            phone = result.getString("user_phone");
            password = result.getString("user_password");
        }else {
            System.out.println("Number phone: " + user_phone +" not exist!");
            return false;
        }

        if(user_phone.equals(phone)){
            if(user_password.equals(password)){
                System.out.println("Login success!");
                return true;
            }else {
                System.out.println("Wrong password!");
                System.out.println("Password DB: " + password);
                System.out.println("Password Input: " + user_password);
                return false;
            }
        }
        return false;
    }
    public users getInformationUsers(Connection connection){
        String query;
        users user = new users();
        query = "SELECT * FROM book.users";
        return user;
    }

    public void registerUser(Connection connection, String user_phone, String user_name, String user_password, String user_email ) throws SQLException {
        String exist_phone, query;
        System.out.println("\n====Register account====");
        exist_phone = user_phone;

        Statement statement = connection.createStatement();
        query = "SELECT `user_phone` FROM book.users where user_phone = '"+user_phone+"';";
        ResultSet result = statement.executeQuery(query);

        if(result.next()){
            exist_phone = result.getString("user_phone");
        }else{
            exist_phone = null;
        }

        if(!(user_phone.equals(exist_phone))){
            query = "INSERT INTO `book`.`users` (`user_phone`, `user_name`, `user_password`, `user_email`) VALUES ('"+user_phone+"', '"+user_name+"', '"+user_password+"', '"+user_email+"');";
            statement.executeUpdate(query);
            System.out.println("Register success!");

        }else{
            System.out.println("Failed "+user_phone+" has used by another account!");
        }
    }

    public void changeInfo(String user_phone, Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String user_name, user_password, user_email, query;
        System.out.println("Enter new username: ");
        user_name = scanner.nextLine();
        System.out.print("Enter new password: ");
        user_password = scanner.nextLine();
        System.out.print("Enter new email: ");
        user_email = scanner.nextLine();
        Statement statement = connection.createStatement();
        query = "UPDATE `book`.`users` SET `user_name` = '"+ user_name +"', `user_password` = '" + user_password + "', `user_email` = '" + user_email + "' WHERE (`user_phone` = '" + user_phone + "');";
        statement.executeUpdate(query);
        System.out.println("Change info success!");
    }

    public void changePassword(String user_phone,Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        String ser_password, query;
        System.out.print("Enter new password: ");
        user_password = scanner.nextLine();
        Statement statement = connection.createStatement();
        query = "UPDATE `book`.`users` SET `user_password` = '"+user_password+"' WHERE (`user_phone` = '"+user_phone+"')";
        statement.executeUpdate(query);
        System.out.println("Update password success!");
    }

    public boolean isValidInput(String user_phone, String user_password, String user_email){
        for(int i = 0; i < user_phone.length(); i++){
            if(!Character.isDigit(user_phone.charAt(i))){
                System.out.println("Exist character in this phone: " + user_phone);
                return false;
            }
        }
        if(user_password.length() < 8){
            System.out.println("Password must contain lest 8 character!");
            return false;
        }

        if((!user_email.contains("@gmail.com"))){
            System.out.println(user_email + " format email invalid!");
            return false;
        }
        return true;
    }
    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    @Override
    public String toString() {
        return "users{" +
                "user_phone='" + user_phone + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_email='" + user_email + '\'' +
                '}';
    }
}
