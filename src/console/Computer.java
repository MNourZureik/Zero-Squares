package console;

import constants.BoardLayouts;
import constants.Directions;
import constants.Position;
import constants.SquareTypes;

import java.util.*;

public class Computer {

    /**
     * Generates the next possible states from the current game board,
     * along with the directions that lead to them.
     */
    public static List<Map.Entry<Directions, GameBoard>> nextState(GameBoard gameBoard) {
        List<Map.Entry<Directions, GameBoard>> nextStates = new ArrayList<>();

        for (Directions direction : Directions.values()) {
            // Create a deep copy once for each direction
            GameBoard gameBoardCopy = GameBoard.getDeepCopy(gameBoard);
            GameBoard movedBoard = Play.move(gameBoardCopy, direction);

            if (movedBoard != null && !movedBoard.equals(gameBoard) && !hasRepeatedGoals(movedBoard.getGoalsMap())) {
                nextStates.add(new AbstractMap.SimpleEntry<>(direction, movedBoard));
            }
        }

        return nextStates;
    }

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

    /**
     * Performs a Breadth-First Search (BFS) to find the shortest path to the goal.
     */
    public static List<Directions> bfs(GameBoard startGameBoard) {
        long startTime = System.currentTimeMillis();

        Queue<Node> queue = new LinkedList<>();
        Set<GameBoard> visited = new HashSet<>();

        Node startNode = new Node(startGameBoard, null, null);
        queue.add(startNode);
        visited.add(startGameBoard);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            GameBoard currentState = currentNode.getState();

            // Check if all goals are reached
            if (currentState.getGoalsMap().isEmpty()) {
                // Goal reached; reconstruct the path
                List<Directions> path = new ArrayList<>();
                Node node = currentNode;
                while (node.getAction() != null) {
                    path.addFirst(node.getAction());
                    node = node.getPredecessor();
                }

                long elapsedTime = System.currentTimeMillis() - startTime;
                System.out.println("Visited Set: " + visited.size());
                System.out.println("Path Length: " + path.size());
                System.out.println("Directions: " + path);
                System.out.println("Time: " + elapsedTime / 1000.0 + " seconds");
                return path;
            }

            // Generate successors
            List<Map.Entry<Directions, GameBoard>> successors = nextState(currentState);

            for (Map.Entry<Directions, GameBoard> entry : successors) {
                Directions direction = entry.getKey();
                GameBoard nextState = entry.getValue();

                if (!visited.contains(nextState)) {
                    visited.add(nextState);
                    queue.add(new Node(nextState, currentNode, direction));
                }
            }
        }

        return null; // No solution found
    }

    /**
     * Performs a Depth-First Search (DFS) to find a path to the goal.
     */
    public static List<Directions> dfs(GameBoard startGameBoard) {
        long startTime = System.currentTimeMillis();

        Deque<Node> stack = new LinkedList<>();
        Set<GameBoard> visited = new HashSet<>();

        Node startNode = new Node(startGameBoard, null, null);
        stack.push(startNode);
        visited.add(startGameBoard);

        while (!stack.isEmpty()) {
            Node currentNode = stack.removeFirst();
            GameBoard currentState = currentNode.getState();

            // Check if all goals are reached
            if (currentState.getGoalsMap().isEmpty()) {
                // Goal reached; reconstruct the path
                List<Directions> path = new ArrayList<>();
                Node node = currentNode;
                while (node.getAction() != null) {
                    path.addFirst(node.getAction()); // Add action at the beginning
                    node = node.getPredecessor();
                }

                long elapsedTime = System.currentTimeMillis() - startTime;
                System.out.println("Visited Set: " + visited.size());
                System.out.println("Path Length: " + path.size());
                System.out.println("Directions: " + path);
                System.out.println("Time: " + elapsedTime / 1000.0 + " seconds");
                return path;
            }

            // Generate successors
            List<Map.Entry<Directions, GameBoard>> successors = nextState(currentState);

            for (Map.Entry<Directions, GameBoard> entry : successors) {
                Directions direction = entry.getKey();
                GameBoard nextState = entry.getValue();

                if (!visited.contains(nextState)) {
                    visited.add(nextState);
                    stack.push(new Node(nextState, currentNode, direction));
                }
            }
        }

        return null; // No solution found
    }

    /**
     * Initiates the search algorithm based on the provided type.
     */

    private static List<Directions> ucs(GameBoard startGameBoard) {
        long startTime = System.currentTimeMillis();

        PriorityQueue<CostNode> queue = new PriorityQueue<>();
        Set<GameBoard> visited = new HashSet<>();

        CostNode startNode = new CostNode(startGameBoard, null, null , 1);
        queue.add(startNode);
        visited.add(startGameBoard);

        while (!queue.isEmpty()) {
            CostNode currentNode = queue.poll();
            GameBoard currentState = currentNode.getState();

            // Check if all goals are reached
            if (currentState.getGoalsMap().isEmpty()) {
                int sumCost = 0;
                // Goal reached; reconstruct the path
                List<Directions> path = new ArrayList<>();
                CostNode node = currentNode;
                while (node.getAction() != null) {
                    path.addFirst(node.getAction());
                    node = node.getPredecessor();
                    sumCost += node.getCost();
                }

                long elapsedTime = System.currentTimeMillis() - startTime;
                System.out.println("Visited Set: " + visited.size());
                System.out.println("Path Length: " + path.size());
                System.out.println("Directions: " + path);
                System.out.println("Time: " + elapsedTime / 1000.0 + " seconds");
                System.out.println("Cost: " + sumCost);
                return path;
            }

            // Generate successors
            List<Map.Entry<Directions, GameBoard>> successors = nextState(currentState);

            for (Map.Entry<Directions, GameBoard> entry : successors) {
                Directions direction = entry.getKey();
                GameBoard nextState = entry.getValue();

                if (!visited.contains(nextState)) {
                    visited.add(nextState);
                    queue.add(new CostNode(nextState, currentNode, direction , 1));
                }
            }
        }

        return null; // No solution found
    }

    public static List<Directions> play(String type, int index) {
        GameBoard gameBoard = GameBoard.getDeepCopy(BoardLayouts.LEVELS.get(index));
        if ("bfs".equalsIgnoreCase(type)) {
            return ucs(gameBoard);
        } else if ("dfs".equalsIgnoreCase(type)) {
            return dfs(gameBoard);
        }else if("ucs".equalsIgnoreCase(type)){
            return ucs(gameBoard);
        }

        return null;
    }

}
