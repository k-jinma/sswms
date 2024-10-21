package com.example.sswms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/")
public class Login {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("")
    public String redirectToLogin() {
        return "redirect:/login";
    }
    

    @GetMapping("login")
    public String login(){

        return "teacher-login";
    }

    @PostMapping("login")
    public String login(@RequestParam String email, @RequestParam String password, Model model){
        // デモ用の固定パスワードを設定
        String demoEmail = "test@test";
        String demoPassword = "test";  

        String sql = "SELECT name FROM demo_user WHERE mail = ? AND password = ?";
        try{
            String result = jdbcTemplate.queryForObject(sql, String.class, email, password);

        }catch(EmptyResultDataAccessException e){
            String errMessage = "ユーザー名かパスワードが異なります";
            model.addAttribute("err", errMessage);
            return "teacher-login";
        }
        return "teacher-dashboard";



        // if( email.equals(demoEmail) && password.equals(demoPassword) ){
        //     return "teacher-dashboard";

        // }else{
        //     String errMessage = "ユーザー名かパスワードが異なります";
        //     model.addAttribute("err", errMessage);
        //     return "teacher-login";
        // }

    }    
        

}
