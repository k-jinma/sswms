package com.example.sswms.model;

import java.util.List;

public class TestData {
    private String testName;
    private List<Question> questions;

    // ゲッターとセッター
    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "TestData{" +
                "testName='" + testName + '\'' +
                ", questions=" + questions +
                '}';
    }
}
