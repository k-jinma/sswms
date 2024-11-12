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
    
    @GetMapping("teacher")
    public String redirectToLogin() {
        return "redirect:/teacher-login";
    }
    
    @GetMapping("teacher-login")
    public String login(@RequestParam( value = "email", defaultValue = "") String email){

        //TODO: あとでセッション管理などに変更
        if( !email.isEmpty() ){
            return "teacher-dashboard";
        }
        return "teacher-login";
    }

    @PostMapping("teacher-login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model){

        String sql = "SELECT name FROM teacher WHERE mail = ? AND password = ?";

        // teacher1@example.com       password123
        try {
            // データがなければEmptyResultDataAccessExceptionが発生する
            String result = jdbcTemplate.queryForObject(sql, String.class, email, password);
            model.addAttribute("email", email);
            return "teacher-dashboard";
            
        } catch (EmptyResultDataAccessException e) {
            String errMessage = "ユーザー名かパスワードが異なります";
            model.addAttribute("err", errMessage);
            return "teacher-login"; 
        }
    }

    @GetMapping("")
    public String student(){
        return "redirect:/student";
    }

    @GetMapping("student")
    public String studentLogin(){

        return "student-login";

    }
    
    @PostMapping("student-login")
    public String studentLogin(@RequestParam("email") String email, @RequestParam("password") String password, Model model){

        String sql = "SELECT name FROM student WHERE mail = ? AND password = ?";

        try {
            // データがなければEmptyResultDataAccessExceptionが発生する
            String result = jdbcTemplate.queryForObject(sql, String.class, email, password);
            model.addAttribute("email", email);
            return "student-dashboard";
            
        } catch (EmptyResultDataAccessException e) {
            String errMessage = "ユーザー名かパスワードが異なります";
            model.addAttribute("err", errMessage);
            return "student-login"; 
        }
    }
        

}
