package group.wordle.ui;

import group.wordle.logic.WordleController;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Parent;

public class WordleApplication extends Application {

    private final WordleController wordleController;
    private final List<Label> guessResultTiles;           // Stores historical guesses and results for each guessed letter
    private final List<String> thisGuess;                 // Stores the letters being added to the current guess
    private final List<Button> letterButtons;             // Stores the letter Buttons the user clicks on to enter a guess

    public WordleApplication() {
        wordleController = new WordleController();
        guessResultTiles = new ArrayList<>();
        thisGuess = new ArrayList<>();
        letterButtons = new ArrayList<>();
        wordleController.setWord(wordleController.getRandomWord());
    }

    // This method launches the Application
    public void go() {
        Application.launch(WordleApplication.class);
    }

    // This method configures the Stage
    @Override
    public void start(Stage stage) {
        // Create Scene and set Stage
        Scene guessingScene = createGuessingScene();
        stage.setTitle("Desktop Wordle");
        stage.setScene(guessingScene);
        stage.show();
    }

    // This method configures the Scene
    public Scene createGuessingScene() {
        // Create components
        Parent guessResultsLayout = createGuessResultsLayout();
        Parent guessInputLayout = createGuessInputLayout();

        // Add components to layout
        VBox guessingSceneVBox = new VBox();
        guessingSceneVBox.setAlignment(Pos.CENTER);
        guessingSceneVBox.setSpacing(50);
        guessingSceneVBox.setPadding(new Insets(50, 50, 50, 50));
        guessingSceneVBox.getChildren().addAll(guessResultsLayout, guessInputLayout);

        // Create Scene
        return new Scene(guessingSceneVBox);
    }

    // This method creates the display of historically guessed tiles and their results
    private Parent createGuessResultsLayout() {
        VBox guessesVBox = new VBox();
        for (int row = 0; row < 6; row++) {
            HBox guessRowHBox = new HBox();
            guessRowHBox.setAlignment(Pos.CENTER);
            guessRowHBox.setSpacing(5);
            guessRowHBox.setPadding(new Insets(0, 0, 5, 0));
            guessesVBox.getChildren().add(guessRowHBox);
            for (int col = 0; col < 5; col++) {
                Label guessTileLabel = new Label("");
                guessTileLabel.setMinHeight(80);
                guessTileLabel.setMinWidth(80);
                guessTileLabel.setFont(Style.FONT_LARGE);
                guessTileLabel.setAlignment(Pos.CENTER);
                guessTileLabel.setBorder(new Border(Style.BORDER_LIGHT_GRAY));
                guessResultTiles.add(guessTileLabel);
                guessRowHBox.getChildren().add(guessTileLabel);
            }
        }
        return guessesVBox;
    }

    // This method creates the interface for the user to add letters to the currently guessed word
    private Parent createGuessInputLayout() {
        String[] keyboardRow1 = {"Q","W","E","R","T","Y","U","I","O","P"};
        String[] keyboardRow2 = {"A","S","D","F","G","H","J","K","L"};
        String[] keyboardRow3 = {"ENTER","Z","X","C","V","B","N","M","CLEAR"};
        HBox tileRow1 = createTileRow(keyboardRow1);
        HBox tileRow2 = createTileRow(keyboardRow2);
        HBox tileRow3 = createTileRow(keyboardRow3);

        VBox tileRows = new VBox();
        tileRows.setSpacing(10);
        tileRows.getChildren().addAll(tileRow1, tileRow2, tileRow3);

        return tileRows;
    }

    // This method creates one tile of keyboard keys for use in adding letters to a guessed word
    private HBox createTileRow(String[] tileValues) {
        HBox tileRow = new HBox();
        tileRow.setSpacing(10);
        tileRow.setAlignment(Pos.CENTER);
        for (String tileValue : tileValues) {
            Button letterButton = new Button(tileValue);
            letterButton.setFont(Style.FONT_MEDIUM);
            letterButton.setMinWidth(80);
            letterButton.setMinHeight(80);
            tileRow.getChildren().add(letterButton);
            letterButton.setOnAction(event -> processTileClick(tileValue));
            letterButtons.add(letterButton);
        }
        return tileRow;
    }

    // This method handles a letter tile click from the user
    private void processTileClick(String tileValue) {

        // If game is already over, do nothing
        if (wordleController.isGameOver()) {
            return;
        }

        // If user pressed CLEAR, then clear the guess and quit
        if (tileValue.equals("CLEAR")) {
            for (int i = 0; i < thisGuess.size(); i++) {
                int resetTileNum = wordleController.getGuessNum() * 5 + i;
                guessResultTiles.get(resetTileNum).setText("");
                guessResultTiles.get(resetTileNum).setBorder(new Border(Style.BORDER_LIGHT_GRAY));
            }
            thisGuess.clear();
            return;
        }

        // If user pressed ENTER, then process the guess and quit
        if (tileValue.equals("ENTER")) {
            if (thisGuess.size() < 5) {
                return;
            }
            StringBuilder s = new StringBuilder();
            for (String letter : thisGuess) {
                s.append(letter);
            }
            processGuess(s.toString(), wordleController.getGuessNum());
            thisGuess.clear();
            return;
        }

        // If no more tiles can be added to the guess, then quit
        if (thisGuess.size() >= 5) {
            return;
        }

        // Add the letter to the guess
        int tileNum = wordleController.getGuessNum() * 5 + thisGuess.size();
        guessResultTiles.get(tileNum).setText(tileValue);
        guessResultTiles.get(tileNum).setBorder(new Border(Style.BORDER_BLACK));
        thisGuess.add(tileValue);
    }

    // This method processes a guessed word
    private void processGuess(String guessWord, int guessNum) {

        // Update logic
        wordleController.processGuess(guessWord);

        // Update guess result tiles
        for (int i = 0; i < guessWord.length(); i++) {
            int tileNum = guessNum * 5 + i;
            String letter = String.valueOf(guessWord.charAt(i));
            if (wordleController.correctPosition(letter, i)) {
                guessResultTiles.get(tileNum).setBackground(Background.fill(Color.LIGHTGREEN));
            } else if (wordleController.incorrectPosition(guessWord, letter, i)) {
                guessResultTiles.get(tileNum).setBackground(Background.fill(Color.LIGHTYELLOW));
            } else {
                guessResultTiles.get(tileNum).setBackground(Background.fill(Color.LIGHTGRAY));
            }
        }

        // Update guess input tiles
        for (Button letterButton : letterButtons) {
            String buttonText = letterButton.getText();
            if (wordleController.hasBeenConfirmedMissing(buttonText)) {
                letterButton.setBackground(Background.fill(Color.GRAY));
                letterButton.setBorder(new Border(Style.BORDER_BLACK));
            } else if (wordleController.hasBeenCorrectlyGuessed(buttonText)) {
                letterButton.setBackground(Background.fill(Color.LIGHTGREEN));
                letterButton.setBorder(new Border(Style.BORDER_BLACK));
            } else if (wordleController.hasBeenIncorrectlyGuessed(buttonText)) {
                letterButton.setBackground(Background.fill(Color.LIGHTYELLOW));
                letterButton.setBorder(new Border(Style.BORDER_BLACK));
            }
        }
    }

}