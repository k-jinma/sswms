package com.example.sswms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.sswms.model.StudentGrade;
import com.example.sswms.model.TeacherGrade;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequestMapping("/")
public class Grade {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("view-teacher-grades")
    public String showTeacherGrade(HttpSession session, Model model){

        if( session.getAttribute("email") == null ){
            return "redirect:/teacher";
        }

        String sql = """
                        SELECT 
                            t.test_id,
                            t.mail AS creator_mail,
                            a.mail AS respondent_mail,
                            ROUND(
                                100.0 * SUM(
                                    CASE 
                                        WHEN a.a_no = 1 AND c.ans1 = 'Y' THEN 1
                                        WHEN a.a_no = 2 AND c.ans2 = 'Y' THEN 1
                                        WHEN a.a_no = 3 AND c.ans3 = 'Y' THEN 1
                                        WHEN a.a_no = 4 AND c.ans4 = 'Y' THEN 1
                                        ELSE 0
                                    END
                                ) / COUNT(*), 
                                2
                            ) AS correct_rate
                        FROM 
                            answer a
                        JOIN 
                            contents c ON a.test_id = c.test_id AND a.q_no = c.q_no
                        JOIN 
                            test t ON a.test_id = t.test_id
                        WHERE 
                            t.mail = ?
                        GROUP BY 
                            t.test_id, t.mail, a.mail; 
                    """;

        try {
            RowMapper<TeacherGrade> rowMapper = (rs, rowNum) -> {
                TeacherGrade grade = new TeacherGrade();
                grade.setTestId(rs.getInt("test_id"));
                grade.setTeacherEmail(rs.getString("creator_mail"));
                grade.setStudentEmail(rs.getString("respondent_mail"));
                grade.setCorrectRatePercentage(rs.getDouble("correct_rate"));
                return grade;
            };

            List<TeacherGrade> result = jdbcTemplate.query(sql, rowMapper, session.getAttribute("email")); // sql文, マッパー, プレースホルダーの値
            result.forEach( r -> {
                System.out.println(r.getTestId());
                System.out.println(r.getTeacherEmail());
                System.out.println(r.getStudentEmail());
                System.out.println(r.getCorrectRatePercentage());
            });

            model.addAttribute("gradeList", result);

            return "teacher-grade-test";

        } catch (EmptyResultDataAccessException e) {
            return "teacher-dashboard"; 
        }
    }

    @GetMapping("view-student-grades")
    public String showStudentGrade(HttpSession session, Model model){

        if( session.getAttribute("email") == null ){
            return "redirect:/student";
        }

        String sql = """
                        SELECT 
                        a.test_id,
                        ROUND(
                            COUNT(CASE 
                                    WHEN (a.a_no = 1 AND c.ans1 = 'Y') OR 
                                        (a.a_no = 2 AND c.ans2 = 'Y') OR 
                                        (a.a_no = 3 AND c.ans3 = 'Y') OR 
                                        (a.a_no = 4 AND c.ans4 = 'Y') 
                                    THEN 1 
                                END) * 100.0 / COUNT(*),
                            1
                        ) AS correct_rate_percentage
                        FROM 
                        answer a
                        JOIN 
                        contents c
                        ON a.test_id = c.test_id AND a.q_no = c.q_no
                        WHERE 
                        a.mail = ?
                        GROUP BY 
                        a.test_id
                        ORDER BY 
                        a.test_id;
                        
                    """;

        try {
            RowMapper<StudentGrade> rowMapper = (rs, rowNum) -> {
                StudentGrade grade = new StudentGrade();
                grade.setTest_id(rs.getInt("test_id"));
                grade.setCorrect_rate_percentage(rs.getDouble("correct_rate_percentage"));
                return grade;
            };

            List<StudentGrade> result = jdbcTemplate.query(sql, rowMapper, session.getAttribute("email")); // sql文, マッパー, プレースホルダーの値
            result.forEach( r -> {
                System.out.println(r.getTest_id());
                System.out.println(r.getCorrect_rate_percentage());
            });

            model.addAttribute("gradeList", result);

            return "student-grade-test";

        } catch (EmptyResultDataAccessException e) {
            return "student-dashboard"; 
        }
    }
}
