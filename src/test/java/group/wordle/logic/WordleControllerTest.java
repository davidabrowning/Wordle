package group.wordle.logic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WordleControllerTest {


    private WordleController wc;

    @BeforeEach
    void setup() {
        wc = new WordleController();
        wc.setWord("ALOHA");
    }

    @Test
    void getWordInitiallyReturnsNull() {
        WordleController newWC = new WordleController();
        assertNull(newWC.getWord());
    }

    @Test
    void getWordIsNotNullAfterWordIsSet() {
        assertNotNull(wc.getWord());
    }

    @Test
    void guessesStartsAtZero() {
        assertEquals(0, wc.getGuessNum());
    }

    @Test
    void guessesIncrementsAfterGuess() {
        wc.processGuess("ARUBA");
        assertEquals(1, wc.getGuessNum());
    }

    @Test
    void guessHistoryReturnsGuessedWord() {
        wc.processGuess("ARUBA");
        assertEquals("ARUBA", wc.getGuessHistory(0));
    }

    @Test
    void correctPositionIsFalseIfPositionIsIncorrect() {
        assertFalse(wc.correctPosition("A", 1));
    }

    @Test
    void correctPositionIsTrueIfPositionIsCorrect() {
        assertTrue(wc.correctPosition("A", 4));
    }

    @Test
    void gameOverIsInitiallyFalse() {
        assertFalse(wc.isGameOver());
    }

    @Test
    void gameOverIsFalseAfterIncorrectGuess() {
        wc.processGuess("TRUNK");
        wc.processGuess("THINK");
        assertFalse(wc.isGameOver());
    }

    @Test
    void gameOverIsTrueAfterCorrectGuess() {
        wc.processGuess("TRUNK");
        wc.processGuess("ALOHA");
        assertTrue(wc.isGameOver());
    }

    @Test
    void singleIncorrectlyPlacedLetterIsTrue() {
        assertTrue(wc.incorrectPosition("LOOKS", "L", 0));
    }

    @Test
    void firstAInAlohaIsNotIncorrect() {
        assertFalse(wc.incorrectPosition("ATOLL", "A", 0));
    }

    @Test
    void secondAInAlohaIsCorrectlyIncorrect() {
        assertTrue(wc.incorrectPosition("RANGE", "A", 1));
    }

    @Test
    void secondAInAlohaIsNotIncorrectWithTwoIncorrectAs() {
        assertTrue(wc.incorrectPosition("AARTS", "A", 1));
    }

    @Test
    void secondAInAlohaIsCorrectWithTwoIncorrectAs() {
        assertTrue(wc.incorrectPosition("CARTA", "A", 1));
    }

    @Test
    void secondOfTwoIncorrectlyGuessedAsInAlohaIsIncorrect() {
        assertTrue(wc.incorrectPosition("RAAAR", "A", 2));
    }

    @Test
    void letterHIsNotInitiallyInCorrectlyGuessedLetters() {
        assertFalse(wc.hasBeenCorrectlyGuessed("H"));
    }

    @Test
    void letterHIsInCorrectlyGuessedLettersAfterCorrectGuess() {
        wc.processGuess("PUSHY");
        assertTrue(wc.hasBeenCorrectlyGuessed("H"));
    }

    @Test
    void letterHHasNotBeenInitiallyIncorrectlyGuessed() {
        assertFalse(wc.hasBeenIncorrectlyGuessed("H"));
    }

    @Test
    void letterHHasBeenIncorrectlyGuessedAfterGues() {
        wc.processGuess("SHORT");
        assertTrue(wc.hasBeenIncorrectlyGuessed("H"));
    }

    @Test
    void letterXIsNotInitiallyConfirmedMissing() {
        assertFalse(wc.hasBeenConfirmedMissing("X"));
    }

    @Test
    void letterXIsConfirmedMissingAfterGuess() {
        wc.processGuess("EXTRA");
        assertTrue(wc.hasBeenConfirmedMissing("X"));
    }





}