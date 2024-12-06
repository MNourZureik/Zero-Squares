package constants;

import console.GameBoard;
import console.Node;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.*;

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

    public static SquareTypes getPlayerGoalType(SquareTypes playerType) {
        return switch (playerType) {
            case RED -> SquareTypes.RED_GOAL;
            case BLUE -> SquareTypes.BLUE_GOAL;
            case YELLOW -> SquareTypes.YELLOW_GOAL;
            case CYAN -> SquareTypes.CYAN_GOAL;
            case PINK -> SquareTypes.PINK_GOAL;
            default -> throw new IllegalArgumentException("Invalid player type: " + playerType);
        };
    }

    public static List<Directions> ReturnGoalPath(String type, Node goalNode, long startTime, int visitedSize, boolean isHaveCost) {
        Deque<Directions> path = new ArrayDeque<>();
        Node node = goalNode;

        while (node.getAction() != null) {
            path.addFirst(node.getAction()); // Insert at the beginning
            node = node.getPredecessor();
        }

        long elapsedTime = System.currentTimeMillis() - startTime;

        // Write output to a log file
        try (FileWriter fileWriter = new FileWriter("algorithm_output.log", true);
             PrintWriter logWriter = new PrintWriter(fileWriter)) {

            if (!type.equalsIgnoreCase("hill-climbing-simple:") && !type.equalsIgnoreCase("hill-climbing-steepest:")) {
                logWriter.println("Algorithm: " + type);
                logWriter.println("Visited Set: " + visitedSize);
                logWriter.println("Path Length: " + path.size());
                logWriter.println("Directions: " + path);
                logWriter.println("Time: " + (elapsedTime / 1000.0) + " second");
                if (isHaveCost) {
                    logWriter.println("Cost: " + goalNode.getState().getCost());
                }
            } else {
                logWriter.println("Algorithm: " + type);
                if (visitedSize == 0) {
                    logWriter.println("No solution found for this level\n");
                } else {
                    logWriter.println("Visited Set: " + visitedSize);
                    logWriter.println("Time: " + elapsedTime / 1000.0 + " seconds");
                    logWriter.println("Path Length: " + path.size());
                    logWriter.println("Directions: " + path);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Handle exceptions during file writing
        }

        return new ArrayList<>(path);
    }

    public static void clearLogFileIfNotEmpty(String logFilePath) {
        File logFile = new File(logFilePath);

        if (!logFile.exists()) {
            System.out.println("Log file does not exist.");
            return;
        }

        try {
            // Check if the file has content
            String content = Files.readString(logFile.toPath());
            if (content.isBlank()) {
                System.out.println("Log file is already empty.");
            } else {
                // Clear the file
                try (FileWriter fileWriter = new FileWriter(logFile, false)) {
                    fileWriter.write(""); // Clear the file
                    System.out.println("Log file cleared successfully.");
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading or clearing the log file: " + e.getMessage());
        }
    }

    public static long measureDataStorageUsage(Runnable storageTask) {
        long initialMemoryUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        storageTask.run();
        long finalMemoryUsage = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        return Math.abs(finalMemoryUsage - initialMemoryUsage) / 1000;
    }

    public static String getFormattedDataStorageUsage(long dataStorageUsageBytes) {
        if (dataStorageUsageBytes < 1024) {
            return String.format("Data storage usage: %d bytes", dataStorageUsageBytes);
        } else if (dataStorageUsageBytes < 1048576) {
            return String.format("Data storage usage: %.2f KB", dataStorageUsageBytes / 1024.0);
        } else {
            return String.format("Data storage usage: %.2f MB", dataStorageUsageBytes / 1048576.0);
        }
    }

    public static Position calculateNewPosition(GameBoard gameBoard, Position pos, Directions direction) {
        Position currentPosition = pos;
        SquareTypes playerType = gameBoard.getSquareType(pos);

        // Ensure the position is a player square
        if (!isPlayerSquare(playerType)) {
            return null;
        }

        SquareTypes playerGoalType = HelpingFunctions.getPlayerGoalType(playerType);

        while (true) {
            Position nextPosition = getNextPosition(currentPosition, direction);
            SquareTypes squareType = gameBoard.getSquareType(nextPosition);

            // Handle different square types based on game rules
            if (squareType == SquareTypes.COLORABLE) {
                // Simulate coloring without modifying game state
                currentPosition = nextPosition;
            } else if (squareType == playerGoalType) {
                // Player reaches its own goal
                break; // Stop moving
            } else if (HelpingFunctions.isGoalSquare(squareType)) {
                // Continue through other goal squares
                currentPosition = nextPosition;
            } else if (squareType == SquareTypes.WEAK_BARRIER) {
                // Hit a weak barrier; movement is invalid
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
}
