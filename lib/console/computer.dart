// ignore_for_file: file_names

//import 'package:zero_squares/Constants/directions.dart';
import 'package:zero_squares/console/game_board.dart';
import 'package:zero_squares/console/play.dart';

class Computer {
  static List<GameBoard> nextState(GameBoard gameBoard) {
    List<GameBoard> nextStates = [];
    final GameBoard? gameBoardRight = Play.moveRight(gameBoard);
    if (gameBoardRight != null && gameBoardRight != gameBoard) {
      nextStates.add(gameBoardRight);
    }
    final GameBoard? gameBoardLeft = Play.moveLeft(gameBoard);
    if (gameBoardLeft != null && gameBoardLeft != gameBoard) {
      nextStates.add(gameBoardLeft);
    }
    final GameBoard? gameBoardDown = Play.moveDown(gameBoard);
    if (gameBoardDown != null && gameBoardDown != gameBoard) {
      nextStates.add(gameBoardDown);
    }
    final GameBoard? gameBoardUp = Play.moveUp(gameBoard);
    if (gameBoardUp != null && gameBoardUp != gameBoard) {
      nextStates.add(gameBoardUp);
    }
    return nextStates;
  }
}
