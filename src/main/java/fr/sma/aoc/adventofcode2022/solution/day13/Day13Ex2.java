package fr.sma.aoc.adventofcode2022.solution.day13;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.concurrent.atomic.AtomicInteger;
import one.util.streamex.StreamEx;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day13Ex2 implements ExSolution {

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "13", "2");
  }

  @Override
  public String run(String input) throws Exception {
    AtomicInteger counter = new AtomicInteger(0);

    return String.valueOf(StreamEx.split(input, "\n")
      .filter(s -> !s.isEmpty())
      .append("[[2]]", "[[6]]")
      .map(ListElem::parse)
      .sorted()
      .peek(l -> counter.getAndIncrement())
      .filter(listElem -> listElem.isDivider())
      .mapToInt(l -> counter.get())
      .reduce(1, (a, b) -> a * b));
  }
}

