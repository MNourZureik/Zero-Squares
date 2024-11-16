package console;


import constants.Position;
import constants.SquareTypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class GameBoard {
    private final HashMap<Position, SquareTypes> board;
    private final HashMap<Position, SquareTypes> goals;
    private final HashMap<Position, SquareTypes> players;

    public GameBoard(HashMap<Position, SquareTypes> board, HashMap<Position, SquareTypes> goals, HashMap<Position, SquareTypes> players) {
        this.board = board;
        this.goals = goals;
        this.players = players;
    }

    public static GameBoard getDeepCopy(GameBoard originalGameBoard) {
        // Get the original maps
        HashMap<Position, SquareTypes> originalBoardMap = originalGameBoard.getBoardMap();
        HashMap<Position, SquareTypes> originalGoalsMap = originalGameBoard.getGoalsMap();
        HashMap<Position, SquareTypes> originalPlayersMap = originalGameBoard.getPlayers();

        // Create deep copies of each map to ensure no references are shared
        HashMap<Position, SquareTypes> newBoardMap = new HashMap<>();
        HashMap<Position, SquareTypes> newGoalsMap = new HashMap<>();
        HashMap<Position, SquareTypes> newPlayersMap = new HashMap<>();

        // Deep copy each entry to avoid shared references
        originalBoardMap.forEach((key, value) -> newBoardMap.put(new Position(key.getX(), key.getY()), value));
        originalGoalsMap.forEach((key, value) -> newGoalsMap.put(new Position(key.getX(), key.getY()), value));
        originalPlayersMap.forEach((key, value) -> newPlayersMap.put(new Position(key.getX(), key.getY()), value));

        return new GameBoard(newBoardMap, newGoalsMap, newPlayersMap);
    }

    public SquareTypes getSquareType(Position pos) {
        return board.getOrDefault(pos, players.getOrDefault(pos, goals.getOrDefault(pos, SquareTypes.NULL)));
    }

    public void setSquare(Position pos, SquareTypes squareType) {
        board.put(pos, squareType);
    }

    public void clearBoard() {
        board.clear();
        goals.clear();
        players.clear();
    }

    public HashMap<Position, SquareTypes> getBoardMap() {
        return board;
    }

    public HashMap<Position, SquareTypes> getGoalsMap() {
        return goals;
    }

    public HashMap<Position, SquareTypes> getPlayers() {
        return players;
    }

    public List<Position> getPlayersPositions() {
        return new ArrayList<>(players.keySet());
    }

    public void resetGoals() {
        for (Position pos : goals.keySet()) {
            if (board.get(pos) == SquareTypes.EMPTY) {
                board.put(pos, goals.get(pos));
            }
        }
    }

    public void removeGoal(Position pos) {
        setSquare(pos, SquareTypes.EMPTY);
        goals.remove(pos);
    }

    public void removePlayer(Position pos) {
        setSquare(pos, SquareTypes.EMPTY);
        players.remove(pos);
    }

    public void setGoal(Position pos, SquareTypes squareType) {
        switch (squareType) {
            case RED -> goals.put(pos, SquareTypes.RED_GOAL);
            case BLUE -> goals.put(pos, SquareTypes.BLUE_GOAL);
            case PINK -> goals.put(pos, SquareTypes.PINK_GOAL);
            case YELLOW -> goals.put(pos, SquareTypes.YELLOW_GOAL);
            case CYAN -> goals.put(pos, SquareTypes.CYAN_GOAL);
            default -> throw new IllegalArgumentException("Invalid square type for a goal: " + squareType);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof GameBoard that)) return false;
        return Objects.equals(goals, that.goals) &&
                Objects.equals(players, that.players);
    }

    @Override
    public int hashCode() {
        return Objects.hash(players , goals);
    }
}
