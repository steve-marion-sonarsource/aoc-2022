package fr.sma.aoc.adventofcode2022.solution.day14;

import org.junit.jupiter.api.Test;

class Day14Ex1Test {

  private Day14Ex1 day = new Day14Ex1();

  @Test
  public void testRun() throws Exception {
    String input = """
      498,4 -> 498,6 -> 496,6
      503,4 -> 502,4 -> 502,9 -> 494,9""";

    assert day.run(input).equals("24");
  }

}
