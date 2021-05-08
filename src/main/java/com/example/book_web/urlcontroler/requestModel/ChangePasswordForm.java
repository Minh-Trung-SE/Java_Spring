package com.example.book_web.urlcontroler.requestModel;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordForm {
    public String userPhone;
    public String userPassword;
    public String userNewPassword;
}
