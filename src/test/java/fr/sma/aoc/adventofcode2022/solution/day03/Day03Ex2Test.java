package fr.sma.aoc.adventofcode2022.solution.day03;

import fr.sma.aoc.adventofcode2022.solution.day03.Day03Ex2;
import org.junit.jupiter.api.Test;

class Day03Ex2Test {
  @Test
  public void testRun() throws Exception {
    String input = """
      vJrwpWtwJgWrhcsFMMfFFhFp
      jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
      PmmdzqPrVvPwwTWBwg""";

    assert new Day03Ex2().run(input).equals("18");
  }

  @Test
  public void testRun2() throws Exception {
    String input = """
      wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
      ttgJtRGJQctTZtZT
      CrZsJsPPZsGzwwsLwLmpwMDw""";

    assert new Day03Ex2().run(input).equals("52");
  }

}
