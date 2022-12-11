package fr.sma.aoc.adventofcode2022.solution.day07;


import com.google.common.collect.Iterators;
import com.google.common.collect.UnmodifiableIterator;
import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day07Ex2 implements ExSolution {
  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "7", "2");
  }

  @Override
  public String run(String input) throws Exception {
    UnmodifiableIterator<String> lines = Iterators.forArray(input.split("\n"));
    final Entry base = new Entry(null);
    Entry current = base;
    while (lines.hasNext()) {
      String l = lines.next();
      switch (l.split(" ")[0]) {
        case "$" -> current = switch (l.split(" ")[1]) {
          case "cd" -> switch(l.split(" ")[2]) {
            case "/" -> base;
            case ".." -> current.parent;
            default -> current.getDir(l.substring(5));
          };
          case "ls" -> current;
          default -> throw new IllegalStateException("unknown command " + l);
        };
        case "dir" -> current.addDir(l.substring(4));
        default -> {
          String[] fileLine = l.split(" ");
          current.addFile(fileLine[1], Integer.parseInt(fileLine[0]));
        }
      }
    }

    long spaceNeeded = base.weight - 40_000_000;

    AtomicLong totalWeight = new AtomicLong(base.weight);
    base.walk(e -> {
      if (e.isFolder() && e.weight > spaceNeeded && e.weight < totalWeight.get()) {
        totalWeight.set(e.weight);
      }
    });
    return totalWeight.toString();
  }
}
