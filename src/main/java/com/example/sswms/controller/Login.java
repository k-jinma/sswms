package com.example.sswms.controller;

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
    public String login(@RequestParam(value = "mail", defaultValue = "") String mail){  //デフォルト値が空文字になるので、空文字の場合はログイン画面を表示

        //TODO: あとでセッション管理などに変更
        if( !mail.isEmpty() ){
            return "teacher-dashboard";
        }

        return "teacher-login";
    }

    @PostMapping("login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model ) {
        
        String sql = "SELECT mail FROM teacher WHERE mail = ? AND password = ?";

        try{
            String result = jdbcTemplate.queryForObject(sql, String.class, email, password);
            model.addAttribute("mail", result);
            
        }catch(EmptyResultDataAccessException e){
            String errMessage = "ユーザー名かパスワードが異なります";
            model.addAttribute("err", errMessage);
            return "teacher-login";
        }
        return "teacher-dashboard";

    }


        

}
