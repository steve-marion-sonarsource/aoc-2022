package fr.sma.aoc.adventofcode2022.solution.day03;

import fr.sma.aoc.adventofcode2022.DataFetcher;
import fr.sma.aoc.adventofcode2022.ExSolution;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import one.util.streamex.IntStreamEx;
import org.springframework.stereotype.Component;

@Component
public class Day03Ex1 implements ExSolution {
  @Override
  public String run(String input) throws Exception {
    return String.valueOf(Stream.of(input.split("\n"))
      .mapToInt(s -> {
        Set<Integer> c1 = s.substring(0, s.length() / 2).chars().distinct().boxed().collect(Collectors.toSet());
        Set<Integer> c2 = s.substring(s.length() / 2).chars().distinct().boxed().collect(Collectors.toSet());
        c1.retainAll(c2);
        Integer commonChar = c1.stream().findFirst().get();
        return commonChar.intValue() < '_' ? commonChar.intValue()  - 'A' + 27 : commonChar.intValue() - 'a' + 1;
      })
      .sum());
  }
}
