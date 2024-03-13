package fr.sma.aoc.adventofcode2022.solution.day19;

import org.junit.jupiter.api.Test;

class Day19Ex1Test {

  private Day19Ex1 day = new Day19Ex1();

  @Test
  public void testRun() throws Exception {
    String input = """
      Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.
      Blueprint 2: Each ore robot costs 2 ore. Each clay robot costs 3 ore. Each obsidian robot costs 3 ore and 8 clay. Each geode robot costs 3 ore and 12 obsidian.""";

    String result = day.run(input);
    System.out.println("result = " + result);
    assert result.equals("33");
  }

  @Test
  public void testRun4() throws Exception {
    String input = "Blueprint 4: Each ore robot costs 2 ore. Each clay robot costs 2 ore. Each obsidian robot costs 2 ore and 20 clay. Each geode robot costs 2 ore and 14 obsidian.";

    String result = day.run(input);
    System.out.println("result = " + result);
    assert result.equals("12");
  }

}
