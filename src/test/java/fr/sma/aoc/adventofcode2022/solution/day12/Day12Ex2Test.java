package fr.sma.aoc.adventofcode2022.solution.day12;

import org.junit.jupiter.api.Test;

class Day12Ex2Test {

  private Day12Ex2 day = new Day12Ex2();

  @Test
  public void testRun() throws Exception {
    String input = """
      Sabqponm
      abcryxxl
      accszExk
      acctuvwj
      abdefghi""";

    assert day.run(input).equals("29");
  }

}
