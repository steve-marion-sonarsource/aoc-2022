package fr.sma.aoc.adventofcode2022.solution.day03;

import com.google.common.collect.Iterators;
import fr.sma.aoc.adventofcode2022.ExSolution;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Component;

@Component
public class Day03Ex2 implements ExSolution {
  @Override
  public String run(String input) throws Exception {
    Iterator<String> bagIterator = Iterators.forArray(input.split("\n"));
    return String.valueOf(StreamEx.<Stream<String>>produce(consumer -> {
        if (bagIterator.hasNext()) {
          consumer.accept(Stream.of(bagIterator.next(), bagIterator.next(), bagIterator.next()));
          return true;
        }
        return false;
      })
      .map(subStream -> subStream
        .map(Day03Ex2::toSet)
        .reduce((s1, s2) -> {
          s1.retainAll(s2);
          return s1;
        })
        .flatMap(s -> s.stream().findFirst()))
      .map(Optional::get)
      .mapToInt(Day03Ex2::valueOfChar).sum());
  }

  private static Set<Integer> toSet(String s) {
    return s.chars().distinct().boxed().collect(Collectors.toSet());
  }

  private static int valueOfChar(Integer commonChar) {
    return commonChar.intValue() < '_' ? commonChar.intValue() - 'A' + 27 : commonChar.intValue() - 'a' + 1;
  }
}
