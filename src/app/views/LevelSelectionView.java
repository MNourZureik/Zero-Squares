package app.views;

import app.controllers.LevelSelectionController;
import constants.BoardLayouts;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LevelSelectionView {
    private final VBox view;

    public LevelSelectionView(Stage stage) {
        view = new VBox(20);
        view.setAlignment(Pos.CENTER);

        FlowPane levelsPane = new FlowPane();
        levelsPane.setAlignment(Pos.CENTER);
        levelsPane.setHgap(10);
        levelsPane.setVgap(10);
        levelsPane.setPadding(new Insets(20));

        // Create buttons for each level
        for (int i = 0; i < BoardLayouts.LEVELS.size(); i++) {
            int levelIndex = i;
            Button levelButton = new Button("Level " + (levelIndex + 1));
            //levelButton.setOnAction(e -> PlayerModeController.navigateToAlgorithmSelection(stage, levelIndex));
            levelButton.setOnAction(_ -> LevelSelectionController.navigateToPlayerMode(stage, levelIndex));
            levelsPane.getChildren().add(levelButton);
        }

        Button exitButton = new Button("Exit Game");
        exitButton.setOnAction(_ -> stage.close());

        view.getChildren().addAll(levelsPane, exitButton);
    }

    public VBox getView() {
        return view;
    }
}
