package com.example.sswms.model;

import java.util.List;

public class Question {
    private String questionText;
    private List<String> choices;
    private String correctAnswer;

    // ゲッターとセッター
    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "questionText='" + questionText + '\'' +
                ", choices=" + choices +
                ", correctAnswer='" + correctAnswer + '\'' +
                '}';
    }
}
