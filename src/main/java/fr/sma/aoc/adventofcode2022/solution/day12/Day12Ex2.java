package fr.sma.aoc.adventofcode2022.solution.day12;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.Stream;
import one.util.streamex.StreamEx;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day12Ex2 implements ExSolution {

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "12", "2");
  }

  private int[][] map;
  private int[][] distance;

  @Override
  public String run(String input) throws Exception {
    map = StreamEx.split(input, "\n")
      .map(line -> line.chars().toArray())
      .toArray(new int[0][0]);
    distance = StreamEx.generate(() -> new int[map[0].length])
      .peek(a -> Arrays.fill(a, Integer.MAX_VALUE))
      .limit(map.length)
      .toArray(new int[0][0]);

    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[0].length; y++) {
        if (map[x][y] == 'S') {
          distance[x][y] = 0;

          OptionalInt explore = explore(x, y);
          if (explore.isEmpty()) {
            System.out.println(printMap());
          } else {
            return String.valueOf(explore.getAsInt());
          }
        }
      }
    }
    throw new IllegalStateException();
  }

  public OptionalInt explore(int x, int y) {
    if(map[x][y] == 'a') {
      distance[x][y] = 0;
    }
    int curDistance = distance[x][y];

    if (map[x][y] == 'E') {
      return OptionalInt.of(distance[x][y]);
    }
    int curHeight = getHeight(x, y);

    return Stream.of(
        exploreNeighbor(curHeight, x - 1, y, curDistance),
        exploreNeighbor(curHeight, x + 1, y, curDistance),
        exploreNeighbor(curHeight, x, y - 1, curDistance),
        exploreNeighbor(curHeight, x, y + 1, curDistance))
      .filter(OptionalInt::isPresent)
      .mapToInt(OptionalInt::getAsInt)
      .min();
  }

  private int getHeight(int x, int y) {
    return switch (map[x][y]) {
      case 'S' -> 'a';
      case 'E' -> 'z';
      default -> map[x][y];
    };
  }

  private OptionalInt exploreNeighbor(int prevHeight, int x, int y, int prevDistance) {
    if (x >= 0 && x < map.length && y >= 0 && y < map[x].length) {
      if (getHeight(x, y) <= prevHeight + 1 && distance[x][y] > prevDistance + 1) {
        distance[x][y] = prevDistance + 1;
        return explore(x, y);
      }
    }
    return OptionalInt.empty();
  }

  private String printMap() {
    StringBuilder sb = new StringBuilder();
    for (int x = 0; x < map.length; x++) {
      for (int y = 0; y < map[x].length; y++) {
        if (distance[x][y] != Integer.MAX_VALUE) {
          sb.append('.');
        } else {
          sb.append((char)map[x][y]);
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
