package constants;

import console.GameBoard;
import javafx.scene.paint.Color;

import java.util.HashMap;

public class HelpingFunctions {

    // Check if player reaches their specific goal
    public static boolean isPlayerWin(Position pos, Position nextPos, GameBoard gameBoard) {
        SquareTypes currentPosType = gameBoard.getSquareType(pos);
        SquareTypes nextPosType = gameBoard.getSquareType(nextPos);

        return switch (currentPosType) {
            case RED -> nextPosType == SquareTypes.RED_GOAL;
            case BLUE -> nextPosType == SquareTypes.BLUE_GOAL;
            case YELLOW -> nextPosType == SquareTypes.YELLOW_GOAL;
            case PINK -> nextPosType == SquareTypes.PINK_GOAL;
            case CYAN -> nextPosType == SquareTypes.CYAN_GOAL;
            default -> false;
        };
    }

    // Check if the square type represents a player
    public static boolean isPlayerSquare(SquareTypes squareType) {
        return squareType == SquareTypes.RED ||
                squareType == SquareTypes.BLUE ||
                squareType == SquareTypes.YELLOW ||
                squareType == SquareTypes.CYAN ||
                squareType == SquareTypes.PINK;
    }

    // Check if the square type is a goal square
    public static boolean isGoalSquare(SquareTypes squareType) {
        return squareType == SquareTypes.RED_GOAL ||
                squareType == SquareTypes.BLUE_GOAL ||
                squareType == SquareTypes.YELLOW_GOAL ||
                squareType == SquareTypes.CYAN_GOAL ||
                squareType == SquareTypes.PINK_GOAL ||
                squareType == SquareTypes.COLORABLE;
    }

    // Check if all players have reached their goals
    public static boolean isAllPlayersWins(GameBoard gameBoard) {
        for (Position position : gameBoard.getGoalsMap().keySet()) {
            SquareTypes squareType = gameBoard.getSquareType(position);
            if (isGoalSquare(squareType) || isPlayerSquare(squareType)) {
                return false;  // A goal or player square is still active
            }
        }
        return true;  // All players have reached their goals
    }

    // Get color for goal square type
    public static Color getGoalColor(SquareTypes squareType) {
        return switch (squareType) {
            case RED_GOAL -> Color.RED;
            case BLUE_GOAL -> Color.BLUE;
            case YELLOW_GOAL -> Color.YELLOW;
            case CYAN_GOAL -> Color.CYAN;
            case PINK_GOAL -> Color.PINK;
            case COLORABLE -> Color.LIGHTGREY;
            default -> Color.GREEN;  // Default goal color if unspecified
        };
    }

    // Get color for any square type
    public static Color getColorForSquare(SquareTypes type) {
        return switch (type) {
            case RED -> Color.RED;
            case BLUE -> Color.BLUE;
            case YELLOW -> Color.YELLOW;
            case CYAN -> Color.CYAN;
            case WEAK_BARRIER, BARRIER -> Color.BLACK;
            case PINK -> Color.PINK;
            default -> Color.WHITE;  // Default color for unspecified square type
        };
    }

    public static int getMaxX(HashMap<Position, SquareTypes> boardMap,
                              HashMap<Position, SquareTypes> goalsMap,
                              HashMap<Position, SquareTypes> playersMap) {
        int maxX = 0;

        // Check max X in all maps
        for (Position pos : boardMap.keySet()) {
            maxX = Math.max(maxX, pos.getX());
        }
        for (Position pos : goalsMap.keySet()) {
            maxX = Math.max(maxX, pos.getX());
        }
        for (Position pos : playersMap.keySet()) {
            maxX = Math.max(maxX, pos.getX());
        }

        return maxX;
    }

    public static int getMaxY(HashMap<Position, SquareTypes> boardMap,
                              HashMap<Position, SquareTypes> goalsMap,
                              HashMap<Position, SquareTypes> playersMap) {
        int maxY = 0;

        // Check max Y in all maps
        for (Position pos : boardMap.keySet()) {
            maxY = Math.max(maxY, pos.getY());
        }
        for (Position pos : goalsMap.keySet()) {
            maxY = Math.max(maxY, pos.getY());
        }
        for (Position pos : playersMap.keySet()) {
            maxY = Math.max(maxY, pos.getY());
        }

        return maxY;
    }
}
