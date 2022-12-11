package fr.sma.aoc.adventofcode2022.solution.day01;

import fr.sma.aoc.adventofcode2022.DataFetcher;
import fr.sma.aoc.adventofcode2022.ExSolution;
import java.util.Arrays;
import java.util.stream.Stream;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Component;

@Component
public class Day01Ex2 implements ExSolution {
  @Override
  public String run(String input) throws Exception {
    return String.valueOf(StreamEx.of(input.split("\n\n"))
      .mapToInt(elfSupply -> Arrays.stream(elfSupply.split("\n"))
        .mapToInt(Integer::parseInt)
        .sum())
      .reverseSorted()
      .limit(3).sum());
  }
}
