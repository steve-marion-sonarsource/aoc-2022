package fr.sma.aoc.adventofcode2022.solution.day04;


import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import one.util.streamex.StreamEx;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day04Ex1 implements ExSolution {
  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "4", "1");
  }

  @Override
  public String run(String input) throws Exception {
    return String.valueOf(StreamEx.of(input.split("\n"))
      .filter(p -> {
        String[] pair = p.split(",");
        Interval i1 = Interval.parse(pair[0]);
        Interval i2 = Interval.parse(pair[1]);
        return i1.isIncludedIn(i2) || i2.isIncludedIn(i1);
      }).count());
  }

  record Interval(int from, int to) {
    public static Interval parse(String fromText) {
      String[] rawInterval = fromText.split("-");
      return new Interval(Integer.parseInt(rawInterval[0]), Integer.parseInt(rawInterval[1]));
    }

    public boolean isIncludedIn(Interval other) {
      return this.from >= other.from && this.to <= other.to;
    }
  }
}
