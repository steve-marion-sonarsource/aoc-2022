package fr.sma.aoc.adventofcode2022.solution.day15;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import one.util.streamex.StreamEx;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day15Ex2 implements ExSolution {

  public static final int MAX_VALUE = 4_000_000;

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "15", "2");
  }

  Pattern sensorPattern = Pattern.compile("Sensor at x=(-?\\d+), y=(-?\\d+): closest beacon is at x=(-?\\d+), y=(-?\\d+)");

  @Override
  public String run(String input) throws Exception {
    List<SensedArea> sensedAreas = StreamEx.split(input, "\n")
      .map(sensorPattern::matcher)
      .filter(Matcher::matches)
      .map(m ->
        new SensedArea(
          Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)),
          Integer.parseInt(m.group(3)), Integer.parseInt(m.group(4)))
      ).toList();

    for(int y=0 ; y<MAX_VALUE ; y++) {

      final int yTarget = y;
      Deque<Interval> sensedTiles = new ArrayDeque<>();
      sensedAreas.stream()
        .filter(sa -> Math.abs(sa.sy - yTarget) <= sa.radius)
        .map(sa -> new Interval(sa.sx - (sa.radius - Math.abs(sa.sy - yTarget)), sa.sx + (sa.radius - Math.abs(sa.sy - yTarget))))
        .sorted(Comparator.comparingInt(Interval::a))
        .forEachOrdered(cur -> {
          Interval prev = sensedTiles.peekLast();
          if (prev == null) {
            sensedTiles.addLast(cur);
          } else {
            if (cur.a - prev.b <= 1) {
              sensedTiles.removeLast();
              sensedTiles.addLast(new Interval(prev.a, Math.max(cur.b, prev.b)));
            } else {
              sensedTiles.addLast(cur);
            }
          }
        });
      if(sensedTiles.size() > 1) {
        return String.valueOf(4_000_000L * (sensedTiles.getFirst().b + 1) + yTarget);
      }
    }

    throw new IllegalStateException("failure");
  }

  private record Interval(int a, int b) {
  }

  private int distance(SensedArea s1, SensedArea s2) {
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
