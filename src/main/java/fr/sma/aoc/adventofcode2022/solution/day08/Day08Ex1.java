package fr.sma.aoc.adventofcode2022.solution.day08;


import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.HashSet;
import java.util.Set;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day08Ex1 implements ExSolution {
  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "8", "1");
  }

  @Override
  public String run(String input) throws Exception {
    String[] lines = input.split("\n");
    Set<String> visible = new HashSet<>();

    for (int x = 0; x < lines.length; x++) {
      // top to bottom
      char prev = '0' - 1;
      for (int y = 0; y < lines[x].length() && prev < '9'; y++) {
        if (lines[x].charAt(y) > prev) {
          prev = lines[x].charAt(y);
          visible.add(x + "," + y);
        }
      }
    }
    for (int x = 0; x < lines.length; x++) {
      // bottom to top
      char prev = '0' - 1;
      for (int y = lines[x].length() - 1; y >= 0 && prev < '9'; y--) {
        if (lines[x].charAt(y) > prev) {
          prev = lines[x].charAt(y);
          visible.add(x + "," + y);
        }
      }
    }

    for (int y = 0; y < lines[0].length(); y++) {
      // left to right
      char prev = '0' - 1;
      for (int x = 0; x < lines.length && prev < '9'; x++) {
        if (lines[x].charAt(y) > prev) {
          prev = lines[x].charAt(y);
          visible.add(x + "," + y);
        }
      }
    }
    for (int y = 0; y < lines[0].length(); y++) {
      // right to left
      char prev = '0' - 1;
      for (int x = lines.length - 1; x >= 0 && prev < '9'; x--) {
        if (lines[x].charAt(y) > prev) {
          prev = lines[x].charAt(y);
          visible.add(x + "," + y);
        }
      }
    }

    return String.valueOf(visible.size());
  }
}
