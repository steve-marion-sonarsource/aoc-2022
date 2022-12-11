package fr.sma.aoc.adventofcode2022.solution.day05;


import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import one.util.streamex.StreamEx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day05Ex1 implements ExSolution {
  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "5", "1");
  }

  @Override
  public String run(String input) throws Exception {
    String[] lines = input.trim().split("\n");
    int nbStack = (lines[0].length() + 1) / 4;
    ArrayDeque<Character>[] stacks = Stream.generate(ArrayDeque<Character>::new)
      .limit(nbStack)
      .toArray(ArrayDeque[]::new);

    int l=0 ;
    while (lines[l].charAt(1) != '1') {
      for(int i=0 ; i<stacks.length ; i++) {
        int columnIndex = 4*i+1;
        char crate = lines[l].charAt(columnIndex);
        if(crate != ' ') {
          stacks[i].addFirst(crate);
        }
      }
      l++;
    }
    l+=2;

    Pattern movePattern = Pattern.compile("move (\\d+) from (\\d+) to (\\d+)");
    while (l < lines.length) {
      Matcher matcher = movePattern.matcher(lines[l]);
      matcher.matches();
      int nb = Integer.parseInt(matcher.group(1));
      int from = Integer.parseInt(matcher.group(2));
      int to = Integer.parseInt(matcher.group(3));

      for(int i=0 ; i<nb ; i++) {
        stacks[to-1].addLast(stacks[from-1].removeLast());
      }

      l++;
    }

    return Stream.of(stacks)
      .map(ArrayDeque::getLast)
      .map(c -> Character.toString(c))
      .collect(Collectors.joining());
  }
}
