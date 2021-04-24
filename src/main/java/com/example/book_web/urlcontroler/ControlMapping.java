package com.example.book_web.urlcontroler;

import com.example.book_web.model.Users;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class ControlMapping {

    @PostMapping(value = "/login")
    public void Login(@RequestBody Users user) throws SQLException {
        user.userLogin(user.getUser_phone(), user.getUser_password());
    }

    @PostMapping(value = "/register")
    public void Register(@RequestBody Users user) throws SQLException {
        if(user.isValidRegister(user)){
            user.userRegister(user);
        }else {
            System.out.println("Register invalid!");
        }
    }
}
