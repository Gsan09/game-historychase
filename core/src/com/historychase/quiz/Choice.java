package com.historychase.quiz;

public class Choice {

    private String text;
    private boolean isCorrect;
    private Question question;

    Choice(Question question,String text,boolean isCorrect){
        this.question = question;
        this.text = text;
        this.isCorrect = isCorrect;
    }

    public String getText() {
        return this.text;
    }

    public String getMaskedText(){
        return String.format("%s. %s",(char)(question.indexOf(this)+65),this.text);
    }

    public Choice setText(String text) {
        this.text = text;
        return this;
    }

    public Choice remove(){
        question.remove(this);
        return this;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public Question getQuestion() {
        return question;
    }

    public void choose(){
        question.chosen = this;
    }

    public void unChoose(){
        question.chosen = null;
    }
}
