package console;

import constants.Directions;
import constants.HelpingFunctions;
import constants.Position;
import constants.SquareTypes;
import javafx.geometry.Pos;

import java.util.*;

public class Play {

    public static GameBoard move(GameBoard gameBoard, Directions direction) {
        // Get all players' positions
        List<Position> playersPositions = gameBoard.getPlayersPositions();

        // Rearrange players if necessary
        reArrangePlayers(playersPositions, direction);

        ArrayList<Boolean> isValids = new ArrayList<>();
        int index =0 ;
        for(Position playerPosition : playersPositions){
            if (isValidToMove(gameBoard, playerPosition , direction)){
             isValids.add(true);
            }
            else {
                isValids.add(false);
            }
        }
        // Move each player
        for (Position playerPosition : playersPositions) {
            if (isValids.getFirst()) {
                Position finalPosition = calculateNewPosition(gameBoard, playerPosition, direction);
                if (finalPosition == null) {
                    // Player hit a weak barrier; reset the game
                    return null;
                }

                // Check if the player still exists (may have reached their goal)
                if (!gameBoard.getPlayers().containsKey(playerPosition)) {
                    continue; // Skip updating if player was removed
                }

                // Update the game board and player positions
                SquareTypes playerType = gameBoard.getPlayers().get(playerPosition);
                gameBoard.setSquare(playerPosition, SquareTypes.EMPTY);
                gameBoard.setSquare(finalPosition, playerType);

                // Update players map
                gameBoard.getPlayers().remove(playerPosition);
                gameBoard.getPlayers().put(finalPosition, playerType);
            }
            isValids.removeFirst();
        }

        // Reset goals if necessary
        gameBoard.resetGoals();

        return gameBoard;
    }

    public static Position calculateNewPosition(GameBoard gameBoard, Position pos, Directions direction) {
        Position currentPosition = pos;
        SquareTypes playerType = gameBoard.getSquareType(pos);
        SquareTypes playerGoalType = HelpingFunctions.getPlayerGoalType(playerType);

        while (true) {
            Position nextPosition = getNextPosition(currentPosition, direction);
            SquareTypes squareType = gameBoard.getSquareType(nextPosition);

            if (squareType == SquareTypes.COLORABLE) {
                // Color the cell and continue moving
                gameBoard.setGoal(nextPosition, playerType);
                currentPosition = nextPosition;
            } else if (squareType == playerGoalType) {
                // Player reaches own goal
                gameBoard.removePlayer(pos);
                gameBoard.removeGoal(nextPosition);
                break;
            } else if (HelpingFunctions.isGoalSquare(squareType)) {
                // Continue moving through other goals
                currentPosition = nextPosition;
            } else if (squareType == SquareTypes.WEAK_BARRIER) {
                // Hit a weak barrier; reset the game
                return null;
            } else if (squareType != SquareTypes.EMPTY) {
                // Hit any non-empty square; stop moving
                break;
            } else {
                // Continue moving through empty squares
                currentPosition = nextPosition;
            }
        }
        return currentPosition;
    }

    public static Position getNextPosition(Position pos, Directions direction) {
        return switch (direction) {
            case UP -> new Position(pos.getX() - 1, pos.getY());
            case DOWN -> new Position(pos.getX() + 1, pos.getY());
            case LEFT -> new Position(pos.getX(), pos.getY() - 1);
            case RIGHT -> new Position(pos.getX(), pos.getY() + 1);
        };
    }

    public static boolean isValidToMove(GameBoard gameBoard, Position pos, Directions direction) {
        Position nextPosition = getNextPosition(pos, direction);
        SquareTypes squareType = gameBoard.getSquareType(nextPosition);

        // Check if the next square is empty or a goal square
        return squareType == SquareTypes.EMPTY || HelpingFunctions.isGoalSquare(squareType) ;
    }

    public static void reArrangePlayers(List<Position> playersPositions, Directions direction) {
        Comparator<Position> comparator = switch (direction) {
            case LEFT -> Comparator.comparingInt(Position::getY);
            case RIGHT -> Comparator.comparingInt(Position::getY).reversed();
            case UP -> Comparator.comparingInt(Position::getX);
            case DOWN -> Comparator.comparingInt(Position::getX).reversed();
        };
        playersPositions.sort(comparator);
    }
}
