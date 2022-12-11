package fr.sma.aoc.adventofcode2022.solution.day03;

import fr.sma.aoc.adventofcode2022.solution.day03.Day03Ex1;
import org.junit.jupiter.api.Test;

class Day03Ex1Test {
  @Test
  public void testRun() throws Exception {
    String input = """
      vJrwpWtwJgWrhcsFMMfFFhFp
      jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL
      PmmdzqPrVvPwwTWBwg
      wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn
      ttgJtRGJQctTZtZT
      CrZsJsPPZsGzwwsLwLmpwMDw""";

    assert new Day03Ex1().run(input).equals("157");
  }

}
