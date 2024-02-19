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
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.Parent;


public class WordleApplication extends Application {

    private WordleController wordleController;
    private List<Label> guessResultTiles;
    private List<String> thisGuess;
    private List<Button> letterButtons;


    public WordleApplication() {
        wordleController = new WordleController();
        wordleController.setWord(wordleController.getRandomWord());
        guessResultTiles = new ArrayList<>();
        thisGuess = new ArrayList<>();
        letterButtons = new ArrayList<>();
    }

    public void go(String[] args) {
        Application.launch(WordleApplication.class);
    }

    @Override
    public void start(Stage stage) {
        Scene guessingScene = createGuessingScene();

        stage.setTitle("Desktop Wordle");
        stage.setScene(guessingScene);
        stage.show();
    }

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

    private void processTileClick(String tileValue) {

        if (wordleController.isGameOver()) {
            return;
        }

        int tileNum = wordleController.getGuessNum() * 5 + thisGuess.size();

        // If user wants to clear guess, then clear it and quit
        if (tileValue.equals("CLEAR")) {
            for (int i = 0; i < thisGuess.size(); i++) {
                int resetTileNum = wordleController.getGuessNum() * 5 + i;
                guessResultTiles.get(resetTileNum).setText("");
                guessResultTiles.get(resetTileNum).setBorder(new Border(Style.BORDER_LIGHT_GRAY));
            }
            thisGuess.clear();
            return;
        }

        // If the user is trying to enter a guess
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

        // If all 5 tiles have already been entered for this guess, then quit
        if (thisGuess.size() >= 5) {
            return;
        }

        thisGuess.add(tileValue);

        guessResultTiles.get(tileNum).setText(tileValue);
        guessResultTiles.get(tileNum).setBorder(new Border(Style.BORDER_BLACK));
    }

    private void processGuess(String guessWord, int guessNum) {
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