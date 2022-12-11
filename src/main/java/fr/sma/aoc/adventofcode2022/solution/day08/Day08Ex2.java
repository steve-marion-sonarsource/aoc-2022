package fr.sma.aoc.adventofcode2022.solution.day08;


import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
import one.util.streamex.IntStreamEx;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day08Ex2 implements ExSolution {
  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "8", "2");
  }

  @Override
  public String run(String input) throws Exception {
    String[] lines = input.split("\n");

    int maxScenicScore = 0;
    for (int x = 1; x < lines.length - 1; x++) {
      for (int y = 1; y < lines[x].length() - 1; y++) {
        final int fx = x;
        final int fy = y;
        int leftView = nextTallest(lines[x].charAt(y), IntStreamEx.iterate(fx-1, i -> i >= 0, i -> i - 1).mapToObj(i -> lines[i].charAt(fy)));
        int rightView = nextTallest(lines[x].charAt(y), IntStreamEx.iterate(fx+1, i -> i < lines.length, i -> i + 1).mapToObj(i -> lines[i].charAt(fy)));
        int topView = nextTallest(lines[x].charAt(y), IntStreamEx.iterate(fy-1, i -> i >= 0, i -> i - 1).mapToObj(i -> lines[fx].charAt(i)));
        int bottomView = nextTallest(lines[x].charAt(y), IntStreamEx.iterate(fy+1, i -> i < lines[0].length(), i -> i + 1).mapToObj(i -> lines[fx].charAt(i)));
        int curScenicScore = leftView * rightView * topView * bottomView;
        if (curScenicScore > maxScenicScore) {
          maxScenicScore = curScenicScore;
        }
      }
    }
    return String.valueOf(maxScenicScore);
  }

  private int nextTallest(char curSize, Stream<Character> treeLine) {
    AtomicInteger counter = new AtomicInteger(0);
    treeLine.takeWhile(c -> {
      counter.incrementAndGet();
      return c < curSize;
    }).count();
    return counter.get();
  }
}
