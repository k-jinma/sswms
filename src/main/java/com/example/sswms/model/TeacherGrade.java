package com.example.sswms.model;

public class TeacherGrade {

    private int testId;
    private String teacherEmail;
    private String testName;
    private String studentEmail;
    private Double correctRatePercentage;


    public int getTestId() {
        return testId;
    }
    public void setTestId(int testId) {
        this.testId = testId;
    }
    public String getTeacherEmail() {
        return teacherEmail;
    }
    public void setTeacherEmail(String teacherEmail) {
        this.teacherEmail = teacherEmail;
    }
    public String getStudentEmail() {
        return studentEmail;
    }
    public void setStudentEmail(String studentEmail) {
        this.studentEmail = studentEmail;
    }
    public double getCorrectRatePercentage() {
        return correctRatePercentage;
    }
    public void setCorrectRatePercentage(double correctRatePercentage) {
        this.correctRatePercentage = correctRatePercentage;
    }
    public String getTestName() {
        return testName;
    }
    public void setTestName(String testName) {
        this.testName = testName;
    }

}
