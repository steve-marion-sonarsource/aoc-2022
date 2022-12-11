package fr.sma.aoc.adventofcode2022.solution.day06;

import fr.sma.aoc.adventofcode2022.solution.day03.Day03Ex1;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

class Day06Ex1Test {

  private Day06Ex1 day06Ex1 = new Day06Ex1();

  @Test
  public void testRun() throws Exception {
    String input = "bvwbjplbgvbhsrlpgdmjqwftvncz";
    assert day06Ex1.run(input).equals("5");
  }
  @Test
  public void testRun2() throws Exception {
    String input = "nppdvjthqldpwncqszvftbrmjlhg";
    assert day06Ex1.run(input).equals("6");
  }
  @Test
  public void testRun3() throws Exception {
    String input = "nznrnfrfntjfmvfwmzdfjlvtqnbhcprsg";
    assert day06Ex1.run(input).equals("10");
  }
  @Test
  public void testRun4() throws Exception {
    String input = "zcfzfwzzqfrljwzlrfnpqdbhtmscgvjw";
    assert day06Ex1.run(input).equals("11");
  }
}
