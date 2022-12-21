package fr.sma.aoc.adventofcode2022.solution.day17;

import org.junit.jupiter.api.Test;

class Day17Ex2Test {

  private Day17Ex2 day = new Day17Ex2();

  @Test
  public void testRun() throws Exception {
    String input = ">>><<><>><<<>><>>><<<>>><<<><<<>><>><<>>";

    String result = day.run(input);
    System.out.println("result = " + result);
    assert result.equals("1514285714288");
  }

}
