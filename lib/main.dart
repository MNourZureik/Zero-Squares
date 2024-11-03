// ignore_for_file: library_private_types_in_public_api, non_constant_identifier_names
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:zero_squares/Constants/boarder_layouts.dart';
import 'package:zero_squares/console/play.dart';
import 'package:zero_squares/widgets/GameBoardWidget.dart';
import 'console/game_board.dart';

void main() {
  runApp(const GameApp());
}

class GameApp extends StatelessWidget {
  const GameApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Zero Squares',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const GameBoardScreen(),
    );
  }
}

class GameBoardScreen extends StatefulWidget {
  const GameBoardScreen({super.key});

  @override
  _GameBoardScreenState createState() => _GameBoardScreenState();
}

class _GameBoardScreenState extends State<GameBoardScreen> {
  late GameBoard? gameBoard;
  int index = 0;

  @override
  void initState() {
    super.initState();
    gameBoard = GameBoard(BoardLayouts.LEVEL_DATA[index]['board']!,
        BoardLayouts.LEVEL_DATA[index]['goals']!);

    // BoardLayouts.getLevel(index);
  }

  void resetLevel() {
    gameBoard = GameBoard(BoardLayouts.LEVEL_DATA[index]['board']!,
        BoardLayouts.LEVEL_DATA[index]['goals']!);

    //BoardLayouts.getLevel(index);
    setState(() {});
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Game Board"),
        actions: [
          IconButton(
            onPressed:
                resetLevel, // Reset the level when this button is pressed
            icon: const Icon(Icons.refresh),
          ),
        ],
      ),
      body: Center(
        child: KeyboardListener(
          focusNode: FocusNode()..requestFocus(),
          onKeyEvent: (KeyEvent event) {
            if (!Play.isAllPlayersWins(gameBoard!)) {
              if (event is KeyDownEvent) {
                switch (event.logicalKey.keyLabel) {
                  case 'Arrow Up':
                    gameBoard = Play.moveUp(gameBoard!);
                    if (gameBoard == null) {
                      resetLevel();
                      break;
                    }
                    setState(() {});
                    break;
                  case 'Arrow Left':
                    gameBoard = Play.moveLeft(gameBoard!);
                    if (gameBoard == null) {
                      resetLevel();
                      break;
                    }
                    setState(() {});
                    break;

                  case 'Arrow Down':
                    gameBoard = Play.moveDown(gameBoard!);
                    if (gameBoard == null) {
                      resetLevel();
                      break;
                    }
                    setState(() {});
                    break;
                  case 'Arrow Right':
                    gameBoard = Play.moveRight(gameBoard!);
                    if (gameBoard == null) {
                      resetLevel();
                      break;
                    }
                    setState(() {});
                    break;
                }
              }
            } else {
              if (index >= BoardLayouts.LEVEL_DATA.length - 1) {
                showDialog(
                  context: context,
                  builder: (ctx) {
                    return const AlertDialog(
                      title: Text("Congratulations!"),
                      content: Text("You have won the game!"),
                    );
                  },
                );
              } else {
                setState(() {
                  index++;
                  gameBoard = GameBoard(
                      BoardLayouts.LEVEL_DATA[index]['board']!,
                      BoardLayouts.LEVEL_DATA[index]['goals']!);
                  // BoardLayouts.getLevel(index); // Load the next level
                });
              }
            }
          },
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              const Text(
                "Use Arrow Keys to Move",
                style: TextStyle(fontSize: 20, fontWeight: FontWeight.bold),
              ),
              const SizedBox(height: 20),
              GameBoardWidget(gameBoard: gameBoard!),
            ],
          ),
        ),
      ),
    );
  }
}
