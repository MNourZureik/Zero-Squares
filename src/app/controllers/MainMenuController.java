package app.controllers;

import app.views.PlayerModeView;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMenuController {
    public static void navigateToPlayerMode(Stage stage, int levelIndex) {
        PlayerModeView playerModeView = new PlayerModeView(stage, levelIndex);
        stage.setScene(new Scene(playerModeView.getView(), 800, 600));
    }
}
