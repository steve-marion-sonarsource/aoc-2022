package fr.sma.aoc.adventofcode2022.solution.day15;

import org.junit.jupiter.api.Test;

class Day15Ex2Test {

  private Day15Ex2 day = new Day15Ex2();

  @Test
  public void testRun() throws Exception {
    String input = """
      Sensor at x=2, y=18: closest beacon is at x=-2, y=15
      Sensor at x=9, y=16: closest beacon is at x=10, y=16
      Sensor at x=13, y=2: closest beacon is at x=15, y=3
      Sensor at x=12, y=14: closest beacon is at x=10, y=16
      Sensor at x=10, y=20: closest beacon is at x=10, y=16
      Sensor at x=14, y=17: closest beacon is at x=10, y=16
      Sensor at x=8, y=7: closest beacon is at x=2, y=10
      Sensor at x=2, y=0: closest beacon is at x=2, y=10
      Sensor at x=0, y=11: closest beacon is at x=2, y=10
      Sensor at x=20, y=14: closest beacon is at x=25, y=17
      Sensor at x=17, y=20: closest beacon is at x=21, y=22
      Sensor at x=16, y=7: closest beacon is at x=15, y=3
      Sensor at x=14, y=3: closest beacon is at x=15, y=3
      Sensor at x=20, y=1: closest beacon is at x=15, y=3""";

    assert day.run(input).equals("56000011");
  }

  private static int distance(SensedArea s1, SensedArea s2) {
    return manhattanDistance(s1.sx, s1.by, s2.sx, s2.sy);
  }

  private class SensedArea {
    public final int sx;
    public final int sy;
    public final int bx;
    public final int by;
    public final int radius;

    public SensedArea(int sx, int sy, int bx, int by) {
      this.sx = sx;
      this.sy = sy;
      this.bx = bx;
      this.by = by;
      this.radius = manhattanDistance(sx, sy, bx, by);
    }

    public boolean isSensed(int x, int y) {
      return manhattanDistance(x, y, this.sx, this.sy) <= radius;
    }
  }

  public static int manhattanDistance(int x1, int y1, int x2, int y2) {
    return Math.abs(x1 - x2) + Math.abs(y1 - y2);
  }
}
