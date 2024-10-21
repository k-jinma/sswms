package com.example.sswms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/")
public class Login {

    @GetMapping("login")
    public String login(){

        return "teacher-login";
    }

    @PostMapping("login")
    public String login(@RequestParam String email, @RequestParam String password, Model model){
        // デモ用の固定パスワードを設定
        String demoEmail = "test@test";
        String demoPassword = "test";  

        if( email.equals(demoEmail) && password.equals(demoPassword) ){
            return "teacher-dashboard";

        }else{
            String errMessage = "ユーザー名かパスワードが異なります";
            model.addAttribute("err", errMessage);
            return "teacher-login";
        }

    }    
        

}
