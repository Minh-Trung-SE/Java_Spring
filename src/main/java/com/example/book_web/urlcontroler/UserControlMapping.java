package com.example.book_web.urlcontroler;

import com.example.book_web.entity.Users;
import com.example.book_web.model.UserServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class UserControlMapping {

    @PostMapping(value = "/login")
    public void Login(@RequestBody Users user) throws SQLException {
        UserServices user_services = new UserServices();
        user_services.userLogin(user.getUserPhone(), user.getUserPassword());
    }

    @PostMapping(value = "/register")
    public void Register(@RequestBody Users user) throws SQLException {
        UserServices user_services = new UserServices();
        if(user_services.isValidRegister(user)){
            user_services.userRegister(user);
        }else {
            System.out.println("Register filed!");
        }
    }
}
