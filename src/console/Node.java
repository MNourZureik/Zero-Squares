package console;

import constants.Directions;

import java.util.Objects;

public class Node {
    private final GameBoard state;
    private final Node predecessor;
    private final Directions action;
    private int heuristic;

    public Node(GameBoard state, Node predecessor, Directions action, int heuristic) {
        this.state = state;
        this.predecessor = predecessor;
        this.action = action;
        this.heuristic = heuristic;
    }

    public Node(GameBoard state, Node predecessor, Directions action) {
        this.state = state;
        this.predecessor = predecessor;
        this.action = action;
    }


    public GameBoard getState() {
        return state;
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public Directions getAction() {
        return action;
    }

    public int getHeuristic() {
        return heuristic;
    }
    // Override equals for proper comparison in sets and maps
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Node node)) return false;
        return Objects.equals(state.getPlayers(), node.state.getPlayers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(state.getPlayers());
    }
}
