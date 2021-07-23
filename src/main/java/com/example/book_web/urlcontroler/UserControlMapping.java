package com.example.book_web.urlcontroler;

import com.example.book_web.urlcontroler.requestModel.ChangeEmailForm;
import com.example.book_web.urlcontroler.requestModel.ChangePasswordForm;
import com.example.book_web.entity.Users;
import com.example.book_web.services.UserServices;
import com.example.book_web.urlcontroler.responseModel.ResponseUserProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@Controller
public class UserControlMapping {
    private final UserServices userServices;

    public UserControlMapping(UserServices userServices) {
        this.userServices = userServices;
    }

    @GetMapping(value = {"/home", "/"})
    public String index(Model model){
        Users user = new Users();
        model.addAttribute("user", user);
        return "index";
    }
    @GetMapping(value = "/loginSuccess")
    public String loginSuccess(){
        return "home";
    }

    @ResponseBody
    @PostMapping(value = "/user/register")
    public String Register(Model model, @ModelAttribute("user") Users user){
        if(userServices.userRegister(user)){
            return "Register Success!";
        }
        return "Register failed!";
    }

    @ResponseBody
    @GetMapping(value = "/user/profile")
    public ResponseUserProfile showProfile(@RequestParam(value = "userPhone") String userPhone){
        return userServices.geProfileUser(userPhone);
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
