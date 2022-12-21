package fr.sma.aoc.adventofcode2022.solution.day18;

import org.junit.jupiter.api.Test;

class Day18Ex2Test {

  private Day18Ex2 day = new Day18Ex2();

  @Test
  public void testRun() throws Exception {
    String input = """
      2,2,2
      1,2,2
      3,2,2
      2,1,2
      2,3,2
      2,2,1
      2,2,3
      2,2,4
      2,2,6
      1,2,5
      3,2,5
      2,1,5
      2,3,5""";

    assert day.run(input).equals("58");
  }

}
