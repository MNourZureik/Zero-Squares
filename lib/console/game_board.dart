import 'dart:collection';
import 'package:zero_squares/Constants/position.dart';
import 'package:zero_squares/Constants/square_types.dart';

class GameBoard {
  HashMap<Position, SquareTypes> boardMap;
  HashMap<Position, SquareTypes> goals;

  GameBoard(this.boardMap, this.goals);
  SquareTypes getSquareType(Position pos) {
    return boardMap[pos] ?? goals[pos] ?? SquareTypes.NULL;
  }

  void setSquare(Position pos, SquareTypes squareType) {
    boardMap[pos] = squareType;
  }

  void clearBoard() {
    boardMap.clear();
    goals.clear();
  }

  HashMap<Position, SquareTypes> getBoardMap() {
    return boardMap;
  }

  HashMap<Position, SquareTypes> getGoalsMap() {
    return goals;
  }

  void resetGoals() {
    for (var pos in goals.keys) {
      if (boardMap[pos] == SquareTypes.EMPTY) {
        boardMap[pos] = goals[pos]!;
      }
    }
  }

  void removeGoal(Position pos) {
    setSquare(pos, SquareTypes.EMPTY);
    goals.remove(pos);
  }

  void setGoal(Position pos, SquareTypes squareType) {
    if (squareType == SquareTypes.RED) {
      goals[pos] = SquareTypes.RED_GOAL;
      return;
    }
    if (squareType == SquareTypes.BLUE) {
      goals[pos] = SquareTypes.BLUE_GOAL;
      return;
    }
    if (squareType == SquareTypes.PINK) {
      goals[pos] = SquareTypes.PINK_GOAL;
      return;
    }
    if (squareType == SquareTypes.YELLOW) {
      goals[pos] = SquareTypes.YELLOW_GOAL;
      return;
    }
    if (squareType == SquareTypes.CYAN) {
      goals[pos] = SquareTypes.CYAN_GOAL;
      return;
    }
  }

  static GameBoard getDeepCopy(GameBoard originalGameBoard) {
    // Get the original board and goals data
    var originalBoardMap = originalGameBoard.getBoardMap();
    var originalGoalsMap = originalGameBoard.getGoalsMap();

    // Create deep copies of board and goals to ensure no references are shared
    HashMap<Position, SquareTypes> newBoardMap = HashMap();
    HashMap<Position, SquareTypes> newGoalsMap = HashMap();

    // Deep copy each entry to avoid shared references
    originalBoardMap.forEach((key, value) {
      newBoardMap[Position(key.x, key.y)] = value;
    });
    originalGoalsMap.forEach((key, value) {
      newGoalsMap[Position(key.x, key.y)] = value;
    });

    return GameBoard(newBoardMap, newGoalsMap);
  }
}
