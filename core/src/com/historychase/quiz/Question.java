package com.historychase.quiz;

import java.util.ArrayList;
import java.util.Collections;

public class Question extends ArrayList<Choice> implements QuizRuntime{

    private String[] text;

    Choice chosen;

    public Question(String... text) {
        this.text = text;
    }

    public String[] getText() {
        return text;
    }

    public Question setText(String[] text) {
        this.text = text;
        return this;
    }

    public Choice makeChoice(String label){
        return makeChoice(label,false);
    }

    public Choice makeChoice(String label,Boolean isCorrect){
        Choice choice = new Choice(this,label,isCorrect);
        this.add(choice);
        return choice;
    }

    public void removeChoice(Choice c){
        if(c.getQuestion() != this)
            throw new RuntimeException("Invalid choice");
        this.remove(c);
    }

    @Override
    public void initQuiz() {
        chosen = null;
        Collections.shuffle(this);
    }

    public Choice getChosen() {
        return chosen;
    }

    @Override
    public String toString() {
        String stringVal = "";
        stringVal += "Question: ";
        for(String line : text)
            stringVal+= " " + line;
        for (Choice choice: this) {
            stringVal += "\n\t" + choice.getMaskedText() + (choice.isCorrect()?"(Correct)":"");
        }
        return stringVal;
    }

}
