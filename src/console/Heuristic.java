package console;

import constants.HelpingFunctions;
import constants.Position;
import constants.SquareTypes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Heuristic {

    // Heuristic for if there is any repeated goals :
    public static boolean hasRepeatedGoals(Map<Position, SquareTypes> goalsMap) {
        // Use a HashSet to track seen goal types
        Set<SquareTypes> seenGoals = new HashSet<>();

        for (SquareTypes goalType : goalsMap.values()) {
            if (seenGoals.contains(goalType)) {
                // If the goal type has already been seen, it's repeated
                return true;
            }
            // Add the goal type to the set
            seenGoals.add(goalType);
        }

        // No repeated goals found
        return false;
    }

    // Manhattan Distance Heuristic :
    public static int ManhatenDistancehueristic(GameBoard board) {
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
    // My Super Heuristic :
    public  static HashMap<Position, SquareTypes> setPlayersPriority(GameBoard gameBoard){
        return null;
    }
}
