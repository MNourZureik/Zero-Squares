// ignore_for_file: non_constant_identifier_names
import 'package:zero_squares/Constants/directions.dart';
import 'package:zero_squares/Constants/helping_functions.dart';
import 'package:zero_squares/Constants/position.dart';
import 'package:zero_squares/Constants/square_types.dart';
import 'package:zero_squares/console/game_board.dart';

class Play {
  static GameBoard? moveUp(GameBoard gameBoard) {
    return move(gameBoard, Directions.UP);
  }

  static GameBoard? moveDown(GameBoard gameBoard) {
    return move(gameBoard, Directions.DOWN);
  }

  static GameBoard? moveLeft(GameBoard gameBoard) {
    return move(gameBoard, Directions.LEFT);
  }

  static GameBoard? moveRight(GameBoard gameBoard) {
    return move(gameBoard, Directions.RIGHT);
  }

  static GameBoard? move(GameBoard originalGameBoard, Directions direction) {
    // Deep Copy :
    GameBoard gameBoard = GameBoard.getDeepCopy(originalGameBoard);

    // find all players positions first :
    List<Position> playersPositions = findPlayersPositions(gameBoard);

    // checking if state plyer empty player and both can move in the same axis to rearrange priority :
    if (isNeedReArrange(gameBoard, playersPositions, direction)) {
      playersPositions = reArrangePlayers(playersPositions, direction);
    }

    List<bool> isValids = [];
    int index = 0;

    for (Position playerPosition in playersPositions) {
      // check if the player can Move or not :
      isValids.add(isValidToMove(gameBoard, playerPosition, direction));
    }

    for (Position playerPosition in playersPositions) {
      // if player can Move :
      if (isValids[index]) {
        // get the final position to this player :
        Position? finalPosition =
            calculateNewPosition(gameBoard, playerPosition, direction);
        // the player hit weak barrier so this {if} will reset the game by returning null :
        if (finalPosition == null) {
          return null;
        }
        // set the new position from EMPTY to PLAYER_TYPE and set the old position to EMPTY:
        gameBoard.setSquare(
            finalPosition, gameBoard.getSquareType(playerPosition));
        gameBoard.setSquare(playerPosition, SquareTypes.EMPTY);
      }
      index++;
    }
    // reset goals to its places passing through it :
    gameBoard.resetGoals();

    return gameBoard;
  }

  static List<Position> findPlayersPositions(GameBoard gameBoard) {
    List<Position> playersPositions = [];

    for (var pos in gameBoard.getBoardMap().keys) {
      SquareTypes squareType = gameBoard.getSquareType(pos);
      if (HelpingFunctions.isPlayerSquare(squareType)) {
        playersPositions.add(pos);
      }
    }
    return playersPositions;
  }

  static Position? calculateNewPosition(
      GameBoard gameBoard, Position pos, Directions direction) {
    // get copy from player pos to change on it :
    Position newPosition = pos;

    // get player type from its position :
    SquareTypes playerType = gameBoard.getSquareType(pos);

    // still moving the player untill :
    // 1.  hit NON-EMPTY SQUARE_TYPE.
    // 2.  hit WEAK_BARRIER.
    // 3.  hit his own goal.
    // 4.  hit COLORABLE type and coloring this cell by the player color.

    while (true) {
      Position nextPosition = getNextPosition(newPosition, direction);
      SquareTypes squareType = gameBoard.getSquareType(nextPosition);

      // 4.  hit COLORABLE type and coloring this cell by the player color.
      if (squareType == SquareTypes.COLORABLE) {
        gameBoard.setGoal(nextPosition, gameBoard.getSquareType(pos));
        newPosition = nextPosition;
        continue;
      }
      // 3.  hit his own goal.
      if (HelpingFunctions.isPlayerWin(pos, nextPosition, gameBoard)) {
        gameBoard.setSquare(pos, SquareTypes.EMPTY);
        gameBoard.removeGoal(nextPosition);
        break;
      }
      // check if next position is goal and if not its own goal let him continue :
      if (HelpingFunctions.isGoalSquare(squareType) &&
          playerType != squareType) {
        newPosition = nextPosition;
        continue;
      }
      // 2.  hit WEAK_BARRIER.
      if (squareType == SquareTypes.WEAK_BARRIER) {
        return null;
      }
      // 1.  hit NON-EMPTY SQUARE_TYPE.
      if (squareType != SquareTypes.EMPTY) {
        break;
      }

      newPosition = nextPosition;
    }
    return newPosition;
  }

  static Position getNextPosition(Position pos, Directions direction) {
    // step 1 cell depends on specific direction :
    switch (direction) {
      case Directions.UP:
        return Position(pos.x - 1, pos.y);
      case Directions.DOWN:
        return Position(pos.x + 1, pos.y);
      case Directions.LEFT:
        return Position(pos.x, pos.y - 1);
      case Directions.RIGHT:
        return Position(pos.x, pos.y + 1);
    }
  }

  static bool isValidToMove(
      GameBoard gameBoard, Position pos, Directions direction) {
    Position nextPosition = getNextPosition(pos, direction);
    SquareTypes squareType = gameBoard.getSquareType(nextPosition);

    // if next position on this direction is Goal it can Move :
    if (HelpingFunctions.isGoalSquare(squareType)) {
      return true;
    }
    // if it is not empty it can not move :
    if (squareType != SquareTypes.EMPTY) {
      return false;
    }
    // not all of previous it can move :
    return true;
  }

  static List<Position> reArrangePlayers(
      List<Position> playersPositions, Directions direction) {
    // reArrange players depends on their position after checking that they really need reArranging :
    playersPositions.sort((a, b) {
      switch (direction) {
        case Directions.LEFT:
          return a.y.compareTo(b.y);
        case Directions.RIGHT:
          return b.y.compareTo(a.y);
        case Directions.UP:
          return a.x.compareTo(b.x);
        case Directions.DOWN:
          return b.x.compareTo(a.x);
      }
    });

    return playersPositions;
  }

  static bool isNeedReArrange(GameBoard gameBoard,
      List<Position> playersPositions, Directions direction) {
    // check axis and the direction is aplicable to state and move ability :
    for (int i = 0; i < playersPositions.length - 1; i++) {
      if (isHaveSameAxis_and_isRightDirection(playersPositions, direction, i) &&
          isValidToMove(gameBoard, playersPositions[i], direction) &&
          isValidToMove(gameBoard, playersPositions[i + 1], direction)) {
        return true;
      }
    }
    return false;
  }

  static bool isHaveSameAxis_and_isRightDirection(
      List<Position> playersPositions, Directions direction, int i) {
    return (playersPositions[i].getX() == playersPositions[i + 1].getX() &&
            (Directions.LEFT == direction || Directions.RIGHT == direction)) ||
        (playersPositions[i].getY() == playersPositions[i + 1].getY() &&
            (Directions.DOWN == direction || Directions.UP == direction));
  }
}
