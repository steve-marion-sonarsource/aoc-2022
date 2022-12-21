package fr.sma.aoc.adventofcode2022.solution.day17;

enum Piece {
  BAR(new boolean[][]{{true, true, true, true}}),
  CROSS(new boolean[][]{{false, true, false}, {true, true, true}, {false, true, false}}),
  L(new boolean[][]{{false, false, true}, {false, false, true}, {true, true, true}}),
  POLE(new boolean[][]{{true}, {true}, {true}, {true}}),
  SQUARE(new boolean[][]{{true, true}, {true, true}});

  public final boolean[][] shape;

  Piece(boolean[][] shape) {
    this.shape = shape;
  }
}
