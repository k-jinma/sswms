package com.example.sswms.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sswms.form.LoginForm;


@RestController
@RequestMapping("/")
public class RestRegister {
       

    @PostMapping("register")
    public String postMethodName(@RequestBody LoginForm formData) {
        
        System.out.println( formData.name );
        System.out.println( formData.email );
        System.out.println( formData.password );

        return null;
    }
}
