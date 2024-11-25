package com.example.sswms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class CreateTest {

    @GetMapping("create")      
    public String showCreate(HttpSession session){

        if( session.getAttribute("email") == null ){
            return "redirect:/teacher";
        }

        return "teacher-create-test";
    }
    
}
