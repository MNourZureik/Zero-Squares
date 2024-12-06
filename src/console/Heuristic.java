package console;

import constants.Directions;
import constants.HelpingFunctions;
import constants.Position;
import constants.SquareTypes;

import java.util.*;
import java.util.stream.Collectors;

public class Heuristic {
    // Heuristic for if there is any repeated goals :
    public static boolean hasRepeatedGoals(Map<Position, SquareTypes> goalsMap) {
        // Use a HashSet to track seen goal types
        Set<SquareTypes> seenGoals = new HashSet<>();

        for (SquareTypes goalType : goalsMap.values()) {
            if (seenGoals.contains(goalType)) {
                // If the goal type has already been seen, it's repeated
                return false;
            }
            // Add the goal type to the set
            seenGoals.add(goalType);
        }

        // No repeated goals found
        return true;
    }

    // Manhattan Distance Heuristic :
    public static int ManhattanUncharacteristic(GameBoard board) {
        int totalDistance = 0;
        Map<SquareTypes, Position> goalPositions = board.getGoalsMap().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey));

        for (Map.Entry<Position, SquareTypes> playerEntry : board.getPlayers().entrySet()) {
            Position playerPosition = playerEntry.getKey();
            SquareTypes goalType = HelpingFunctions.getPlayerGoalType(playerEntry.getValue());
            Position goalPosition = goalPositions.get(goalType);
            if (goalPosition != null) {
                totalDistance += Math.abs(playerPosition.getX() - goalPosition.getX()) +
                        Math.abs(playerPosition.getY() - goalPosition.getY());
            }
        }
        return totalDistance;
    }

    private static boolean canReachGoalDirectly(GameBoard board, Position playerPosition, Position goalPosition) {
        Queue<Position> queue = new LinkedList<>();
        Set<Position> visited = new HashSet<>();

        // Add the initial position to the queue
        queue.add(goalPosition);
        visited.add(playerPosition);

        while (!queue.isEmpty()) {
            Position current = queue.poll();

            // Explore all possible directions the player can move
            for (Directions direction : Directions.values()) {
                // Simulate the player's movement and calculate their final position
                Position nextPosition = HelpingFunctions.calculateNewPosition(board, current, direction);

                // If the movement hits a weak barrier or invalid square, skip
                if (nextPosition == null) {
                    continue;
                }

                // If the next position is the goal, return true
                if (nextPosition.equals(goalPosition)) {
                    return true;
                }

                // Add the valid and unvisited positions to the queue for further exploration
                if (board.getSquareType(nextPosition) == SquareTypes.EMPTY && visited.add(nextPosition)) {
                    queue.add(nextPosition);
                }
            }
        }

        // If the queue is exhausted without finding the goal, return false
        return false;
    }

    public static int DynamicWeightedHeuristic(GameBoard board) {
        int totalWeightedDistance = 0;

        // Cache goal positions for quick lookups
        Map<SquareTypes, Position> goalPositions = new HashMap<>();
        for (Map.Entry<Position, SquareTypes> goalEntry : board.getGoalsMap().entrySet()) {
            goalPositions.put(goalEntry.getValue(), goalEntry.getKey());
        }

        // Compute weighted heuristic for all players
        for (Map.Entry<Position, SquareTypes> playerEntry : board.getPlayers().entrySet()) {
            Position playerPosition = playerEntry.getKey();
            SquareTypes playerType = playerEntry.getValue();

            Position goalPosition = goalPositions.get(HelpingFunctions.getPlayerGoalType(playerType));

            if (goalPosition != null) {
                boolean directReachable = canReachGoalDirectly(board, playerPosition, goalPosition);
                int weight = directReachable ? 1 : 5;

                int manhattanDistance = Math.abs(playerPosition.getX() - goalPosition.getX()) +
                        Math.abs(playerPosition.getY() - goalPosition.getY());

                totalWeightedDistance += manhattanDistance * weight;
            }
        }

        return totalWeightedDistance;
    }
}
