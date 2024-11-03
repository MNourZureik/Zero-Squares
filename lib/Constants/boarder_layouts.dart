// ignore_for_file: non_constant_identifier_names

import 'dart:collection';
import 'position.dart';
import 'square_types.dart';

class BoardLayouts {
  static final List<Map<String, HashMap<Position, SquareTypes>>> LEVEL_DATA = [
    {
      "board": createLevel1(),
      "goals": createLevel1_Goals(),
    },
    {
      "board": createLevel2(),
      "goals": createLevel2_Goals(),
    },
    {
      "board": createLevel6(),
      "goals": createLevel6_Goals(),
    },
    {
      "board": createLevel7(),
      "goals": createLevel7_Goals(),
    },
    {
      "board": createLevel10(),
      "goals": createLevel10_Goals(),
    },
    {
      "board": createLevel11(),
      "goals": createLevel11_Goals(),
    },
    {
      "board": createLevel15(),
      "goals": createLevel15_Goals(),
    },
    {
      "board": createLevel19(),
      "goals": createLevel19_Goals(),
    },
    {
      "board": createLevel20(),
      "goals": createLevel20_Goals(),
    },
    {
      "board": createLevel21(),
      "goals": createLevel21_Goals(),
    },
    {
      "board": createLevel24(),
      "goals": createLevel24_Goals(),
    },
    {
      "board": createLevel26(),
      "goals": createLevel26_Goals(),
    },
    {
      "board": createLevel27(),
      "goals": createLevel27_Goals(),
    },
  ];
  static HashMap<Position, SquareTypes> createLevel1() {
    final HashMap<Position, SquareTypes> board = HashMap();

    setSquare(getPosition(1, 1), SquareTypes.RED, board);

    for (int y = 2; y <= 5; y++) {
      setSquare(getPosition(1, y), SquareTypes.EMPTY, board);
    }

    for (int y = 0; y <= 7; y++) {
      setSquare(getPosition(0, y), SquareTypes.BARRIER, board);
      setSquare(getPosition(2, y), SquareTypes.BARRIER, board);
    }
    setSquare(getPosition(1, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(1, 7), SquareTypes.BARRIER, board);

    return board;
  }

  static HashMap<Position, SquareTypes> createLevel1_Goals() {
    final HashMap<Position, SquareTypes> gaols = HashMap();
    setSquare(getPosition(1, 6), SquareTypes.RED_GOAL, gaols);
    return gaols;
  }

  static HashMap<Position, SquareTypes> createLevel2() {
    final HashMap<Position, SquareTypes> board = HashMap();

    // Row 1:
    for (int y = 0; y <= 7; y++) {
      setSquare(getPosition(0, y), SquareTypes.BARRIER, board);
    }
    // Lonely BARRIER in Row 2:
    setSquare(getPosition(1, 3), SquareTypes.BARRIER, board);

    // Row 2 EMPTY squares:
    for (int y = 1; y <= 6; y++) {
      if (y == 3) continue;
      setSquare(getPosition(1, y), SquareTypes.EMPTY, board);
    }

    // Row 3 EMPTY squares:
    for (int y = 2; y <= 6; y++) {
      setSquare(getPosition(2, y), SquareTypes.EMPTY, board);
    }

    // Row 4 EMPTY squares:
    for (int y = 1; y <= 6; y++) {
      setSquare(getPosition(3, y), SquareTypes.EMPTY, board);
    }

    // Right and left walls BARRIER:
    for (int x = 1; x <= 4; x++) {
      setSquare(getPosition(x, 0), SquareTypes.BARRIER, board);
      setSquare(getPosition(x, 7), SquareTypes.BARRIER, board);
    }

    // Row 5 && 6 BARRIER squares:
    for (int y = 1; y <= 6; y++) {
      if (y == 4) {
        setSquare(getPosition(5, y), SquareTypes.BARRIER, board);
        continue;
      }
      setSquare(getPosition(4, y), SquareTypes.BARRIER, board);
      if (y == 3 || y == 5) {
        setSquare(getPosition(5, y), SquareTypes.BARRIER, board);
      }
    }

    setSquare(getPosition(2, 1), SquareTypes.RED, board);

    return board;
  }

  static HashMap<Position, SquareTypes> createLevel2_Goals() {
    final HashMap<Position, SquareTypes> gaols = HashMap();
    setSquare(getPosition(4, 4), SquareTypes.RED_GOAL, gaols);
    return gaols;
  }

  static HashMap<Position, SquareTypes> createLevel6() {
    final HashMap<Position, SquareTypes> board = HashMap();

    // Row 1 :
    for (int y = 0; y <= 6; y++) {
      setSquare(getPosition(0, y), SquareTypes.BARRIER, board);
    }

    // Row 2 :
    setSquare(getPosition(1, 0), SquareTypes.BARRIER, board);
    for (var y = 2; y <= 4; y++) {
      setSquare(getPosition(1, y), SquareTypes.EMPTY, board);
    }
    setSquare(getPosition(1, 5), SquareTypes.BARRIER, board);
    setSquare(getPosition(1, 6), SquareTypes.BARRIER, board);

    // Row 3 :
    setSquare(getPosition(2, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 1), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 2), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 4), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 5), SquareTypes.EMPTY, board);
    for (var y = 6; y <= 8; y++) {
      setSquare(getPosition(2, y), SquareTypes.BARRIER, board);
    }

    // Row 4 :
    setSquare(getPosition(3, 0), SquareTypes.BARRIER, board);
    for (var y = 1; y <= 5; y++) {
      setSquare(getPosition(3, y), SquareTypes.EMPTY, board);
    }
    setSquare(getPosition(3, 6), SquareTypes.BLUE, board);
    setSquare(getPosition(3, 7), SquareTypes.RED, board);
    setSquare(getPosition(3, 8), SquareTypes.BARRIER, board);

    // Row 5 :
    for (var y = 0; y <= 8; y++) {
      setSquare(getPosition(4, y), SquareTypes.BARRIER, board);
    }
    return board;
  }

  static HashMap<Position, SquareTypes> createLevel6_Goals() {
    final HashMap<Position, SquareTypes> gaols = HashMap();
    setSquare(getPosition(1, 1), SquareTypes.RED_GOAL, gaols);
    setSquare(getPosition(2, 3), SquareTypes.BLUE_GOAL, gaols);
    return gaols;
  }

  static HashMap<Position, SquareTypes> createLevel7() {
    final HashMap<Position, SquareTypes> board = HashMap();
    // Row 1 :
    for (var i = 4; i <= 8; i++) {
      setSquare(getPosition(0, i), SquareTypes.BARRIER, board);
    }
    // Row 2 :
    setSquare(getPosition(1, 4), SquareTypes.BARRIER, board);
    setSquare(getPosition(1, 8), SquareTypes.BARRIER, board);
    for (var i = 5; i <= 7; i++) {
      setSquare(getPosition(1, i), SquareTypes.EMPTY, board);
    }
    // Row 3 :
    for (var i = 0; i <= 8; i++) {
      if (i == 5) continue;
      if (i == 7) {
        setSquare(getPosition(2, i), SquareTypes.EMPTY, board);
        continue;
      }
      setSquare(getPosition(2, i), SquareTypes.BARRIER, board);
    }

    // Row 4 :
    setSquare(getPosition(3, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(3, 8), SquareTypes.BARRIER, board);
    setSquare(getPosition(3, 6), SquareTypes.BARRIER, board);
    for (var i = 1; i <= 7; i++) {
      if (i == 4) {
        setSquare(getPosition(3, i), SquareTypes.RED, board);
        continue;
      }
      if (i == 6) continue;
      setSquare(getPosition(3, i), SquareTypes.EMPTY, board);
    }
    // Row 5 :
    setSquare(getPosition(4, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(4, 8), SquareTypes.BARRIER, board);
    setSquare(getPosition(4, 6), SquareTypes.BARRIER, board);
    setSquare(getPosition(4, 4), SquareTypes.BARRIER, board);

    setSquare(getPosition(4, 1), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 2), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 5), SquareTypes.BLUE, board);
    setSquare(getPosition(4, 7), SquareTypes.EMPTY, board);
    // Row 6 :
    for (var i = 5; i <= 7; i++) {
      setSquare(getPosition(i, 0), SquareTypes.BARRIER, board);
      setSquare(getPosition(i, 8), SquareTypes.BARRIER, board);
    }
    for (var i = 1; i <= 7; i++) {
      setSquare(getPosition(5, i), SquareTypes.EMPTY, board);
      setSquare(getPosition(6, i), SquareTypes.EMPTY, board);
      setSquare(getPosition(7, i), SquareTypes.BARRIER, board);
    }

    return board;
  }

  static HashMap<Position, SquareTypes> createLevel7_Goals() {
    final HashMap<Position, SquareTypes> gaols = HashMap();
    setSquare(getPosition(2, 5), SquareTypes.RED_GOAL, gaols);
    setSquare(getPosition(4, 3), SquareTypes.BLUE_GOAL, gaols);
    return gaols;
  }

  static HashMap<Position, SquareTypes> createLevel10() {
    final HashMap<Position, SquareTypes> board = HashMap();

    // Row 1 :
    for (int y = 2; y <= 9; y++) {
      setSquare(getPosition(0, y), SquareTypes.BARRIER, board);
    }

    // Row 2 :
    for (int y = 1; y <= 10; y++) {
      if (y < 9 && y > 2) {
        setSquare(getPosition(1, y), SquareTypes.EMPTY, board);
        continue;
      }
      setSquare(getPosition(1, y), SquareTypes.BARRIER, board);
    }

    // Row 3 :
    setSquare(getPosition(2, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 1), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 2), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 3), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 4), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 5), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 7), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 8), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 9), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 10), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 11), SquareTypes.BARRIER, board);

    // Row 4 :
    for (int x = 3; x <= 7; x++) {
      setSquare(getPosition(x, 0), SquareTypes.BARRIER, board);
      setSquare(getPosition(x, 11), SquareTypes.BARRIER, board);
    }
    setSquare(getPosition(3, 1), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 2), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 3), SquareTypes.BARRIER, board);
    setSquare(getPosition(3, 4), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 6), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 7), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 8), SquareTypes.BARRIER, board);
    setSquare(getPosition(3, 9), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 10), SquareTypes.EMPTY, board);

    // Row 5 :
    setSquare(getPosition(4, 1), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 2), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 3), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 4), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 5), SquareTypes.BARRIER, board);
    setSquare(getPosition(4, 6), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 7), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 8), SquareTypes.BARRIER, board);
    setSquare(getPosition(4, 9), SquareTypes.BARRIER, board);
    setSquare(getPosition(4, 10), SquareTypes.EMPTY, board);

    // Row 6 :
    setSquare(getPosition(5, 1), SquareTypes.RED, board);
    setSquare(getPosition(5, 2), SquareTypes.BARRIER, board);
    setSquare(getPosition(5, 3), SquareTypes.EMPTY, board);
    setSquare(getPosition(5, 4), SquareTypes.BARRIER, board);
    setSquare(getPosition(5, 5), SquareTypes.BARRIER, board);
    setSquare(getPosition(5, 6), SquareTypes.EMPTY, board);
    setSquare(getPosition(5, 7), SquareTypes.EMPTY, board);
    setSquare(getPosition(5, 8), SquareTypes.EMPTY, board);
    setSquare(getPosition(5, 9), SquareTypes.BARRIER, board);
    setSquare(getPosition(5, 10), SquareTypes.BLUE, board);

    // Row 7 :
    for (var i = 1; i <= 10; i++) {
      if (i == 4) {
        setSquare(getPosition(6, i), SquareTypes.BARRIER, board);
        continue;
      }
      setSquare(getPosition(6, i), SquareTypes.EMPTY, board);
    }

    // Row 8 :
    for (var i = 1; i <= 10; i++) {
      setSquare(getPosition(7, i), SquareTypes.BARRIER, board);
    }
    return board;
  }

  static HashMap<Position, SquareTypes> createLevel10_Goals() {
    final HashMap<Position, SquareTypes> goals = HashMap();

    setSquare(getPosition(3, 5), SquareTypes.RED_GOAL, goals);
    setSquare(getPosition(2, 6), SquareTypes.BLUE_GOAL, goals);

    return goals;
  }

  static HashMap<Position, SquareTypes> createLevel11() {
    final HashMap<Position, SquareTypes> board = HashMap();

    // Row 1 :
    for (int y = 1; y <= 3; y++) {
      setSquare(getPosition(0, y), SquareTypes.BARRIER, board);
    }

    // Row 2 :
    for (var i = 0; i <= 7; i++) {
      if (i == 2) {
        continue;
      }
      setSquare(getPosition(1, i), SquareTypes.BARRIER, board);
    }

    // Row 3 :
    setSquare(getPosition(2, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 1), SquareTypes.YELLOW, board);
    setSquare(getPosition(2, 2), SquareTypes.RED, board);
    setSquare(getPosition(2, 3), SquareTypes.BLUE, board);
    setSquare(getPosition(2, 4), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 5), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 6), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 7), SquareTypes.BARRIER, board);

    // Row 4 :
    for (var i = 0; i <= 7; i++) {
      if (i == 1 || i == 3) continue;
      setSquare(getPosition(3, i), SquareTypes.BARRIER, board);
    }
    for (var i = 0; i <= 4; i++) {
      setSquare(getPosition(4, i), SquareTypes.BARRIER, board);
    }

    return board;
  }

  static HashMap<Position, SquareTypes> createLevel11_Goals() {
    final HashMap<Position, SquareTypes> goals = HashMap();

    // Define goals for each player color
    setSquare(getPosition(1, 2), SquareTypes.YELLOW_GOAL, goals);
    setSquare(getPosition(3, 1), SquareTypes.BLUE_GOAL, goals);
    setSquare(getPosition(3, 3), SquareTypes.RED_GOAL, goals);

    return goals;
  }

  static HashMap<Position, SquareTypes> createLevel15() {
    final HashMap<Position, SquareTypes> board = HashMap();
    // Row 1 :
    for (int y = 1; y <= 7; y++) {
      setSquare(getPosition(0, y), SquareTypes.BARRIER, board);
    }
    // Row 2 :
    setSquare(getPosition(1, 1), SquareTypes.BARRIER, board);
    setSquare(getPosition(1, 7), SquareTypes.BARRIER, board);
    for (var i = 2; i <= 6; i++) {
      setSquare(getPosition(1, i), SquareTypes.EMPTY, board);
    }
    // Row 3 :
    setSquare(getPosition(2, 1), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 2), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 4), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 5), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 6), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 7), SquareTypes.BARRIER, board);

    // Row 4 :
    for (var i = 0; i <= 7; i++) {
      if (i == 2 || i == 6) {
        setSquare(getPosition(3, i), SquareTypes.EMPTY, board);
        continue;
      }
      setSquare(getPosition(3, i), SquareTypes.BARRIER, board);
    }

    // Row 5 :
    setSquare(getPosition(4, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(4, 1), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 2), SquareTypes.YELLOW, board);
    setSquare(getPosition(4, 3), SquareTypes.RED, board);
    setSquare(getPosition(4, 4), SquareTypes.BLUE, board);
    setSquare(getPosition(4, 5), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 6), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 7), SquareTypes.BARRIER, board);

    // Row 6 :
    for (var i = 0; i <= 7; i++) {
      if (i == 1 || i == 6) {
        setSquare(getPosition(5, i), SquareTypes.EMPTY, board);
        continue;
      }
      setSquare(getPosition(5, i), SquareTypes.BARRIER, board);
    }

    // Row 7 :
    setSquare(getPosition(6, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(6, 1), SquareTypes.EMPTY, board);
    setSquare(getPosition(6, 2), SquareTypes.BARRIER, board);
    setSquare(getPosition(6, 3), SquareTypes.EMPTY, board);
    setSquare(getPosition(6, 5), SquareTypes.BARRIER, board);
    setSquare(getPosition(6, 7), SquareTypes.BARRIER, board);

    // Row 8 :
    setSquare(getPosition(7, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(7, 7), SquareTypes.BARRIER, board);
    for (var i = 1; i <= 6; i++) {
      setSquare(getPosition(7, i), SquareTypes.EMPTY, board);
    }

    // Row 9 :
    for (int y = 0; y <= 7; y++) {
      setSquare(getPosition(8, y), SquareTypes.BARRIER, board);
    }
    return board;
  }

  static HashMap<Position, SquareTypes> createLevel15_Goals() {
    final HashMap<Position, SquareTypes> goals = HashMap();
    setSquare(getPosition(2, 3), SquareTypes.BLUE_GOAL, goals);
    setSquare(getPosition(6, 6), SquareTypes.RED_GOAL, goals);
    setSquare(getPosition(6, 4), SquareTypes.YELLOW_GOAL, goals);
    return goals;
  }

  static HashMap<Position, SquareTypes> createLevel19() {
    final HashMap<Position, SquareTypes> board = HashMap();
    // Row 1 :
    setSquare(getPosition(0, 1), SquareTypes.BARRIER, board);
    setSquare(getPosition(0, 2), SquareTypes.BARRIER, board);
    setSquare(getPosition(0, 3), SquareTypes.BARRIER, board);

    // Row 2 :
    for (var i = 0; i <= 4; i++) {
      if (i == 2) continue;
      setSquare(getPosition(1, i), SquareTypes.BARRIER, board);
    }

    // Row 3 :
    setSquare(getPosition(2, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 1), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 2), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 3), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 4), SquareTypes.BARRIER, board);

    // Row 4 :
    for (var i = 0; i <= 7; i++) {
      if (i == 3) {
        setSquare(getPosition(3, i), SquareTypes.EMPTY, board);
        continue;
      }
      setSquare(getPosition(3, i), SquareTypes.BARRIER, board);
    }

    // Row 5 :
    setSquare(getPosition(4, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(4, 1), SquareTypes.BLUE, board);
    setSquare(getPosition(4, 2), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 3), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 4), SquareTypes.YELLOW, board);
    setSquare(getPosition(4, 5), SquareTypes.BARRIER, board);
    setSquare(getPosition(4, 6), SquareTypes.BARRIER, board);
    setSquare(getPosition(4, 7), SquareTypes.BARRIER, board);

    // Row 6 :
    setSquare(getPosition(5, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(5, 1), SquareTypes.BARRIER, board);
    setSquare(getPosition(5, 2), SquareTypes.EMPTY, board);
    setSquare(getPosition(5, 3), SquareTypes.EMPTY, board);
    setSquare(getPosition(5, 4), SquareTypes.EMPTY, board);
    setSquare(getPosition(5, 5), SquareTypes.EMPTY, board);
    setSquare(getPosition(5, 6), SquareTypes.EMPTY, board);
    setSquare(getPosition(5, 7), SquareTypes.BARRIER, board);

    // Row 7 :
    setSquare(getPosition(6, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(6, 1), SquareTypes.RED, board);
    setSquare(getPosition(6, 2), SquareTypes.EMPTY, board);
    setSquare(getPosition(6, 3), SquareTypes.BARRIER, board);
    setSquare(getPosition(6, 5), SquareTypes.EMPTY, board);
    setSquare(getPosition(6, 6), SquareTypes.CYAN, board);
    setSquare(getPosition(6, 7), SquareTypes.BARRIER, board);

    // Row 8 :
    for (var i = 0; i <= 7; i++) {
      if (i == 4) {
        setSquare(getPosition(7, i), SquareTypes.EMPTY, board);
        continue;
      }
      setSquare(getPosition(7, i), SquareTypes.BARRIER, board);
    }

    // ROW 9 :
    setSquare(getPosition(8, 3), SquareTypes.BARRIER, board);
    setSquare(getPosition(8, 4), SquareTypes.EMPTY, board);
    setSquare(getPosition(8, 6), SquareTypes.EMPTY, board);
    setSquare(getPosition(8, 7), SquareTypes.BARRIER, board);

    // Row 10 :
    setSquare(getPosition(9, 3), SquareTypes.BARRIER, board);
    setSquare(getPosition(9, 4), SquareTypes.EMPTY, board);
    setSquare(getPosition(9, 5), SquareTypes.EMPTY, board);
    setSquare(getPosition(9, 6), SquareTypes.EMPTY, board);
    setSquare(getPosition(9, 7), SquareTypes.BARRIER, board);

    // Row 11 :
    for (var i = 3; i <= 7; i++) {
      if (i == 5) continue;
      setSquare(getPosition(10, i), SquareTypes.BARRIER, board);
    }

    // Row 12 :
    for (var i = 4; i <= 6; i++) {
      setSquare(getPosition(11, i), SquareTypes.BARRIER, board);
    }
    return board;
  }

  static HashMap<Position, SquareTypes> createLevel19_Goals() {
    final HashMap<Position, SquareTypes> goals = HashMap();
    setSquare(getPosition(8, 5), SquareTypes.BLUE_GOAL, goals);
    setSquare(getPosition(6, 4), SquareTypes.RED_GOAL, goals);
    setSquare(getPosition(1, 2), SquareTypes.YELLOW_GOAL, goals);
    setSquare(getPosition(10, 5), SquareTypes.CYAN_GOAL, goals);
    return goals;
  }

  static HashMap<Position, SquareTypes> createLevel20() {
    final HashMap<Position, SquareTypes> board = HashMap();
    // Row 1 :
    setSquare(getPosition(0, 3), SquareTypes.BARRIER, board);
    setSquare(getPosition(0, 4), SquareTypes.BARRIER, board);
    setSquare(getPosition(0, 5), SquareTypes.BARRIER, board);
    setSquare(getPosition(0, 6), SquareTypes.BARRIER, board);

    // Row 2 :
    for (var i = 0; i <= 8; i++) {
      if (i == 4) {
        setSquare(getPosition(1, i), SquareTypes.RED, board);
        continue;
      }
      if (i == 5) continue;
      setSquare(getPosition(1, i), SquareTypes.BARRIER, board);
    }

    // Row 3 :
    setSquare(getPosition(2, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 2), SquareTypes.PINK, board);
    setSquare(getPosition(2, 3), SquareTypes.BLUE, board);
    setSquare(getPosition(2, 4), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 5), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 6), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 7), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 8), SquareTypes.BARRIER, board);

    // Row 4 :
    setSquare(getPosition(3, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(3, 1), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 2), SquareTypes.CYAN, board);
    setSquare(getPosition(3, 3), SquareTypes.BARRIER, board);
    setSquare(getPosition(3, 5), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 6), SquareTypes.BARRIER, board);
    setSquare(getPosition(3, 7), SquareTypes.YELLOW, board);
    setSquare(getPosition(3, 8), SquareTypes.BARRIER, board);

    // Row 5 :
    for (var i = 0; i <= 8; i++) {
      setSquare(getPosition(4, i), SquareTypes.BARRIER, board);
    }
    return board;
  }

  static HashMap<Position, SquareTypes> createLevel20_Goals() {
    final HashMap<Position, SquareTypes> goals = HashMap();
    setSquare(getPosition(3, 4), SquareTypes.RED_GOAL, goals);
    setSquare(getPosition(1, 5), SquareTypes.YELLOW_GOAL, goals);
    setSquare(getPosition(2, 1), SquareTypes.BLUE_GOAL, goals);
    setSquare(getPosition(3, 7), SquareTypes.CYAN_GOAL, goals);
    setSquare(getPosition(3, 2), SquareTypes.PINK_GOAL, goals);
    return goals;
  }

  static HashMap<Position, SquareTypes> createLevel21() {
    final HashMap<Position, SquareTypes> board = HashMap();
    // Row 1 :
    for (var i = 0; i <= 8; i++) {
      setSquare(getPosition(0, i), SquareTypes.BARRIER, board);
      setSquare(getPosition(5, i), SquareTypes.BARRIER, board);
    }

    // Row 2 :
    setSquare(getPosition(1, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(1, 1), SquareTypes.RED, board);
    for (var i = 2; i <= 7; i++) {
      setSquare(getPosition(1, i), SquareTypes.EMPTY, board);
    }

    // Row 3 :
    for (var i = 0; i <= 8; i++) {
      if (i == 1 || i == 7) {
        setSquare(getPosition(2, i), SquareTypes.EMPTY, board);
        setSquare(getPosition(3, i), SquareTypes.EMPTY, board);
        continue;
      }
      setSquare(getPosition(2, i), SquareTypes.BARRIER, board);
      setSquare(getPosition(3, i), SquareTypes.BARRIER, board);
    }

    // Row 4 :
    setSquare(getPosition(4, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(4, 8), SquareTypes.BARRIER, board);
    for (var i = 1; i <= 6; i++) {
      setSquare(getPosition(4, i), SquareTypes.EMPTY, board);
    }
    return board;
  }

  static HashMap<Position, SquareTypes> createLevel21_Goals() {
    final HashMap<Position, SquareTypes> goals = HashMap();
    setSquare(getPosition(4, 7), SquareTypes.RED_GOAL, goals);
    setSquare(getPosition(1, 8), SquareTypes.WEAK_BARRIER, goals);
    return goals;
  }

  static HashMap<Position, SquareTypes> createLevel24() {
    final HashMap<Position, SquareTypes> board = HashMap();
    // Row 1 :
    for (var i = 1; i <= 8; i++) {
      setSquare(getPosition(0, i), SquareTypes.BARRIER, board);
    }

    // Row 2 :
    setSquare(getPosition(1, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(1, 1), SquareTypes.BARRIER, board);
    for (var i = 3; i <= 7; i++) {
      setSquare(getPosition(1, i), SquareTypes.EMPTY, board);
    }
    setSquare(getPosition(1, 8), SquareTypes.BARRIER, board);
    setSquare(getPosition(1, 9), SquareTypes.BARRIER, board);

    // Row 3 :
    setSquare(getPosition(2, 2), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 3), SquareTypes.YELLOW, board);
    setSquare(getPosition(2, 4), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 5), SquareTypes.BLUE, board);
    setSquare(getPosition(2, 6), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 7), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 8), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 9), SquareTypes.BARRIER, board);

    // Row 4 :
    setSquare(getPosition(3, 1), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 2), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 3), SquareTypes.BARRIER, board);
    setSquare(getPosition(3, 4), SquareTypes.BARRIER, board);
    setSquare(getPosition(3, 5), SquareTypes.BARRIER, board);
    setSquare(getPosition(3, 6), SquareTypes.BARRIER, board);
    setSquare(getPosition(3, 7), SquareTypes.BARRIER, board);
    setSquare(getPosition(3, 8), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 9), SquareTypes.BARRIER, board);

    // Row 5 :
    setSquare(getPosition(4, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(4, 1), SquareTypes.RED, board);
    setSquare(getPosition(4, 2), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 3), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 4), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 5), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 7), SquareTypes.BARRIER, board);
    setSquare(getPosition(4, 8), SquareTypes.EMPTY, board);
    setSquare(getPosition(4, 9), SquareTypes.BARRIER, board);

    // Row 6 :
    setSquare(getPosition(5, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(5, 1), SquareTypes.BARRIER, board);
    for (var i = 2; i <= 8; i++) {
      setSquare(getPosition(5, i), SquareTypes.EMPTY, board);
    }
    setSquare(getPosition(5, 9), SquareTypes.BARRIER, board);

    // Row 7 :
    for (var i = 1; i <= 9; i++) {
      setSquare(getPosition(6, i), SquareTypes.BARRIER, board);
    }

    return board;
  }

  static HashMap<Position, SquareTypes> createLevel24_Goals() {
    final HashMap<Position, SquareTypes> goals = HashMap();
    setSquare(getPosition(2, 0), SquareTypes.WEAK_BARRIER, goals);
    setSquare(getPosition(3, 0), SquareTypes.WEAK_BARRIER, goals);
    setSquare(getPosition(2, 1), SquareTypes.RED_GOAL, goals);
    setSquare(getPosition(1, 2), SquareTypes.YELLOW_GOAL, goals);
    setSquare(getPosition(4, 6), SquareTypes.BLUE_GOAL, goals);
    return goals;
  }

  static HashMap<Position, SquareTypes> createLevel26() {
    final HashMap<Position, SquareTypes> board = HashMap();
    setSquare(getPosition(0, 8), SquareTypes.BARRIER, board);
    setSquare(getPosition(0, 9), SquareTypes.BARRIER, board);
    setSquare(getPosition(0, 10), SquareTypes.BARRIER, board);
    setSquare(getPosition(0, 11), SquareTypes.BARRIER, board);

    // Row 2 :
    setSquare(getPosition(1, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(1, 1), SquareTypes.BARRIER, board);
    setSquare(getPosition(1, 2), SquareTypes.BARRIER, board);
    setSquare(getPosition(1, 3), SquareTypes.BARRIER, board);
    setSquare(getPosition(1, 4), SquareTypes.BARRIER, board);
    setSquare(getPosition(1, 7), SquareTypes.BARRIER, board);
    setSquare(getPosition(1, 8), SquareTypes.BARRIER, board);
    setSquare(getPosition(1, 9), SquareTypes.EMPTY, board);
    setSquare(getPosition(1, 11), SquareTypes.BARRIER, board);

    // Row 3 :
    setSquare(getPosition(2, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 4), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 5), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 6), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 7), SquareTypes.BARRIER, board);
    setSquare(getPosition(2, 8), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 9), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 10), SquareTypes.EMPTY, board);
    setSquare(getPosition(2, 11), SquareTypes.BARRIER, board);

    // Row 4 :
    setSquare(getPosition(3, 0), SquareTypes.BARRIER, board);
    setSquare(getPosition(3, 1), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 2), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 3), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 4), SquareTypes.CYAN, board);
    setSquare(getPosition(3, 5), SquareTypes.PINK, board);
    setSquare(getPosition(3, 6), SquareTypes.YELLOW, board);
    setSquare(getPosition(3, 7), SquareTypes.RED, board);
    setSquare(getPosition(3, 8), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 9), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 10), SquareTypes.EMPTY, board);
    setSquare(getPosition(3, 11), SquareTypes.BARRIER, board);

    // Row 5 :
    for (var i = 0; i <= 11; i++) {
      setSquare(getPosition(4, i), SquareTypes.BARRIER, board);
    }
    return board;
  }

  static HashMap<Position, SquareTypes> createLevel26_Goals() {
    final HashMap<Position, SquareTypes> goals = HashMap();
    setSquare(getPosition(2, 1), SquareTypes.COLORABLE, goals);
    setSquare(getPosition(2, 2), SquareTypes.CYAN_GOAL, goals);
    setSquare(getPosition(2, 3), SquareTypes.YELLOW_GOAL, goals);
    setSquare(getPosition(1, 10), SquareTypes.PINK_GOAL, goals);
    return goals;
  }

  static HashMap<Position, SquareTypes> createLevel27() {
    final HashMap<Position, SquareTypes> board = HashMap();
    for (var i = 0; i <= 8; i++) {
      setSquare(getPosition(0, i), SquareTypes.BARRIER, board);
    }

    // Row 2 :
    for (var i = 2; i <= 7; i++) {
      setSquare(getPosition(1, i), SquareTypes.EMPTY, board);
    }
    setSquare(getPosition(1, 8), SquareTypes.BARRIER, board);

    // Row 3  :
    setSquare(getPosition(2, 0), SquareTypes.BARRIER, board);
    for (var i = 1; i <= 7; i++) {
      setSquare(getPosition(2, i), SquareTypes.EMPTY, board);
    }
    setSquare(getPosition(2, 8), SquareTypes.BARRIER, board);

    // Row 4 :
    setSquare(getPosition(3, 0), SquareTypes.BARRIER, board);
    for (var i = 1; i <= 7; i++) {
      setSquare(getPosition(3, i), SquareTypes.EMPTY, board);
    }
    for (var i = 8; i <= 10; i++) {
      setSquare(getPosition(3, i), SquareTypes.BARRIER, board);
    }

    // Row 5 :
    setSquare(getPosition(4, 0), SquareTypes.BARRIER, board);
    for (var i = 1; i <= 9; i++) {
      setSquare(getPosition(4, i), SquareTypes.EMPTY, board);
    }
    setSquare(getPosition(4, 10), SquareTypes.BARRIER, board);

    // Row 6 :
    setSquare(getPosition(5, 0), SquareTypes.BARRIER, board);
    for (var i = 1; i <= 9; i++) {
      if (i == 8) {
        setSquare(getPosition(5, i), SquareTypes.BARRIER, board);
        continue;
      }
      setSquare(getPosition(5, i), SquareTypes.EMPTY, board);
    }
    setSquare(getPosition(5, 10), SquareTypes.BARRIER, board);

    // Row 7 :
    for (var i = 0; i <= 10; i++) {
      if (i == 3 || i == 7 || i == 9) {
        setSquare(getPosition(6, i), SquareTypes.EMPTY, board);
        continue;
      }
      setSquare(getPosition(6, i), SquareTypes.BARRIER, board);
    }

    // Row 8  :
    for (var i = 2; i <= 10; i++) {
      if (i == 2 || i == 10) {
        setSquare(getPosition(7, i), SquareTypes.BARRIER, board);
        continue;
      }
      if (i == 7) {
        setSquare(getPosition(7, i), SquareTypes.RED, board);
        continue;
      }
      setSquare(getPosition(7, i), SquareTypes.EMPTY, board);
    }
    // Row 9 :
    for (var i = 2; i <= 10; i++) {
      setSquare(getPosition(8, i), SquareTypes.BARRIER, board);
    }
    return board;
  }

  static HashMap<Position, SquareTypes> createLevel27_Goals() {
    final HashMap<Position, SquareTypes> goals = HashMap();
    setSquare(getPosition(1, 0), SquareTypes.WEAK_BARRIER, goals);
    setSquare(getPosition(1, 1), SquareTypes.COLORABLE, goals);
    return goals;
  }

  static void setSquare(Position pos, SquareTypes squareType,
      HashMap<Position, SquareTypes> board) {
    board[pos] = squareType;
  }

  static Position getPosition(int x, int y) {
    return Position(x, y);
  }

  // static GameBoard getLevel(int index) {
  //   // Get the original board and goals data
  //   var originalBoardMap = LEVEL_DATA[index]["board"]!;
  //   var originalGoalsMap = LEVEL_DATA[index]["goals"]!;

  //   // Create deep copies of board and goals to ensure no references are shared
  //   HashMap<Position, SquareTypes> newBoardMap = HashMap();
  //   HashMap<Position, SquareTypes> newGoalsMap = HashMap();

  //   // Deep copy each entry to avoid shared references
  //   originalBoardMap.forEach((key, value) {
  //     newBoardMap[Position(key.x, key.y)] = value;
  //   });
  //   originalGoalsMap.forEach((key, value) {
  //     newGoalsMap[Position(key.x, key.y)] = value;
  //   });

  //   return GameBoard(newBoardMap, newGoalsMap);
  // }


}
