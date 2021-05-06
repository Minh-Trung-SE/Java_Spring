package com.example.book_web.urlcontroler;

import com.example.book_web.entity.Users;
import com.example.book_web.services.UserServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class UserControlMapping {
    private final UserServices user_services;

    public UserControlMapping(UserServices user_services) {
        this.user_services = user_services;
    }

    @PostMapping(value = "/login")
    public void Login(@RequestBody Users user) throws SQLException {

        user_services.userLogin(user.getUserPhone(), user.getUserPassword());
    }

    @PostMapping(value = "/register")
    public void Register(@RequestBody Users user) throws SQLException {
        if(user_services.isValidRegister(user)){
            user_services.userRegister(user);
        }else {
            System.out.println("Register filed!");
        }
    }
}
