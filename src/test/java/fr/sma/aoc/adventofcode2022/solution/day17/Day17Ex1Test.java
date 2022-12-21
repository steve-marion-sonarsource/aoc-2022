package fr.sma.aoc.adventofcode2022.solution.day17;

import org.junit.jupiter.api.Test;

class Day17Ex1Test {

  private Day17Ex1 day = new Day17Ex1();

  @Test
  public void testRun() throws Exception {
    String input = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";

    String result = day.run(input);
    System.out.println("result = " + result);
    assert result.equals("3068");
  }

}
