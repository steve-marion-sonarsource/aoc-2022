package fr.sma.aoc.adventofcode2022.solution.day05;

import org.junit.jupiter.api.Test;

class Day05Ex1Test {

  @Test
  void run() throws Exception {
    String input =
        "    [D]    \n" +
        "[N] [C]    \n" +
        "[Z] [M] [P]\n" +
        " 1   2   3 \n" +
        "\n" +
        "move 1 from 2 to 1\n" +
        "move 3 from 1 to 3\n" +
        "move 2 from 2 to 1\n" +
        "move 1 from 1 to 2";

    assert new Day05Ex1().run(input).equals("CMZ");
  }
}
