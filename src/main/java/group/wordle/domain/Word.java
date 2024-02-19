package group.wordle.domain;

import java.util.ArrayList;
import java.util.List;

public class Word {

    private final String letters;
    private int guessNum;
    private final List<String> guessHistory;
    private final List<String> correctlyGuessedLetters;
    private final List<String> incorrectlyGuessedLetters;
    private final List<String> guessedMissingLetters;

    public Word(String letters) {
        this.letters = letters;
        this.guessNum = 0;
        guessHistory = new ArrayList<>();
        correctlyGuessedLetters = new ArrayList<>();
        incorrectlyGuessedLetters = new ArrayList<>();
        guessedMissingLetters = new ArrayList<>();
    }

    public String getLetters(){ return letters; }
    public int getGuessNum(){ return guessNum; }
    public void setGuessNum(int guessNum){ this.guessNum = guessNum; }
    public List<String> getGuessHistory(){ return guessHistory; }
    public List<String> getCorrectlyGuessedLetters(){ return correctlyGuessedLetters; }
    public List<String> getIncorrectlyGuessedLetters(){ return incorrectlyGuessedLetters; }
    public List<String> getGuessedMissingLetters(){ return guessedMissingLetters; }

}
