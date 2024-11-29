package com.example.sswms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sswms.model.Question;
import com.example.sswms.model.TestData;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/")
public class CreateTestRest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @PostMapping("/save-test")                              //モデル（model）クラス
    public ResponseEntity<String> testCreate( @RequestBody TestData testData, HttpSession session) {

        try {
            String sql1_0 = """
                                SELECT random_id
                                FROM (
                                    SELECT FLOOR(RAND() * 9000) + 1000 AS random_id
                                ) AS subquery
                                WHERE NOT EXISTS (
                                    SELECT 1
                                    FROM test
                                    WHERE test.test_id = subquery.random_id
                                )
                                LIMIT 1;
                            """;

            int testId = jdbcTemplate.queryForObject(sql1_0, Integer.class);

            String sql1_1 = "INSERT INTO test (test_id, test_name, q_count, mail) VALUES (?, ?, ?, ?)";
            System.out.println(sql1_1);
            
            jdbcTemplate.update(sql1_1, testId, testData.getTestName(), testData.getQuestions().size(), session.getAttribute("email"));
        
            int qNo = 1;
            for( Question question : testData.getQuestions()) {
    
                String sql2;
                String correctAnswer = question.getCorrectAnswer();
                if(correctAnswer.equals("1")) { 
                    sql2 = "INSERT INTO contents (test_id, q_no, q_text, sel1, sel2, sel3, sel4, ans1) VALUES (?, ?, ?, ?, ? ,? ,? ,?)";
                }else if(correctAnswer.equals("2")) {
                    sql2 = "INSERT INTO contents (test_id, q_no, q_text, sel1, sel2, sel3, sel4, ans2) VALUES (?, ?, ?, ?, ? ,? ,? ,?)";
                }else if(correctAnswer.equals("3")) {
                    sql2 = "INSERT INTO contents (test_id, q_no, q_text, sel1, sel2, sel3, sel4, ans3) VALUES (?, ?, ?, ?, ? ,? ,? ,?)";
                }else {
                    sql2 = "INSERT INTO contents (test_id, q_no, q_text, sel1, sel2, sel3, sel4, ans4) VALUES (?, ?, ?, ?, ? ,? ,? ,?)";
                }
                jdbcTemplate.update(sql2, testId, qNo, question.getQuestionText(), question.getChoices().get(0), question.getChoices().get(1), question.getChoices().get(2), question.getChoices().get(3), "Y");
                System.out.println(sql2);

                qNo++;

            }

            return ResponseEntity.status(HttpStatus.OK).body("{\"testId\": \"" + testId + "\"}");
            
        } catch (Exception e) {
            // 例外が発生した場合、トランザクションはロールバックされ、ここが実行されます
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"message\": \"データの保存に失敗しました\"}");
        }


    }
}
