// ignore_for_file: file_names

//import 'package:zero_squares/Constants/directions.dart';
import 'package:zero_squares/Constants/directions.dart';
import 'package:zero_squares/console/game_board.dart';
import 'package:zero_squares/console/play.dart';

class Computer {
  static List<GameBoard> nextState(GameBoard gameBoard) {
    List<GameBoard> nextStates = [];

    for (var element in Directions.values) {
      final GameBoard? gameBoardRotate = Play.move(gameBoard, element);
      if (gameBoardRotate != null && gameBoardRotate != gameBoard) {
        nextStates.add(gameBoardRotate);
      }
    }
    return nextStates;
  }
}
