package com.bookweb.com;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Response;
import java.sql.SQLException;

@RestController
public class HomeControl {

    @PostMapping("/register")
    public void userRegister(@RequestParam (value =  "user_phone") String user_phone, @RequestParam (value =  "user_name") String user_name,
                      @RequestParam (value =  "user_password") String user_password, @RequestParam (value =  "user_email") String user_email) throws SQLException {
        users user = new users();
        if(user.isValidInput(user_phone, user_password, user_email)){
            System.out.println(user.toString());
            user.registerUser(SpringWebApplication.connection, user_phone, user_name, user_password, user_email);
        }else {
            System.out.println("Register account failed");
        }
    }

    @PostMapping("/login")
    public void userLogin(@RequestParam (value = "user_phone") String user_phone, @RequestParam(value = "user_password") String user_password) throws SQLException {
        users user = new users();
        user.login(SpringWebApplication.connection, user_phone, user_password);
    }
//    public static void main(String[] args) throws SQLException {
//        int option;
//        System.out.println("-------- Start Connection ------------");

//
//        System.out.println();
//        users users = new users();
//        assert connection != null;
//        users.changePassword("0984500754", connection);
//        users.login(connection, "0984500754","kienve");
//        users.registerUser(connection);
//        users.changeInfo("0984500754", connection);

//        book book = new book();
//        book.showTitleBookByCategory("0335840115", connection);

//        book_post book_post = new book_post();
//        book_post.postBook("0335840115", connection);
//        book_post.showPostBook("0123454321", connection);

//        book_favourite book_favourite = new book_favourite();
//        book_favourite.showFavouriteBook("0335840115", connection);
//        book_favourite.dislike(////);

}

