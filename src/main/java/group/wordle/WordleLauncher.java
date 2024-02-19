package group.wordle;

import group.wordle.ui.WordleApplication;

public class WordleLauncher {

    public static void main(String[] args) {
        WordleApplication wordleApplication = new WordleApplication();
        wordleApplication.go(args);
    }

}
