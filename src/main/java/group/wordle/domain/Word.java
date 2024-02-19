package group.wordle.domain;

import java.util.ArrayList;
import java.util.List;

public class Word {

    private String letters;
    private int guessNum;
    private List<String> guessHistory;
    private List<String> correctlyGuessedLetters;
    private List<String> incorrectlyGuessedLetters;
    private List<String> guessedMissingLetters;

    public Word(String letters) {
        this.letters = letters;
        this.guessNum = 0;
        guessHistory = new ArrayList<>();
        correctlyGuessedLetters = new ArrayList<>();
        incorrectlyGuessedLetters = new ArrayList<>();
        guessedMissingLetters = new ArrayList<>();
    }

    public String getLetters(){ return letters; }
    public void setLetters(String letters){ this.letters = letters; }
    public int getGuessNum(){ return guessNum; }
    public void setGuessNum(int guessNum){ this.guessNum = guessNum; }
    public List<String> getGuessHistory(){ return guessHistory; }
    public List<String> getCorrectlyGuessedLetters(){ return correctlyGuessedLetters; }
    public List<String> getIncorrectlyGuessedLetters(){ return incorrectlyGuessedLetters; }
    public List<String> getGuessedMissingLetters(){ return guessedMissingLetters; }

}
