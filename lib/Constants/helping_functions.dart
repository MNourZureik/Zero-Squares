import 'package:flutter/material.dart';
import 'package:zero_squares/Constants/position.dart';
import 'package:zero_squares/Constants/square_types.dart';
import 'package:zero_squares/console/game_board.dart';

class HelpingFunctions {
  //! helping functions :
  static bool isPlayerWin(Position pos, Position nextPos, GameBoard gameBoard) {
    SquareTypes currentPosType = gameBoard.getSquareType(pos);
    SquareTypes nextPosType = gameBoard.getSquareType(nextPos);

    if (currentPosType == SquareTypes.RED &&
        nextPosType == SquareTypes.RED_GOAL) {
      return true;
    } else if (currentPosType == SquareTypes.BLUE &&
        nextPosType == SquareTypes.BLUE_GOAL) {
      return true;
    } else if (currentPosType == SquareTypes.YELLOW &&
        nextPosType == SquareTypes.YELLOW_GOAL) {
      return true;
    } else if (currentPosType == SquareTypes.PINK &&
        nextPosType == SquareTypes.PINK_GOAL) {
      return true;
    } else if (currentPosType == SquareTypes.CYAN &&
        nextPosType == SquareTypes.CYAN_GOAL) {
      return true;
    } else {
      return false;
    }
  }

  static bool isPlayerSquare(SquareTypes squareType) {
    return squareType == SquareTypes.RED ||
        squareType == SquareTypes.BLUE ||
        squareType == SquareTypes.YELLOW ||
        squareType == SquareTypes.CYAN ||
        squareType == SquareTypes.PINK;
  }

  static bool isGoalSquare(SquareTypes squareType) {
    return squareType == SquareTypes.RED_GOAL ||
        squareType == SquareTypes.BLUE_GOAL ||
        squareType == SquareTypes.YELLOW_GOAL ||
        squareType == SquareTypes.CYAN_GOAL ||
        squareType == SquareTypes.PINK_GOAL ||
        squareType == SquareTypes.COLORABLE;
  }

  static bool isAllPlayersWins(GameBoard gameBoard) {
    // check if any goals left if it is then the game still need to finish if not the game ended and the player win the level :
    for (var position in gameBoard.getGoalsMap().keys) {
      SquareTypes squareType = gameBoard.getSquareType(position);
      if (isGoalSquare(squareType) || isPlayerSquare(squareType)) {
        return false;
      }
    }
    return true;
  }

  static Color? getGoalColor(SquareTypes squareType) {
    switch (squareType) {
      case SquareTypes.RED_GOAL:
        return Colors.red;
      case SquareTypes.BLUE_GOAL:
        return Colors.blue;
      case SquareTypes.YELLOW_GOAL:
        return Colors.yellow;
      case SquareTypes.CYAN_GOAL:
        return Colors.cyan;
      case SquareTypes.PINK_GOAL:
        return Colors.pink;
      case SquareTypes.WEAK_BARRIER:
        return Colors.black;
      case SquareTypes.COLORABLE:
        return Colors.blueGrey[100];
      default:
        return Colors.green;
    }
  }

  static Color getColorForSquare(SquareTypes type) {
    switch (type) {
      case SquareTypes.RED:
        return Colors.red;
      case SquareTypes.BLUE:
        return Colors.blue;
      case SquareTypes.YELLOW:
        return Colors.yellow;
      case SquareTypes.CYAN:
        return Colors.cyan;
      case SquareTypes.PINK:
        return Colors.pink;
      case SquareTypes.BARRIER:
        return Colors.black;
      default:
        return Colors.white;
    }
  }
}
