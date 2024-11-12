package com.example.sswms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class CreateTest {

    @GetMapping("create")      
    public String showCreate(){

        return "teacher-create-test";
    }
    
}
