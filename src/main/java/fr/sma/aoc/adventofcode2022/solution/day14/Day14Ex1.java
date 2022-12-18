package fr.sma.aoc.adventofcode2022.solution.day14;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.ArrayList;
import java.util.List;
import one.util.streamex.StreamEx;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day14Ex1 implements ExSolution {

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "14", "1");
  }

  @Override
  public String run(String input) throws Exception {
    List<Segment> rockLines = StreamEx.split(input, "\n")
      .flatMap(line -> {
        List<Segment> segments = new ArrayList<>();
        String[] corners = line.split(" -> ");
        String[] startPos = corners[0].split(",");
        for (int i = 1; i < corners.length; i++) {
          String[] otherPos = corners[i].split(",");
          int x1 = Integer.parseInt(startPos[0]);
          int y1 = Integer.parseInt(startPos[1]);
          int x2 = Integer.parseInt(otherPos[0]);
          int y2 = Integer.parseInt(otherPos[1]);
          segments.add(new Segment(
            Math.min(x1, x2), Math.min(y1, y2),
            Math.max(x1, x2), Math.max(y1, y2)));
          startPos = otherPos;
        }
        return segments.stream();
      }).toList();

    int minX = rockLines.stream().mapToInt(s -> s.x1).min().getAsInt();
    //int minY = rockLines.stream().mapToInt(s -> s.y1).min().getAsInt();
    int minY = 0;
    int maxX = rockLines.stream().mapToInt(s -> s.x2).max().getAsInt();
    int maxY = rockLines.stream().mapToInt(s -> s.y2).max().getAsInt();

    char[][] cavern = new char[maxX - minX + 1][maxY - minY + 1];

    for (Segment s : rockLines) {
      for (int x = s.x1 - minX; x <= s.x2 - minX; x++) {
        for (int y = s.y1 - minY; y <= s.y2 - minY; y++) {
          cavern[x][y] = '#';
        }
      }
    }

    int u = 0;
    int x = 500 - minX, y = -minY;
    while (true) {
      if (y + 1 == cavern[x].length) {
        return String.valueOf(u);
      } else if ((cavern[x][y + 1] != '#' && cavern[x][y + 1] != 'o')) {
        y++;
      } else if (x == 0) {
        return String.valueOf(u);
      } else if (cavern[x - 1][y + 1] != '#' && cavern[x - 1][y + 1] != 'o') {
        x--;
        y++;
      } else if (x == cavern.length-1) {
        return String.valueOf(u);
      } else if (cavern[x + 1][y + 1] != '#' && cavern[x + 1][y + 1] != 'o') {
        x++;
        y++;
      } else {
        cavern[x][y] = 'o';
        u++;
        x = 500 - minX;
        y = -minY;
      }
    }
  }

  record Segment(int x1, int y1, int x2, int y2) {
  }
}

