package com.example.sswms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class Register {

    @GetMapping("register")
    public String getMethodName() {
        return "teacher-register";
    }
}
