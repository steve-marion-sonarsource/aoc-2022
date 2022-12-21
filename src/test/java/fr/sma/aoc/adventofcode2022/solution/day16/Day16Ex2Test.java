package fr.sma.aoc.adventofcode2022.solution.day16;

import org.junit.jupiter.api.Test;

class Day16Ex2Test {

  private Day16Ex2 day = new Day16Ex2();

  @Test
  public void testRun() throws Exception {
    String input = """
      Valve AA has flow rate=0; tunnels lead to valves DD, II, BB
      Valve BB has flow rate=13; tunnels lead to valves CC, AA
      Valve CC has flow rate=2; tunnels lead to valves DD, BB
      Valve DD has flow rate=20; tunnels lead to valves CC, AA, EE
      Valve EE has flow rate=3; tunnels lead to valves FF, DD
      Valve FF has flow rate=0; tunnels lead to valves EE, GG
      Valve GG has flow rate=0; tunnels lead to valves FF, HH
      Valve HH has flow rate=22; tunnel leads to valve GG
      Valve II has flow rate=0; tunnels lead to valves AA, JJ
      Valve JJ has flow rate=21; tunnel leads to valve II""";

    String result = day.run(input);
    System.out.println("result = " + result);
    assert result.equals("1707");
  }

  @Test
  public void testBitField() {
    var bitfield = new BitField(8);
    bitfield.set(8);
    assert bitfield.get(8);
    bitfield.reset(8);
    assert !bitfield.get(8);
  }

}
