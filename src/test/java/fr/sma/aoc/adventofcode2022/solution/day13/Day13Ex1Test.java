package fr.sma.aoc.adventofcode2022.solution.day13;

import org.junit.jupiter.api.Test;

class Day13Ex1Test {

  private Day13Ex1 day = new Day13Ex1();

  @Test
  public void testRun() throws Exception {
    String input = """
      [1,1,3,1,1]
      [1,1,5,1,1]
      
      [[1],[2,3,4]]
      [[1],4]
      
      [9]
      [[8,7,6]]
      
      [[4,4],4,4]
      [[4,4],4,4,4]
      
      [7,7,7,7]
      [7,7,7]
      
      []
      [3]
      
      [[[]]]
      [[]]
      
      [1,[2,[3,[4,[5,6,7]]]],8,9]
      [1,[2,[3,[4,[5,6,0]]]],8,9]""";

    assert day.run(input).equals("13");
  }

}
