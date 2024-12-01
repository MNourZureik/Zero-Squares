package console;

import constants.*;

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

            if (movedBoard != null && !movedBoard.equals(gameBoard) ) {
                nextStates.add(new AbstractMap.SimpleEntry<>(direction, movedBoard));
            }
        }
        return nextStates;
    }
    //&& !hasRepeatedGoals(movedBoard.getGoalsMap())

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
                return ReturnGoalPath(currentNode, startTime, visited.size() , false);
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
                return ReturnGoalPath(currentNode, startTime, visited.size() , false);
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


    public static List<Directions> dfsRecursion(GameBoard startGameBoard) {
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
            return ReturnGoalPath(goalNode, startTime, visited.size(), false);
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

    /**
     * Initiates the search algorithm based on the provided type.
     */

    private static List<Directions> hillClimbing(GameBoard startGameBoard){
        long startTime = System.currentTimeMillis();

        // Priority Queue to manage exploration based on cost from the GameBoard
        Queue<Node> queue = new LinkedList<>();

        // Map to track visited states with their associated costs
        Map<GameBoard, Integer> visited = new HashMap<>();

        startGameBoard.setCost(0); // Set the initial cost to 0
        Node startNode = new Node(startGameBoard, null, null);
        queue.add(startNode);
        visited.put(startGameBoard, 0);
        int min_cost = 0;

        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            GameBoard currentBoard = currentNode.getState();

            if (currentBoard.getGoalsMap().isEmpty()) {
                return ReturnGoalPath(currentNode , startTime , visited.size() , true);
            }

            List<Map.Entry<Directions, GameBoard>> successors = nextState(currentBoard);
            GameBoard min_cost_gameboard = currentBoard;
            Directions direction = Directions.UP;
            for (Map.Entry<Directions, GameBoard> entry : successors) {
                Directions current_direction = entry.getKey();
                GameBoard nextBoard = entry.getValue();

                int current_cost = startGameBoard.getCost() - nextBoard.getCost();
                System.out.println("current Direction :"+direction);
                System.out.println("current cost :"+current_cost);
                System.out.println("min cost :"+min_cost);
                if(current_cost >= 0 && current_cost <= min_cost){
                    min_cost = current_cost;
                    min_cost_gameboard = nextBoard;
                    min_cost_gameboard.setCost(min_cost);
                    direction = current_direction;
                }

                if (!visited.containsKey(nextBoard)) {
                    nextBoard.setCost(current_cost);
                    visited.put(nextBoard, current_cost);
                }
            }
            startGameBoard.setCost(min_cost);
            queue.add(new Node(min_cost_gameboard, currentNode, direction));
            if(min_cost_gameboard.equals(currentBoard)){
                return ReturnGoalPath(currentNode , startTime , visited.size() , true);
            }

        }

        // No solution found
        return null;
    }

    private static int ManhatenDistancehueristic(GameBoard gameBoard){
        int heuristic = 0;
        HashMap<Position, SquareTypes> goals = gameBoard.getGoalsMap();
        HashMap<Position, SquareTypes> players = gameBoard.getPlayers();

        for(Position player_pos : players.keySet()){
            for(Position goal_pos : goals.keySet()){
                heuristic += HelpingFunctions.isPlayerGoal(player_pos , goal_pos , gameBoard);
            }
        }
        return heuristic;
    }


    private static List<Directions> a_star(GameBoard startGameBoard){
        long startTime = System.currentTimeMillis();

        // Priority Queue to manage exploration based on cost from the GameBoard
        PriorityQueue<Node> queue = new PriorityQueue<>(Comparator.comparingInt(node -> node.getState().getCost()));
        // Map to track visited states with their associated costs
        Map<GameBoard, Integer> visited = new HashMap<>();

        // Initialize with the starting GameBoard
        startGameBoard.setCost(0); // Set the initial cost to 0//ManhatenDistancehueristic(startGameBoard)
        Node startNode = new Node(startGameBoard, null, null);
        queue.add(startNode);
        visited.put(startGameBoard, 0); //ManhatenDistancehueristic(startGameBoard)

        while (!queue.isEmpty()) {
            // Fetch the Node with the lowest cost
            Node currentNode = queue.poll();
            GameBoard currentBoard = currentNode.getState();

            // Check if all goals are reached
            if (currentBoard.getGoalsMap().isEmpty()) {
                return ReturnGoalPath(currentNode , startTime , visited.size() , true);
            }

            // Generate successors
            List<Map.Entry<Directions, GameBoard>> successors = nextState(currentBoard);

            for (Map.Entry<Directions, GameBoard> entry : successors) {
                Directions direction = entry.getKey();
                GameBoard nextBoard = entry.getValue();

                // Calculate cumulative cost
                int newCost = currentBoard.getCost() + ManhatenDistancehueristic(nextBoard);//currentBoard.getCost() + nextBoard.getCost() + ManhatenDistancehueristic(nextBoard);

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
              return ReturnGoalPath(currentNode , startTime , visited.size() , true);
            }

            // Generate successors
            List<Map.Entry<Directions, GameBoard>> successors = nextState(currentBoard);

            for (Map.Entry<Directions, GameBoard> entry : successors) {
                Directions direction = entry.getKey();
                GameBoard nextBoard = entry.getValue();

                // Calculate cumulative cost
                int newCost = currentBoard.getCost() + nextBoard.getCost();

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

    public static List<Directions> play(String type, int index) {
        GameBoard gameBoard = GameBoard.getDeepCopy(BoardLayouts.LEVELS.get(index));
        if ("bfs".equalsIgnoreCase(type)) {
            return bfs(gameBoard);
        } else if ("dfs".equalsIgnoreCase(type)) {
            return dfs(gameBoard);
        }else if("ucs".equalsIgnoreCase(type)){
            return ucs(gameBoard);
        } else if ("dfs-r".equalsIgnoreCase(type)) {
            return dfsRecursion(gameBoard);
        } else if ("hill-climbing".equalsIgnoreCase(type)) {
            return hillClimbing(gameBoard);
        }else if("a_star".equalsIgnoreCase(type)){
            return a_star(gameBoard);
        }

        return null;
    }

    private static List<Directions> ReturnGoalPath(Node goalNode, long startTime, int visitedSize , boolean isHaveCost) {
        Deque<Directions> path = new ArrayDeque<>();
        Node node = goalNode;

        while (node.getAction() != null) {
            path.addFirst(node.getAction()); // Insert at the beginning
            node = node.getPredecessor();
        }

        long elapsedTime = System.currentTimeMillis() - startTime;
        System.out.println("Visited Set: " + visitedSize);
        System.out.println("Path Length: " + path.size());
        System.out.println("Directions: " + path);
        System.out.println("Time: " + elapsedTime / 1000.0 + " seconds");
        if(isHaveCost){
            System.out.println("Cost: " + goalNode.getState().getCost());
        }
        return new ArrayList<>(path);
    }

}
