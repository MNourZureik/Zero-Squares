package app.controllers;

import app.views.GameBoardView;
import app.views.LevelSelectionView;
import javafx.stage.Stage;

public class AlgorithmSelectionController {

    private final Stage stage;

    public AlgorithmSelectionController(Stage stage) {
        this.stage = stage;
    }

    // Method to navigate to the level selection screen
    public void navigateBackToLevelSelection() {
        LevelSelectionView levelSelectionView = new LevelSelectionView(stage);
        stage.getScene().setRoot(levelSelectionView.getView());
    }

    // Method to navigate to the game board screen
    public static void navigateToGameBoard(Stage stage, int levelIndex, String algorithm) {
        GameBoardController controller = new GameBoardController(stage, stage.getScene(),  levelIndex, true, algorithm);
        GameBoardView gameBoardView = new GameBoardView(controller , levelIndex );
        stage.getScene().setRoot(gameBoardView.getView());
    }
}
