package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeControl {
    @GetMapping("/home")
    public void home (){
        System.out.println("Ok");
    }


}
