package com.example.book_web.services;

import com.example.book_web.entity.Users;
import com.example.book_web.repository.UserRepository;
import com.example.book_web.urlcontroler.requestModel.ChangeEmailForm;
import com.example.book_web.urlcontroler.responseModel.ResponseUserProfile;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.Entity;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
@Service
public class UserServices implements UserDetailsService {

    private final Connection connection;
    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserServices(Connection connection, UserRepository userRepository, @Lazy BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.connection = connection;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
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
        Users user = userRepository.getUsersByUserPhoneAndUserPassword(userPhone, userPassword);
        if(user == null){
            return false;
        }
        return user.getUserPhone().equals(userPhone) && user.getUserPassword().equals(userPassword);
    }

    /*Method register require userPhone and userEmail has already not yet register before
    and it will be return notification detail that whether register success or failed     */
    public boolean userRegister(Users user){
        Users existUser = null;
        if(!isValidGmail(user.getUserEmail()) || !isValidPassword(user.getUserPassword()) || !isValidNumberPhone(user.getUserPhone())){
            return false;
        }
        try {
            existUser = userRepository.getUsersByUserPhoneOrUserEmail(user.getUserPhone(), user.getUserEmail());
            if (existUser != null){
                return false;
            }
            user.setUserPassword(bCryptPasswordEncoder.encode(user.getUserPassword()));
            userRepository.save(user);
            return true;
        }catch (Exception exception){
            exception.printStackTrace();
            return false;
        }
    }

    //Method will return all information of user
    public ResponseUserProfile geProfileUser(String userPhone){
        ResponseUserProfile responseUserProfile = null;
        try {
            Users user = userRepository.getUsersByUserPhone(userPhone);
            responseUserProfile = new ResponseUserProfile(user.getUserPhone(), user.getUserEmail(), user.getUserName());
        }catch (Exception exception){
            exception.printStackTrace();
        }
        return responseUserProfile;
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


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println(s);
        Users user = userRepository.findUsersByUserPhone(s);
        if(user == null){
            throw new RuntimeException("User " + s + "not found!");
        }
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority(user.getUser_role());
        grantedAuthorityList.add(authority);

        return new User(user.getUserName(), user.getUserPassword(), grantedAuthorityList);
    }
}