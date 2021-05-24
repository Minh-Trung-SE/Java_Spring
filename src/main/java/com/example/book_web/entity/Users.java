package com.example.book_web.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
@Getter @Setter
public class Users {
    @Id
    @Column(name = "user_phone")
    public String userPhone;
    @Column(name = "user_password")
    public String userPassword;
    @Column(name =  "user_name")
    public String userName;
    @Column(name = "user_email")
    public String userEmail;
}
