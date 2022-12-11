package fr.sma.aoc.adventofcode2022.solution.day10;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day10Ex1 implements ExSolution {

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "10", "1");
  }

  @Override
  public String run(String input) throws Exception {
    AtomicInteger signalStrength = new AtomicInteger(0);
    final Cpu cpu = new Cpu(c -> {
      switch (c.getCycle()) {
        case 20, 60, 100, 140, 180, 220 -> signalStrength.addAndGet(c.getCycle() * c.getRegValue("X"));
      }
    });

    for (String line : input.split("\n")) {
      String[] splitLine = line.split(" ");
      switch (splitLine[0]) {
        case "noop" -> cpu.execNoop();
        case "addx" -> cpu.execAdd("X", Integer.parseInt(splitLine[1]));
        default -> throw new IllegalStateException("unknown instruction: " + line);
      }
    }
    return signalStrength.toString();
  }
}
