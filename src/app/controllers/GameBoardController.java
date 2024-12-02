package app.controllers;

import app.views.GameBoardView;
import console.Computer;
import console.GameBoard;
import constants.Directions;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.List;

public class GameBoardController {
    private final Stage stage;
    private final Scene scene;
    private final boolean isComputerMode;
    private final String algorithm;
    private GameBoard gameBoard;
    private List<Directions> solutionPath;
    private int currentStep;
    private GameBoardView view;

    public GameBoardController(Stage stage, Scene scene, int levelIndex, boolean isComputerMode, String algorithm) {
        this.stage = stage;
        this.scene = scene;
        this.isComputerMode = isComputerMode;
        this.algorithm = algorithm;
        this.currentStep = 0;

        // Initialize game board
        this.gameBoard = GameBoard.getDeepCopy(constants.BoardLayouts.LEVELS.get(levelIndex));

        if (!isComputerMode) {
            setupKeyboardHandling();
        } else {
            // Start computer mode logic
            runAlgorithm(levelIndex);
        }
    }

    public void setView(GameBoardView view) {
        this.view = view;
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public boolean isComputerMode() {
        return isComputerMode;
    }

    // Handle player moves in human mode
    private void setupKeyboardHandling() {
        scene.setOnKeyPressed(event -> {
            Directions direction = null;
            switch (event.getCode()) {
                case UP -> direction = Directions.UP;
                case DOWN -> direction = Directions.DOWN;
                case LEFT -> direction = Directions.LEFT;
                case RIGHT -> direction = Directions.RIGHT;
            }
            if (direction != null) {
                handlePlayerMove(direction);
            }
        });
    }

    public void handlePlayerMove(Directions direction) {
        gameBoard = console.Play.move(gameBoard, direction);
        if (view != null) {
            Platform.runLater(() -> view.renderBoard());
        }
    }

    // Computer mode logic
    private void runAlgorithm(int levelIndex) {
        new Thread(() -> {
            solutionPath = Computer.play(algorithm, levelIndex);
            if (solutionPath != null && !solutionPath.isEmpty()) {
                animateComputerSolution();
            } else {
                Platform.runLater(() -> System.out.println("No solution found!"));
            }
        }).start();
    }

    private void animateComputerSolution() {
        new Thread(() -> {
            while (currentStep < solutionPath.size()) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                nextComputerMove();

                Platform.runLater(() -> {
                    if (view != null) {
                        view.renderBoard();
                    }
                });
            }
        }).start();
    }

    public void nextComputerMove() {
        if (currentStep < solutionPath.size()) {
            Directions direction = solutionPath.get(currentStep);
            gameBoard = console.Play.move(gameBoard, direction);
            currentStep++;
        }
    }

    public int getCurrentStep() {
        return currentStep;
    }

    public List<Directions> getSolutionPath() {
        return solutionPath;
    }


    public void goBack(int levelIndex) {
        Platform.runLater(() -> {
            // Navigate back to the previous screen, e.g., Level Selection or Player Mode Selection
            // For example:

            PlayerModeController.navigateToAlgorithmSelection(stage, levelIndex);

        });
    }

}

