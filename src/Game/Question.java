package Game;


import java.util.ArrayList;
import java.util.Collections;

public class Question {

    private final String question, correctAnswer;
    private final char difficulty;
    private final ArrayList<String> allOptions;

    private final int INDEX_OF_CORRECT_ANSWER; //Very important. Storing index of correct answer because after using
    // lifelines, the answer strings are actively modified after lifelines are used, rendering the ArrayList.indexOf() and String
    // .equals() (which are used to check for the correct answer) methods basically useless!


    public Question(String question, String correctAnswer, ArrayList<String> allOptions, char difficulty) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.difficulty = difficulty;

        Collections.shuffle(allOptions);
        this.allOptions = allOptions;
        INDEX_OF_CORRECT_ANSWER = this.allOptions.indexOf(correctAnswer);
    }



    //GETTERS
    public ArrayList<String> getAllOptions() {
        return allOptions;
    }

    public String getQuestion() {
        return question;
    }

    public char getDifficulty() {
        return difficulty;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public int getINDEX_OF_CORRECT_ANSWER() {
        return INDEX_OF_CORRECT_ANSWER;
    }
}
