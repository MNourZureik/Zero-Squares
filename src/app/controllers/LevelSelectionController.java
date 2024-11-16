package app.controllers;

import app.views.PlayerModeView;
import javafx.stage.Stage;

public class LevelSelectionController {
    public static void navigateToPlayerMode(Stage stage, int levelIndex) {
        PlayerModeView playerModeView = new PlayerModeView(stage, levelIndex);
        stage.getScene().setRoot(playerModeView.getView());
    }
}
