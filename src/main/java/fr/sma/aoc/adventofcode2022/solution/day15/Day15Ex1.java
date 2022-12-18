package fr.sma.aoc.adventofcode2022.solution.day15;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import one.util.streamex.StreamEx;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day15Ex1 implements ExSolution {

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "15", "1");
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

    int yTarget = 2_000_000;

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
          if (prev.b >= cur.a) {
            sensedTiles.removeLast();
            sensedTiles.addLast(new Interval(prev.a, Math.max(cur.b, prev.b)));
          } else {
            sensedTiles.addLast(cur);
          }
        }
      });

    Set<Integer> annoyingBeacons = sensedAreas.stream()
      .filter(sa -> sa.by == yTarget)
      .mapToInt(sa -> sa.bx)
      .boxed().collect(Collectors.toSet());

    AtomicInteger alreadyPresentBeacon = new AtomicInteger();
    return String.valueOf(sensedTiles.stream()
      .peek(i -> annoyingBeacons.stream()
        .filter(b -> b >= i.a && b <= i.b)
        .forEach(interval -> alreadyPresentBeacon.incrementAndGet()))
      .mapToInt(i -> i.b - i.a + 1)
      .sum() - alreadyPresentBeacon.get());
  }

  private record Interval(int a, int b) {
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
