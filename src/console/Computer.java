package console;

import constants.BoardLayouts;
import constants.Directions;
import constants.HelpingFunctions;

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

            if (movedBoard != null && !movedBoard.equals(gameBoard)) {
                nextStates.add(new AbstractMap.SimpleEntry<>(direction, movedBoard));
            }
        }
        return nextStates;
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
                return HelpingFunctions.ReturnGoalPath(currentNode, startTime, visited.size(), false);
            }

            // Generate successors
            List<Map.Entry<Directions, GameBoard>> successors = nextState(currentState);

            for (Map.Entry<Directions, GameBoard> entry : successors) {
                Directions direction = entry.getKey();
                GameBoard nextState = entry.getValue();
                // Check if all goals are reached
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
                return HelpingFunctions.ReturnGoalPath(currentNode, startTime, visited.size(), false);
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
     * Performs a Recursive Depth-First Search (DFS) to find a path to the goal.
     */
    private static List<Directions> dfsRecursion(GameBoard startGameBoard) {
        long startTime = System.currentTimeMillis();
        Set<GameBoard> visited = new HashSet<>();
        return dfsRecursive(startGameBoard, visited, null, null, startTime);
    }
    private static List<Directions> dfsRecursive(GameBoard currentBoard, Set<GameBoard> visited, Node predecessor, Directions action, long startTime) {
        // Mark the current state as visited
        visited.add(currentBoard);

        // Base case: Check if all goals are reached
        if (currentBoard.getGoalsMap().isEmpty()) {
            Node goalNode = new Node(currentBoard, predecessor, action);
            return HelpingFunctions.ReturnGoalPath(goalNode, startTime, visited.size(), false);
        }

        // Generate successors
        List<Map.Entry<Directions, GameBoard>> successors = nextState(currentBoard);

        for (Map.Entry<Directions, GameBoard> entry : successors) {
            Directions direction = entry.getKey();
            GameBoard nextBoard = entry.getValue();

            // Recursive exploration for unvisited states
            if (!visited.contains(nextBoard)) {
                Node currentNode = new Node(currentBoard, predecessor, action);
                List<Directions> result = dfsRecursive(nextBoard, visited, currentNode, direction, startTime);
                if (result != null) {
                    return result; // Return the path as soon as the goal is found
                }
            }
        }

        return null; // No solution found in this branch
    }
    /*
     * Performs a Hill Climbing Simple to find a path to the goal.
     */
    private static List<Directions> hillClimbing_Simple(GameBoard startGameBoard) {
        long startTime = System.currentTimeMillis();

        // Map to track visited states with their associated costs
        Map<GameBoard, Integer> visited = new HashMap<>();

        // Initialize with the starting GameBoard
        startGameBoard.setCost(Heuristic.ManhatenDistancehueristic(startGameBoard));
        Node currentNode = new Node(startGameBoard, null, null);
        visited.put(startGameBoard, startGameBoard.getCost());

        while (true) {
            GameBoard currentBoard = currentNode.getState();

            // Check if all goals are reached
            if (currentBoard.getGoalsMap().isEmpty()) {
                return HelpingFunctions.ReturnGoalPath(currentNode, startTime, visited.size(), true);
            }

            // Generate successors
            List<Map.Entry<Directions, GameBoard>> successors = nextState(currentBoard);

            // Filter successors to remove boards with repeated goals
            List<Map.Entry<Directions, GameBoard>> validSuccessors = successors.stream()
                    .filter(entry -> !Heuristic.hasRepeatedGoals(entry.getValue().getGoalsMap()))
                    .toList();

            // Find the best successor based on heuristic :
            Node bestNode = null;
            int bestHeuristic = Integer.MAX_VALUE;
            for (Map.Entry<Directions, GameBoard> entry : validSuccessors) {
                Directions direction = entry.getKey();
                GameBoard nextBoard = entry.getValue();

                int heuristic = Heuristic.ManhatenDistancehueristic(nextBoard);

                // Only consider better states
                if (heuristic < bestHeuristic) {
                    bestHeuristic = heuristic;
                    bestNode = new Node(nextBoard, currentNode, direction);
                }
            }

            // If no better state is found, terminate
            if (bestNode == null || bestHeuristic >= currentBoard.getCost()) {
                System.out.println("Stuck in local optimum.");
                return null;
            }

            // Move to the best successor
            currentNode = bestNode;
            currentBoard.setCost(bestHeuristic);
            visited.put(currentBoard, bestHeuristic);
        }
    }
    /*
     * Performs a Hill Climbing Steepest to find a path to the goal.
     */
    private static List<Directions> hillClimbingSteepest(GameBoard startGameBoard) {
        long startTime = System.currentTimeMillis();

        // Initialize with the starting GameBoard
        startGameBoard.setCost(Heuristic.ManhatenDistancehueristic(startGameBoard));
        Node currentNode = new Node(startGameBoard, null, null);

        while (true) {
            GameBoard currentBoard = currentNode.getState();

            // Check if all goals are reached
            if (currentBoard.getGoalsMap().isEmpty()) {
                return HelpingFunctions.ReturnGoalPath(currentNode, startTime, 0, true);
            }

            // Generate successors
            List<Map.Entry<Directions, GameBoard>> successors = nextState(currentBoard);

            // Filter successors to remove boards with repeated goals
            List<Map.Entry<Directions, GameBoard>> validSuccessors = successors.stream()
                    .filter(entry -> !Heuristic.hasRepeatedGoals(entry.getValue().getGoalsMap()))
                    .toList();

            // Evaluate all successors and find the best one
            Node bestNode = null;
            int bestHeuristic = Integer.MAX_VALUE;

            for (Map.Entry<Directions, GameBoard> entry : validSuccessors) {
                Directions direction = entry.getKey();
                GameBoard nextBoard = entry.getValue();

                int heuristic = Heuristic.ManhatenDistancehueristic(nextBoard);

                // Check for the steepest improvement
                if (heuristic < bestHeuristic) {
                    bestHeuristic = heuristic;
                    bestNode = new Node(nextBoard, currentNode, direction);
                }
            }

            // If no better successor is found, terminate (local optimum)
            if (bestNode == null || bestHeuristic >= currentBoard.getCost()) {
                System.out.println("Stuck in local optimum.");
                return null;
            }

            // Move to the best successor
            currentNode = bestNode;
            currentNode.getState().setCost(bestHeuristic);
        }
    }
    /*
     * Performs a-star (A*) to find a path to the goal.
     */
    private static List<Directions> a_star(GameBoard startGameBoard) {
        long startTime = System.currentTimeMillis();

        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node ->
                node.getState().getCost() + Heuristic.ManhatenDistancehueristic(node.getState())));
        Map<GameBoard, Integer> visited = new HashMap<>();

        startGameBoard.setCost(0); // Initialize cost
        Node startNode = new Node(startGameBoard, null, null);
        queue.add(startNode);
        visited.put(startGameBoard, 0);

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            GameBoard currentBoard = currentNode.getState();

            // Goal check
            if (currentBoard.getGoalsMap().isEmpty()) {
                return HelpingFunctions.ReturnGoalPath(currentNode, startTime, visited.size(), true);
            }

            // Generate successors
            List<Map.Entry<Directions, GameBoard>> successors = nextState(currentBoard);

            // Filter successors to remove boards with repeated goals
            List<Map.Entry<Directions, GameBoard>> validSuccessors = successors.stream()
                    .filter(entry -> !Heuristic.hasRepeatedGoals(entry.getValue().getGoalsMap()))
                    .toList();

            for (Map.Entry<Directions, GameBoard> entry : validSuccessors) {
                Directions direction = entry.getKey();
                GameBoard nextBoard = entry.getValue();

                // Compute costs
                int child_cost = currentBoard.getCost() + 1;//nextBoard.getCost(); // Assume movement cost = 1
                int f_n = Heuristic.ManhatenDistancehueristic(nextBoard) + child_cost;

                // Check if the state is new or found with a lower cost
                if (!visited.containsKey(nextBoard) || visited.get(nextBoard) > f_n) {
                    nextBoard.setCost(child_cost); // Update cost in GameBoard
                    visited.put(nextBoard, f_n);   // Store new f_n in visited
                    queue.add(new Node(nextBoard, currentNode, direction));
                }
            }
        }

        // No solution found
        return null;
    }
    /*
     * Performs Uniform Cost Search (UCS) to find a path to the goal.
     */
    private static List<Directions> ucs(GameBoard startGameBoard) {
        long startTime = System.currentTimeMillis();

        // Priority Queue to manage exploration based on cost from the GameBoard
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getState().getCost()));
        // Map to track visited states with their associated costs
        Map<GameBoard, Integer> visited = new HashMap<>();

        // Initialize with the starting GameBoard
        startGameBoard.setCost(0); // Set the initial cost to 0
        Node startNode = new Node(startGameBoard, null, null);
        queue.add(startNode);
        visited.put(startGameBoard, 0);

        while (!queue.isEmpty()) {
            // Fetch the Node with the lowest cost
            Node currentNode = queue.poll();
            GameBoard currentBoard = currentNode.getState();

            // Check if all goals are reached
            if (currentBoard.getGoalsMap().isEmpty()) {
                return HelpingFunctions.ReturnGoalPath(currentNode, startTime, visited.size(), true);
            }

            // Generate successors
            List<Map.Entry<Directions, GameBoard>> successors = nextState(currentBoard);

            for (Map.Entry<Directions, GameBoard> entry : successors) {
                Directions direction = entry.getKey();
                GameBoard nextBoard = entry.getValue();

                // Calculate cumulative cost
                int newCost = currentBoard.getCost()  + nextBoard.getCost() ;// + nextBoard.getCost();

                // If the state is new or found with a lower cost, update it
                if (!visited.containsKey(nextBoard) || visited.get(nextBoard) > newCost) {
                    nextBoard.setCost(newCost); // Update the cost in the GameBoard
                    visited.put(nextBoard, newCost);
                    queue.add(new Node(nextBoard, currentNode, direction));
                }
            }
        }

        // No solution found
        return null;
    }
    /*
     * Initial playing algorithm.
     */
    public static List<Directions> play(String type, int index) {
        GameBoard gameBoard = GameBoard.getDeepCopy(BoardLayouts.LEVELS.get(index));
        if ("bfs".equalsIgnoreCase(type)) {
            return bfs(gameBoard);
        } else if ("dfs".equalsIgnoreCase(type)) {
            return dfs(gameBoard);
        } else if ("ucs".equalsIgnoreCase(type)) {
            return ucs(gameBoard);
        } else if ("dfs-r".equalsIgnoreCase(type)) {
            return dfsRecursion(gameBoard);
        } else if ("hill-climbing-simple".equalsIgnoreCase(type)) {
            return hillClimbing_Simple(gameBoard);
        } else if ("hill-climbing-steepest".equalsIgnoreCase(type)) {
            return hillClimbingSteepest(gameBoard);
        } else if ("a_star".equalsIgnoreCase(type)) {
            return a_star(gameBoard);
        }

        return null;
    }



}
