package app;

import app.views.LevelSelectionView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        LevelSelectionView levelSelectionView = new LevelSelectionView(primaryStage);
        primaryStage.setTitle("Zero Squares Game");
        Scene scene = new Scene(levelSelectionView.getView(), 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
        // Request focus on the scene to capture keyboard input
        scene.getRoot().requestFocus();
    }
}
