module group.wordle {
    requires javafx.controls;
    requires javafx.fxml;


    opens group.wordle to javafx.fxml;
    exports group.wordle;
    exports group.wordle.ui;
    opens group.wordle.ui to javafx.fxml;
    exports group.wordle.logic;
    opens group.wordle.logic to javafx.fxml;
}