package fr.sma.aoc.adventofcode2022.solution.day09;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day09Ex1 implements ExSolution {

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "9", "1");
  }

  @Override
  public String run(String input) throws Exception {
    Set<String> visited = new HashSet<>();
    visited.add("0,0");
    Node rope = new Node(2);
    for(String line : input.split("\n")) {
      final int moveDistance = Integer.parseInt(line.split(" ")[1]);
      for(int i=0 ; i<moveDistance ; i++) {
        (switch (line.charAt(0)) {
          case 'R' -> rope.move(1, 0);
          case 'L' -> rope.move(-1, 0);
          case 'D' -> rope.move(0, 1);
          case 'U' -> rope.move(0, -1);
          default -> throw new IllegalStateException("unrecognized movement instruction " + line);
        }).ifPresent(lastNode -> visited.add(lastNode.x + "," + lastNode.y));
      }
    }
    return String.valueOf(visited.size());
  }
}
