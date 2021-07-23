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
    private String userPhone;
    @Column(name = "user_password")
    private String userPassword;
    @Column(name =  "user_name")
    private String userName;
    @Column(name = "user_email")
    private String userEmail;
    @Column (name = "role")
    private String user_role;
    @Column (name = "token")
    private String user_token;
    @Column (name = "expired_time")
    private String expired_time;
}
