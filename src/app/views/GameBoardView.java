package app.views;

import app.controllers.GameBoardController;
import console.GameBoard;
import constants.HelpingFunctions;
import constants.Position;
import constants.SquareTypes;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.HashMap;

public class GameBoardView {
    private final StackPane root;
    private final GridPane grid;
    private final GameBoardController controller;

    public GameBoardView(GameBoardController controller , int levelIndex) {
        this.controller = controller;
        controller.setView(this);

        Button backButton = new Button("< Back");
        backButton.setOnAction(_ -> controller.goBack(levelIndex));
        // Initialize root and grid
        root = new StackPane();
        grid = new GridPane();
        backButton.setAlignment(Pos.TOP_LEFT);
        grid.setAlignment(Pos.CENTER);
        root.getChildren().add(grid);

        renderBoard();

        // Ensure focus and key event handling for player mode
        if (controller.isComputerMode()) {
            animateComputerSolution();
        }
    }

    public StackPane getView() {
        return root;
    }

    public void renderBoard() {
        grid.getChildren().clear();
        GameBoard gameBoard = controller.getGameBoard();

        HashMap<Position, SquareTypes> boardMap = gameBoard.getBoardMap();
        HashMap<Position, SquareTypes> goalsMap = gameBoard.getGoalsMap();
        HashMap<Position, SquareTypes> playersMap = gameBoard.getPlayers();

        int maxX = HelpingFunctions.getMaxX(boardMap, goalsMap, playersMap);
        int maxY = HelpingFunctions.getMaxY(boardMap, goalsMap, playersMap);

        for (int x = 0; x <= maxX; x++) {
            for (int y = 0; y <= maxY; y++) {
                Position pos = new Position(x, y);
                SquareTypes squareType = gameBoard.getSquareType(pos);

                Rectangle square = new Rectangle(50, 50);

                if (playersMap.containsKey(pos)) {
                    // Players are colored based on their square type
                    square.setFill(getPlayerColor(playersMap.get(pos)));
                    square.setStroke(getPlayerColor(squareType));
                    square.setStrokeWidth(5);
                } else if (goalsMap.containsKey(pos) || squareType == SquareTypes.COLORABLE) {
                    // Goals are empty squares with a border of the goal's color
                    square.setFill(Color.TRANSPARENT);
                    square.setStroke(getGoalColor(squareType));
                    square.setStrokeWidth(5);
                } else if (squareType == SquareTypes.WEAK_BARRIER) {
                    square.setFill(Color.TRANSPARENT);
                    square.setStroke(Color.BLACK);
                    square.setStrokeWidth(5);
                } else {
                    if(squareType == SquareTypes.BARRIER){
                        square.setStroke(Color.BLACK);
                        square.setStrokeWidth(5);
                    }
                    if (squareType == SquareTypes.EMPTY){
                        square.setStroke(Color.WHITE);
                        square.setStrokeWidth(5);
                    }
                    square.setFill(getDefaultColor(squareType));
                }

                grid.add(square, y, x);
            }
        }
    }

    private Color getPlayerColor(SquareTypes playerType) {
        return switch (playerType) {
            case RED -> Color.RED;
            case BLUE -> Color.BLUE;
            case YELLOW -> Color.YELLOW;
            case CYAN -> Color.CYAN;
            case PINK -> Color.HOTPINK;
            default -> Color.TRANSPARENT;
        };
    }

    private Color getGoalColor(SquareTypes squareType) {
        return switch (squareType) {
            case RED_GOAL -> Color.RED;
            case BLUE_GOAL -> Color.BLUE;
            case YELLOW_GOAL -> Color.YELLOW;
            case CYAN_GOAL -> Color.CYAN;
            case PINK_GOAL -> Color.HOTPINK;
            case COLORABLE -> Color.LIGHTGREY;
            default -> Color.TRANSPARENT;
        };
    }

    private Color getDefaultColor(SquareTypes squareType) {
        return switch (squareType) {
            case EMPTY -> Color.WHITE;
            case BARRIER -> Color.BLACK;
            default -> Color.TRANSPARENT;
        };
    }

    private void animateComputerSolution() {
        if (controller.getSolutionPath() == null || controller.getSolutionPath().isEmpty()) {
            System.out.println("Solving...");
            return; // Do not proceed if there is no solution
        }

        new Thread(() -> {
            while (controller.getCurrentStep() < controller.getSolutionPath().size()) {
                try {
                    Thread.sleep(500); // Delay between moves
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return; // Exit the thread if interrupted
                }

                // Apply the next move
                controller.nextComputerMove();

                // Update the UI on the JavaFX Application Thread
                javafx.application.Platform.runLater(this::renderBoard);
            }
        }).start();
    }

}