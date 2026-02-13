package fr.sma.aoc.adventofcode2022.solution.day01;

import fr.sma.aoc.adventofcode2022.ExSolution;
import java.util.Arrays;
import java.util.stream.Stream;
import org.springframework.stereotype.Component;

@Component
public class Day01Ex1 implements ExSolution {
  @Override
  public String run(String input) throws Exception {
    return String.valueOf(Stream.of(input.split("\n\n"))
      .mapToInt(elfSupply -> Arrays.stream(elfSupply.split("\n"))
        .mapToInt(Integer::parseInt)
        .sum())
      .max().getAsInt());
  }
}
