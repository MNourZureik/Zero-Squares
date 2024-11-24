package console;

import constants.Directions;

import java.security.CodeSource;
import java.util.Objects;
import java.lang.Comparable;

public class CostNode implements Comparable<CostNode>{
    private final GameBoard state;
    private final CostNode predecessor;
    private final Directions action;
    private final int cost ;

    public CostNode(GameBoard state, CostNode predecessor, Directions action, int cost) {
        this.state = state;
        this.predecessor = predecessor;
        this.action = action;
        this.cost = cost;
    }

    public GameBoard getState() {
        return state;
    }

    public CostNode getPredecessor() {
        return predecessor;
    }

    public Directions getAction() {
        return action;
    }

    public int getCost() {
        return cost;
    }

    // Override equals for proper comparison in sets and maps
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof CostNode node)) return false;
        return Objects.equals(state.getPlayers(), node.state.getPlayers());
    }

    @Override
    public int hashCode() {
        return Objects.hash(state.getPlayers());
    }

    @Override
    public int compareTo(CostNode node){
        if(node.cost > this.cost){
            return -1;
        }
        else if(node.cost < this.cost){
            return 1;
        }
        else return 1;
    }
}
