import 'dart:collection';
import 'package:zero_squares/Constants/position.dart';
import 'package:zero_squares/Constants/square_types.dart';

class GameBoard {
  final HashMap<Position, SquareTypes> _board;
  final HashMap<Position, SquareTypes> _goals;

  GameBoard(this._board, this._goals);
  SquareTypes getSquareType(Position pos) {
    return _board[pos] ?? _goals[pos] ?? SquareTypes.NULL;
  }

  void setSquare(Position pos, SquareTypes squareType) {
    _board[pos] = squareType;
  }

  void clearBoard() {
    _board.clear();
    _goals.clear();
  }

  HashMap<Position, SquareTypes> getBoardMap() {
    return _board;
  }

  HashMap<Position, SquareTypes> getGoalsMap() {
    return _goals;
  }

  void resetGoals() {
    for (var pos in _goals.keys) {
      if (_board[pos] == SquareTypes.EMPTY) {
        _board[pos] = _goals[pos]!;
      }
    }
  }

  void removeGoal(Position pos) {
    setSquare(pos, SquareTypes.EMPTY);
    _goals.remove(pos);
  }

  void setGoal(Position pos, SquareTypes squareType) {
    if (squareType == SquareTypes.RED) {
      _goals[pos] = SquareTypes.RED_GOAL;
      return;
    }
    if (squareType == SquareTypes.BLUE) {
      _goals[pos] = SquareTypes.BLUE_GOAL;
      return;
    }
    if (squareType == SquareTypes.PINK) {
      _goals[pos] = SquareTypes.PINK_GOAL;
      return;
    }
    if (squareType == SquareTypes.YELLOW) {
      _goals[pos] = SquareTypes.YELLOW_GOAL;
      return;
    }
    if (squareType == SquareTypes.CYAN) {
      _goals[pos] = SquareTypes.CYAN_GOAL;
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
