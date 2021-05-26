package com.example.book_web.services;

import com.example.book_web.entity.Users;
import com.example.book_web.urlcontroler.requestModel.ChangeEmailForm;
import com.example.book_web.urlcontroler.responseModel.ResponseUserProfile;
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

    //Method check valid gmail if gmail contain charters accept and true format if wrong return false if wrong return false
    public boolean isValidGmail(String userGmail){
        if(!Pattern.matches("[A-Za-z0-9!#$%&'*+-/=?^_`{|}~]+@gmail.com", userGmail)){
            System.out.println(userGmail + " invalid!");
            return false;
        }
        return true;
    }
    //Method check  check valid number phone if all charters number phone is digit, start with zero and contain enough 10 digit if wrong return false
    public boolean isValidNumberPhone(String userPhone){
        if(!Pattern.matches("[0][0-9]{9}", userPhone)){
            System.out.println("Format number phone must contains 10 digit");
            return false;
        }
        return true;
    }
    //Method check valid password, password must contains lest 8 character if wrong return false
    public boolean isValidPassword(String userPassword){
        if(userPassword.length() < 8){
            System.out.println("Password contains least 8 character");
            return false;
        }
        return true;
    }

    /* Method login will be return notification whether success or failed
    after execute logic compere userPhone and userPassword with data on database */
    public boolean userLogin(String userPhone, String userPassword){
        String query, phone = null, password = null;
        try {
            //Build statement to query password by userPhone
            query = "SELECT user_phone, user_password FROM book.users WHERE user_phone = '" + userPhone + "';";
            //Create statement
            Statement statement = connection.createStatement();
            //Create result set to receive userPhone and userPassword after execute query
            ResultSet resultSet = statement.executeQuery(query);
            /*If resultSet is not null make sure that account has register and next step is
                Check if password provide by user whether correct:
                    -   if password provide by user correct return message login success
                    -   else return message login failed incorrect password
             Else resultSet is null that mean account hasn't already register */
            if(resultSet.next()){
                //Receive userPhone
                phone = resultSet.getString("user_phone");
                //Receive userPassword
                password = resultSet.getString("user_password");
                //Logic check whether password is same with password on database
                if(password.equals(userPassword)){
                    return true;
                }
            }else {
                System.out.println("Account number phone: " + userPhone + " not exist! \nLogin filed!");
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
       return false;
    }

    /*Method register require userPhone and userEmail has already not yet register before
    and it will be return notification detail that whether register success or failed     */
    public String userRegister(Users user){
        String query;
        try {
            //Build query check whether number phone or email has used to register before?
            query = "SELECT user_phone, user_email " +
                    "FROM book.users WHERE user_phone = '" + user.getUserPhone() +
                    "' OR user_email = '"+ user.getUserEmail() + "';";
            //Create Statement
            Statement statement = connection.createStatement();
            //Create result set
            ResultSet resultSet = statement.executeQuery(query);
            /*If resultSet have any result it make sure that email or number phone has register before
              and prevent insert duplicate data on database*/
            if(resultSet.next()){
                //If clause true return notification this number phone has register before
                if(user.getUserPhone().equals(resultSet.getString("user_phone"))){
                    return "Number phone: " + user.getUserPhone() +" has user by another account. Register Filed!";
                }
                //If clause true return notification this email has register before
                if(user.getUserEmail().equals(resultSet.getString("user_email"))){
                    return "Email: " + user.getUserEmail() +" has user by another account. Register Filed!";
                }
            }
            //If after check and make sure that that number phone and email is unique, implement build query to insert data
            query = "INSERT INTO `book`.`users` (`user_phone`, `user_name`, `user_password`, `user_email`) VALUES " +
                    "('" + user.getUserPhone() + "','" +
                    user.getUserName() + "','" +
                    user.getUserPassword() + "','" +
                    user.getUserEmail() + "');";
            //Execute query to insert data
            statement.executeUpdate(query);
        }catch (SQLException exception){
            exception.printStackTrace();
            return "Internal error, Register failed!";
        }
        return "Register success!";
    }

    //Method will return all information of user
    public ResponseUserProfile geProfileUser(String userPhone){
        String query, userName = null, userEmail = null;
        try {
            Statement statement = connection.createStatement();
            query = "SELECT * FROM `book`.`users` WHERE user_phone = '" + userPhone + "';";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
                userEmail = resultSet.getString("user_email");
                userName = resultSet.getString("user_name");
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return new ResponseUserProfile(userPhone, userName, userEmail);
    }

    //Method to change user password
    public String changePassword(String userPhone, String userPassword, String newPassword ){
        String query;
        try {
            Statement statement = connection.createStatement();
            query = "SELECT `user_password` FROM `book`.`users` " +
                    "WHERE `user_phone` =  '" + userPhone +
                    "' AND `user_password` = '" + userPassword + "';";
            System.out.println(query);
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                query = "UPDATE `book`.`users` SET `user_password` = '" + newPassword + "' WHERE (`user_phone` = '" + userPhone + "');";
                statement.executeUpdate(query);
                return "Change password success!";
            }else {
                return "Change password failed!";
            }
        }catch (SQLException exception){
            exception.printStackTrace();
            return "Internal error, Change password failed!";
        }
    }

    /*Method change user email after check and make sure that userPhone, userNewEmail true format
        Step 1: Check user phone and password if both true then allow user change email
                    if wrong exit method return notification failed!
        Step 2: Must past step 1
                -   Check whether new email same old email if same exit method this mean also change email failed
                -   Continue to check whether new email provide by user already user by another account
                        if not exit move step 3
                        else exit method and notification that change email failed
       Step 3: Execute query update data
                        if success return notification success!
                        else return notification failed!
*/
    public String changeGmail(ChangeEmailForm emailForm){
        String query;
        try {
            //Build query to check password and number phone
            query = "SELECT user_email FROM `book`.`users` " +
                    "WHERE `user_phone` = '" + emailForm.getUserPhone() + "' AND `user_password` = '" + emailForm.getUserPassword() + "';";
            //Create statement
            Statement statement = connection.createStatement();
            //Create resultSet to check result if resultSet not null this mean allow move to step 2
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){

                //Check whether new email it already same old email if same exit
                if(resultSet.getString("user_email").equals(emailForm.getUserNewGmail())){
                    return "Change gmail failed new gmail same old gmail";
                }
                //Check whether email user by anther account
                query = "SELECT user_email FROM `book`.`users` WHERE  `user_email`= '" + emailForm.getUserNewGmail() + "';";
                resultSet = statement.executeQuery(query);
                if(resultSet.next()){
                    return "Gmail has been use by another account!";
                }
                //Build query update data
                query = "UPDATE `book`.`users` SET `user_email` = '"+ emailForm.getUserNewGmail() +"' " +
                        "WHERE (`user_phone` = '" + emailForm.getUserPhone() + "');";
                statement.executeUpdate(query);
                return "Change gmail success!";
            }
        }catch (SQLException exception){
            exception.printStackTrace();
        }
        return "Change gmail failed!";
    }
}