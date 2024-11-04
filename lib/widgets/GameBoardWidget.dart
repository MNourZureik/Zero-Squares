// ignore_for_file: file_names

import 'package:flutter/material.dart';
import 'package:zero_squares/Constants/helping_functions.dart';
import 'package:zero_squares/Constants/position.dart';
import 'package:zero_squares/Constants/square_types.dart';
import 'package:zero_squares/console/game_board.dart';

class GameBoardWidget extends StatelessWidget {
  final GameBoard gameBoard;

  const GameBoardWidget({super.key, required this.gameBoard});

  @override
  Widget build(BuildContext context) {
    const double size = 35;
    int maxX = 0;
    int maxY = 0;
    for (Position pos in gameBoard.getBoardMap().keys) {
      if (pos.getX() > maxX) {
        maxX = pos.getX();
      }
      if (pos.getY() > maxY) {
        maxY = pos.getY();
      }
    }

    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: List.generate(
        maxX + 1,
        (x) {
          return Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: List.generate(
              maxY + 1,
              (y) {
                Position pos = Position(x, y);
                SquareTypes squareType = gameBoard.getSquareType(pos);
                SquareTypes? goalType = gameBoard.getGoalsMap()[pos];

                bool isGoal = goalType != null && goalType != SquareTypes.EMPTY;
                bool isPlay = HelpingFunctions.isPlayerSquare(squareType);

                Color color = isPlay
                    ? HelpingFunctions.getColorForSquare(squareType)
                    : HelpingFunctions.getColorForSquare(goalType ?? squareType);

                return squareType != SquareTypes.NULL
                    ? Container(
                        decoration: BoxDecoration(
                          border: Border.all(
                            color:
                                isGoal ? HelpingFunctions.getGoalColor(goalType)! : color,
                            width: 4,
                          ),
                          color: color,
                        ),
                        width: size,
                        height: size,
                        //  margin: const EdgeInsets.all(2),
                      )
                    : const SizedBox(
                        width: size,
                        height: size,
                      );
              },
            ),
          );
        },
      ),
    );
  }
}
