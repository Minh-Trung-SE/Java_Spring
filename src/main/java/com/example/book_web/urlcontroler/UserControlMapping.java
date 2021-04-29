package com.example.book_web.urlcontroler;

import com.example.book_web.entity.Users;
import com.example.book_web.model.User_Services;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class UserControlMapping {

    @PostMapping(value = "/login")
    public void Login(@RequestBody Users user) throws SQLException {
        User_Services user_services = new User_Services();
        user_services.userLogin(user.getUser_phone(), user.getUser_password());
    }

    @PostMapping(value = "/register")
    public void Register(@RequestBody Users user) throws SQLException {
        User_Services user_services = new User_Services();
        if(user_services.isValidRegister(user)){
            user_services.userRegister(user);
        }else {
            System.out.println("Register filed!");
        }
    }
}
