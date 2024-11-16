package app.controllers;

import app.views.AlgorithmSelectionView;
import app.views.GameBoardView;
import javafx.stage.Stage;

public class PlayerModeController {
    public static void navigateToAlgorithmSelection(Stage stage, int levelIndex) {
        AlgorithmSelectionView algorithmSelectionView = new AlgorithmSelectionView(stage, levelIndex);
        stage.getScene().setRoot(algorithmSelectionView.getView());
    }

    // In your controller or main application
    public static void navigateToGameBoard(Stage stage, int levelIndex, boolean isComputerMode, String algorithm) {
        GameBoardController controller = new GameBoardController(stage,stage.getScene() ,  levelIndex, isComputerMode, algorithm);
        GameBoardView gameBoardView = new GameBoardView(controller , levelIndex );
        stage.getScene().setRoot(gameBoardView.getView());
    }

}
