package com.example.sswms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/")
public class Register {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("register")
    public String getMethodName() {
        return "teacher-register";
    }

    @PostMapping("create-teacher")
    public String postMethodName(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        
        System.out.println( name );
        System.out.println( email );
        System.out.println( password );

        try {

            String sql = "INSERT INTO teacher values( ?, ? ,? )";
            jdbcTemplate.update( sql , email, name, password );
            
            return "teacher-login";
            
        } catch (Exception e) {
            
            String errMessage = "すでに登録されているメールアドレスです。";
            model.addAttribute("message", errMessage);
            
            return "teacher-register";
        }
    }
        
    
}
