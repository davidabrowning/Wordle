package group.wordle.logic;

import group.wordle.domain.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleController {

    Word word;

    public WordleController() {
    }

    public String getWord() {
        if (word == null) {
            return null;
        }
        return word.getLetters();
    }

    public void setWord(String newWord) {
        word = new Word(newWord);
    }

    public int getGuessNum(){
        return word.getGuessNum();
    }

    public void processGuess(String guess) {
        word.setGuessNum(word.getGuessNum() + 1);
        word.getGuessHistory().add(guess);
        for (int i = 0; i < guess.length(); i++) {
            String guessLetter = String.valueOf(guess.charAt(i));
            if (correctPosition(guessLetter, i)) {
                word.getCorrectlyGuessedLetters().add(guessLetter);
            }
            if (incorrectPosition(guess, guessLetter, i)) {
                word.getIncorrectlyGuessedLetters().add(guessLetter);
            }
            if (!word.getLetters().contains(guessLetter)) {
                word.getGuessedMissingLetters().add(guessLetter);
            }
        }
    }

    public String getGuessHistory(int guessNum) {
        return word.getGuessHistory().get(guessNum);
    }

    public boolean correctPosition(String letter, int position) {
        return letter.equals(String.valueOf(word.getLetters().charAt(position)));
    }

    public boolean incorrectPosition(String guessWord, String guessLetter, int position) {
        if (correctPosition(guessLetter, position)) {
            return false;
        }
        String answer = word.getLetters();
        int numThisLetterInAnswer = 0;
        int numThisLetterCorrectlyGuessed = 0;
        int numThisLetterPreviouslyIncorrectlyGuessed = 0;
        int guessNumOfThisLetter = 0;
        for (int i = 0; i < answer.length(); i++) {
            String g = String.valueOf(guessWord.charAt(i));
            String a = String.valueOf(answer.charAt(i));
            if (g.equals(guessLetter) && i <= position) {
                guessNumOfThisLetter++;
            }
            if (a.equals(guessLetter)) {
                numThisLetterInAnswer++;
                if (g.equals(guessLetter)) {
                    numThisLetterCorrectlyGuessed++;
                }
            } else {
                if (g.equals(guessLetter) && i < position) {
                    numThisLetterPreviouslyIncorrectlyGuessed++;
                }
            }
        }
        return numThisLetterInAnswer - numThisLetterCorrectlyGuessed - numThisLetterPreviouslyIncorrectlyGuessed > 0;
    }

    public boolean isGameOver() {
        if (word.getGuessHistory().isEmpty()) {
            return false;
        }
        String lastGuess = word.getGuessHistory().get(word.getGuessHistory().size() - 1);
        return lastGuess.equals(word.getLetters());
    }

    public boolean hasBeenCorrectlyGuessed(String letter) {
        return word.getCorrectlyGuessedLetters().contains(letter);
    }

    public boolean hasBeenIncorrectlyGuessed(String letter) {
        return word.getIncorrectlyGuessedLetters().contains(letter);
    }

    public boolean hasBeenConfirmedMissing(String letter) {
        return word.getGuessedMissingLetters().contains(letter);
    }

    public String getRandomWord() {
        List<String> possibleWords = new ArrayList<>();
        possibleWords.add("SPORK");
        possibleWords.add("SKUNK");
        possibleWords.add("START");
        possibleWords.add("BRINK");
        possibleWords.add("RANGE");
        possibleWords.add("ALOHA");
        possibleWords.add("FLUNK");
        possibleWords.add("PLUNK");
        possibleWords.add("WATER");
        possibleWords.add("PRINT");
        possibleWords.add("PLANE");
        possibleWords.add("TRUCK");
        possibleWords.add("QUIET");
        possibleWords.add("QUEST");
        possibleWords.add("BLAZE");
        possibleWords.add("ATOLL");
        possibleWords.add("BINGE");
        possibleWords.add("MOVIE");
        possibleWords.add("ROAST");
        possibleWords.add("RESTS");
        possibleWords.add("EXITS");
        possibleWords.add("EXIST");
        possibleWords.add("TAXES");
        possibleWords.add("TOWER");
        possibleWords.add("SWORD");
        possibleWords.add("KITTY");
        possibleWords.add("PEACE");
        possibleWords.add("DAWNS");
        possibleWords.add("SWANS");
        possibleWords.add("PARTS");
        possibleWords.add("APART");
        possibleWords.add("OUSTS");

        Random rand = new Random();
        int wordNum = rand.nextInt(0, possibleWords.size());
        return possibleWords.get(wordNum);
    }

}