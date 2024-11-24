package com.example.sswms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/")
public class Login {

    @Autowired
    JdbcTemplate jdbcTemplate;
    
    @GetMapping("teacher")
    public String redirectToLogin(HttpSession session) {
        if( session.getAttribute("email") != null ){
            return "teacher-dashboard";
        }
        return "redirect:/teacher-login";
    }
    
    @GetMapping("teacher-login")
    public String login(HttpSession session){

        // セッションにログイン情報があればダッシュボードにリダイレクト
        if( session.getAttribute("email") != null && ! ( (String) session.getAttribute("email") ).isEmpty() ){
            return "teacher-dashboard";
        }
        return "teacher-login";
    }

    @PostMapping("teacher-login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session){

        String sql = "SELECT name FROM teacher WHERE mail = ? AND password = ?";
        // teacher1@example.com       password123
        try {
            // データがなければEmptyResultDataAccessExceptionが発生する
            String result = jdbcTemplate.queryForObject(sql, String.class, email, password);

            // セッションにemailとnameをセット
            session.setAttribute("email", email);
            session.setAttribute("name", result);
            
            model.addAttribute("email", email);
            model.addAttribute("name", result);
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
    public String studentLogin(HttpSession session, Model model){

        // セッションにログイン情報があればダッシュボードにリダイレクト
        if( session.getAttribute("email") != null && ! ( (String) session.getAttribute("email") ).isEmpty() ){

            model.addAttribute("email", session.getAttribute("email"));
            model.addAttribute("name", session.getAttribute("name"));
            
            return "student-dashboard";
        }
        return "student-login";

    }
    
    @PostMapping("student-login")
    public String studentLogin(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession session, Model model){

        String sql = "SELECT name FROM student WHERE mail = ? AND password = ?";

        try {
            // データがなければEmptyResultDataAccessExceptionが発生する
            String result = jdbcTemplate.queryForObject(sql, String.class, email, password);

            session.setAttribute("email", email);
            session.setAttribute("name", result);

            model.addAttribute("email", email);
            model.addAttribute("name", result);

            return "student-dashboard";
            
        } catch (EmptyResultDataAccessException e) {
            String errMessage = "ユーザー名かパスワードが異なります";
            model.addAttribute("err", errMessage);
            return "student-login"; 
        }
    }

    @GetMapping("teacher-logout")
    public String teacherLogout(HttpSession session){
        session.invalidate();
        
        return "redirect:/teacher";
    }

    @GetMapping("student-logout")
    public String studentLogout(HttpSession session){
        session.invalidate();
        
        return "redirect:/student";
    }
}
