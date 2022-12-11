package fr.sma.aoc.adventofcode2022.solution.day08;

import org.junit.jupiter.api.Test;

class Day08Ex2Test {

  private Day08Ex2 day = new Day08Ex2();

  @Test
  public void testRun() throws Exception {
    String input = """
      30373
      25512
      65332
      33549
      35390""";

    assert day.run(input).equals("8");
  }

}
