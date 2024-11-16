package console;

import constants.Directions;
import constants.HelpingFunctions;
import constants.Position;
import constants.SquareTypes;

import java.util.*;

public class Play {

    public static GameBoard move(GameBoard gameBoard, Directions direction) {
        // Find all players' positions first
        List<Position> playersPositions = gameBoard.getPlayersPositions();

        // Check if state needs rearrangement due to movement axis priority
        if (isNeedReArrange(gameBoard, playersPositions, direction)) {
            playersPositions = reArrangePlayers(playersPositions, direction);
        }

        List<Boolean> isValids = new ArrayList<>();
        for (Position playerPosition : playersPositions) {
            isValids.add(isValidToMove(gameBoard, playerPosition, direction));
        }

        int index = 0;
        for (Position playerPosition : playersPositions) {
            // If player can move
            if (isValids.get(index)) {
                Position finalPosition = calculateNewPosition(gameBoard, playerPosition, direction);
                if (finalPosition == null) {
                    return null; // Reset game as player hit weak barrier
                }

                if (!gameBoard.getPlayers().containsKey(playerPosition)) {
                    // The player has been removed (reached their goal), skip updating
                    index++;
                    continue;
                }

                // Update board and player positions
                SquareTypes playerType = gameBoard.getSquareType(playerPosition);
                gameBoard.setSquare(playerPosition, SquareTypes.EMPTY);
                gameBoard.setSquare(finalPosition, playerType);

                // Update _players to reflect the new position
                gameBoard.getPlayers().remove(playerPosition);
                gameBoard.getPlayers().put(finalPosition, playerType);
            }
            index++;
        }
        // Reset goals
        gameBoard.resetGoals();

        return gameBoard;
    }

    public static Position calculateNewPosition(GameBoard gameBoard, Position pos, Directions direction) {
        Position newPosition = pos;
        SquareTypes playerType = gameBoard.getSquareType(pos);

        while (true) {
            Position nextPosition = getNextPosition(newPosition, direction);
            SquareTypes squareType = gameBoard.getSquareType(nextPosition);

            // 4. Hit COLORABLE and color the cell
            if (squareType == SquareTypes.COLORABLE) {
                gameBoard.setGoal(nextPosition, gameBoard.getSquareType(pos));
                newPosition = nextPosition;
                continue;
            }

            // 3. Hit own goal
            if (HelpingFunctions.isPlayerWin(pos, nextPosition, gameBoard)) {
                gameBoard.removePlayer(pos);
                gameBoard.removeGoal(nextPosition);
                break;
            }

            // Check if the next position is a goal but not own goal
            if (HelpingFunctions.isGoalSquare(squareType) && playerType != squareType) {
                newPosition = nextPosition;
                continue;
            }

            // 2. Hit WEAK_BARRIER
            if (squareType == SquareTypes.WEAK_BARRIER) {
                return null;
            }

            // 1. Hit NON-EMPTY SQUARE_TYPE
            if (squareType != SquareTypes.EMPTY) {
                break;
            }

            newPosition = nextPosition;
        }
        return newPosition;
    }

    public static Position getNextPosition(Position pos, Directions direction) {
        switch (direction) {
            case UP:
                return new Position(pos.getX() - 1, pos.getY());
            case DOWN:
                return new Position(pos.getX() + 1, pos.getY());
            case LEFT:
                return new Position(pos.getX(), pos.getY() - 1);
            case RIGHT:
                return new Position(pos.getX(), pos.getY() + 1);
            default:
                throw new IllegalArgumentException("Invalid direction");
        }
    }

    public static boolean isValidToMove(GameBoard gameBoard, Position pos, Directions direction) {
        Position nextPosition = getNextPosition(pos, direction);
        SquareTypes squareType = gameBoard.getSquareType(nextPosition);

        // Check if the next position is a goal
        if (HelpingFunctions.isGoalSquare(squareType)) {
            return true;
        }

        // If it is not empty, it cannot move
        return squareType == SquareTypes.EMPTY;
    }

    public static List<Position> reArrangePlayers(List<Position> playersPositions, Directions direction) {
        // Rearrange players based on direction
        Collections.sort(playersPositions, new Comparator<Position>() {
            @Override
            public int compare(Position a, Position b) {
                switch (direction) {
                    case LEFT:
                        return Integer.compare(a.getY(), b.getY());
                    case RIGHT:
                        return Integer.compare(b.getY(), a.getY());
                    case UP:
                        return Integer.compare(a.getX(), b.getX());
                    case DOWN:
                        return Integer.compare(b.getX(), a.getX());
                    default:
                        return 0;
                }
            }
        });
        return playersPositions;
    }

    public static boolean isNeedReArrange(GameBoard gameBoard, List<Position> playersPositions, Directions direction) {
        // Group players by axis (x or y coordinate)
        Map<Integer, List<Position>> axisGroups = new HashMap<>();

        for (Position pos : playersPositions) {
            int key = (direction == Directions.LEFT || direction == Directions.RIGHT) ? pos.getX() : pos.getY();
            axisGroups.computeIfAbsent(key, k -> new ArrayList<>()).add(pos);
        }

        // Check each group to see if rearrangement is needed
        for (List<Position> group : axisGroups.values()) {
            if (group.size() > 1) {
                int movablePlayers = 0;
                for (Position pos : group) {
                    if (isValidToMove(gameBoard, pos, direction)) {
                        movablePlayers++;
                        if (movablePlayers >= 2) {
                            return true; // Rearrangement needed
                        }
                    }
                }
            }
        }
        return false; // No rearrangement needed
    }
}
