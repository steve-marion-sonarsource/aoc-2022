package fr.sma.aoc.adventofcode2022.solution.day06;


import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.ArrayDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day06Ex1 implements ExSolution {
  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "6", "1");
  }

  @Override
  public String run(String input) throws Exception {
    int i=3;
    while(i<input.length()) {
      char a = input.charAt(i-3);
      char b = input.charAt(i-2);
      char c = input.charAt(i-1);
      char d = input.charAt(i);

      if (a != b && a != c && a != d && b != c && b != d && c != d) {
        return String.valueOf(i+1);
      }
      i++;
    }
    throw new IllegalStateException("no signal found");
  }
}
