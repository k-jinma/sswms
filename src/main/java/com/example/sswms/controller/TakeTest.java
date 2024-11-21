package com.example.sswms.controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.sswms.model.TestWithContents;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
@RequestMapping("/")
public class TakeTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @GetMapping("take-test")
    public String takeTest(@RequestParam("testId") int testId, @RequestParam("email") String email, Model model){

        String sql = "SELECT test.test_id, test.test_name, contents.q_no, contents.q_text, contents.sel1, contents.sel2, contents.sel3, contents.sel4 FROM test " +
                        "LEFT JOIN contents ON test.test_id = contents.test_id " +
                            "WHERE test.test_id = ?";

        try {
            RowMapper<TestWithContents> rowMapper = (rs, rowNum) -> {
                TestWithContents test = new TestWithContents();
                test.setTestId(rs.getInt("test_id"));
                test.setTestName(rs.getString("test_name"));
                test.setqNo(rs.getInt("q_no"));
                test.setqText(rs.getString("q_text"));
                test.setqSel1(rs.getString("sel1"));
                test.setqSel2(rs.getString("sel2"));
                test.setqSel3(rs.getString("sel3"));
                test.setqSel4(rs.getString("sel4"));
                return test;
            };

            List<TestWithContents> result = jdbcTemplate.query(sql, rowMapper, testId); // sql文, マッパー, プレースホルダーの値
            result.forEach( r -> {
                System.out.println(r.getqText());
                System.out.println(r.getqSel1());
                System.out.println(r.getqSel2());
                System.out.println(r.getqSel3());
                System.out.println(r.getqSel4());
            });

            model.addAttribute("testId", testId);
            model.addAttribute("qList", result);

            return "student-answer-test";

        } catch (EmptyResultDataAccessException e) {
            return "student-dashboard"; 
        }
    }

    @PostMapping("submit-test")
    @Transactional(rollbackFor = Exception.class)
    public String submitTest(@RequestParam Map<String, String> allParams, Model model) {

        AtomicInteger counter = new AtomicInteger(1); // カウント用の変数をAtomicIntegerで定義
        boolean isFirst = true; // 最初のエントリをスキップするためのフラグ

        try {

            for (Map.Entry<String, String> entry : allParams.entrySet()) {

                if (isFirst) {
                    isFirst = false;
                    continue; // 最初のエントリをスキップ
                }

                String key = entry.getKey();
                String value = entry.getValue();
                System.out.println("Parameter Name - " + key + ", Value - " + value);
                // データベースに保存する処理
                jdbcTemplate.update(
                    "INSERT INTO answer (mail, test_id, q_no, a_no) VALUES (?, ?, ?, ?)",
                    "test@example.com", // メールアドレス (仮の値)
                    Integer.parseInt(allParams.get("testId")), // テストID
                    counter.getAndIncrement(),  // 質問番号 (カウントアップ)
                    value                       // 回答番号
                );
            }
        } catch (Exception e) {
            // 例外が発生した場合の処理
            System.err.println("Error occurred while submitting test: " + e.getMessage());
            // ロールバック処理
            jdbcTemplate.execute("ROLLBACK");

            return "redirect:/take-test?testId=" + allParams.get("testId") + "&email=" + "test@test.com"; // エラーページにリダイレクト
        }

        return "student-dashboard";
    }
    
}
