package com.example.sswms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sswms.form.LoginForm;

import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/")
public class Login {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @ModelAttribute
    public LoginForm formData() {
        return new LoginForm();
    }
    
    @GetMapping("teacher")
    public String redirectToLogin(HttpSession session, Model model) {
        // セッションにログイン情報があればダッシュボードにリダイレクト
        if( session.getAttribute("email") != null && ! ( (String) session.getAttribute("email") ).isEmpty() ){

            model.addAttribute("email", session.getAttribute("email"));
            model.addAttribute("name", session.getAttribute("name"));
            
            return "teacher-dashboard";
        }
        return "redirect:/teacher-login";
    }
    
    @GetMapping("teacher-login")
    public String login(HttpSession session, Model model){

        // セッションにログイン情報があればダッシュボードにリダイレクト
        if( session.getAttribute("email") != null && ! ( (String) session.getAttribute("email") ).isEmpty() ){

            model.addAttribute("email", session.getAttribute("email"));
            model.addAttribute("name", session.getAttribute("name"));
            return "teacher-dashboard";
        }
        return "teacher-login";
    }

    @PostMapping("teacher-login")
    public String login(@Validated LoginForm form, BindingResult bindingResult, Model model, HttpSession session){

        // 入力チェック
        if( bindingResult.hasErrors() ){
            return "teacher-login";
        }

        // LoginFormからメールアドレスとパスワードを取得
        String email = form.getEmail();
        String password = form.getPassword();

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
            String errMessage = "DBエラー";
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
    public String studentLogin(@Validated LoginForm form, BindingResult bindingResult, HttpSession session, Model model){

        // 入力チェック
        if( bindingResult.hasErrors() ){
            return "student-login";
        }

        // LoginFormからメールアドレスとパスワードを取得
        String email = form.getEmail();
        String password = form.getPassword();

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
            String errMessage = "DBエラー";
            model.addAttribute("err", errMessage);
            return "student-login"; 
        }
    }

    @GetMapping("teacher-logout")
    public String teacherLogout(HttpSession session){
        session.invalidate(); // セッションを破棄
        
        return "redirect:/teacher";
    }

    @GetMapping("student-logout")
    public String studentLogout(HttpSession session){
        session.invalidate(); // セッションを破棄
        
        return "redirect:/student";
    }
}
