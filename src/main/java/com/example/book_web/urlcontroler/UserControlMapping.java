package com.example.book_web.urlcontroler;

import com.example.book_web.entityForm.ChangeEmailForm;
import com.example.book_web.entityForm.ChangePasswordForm;
import com.example.book_web.entity.Users;
import com.example.book_web.services.UserServices;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
public class UserControlMapping {
    private final UserServices userServices;

    public UserControlMapping(UserServices userServices) {
        this.userServices = userServices;
    }

    @PostMapping(value = "/user/login")
    public String Login(@RequestBody Users user) {
        return userServices.userLogin(user.getUserPhone(), user.getUserPassword());
    }

    @PostMapping(value = "/user/register")
    public String Register(@RequestBody Users user) throws SQLException {
        if(!userServices.isValidNumberPhone(user.getUserPhone())){
            return "Number phone: " + user.getUserPhone() + " wrong format. Register failed!";
        }
        if (!userServices.isValidGmail(user.getUserEmail())){
            return "Email: " + user.getUserEmail() + " wrong format. Register failed!";
        }
        if (!userServices.isValidPassword(user.getUserPassword())){
            return "Password: " + user.getUserPassword() + " wrong format. Register failed!";
        }
        return userServices.userRegister(user);
    }

    @GetMapping(value = "/user/profile/{userPhone}")
    public Users showProfile(@PathVariable(value = "userPhone") String userPhone){
        System.out.println(userPhone);
        if(userServices.isValidNumberPhone(userPhone)){
           return userServices.getInformationUser(userPhone);
        }else {
            return null;
        }
    }

    @PostMapping (value = "/user/profile/change-password")
    public String changeUserPassword(@RequestBody ChangePasswordForm passwordForm){
        if(userServices.isValidNumberPhone(passwordForm.getUserPhone())){
            if (userServices.isValidPassword(passwordForm.getUserPassword()) && userServices.isValidPassword(passwordForm.getUserNewPassword())){
                return userServices.changePassword(passwordForm.getUserPhone(), passwordForm.getUserPassword(), passwordForm.getUserNewPassword());
            }
        }else {
            return "Number phone: " + passwordForm.getUserPhone() + " invalid. Change password failed!";
        }
        return "Change password failed!";
    }
    @PostMapping (value = "/user/profile/change-gmail")
    public String changeUserEmail(@RequestBody ChangeEmailForm gmailForm){
        if(userServices.isValidNumberPhone(gmailForm.getUserPhone()) && userServices.isValidGmail(gmailForm.getUserNewGmail())){
            return userServices.changeGmail(gmailForm);
        }else {
            return "Change gmail failed!";
        }

    }
}
