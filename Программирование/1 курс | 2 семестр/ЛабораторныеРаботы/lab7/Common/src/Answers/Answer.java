package Answers;

import java.io.Serializable;

public abstract class Answer implements Serializable {
    protected String answer;

    public Answer(String answer) {
        this.answer = answer;
    }

    public String getAnswer(){ return answer; }

    public abstract void logAnswer();
    public abstract void printAnswer();
}
