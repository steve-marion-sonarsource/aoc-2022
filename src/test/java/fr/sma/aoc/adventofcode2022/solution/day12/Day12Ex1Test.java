package fr.sma.aoc.adventofcode2022.solution.day12;

import fr.sma.aoc.adventofcode2022.solution.day11.Day11Ex1;
import org.junit.jupiter.api.Test;

class Day12Ex1Test {

  private Day12Ex1 day = new Day12Ex1();

  @Test
  public void testRun() throws Exception {
    String input = """
      Sabqponm
      abcryxxl
      accszExk
      acctuvwj
      abdefghi""";

    assert day.run(input).equals("31");
  }

}
