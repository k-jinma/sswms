package com.example.sswms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class Register {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("register")
    public String teacherRegister() {
        return "teacher-register";
    }

    @PostMapping("create-teacher")
    public String teacherRegister(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        
        System.out.println( name );
        System.out.println( email );
        System.out.println( password );

        try {


            if(name.length() > 60 || email.length() > 64 || password.length() > 32 ){
                String errMessage = "Characters are too long";
                model.addAttribute("errMessage",errMessage);
                return "teacher-register";
            }
            
            
            String sql = "INSERT INTO teacher values( ?, ? ,? )";
            jdbcTemplate.update( sql , email, name, password );
            
            return "teacher-login";
            
        } catch (Exception e) {

            String errMessage = "Email already exists";
            model.addAttribute("errMessage", errMessage);
            
            return "teacher-register";
        }
    }

    @GetMapping("student-register")
    public String studentRegister() {
        return "student-register";
    }

    @PostMapping("create-student")
    public String studentRegister(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, Model model) {
        
        System.out.println( name );
        System.out.println( email );
        System.out.println( password );

        try {


            if(name.length() > 60 || email.length() > 64 || password.length() > 32 ){
                String errMessage = "Characters are too long";
                model.addAttribute("errMessage",errMessage);
                return "student-register";
            }
            
            
            String sql = "INSERT INTO student values( ?, ? ,? )";
            jdbcTemplate.update( sql , email, name, password );
            
            return "student-login";
            
        } catch (Exception e) {

            String errMessage = "Email already exists";
            model.addAttribute("errMessage", errMessage);
            
            return "student-register";
        }
    }
    
        
}
