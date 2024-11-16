package app.views;

import app.controllers.AlgorithmSelectionController;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AlgorithmSelectionView {

    private final VBox root;

    public AlgorithmSelectionView(Stage stage, int levelIndex) {
        AlgorithmSelectionController controller = new AlgorithmSelectionController(stage);

        root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        // Buttons for algorithm selection
        Button bfsButton = new Button("BFS (Breadth First Search)");
        bfsButton.setOnAction(_ -> AlgorithmSelectionController.navigateToGameBoard(stage , levelIndex,"bfs"));

        Button dfsButton = new Button("DFS (Depth First Search)");
        dfsButton.setOnAction(_ -> AlgorithmSelectionController.navigateToGameBoard(stage , levelIndex,"dfs"));

        // Back button to go back to level selection
        Button backButton = new Button("Back");
        BorderPane.setAlignment(backButton, Pos.TOP_LEFT);
        backButton.setOnAction(_ -> controller.navigateBackToLevelSelection());

        // Add buttons to the layout
        root.getChildren().addAll(bfsButton, dfsButton, backButton);
    }

    public VBox getView() {
        return root;
    }
}
