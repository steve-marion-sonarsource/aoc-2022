package fr.sma.aoc.adventofcode2022.solution.day06;


import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day06Ex2 implements ExSolution {
  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "6", "2");
  }

  @Override
  public String run(String input) throws Exception {
    int i=13;
    while(i<input.length()) {
      Set<Integer> collect = input.substring(i - 13, i + 1).chars().boxed().collect(Collectors.toSet());
      if(collect.size() == 14) {
        return String.valueOf(i+1);
      }

      i++;
    }
    throw new IllegalStateException("no signal found");
  }


}
