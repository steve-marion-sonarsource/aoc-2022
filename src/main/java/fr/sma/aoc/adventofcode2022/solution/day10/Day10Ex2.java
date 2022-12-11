package fr.sma.aoc.adventofcode2022.solution.day10;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day10Ex2 implements ExSolution {

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "10", "2");
  }

  @Override
  public String run(String input) throws Exception {
    StringBuilder sb = new StringBuilder();
    final Cpu cpu = new Cpu(c -> {
      int regVal = c.getRegValue("X");
      int x = (c.getCycle() % 40)-1;
      if (x == 0) {
        sb.append("\n");
      }
      if (Math.abs(regVal - x) <= 1) {
        sb.append('#');
      } else {
        sb.append(".");
      }
    });

    for (String line : input.split("\n")) {
      cpu.execInstruction(line);
    }

    return sb.toString();
  }
}
