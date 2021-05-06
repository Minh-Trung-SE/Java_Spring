package com.example.book_web.entity.changeForm;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordForm {
    public String userPhone;
    public String userPassword;
    public String userNewPassword;
}
