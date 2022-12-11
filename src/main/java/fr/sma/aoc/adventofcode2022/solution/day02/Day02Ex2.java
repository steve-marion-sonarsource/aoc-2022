package fr.sma.aoc.adventofcode2022.solution.day02;

import fr.sma.aoc.adventofcode2022.DataFetcher;
import fr.sma.aoc.adventofcode2022.ExSolution;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class Day02Ex2 implements ExSolution {
  @Override
  public String run(String input) throws Exception {
    return String.valueOf(Stream.of(input.split("\n"))
      .mapToInt(s ->
        switch (s) {
          case "A X" -> 3 + 0;
          case "A Y" -> 1 + 3;
          case "A Z" -> 2 + 6;
          case "B X" -> 1 + 0;
          case "B Y" -> 2 + 3;
          case "B Z" -> 3 + 6;
          case "C X" -> 2 + 0;
          case "C Y" -> 3 + 3;
          case "C Z" -> 1 + 6;
          default -> throw new IllegalArgumentException(s);
        }
      ).sum());
  }


}
