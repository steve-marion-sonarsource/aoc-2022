package fr.sma.aoc.adventofcode2022.solution.day08;

import fr.sma.aoc.adventofcode2022.solution.day07.Day07Ex1;
import org.junit.jupiter.api.Test;

class Day08Ex1Test {

  private Day08Ex1 day = new Day08Ex1();

  @Test
  public void testRun() throws Exception {
    String input = """
      30373
      25512
      65332
      33549
      35390""";

    assert day.run(input).equals("21");
  }

}
