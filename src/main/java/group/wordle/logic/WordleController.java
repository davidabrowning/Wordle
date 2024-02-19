package group.wordle.logic;

import group.wordle.domain.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordleController {

    Word word;

    public WordleController() {
    }

    // This method returns the word to be guessed as a String
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

    // This method processes a new guess from the user
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

    // This method provides one of the user's previous guesses
    public String getGuessHistory(int guessNum) {
        return word.getGuessHistory().get(guessNum);
    }

    // This method checks if the letter is in the correct position
    public boolean correctPosition(String letter, int position) {
        return letter.equals(String.valueOf(word.getLetters().charAt(position)));
    }

    // This method checks if the letter is contained in the word but in some other position
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

    // This method checks if the game is over
    public boolean isGameOver() {
        if (word.getGuessHistory().isEmpty()) {
            return false;
        }
        String lastGuess = word.getGuessHistory().get(word.getGuessHistory().size() - 1);
        return lastGuess.equals(word.getLetters());
    }

    // This method checks if this letter has been correctly guessed in the past
    public boolean hasBeenCorrectlyGuessed(String letter) {
        return word.getCorrectlyGuessedLetters().contains(letter);
    }

    // This method checks if this letter has been "incorrectly" guessed in the past
    // "incorrect" = contained  in the answer word, but in some other position
    public boolean hasBeenIncorrectlyGuessed(String letter) {
        return word.getIncorrectlyGuessedLetters().contains(letter);
    }

    // This method checks if this letter has been guessed in the past
    // and confirmed not to be in the answer word at all
    public boolean hasBeenConfirmedMissing(String letter) {
        return word.getGuessedMissingLetters().contains(letter);
    }

    // This method generates a random word for the user to guess
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