package fr.sma.aoc.adventofcode2022.solution.day02;

import fr.sma.aoc.adventofcode2022.DataFetcher;
import fr.sma.aoc.adventofcode2022.ExSolution;
import java.util.Arrays;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class Day02Ex1 implements ExSolution {
  @Override
  public String run(String input) throws Exception {
    return String.valueOf(Stream.of(input.split("\n"))
      .mapToInt(s ->
        switch (s) {
          case "A X" -> 3 + 1;
          case "A Y" -> 6 + 2;
          case "A Z" -> 0 + 3;
          case "B X" -> 0 + 1;
          case "B Y" -> 3 + 2;
          case "B Z" -> 6 + 3;
          case "C X" -> 6 + 1;
          case "C Y" -> 0 + 2;
          case "C Z" -> 3 + 3;
          default -> throw new IllegalArgumentException(s);
        }
      ).sum());
  }


}
