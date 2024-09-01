package com.example.doancuoikymonandroid2;

public class QuestionsList {
    String tvQuestion, btnOption1, btnOption2, btnOption3, btnOption4, answer;
    String userSelectedAnswer;

    public QuestionsList(String tvQuestion, String btnOption1, String btnOption2, String btnOption3, String btnOption4, String answer, String userSelectedAnswers) {
        this.tvQuestion = tvQuestion;
        this.btnOption1 = btnOption1;
        this.btnOption2 = btnOption2;
        this.btnOption3 = btnOption3;
        this.btnOption4 = btnOption4;
        this.answer = answer;
        this.userSelectedAnswer = userSelectedAnswer;
    }

    public String getTvQuestion() {
        return tvQuestion;
    }

    public String getBtnOption1() {
        return btnOption1;
    }

    public String getBtnOption2() {
        return btnOption2;
    }

    public String getBtnOption3() {
        return btnOption3;
    }

    public String getBtnOption4() {
        return btnOption4;
    }

    public String getAnswer() {
        return answer;
    }

    public String getUserSelectedAnswer() {
        return userSelectedAnswer;
    }

    public void setUserSelectedAnswer(String userSelectedAnswer) {
        this.userSelectedAnswer = userSelectedAnswer;
    }
}
