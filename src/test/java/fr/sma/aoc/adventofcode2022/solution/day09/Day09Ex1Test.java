package fr.sma.aoc.adventofcode2022.solution.day09;

import fr.sma.aoc.adventofcode2022.solution.day08.Day08Ex1;
import org.junit.jupiter.api.Test;

class Day09Ex1Test {

  private Day09Ex1 day = new Day09Ex1();

  @Test
  public void testRun() throws Exception {
    String input = """
      R 4
      U 4
      L 3
      D 1
      R 4
      D 1
      L 5
      R 2""";

    assert day.run(input).equals("13");
  }

}
