package fr.sma.aoc.adventofcode2022.solution.day13;

import com.google.common.collect.Iterators;
import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day13Ex1 implements ExSolution {

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "13", "1");
  }

  @Override
  public String run(String input) throws Exception {
    int count = 0;
    String[] pairs = input.split("\n\n");
    for (int i = 0; i < pairs.length; i++) {
      String[] lists = pairs[i].split("\n");
      ListElem left = ListElem.parse(Iterators.peekingIterator(lists[0].chars().skip(1).mapToObj(c -> (char) c).iterator()));
      ListElem right = ListElem.parse(Iterators.peekingIterator(lists[1].chars().skip(1).mapToObj(c -> (char) c).iterator()));
      if(Elem.compare(left, right) < 0) {
        count += i+1;
      }
    }
    return String.valueOf(count);
  }
}

