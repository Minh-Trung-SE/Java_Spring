package com.example.book_web.urlcontroler.responseModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseUserProfile {
    public String userPhone;
    public String userName;
    public String userEmail;

    public ResponseUserProfile(String userPhone, String userName, String userEmail) {
        this.userPhone = userPhone;
        this.userName = userName;
        this.userEmail = userEmail;
    }
}
