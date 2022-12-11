package fr.sma.aoc.adventofcode2022.solution.day04;

import fr.sma.aoc.adventofcode2022.solution.day03.Day03Ex1;
import fr.sma.aoc.adventofcode2022.solution.day03.Day03Ex2;
import fr.sma.aoc.adventofcode2022.solution.day04.Day04Ex1;
import org.junit.jupiter.api.Test;

class Day04Ex1Test {
  @Test
  public void testRun() throws Exception {
    String input = """
      2-4,6-8
      2-3,4-5
      5-7,7-9
      2-8,3-7
      6-6,4-6
      2-6,4-8""";

    assert new Day04Ex1().run(input).equals("2");
  }
}
