package fr.sma.aoc.adventofcode2022.solution.day10;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Cpu {
  private Map<String, Integer> registers = new HashMap<>();
  private int cycle = 0;
  private final Consumer<Cpu> clockCallback;

  public Cpu(Consumer<Cpu> clockCallback){
    this.clockCallback = clockCallback;
    registers.put("X", 1);
  }

  public void execInstruction(String line) {
    String[] splitLine = line.split(" ");
    switch (splitLine[0]) {
      case "noop" -> this.execNoop();
      case "addx" -> this.execAdd("X", Integer.parseInt(splitLine[1]));
      default -> throw new IllegalStateException("unknown instruction: " + line);
    }
  }

  public int getCycle() {
    return this.cycle;
  }
  public int getRegValue(String register) {
    return registers.get(register);
  }

  public void execNoop() {
    cycle++;
    clockCallback.accept(this);
  }

  public void execAdd(String register, int delta) {
    cycle++;
    clockCallback.accept(this);
    cycle++;
    clockCallback.accept(this);
    registers.merge(register, delta, Integer::sum);
  }
}
