package app.views;

import app.controllers.PlayerModeController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PlayerModeView {
    private final VBox view;

    public PlayerModeView(Stage stage, int levelIndex) {
        view = new VBox(20);
        view.setAlignment(Pos.CENTER);

        Button backButton = new Button("< Back");
        backButton.setOnAction(_ -> {
            // Navigate back to Level Selection
            LevelSelectionView levelSelectionView = new LevelSelectionView(stage);
            stage.getScene().setRoot(levelSelectionView.getView());
        });


        Button computerModeButton = new Button("Computer Mode");
        computerModeButton.setOnAction(_ -> PlayerModeController.navigateToAlgorithmSelection(stage, levelIndex));

        Button humanModeButton = new Button("Player Mode");
        humanModeButton.setOnAction(_ -> PlayerModeController.navigateToGameBoard(stage, levelIndex, false, null));

        //view.getChildren().add(borderPane);
        view.getChildren().addAll(computerModeButton, humanModeButton , backButton);
    }

    public VBox getView() {
        return view;
    }
}
