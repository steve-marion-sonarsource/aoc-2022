package fr.sma.aoc.adventofcode2022.solution.day11;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import one.util.streamex.LongStreamEx;
import one.util.streamex.StreamEx;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day11Ex2 implements ExSolution {

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "11", "2");
  }

  @Override
  public String run(String input) throws Exception {
    int ppcm = StreamEx.split(input, "\n")
      .filter(s -> s.startsWith("  Test: divisible by "))
      .map(s -> s.trim().split(" ")[3])
      .mapToInt(Integer::parseInt)
      .reduce(1, (left, right) -> left * right);

    ArrayList<Monkey> monkeys = new ArrayList<>();
    StreamEx.split(input, "\n\n")
      .map(monkeyDescription -> new Monkey(monkeyDescription, ppcm))
      .forEachOrdered(monkeys::add);

    Map<Monkey, AtomicInteger> inspectionCounter = monkeys.stream()
      .collect(Collectors.toMap(Function.identity(), m -> new AtomicInteger(0)));
    for (int round = 0; round < 10_000; round++) {
      monkeys.forEach(monkey ->
        monkey.throwInventory()
          .peek(t -> inspectionCounter.get(monkey).addAndGet(1))
          .forEachOrdered(itemThrown -> monkeys.get(itemThrown.monkeyTarget()).receive(itemThrown.itemValue()))
      );
    }

    return String.valueOf(inspectionCounter.values().stream()
      .mapToLong(AtomicInteger::get)
      .sorted()
      .skip(inspectionCounter.size() - 2)
      .reduce(1, (left, right) -> left * right));
  }

  private static class Monkey {
    private final Deque<Long> inventory = new ArrayDeque<>();
    private final Operation operation;
    private final int divisibilityTest;
    private final int monkeyTrue;
    private final int monkeyFalse;

    private final long ppcm;


    public Monkey(String monkeyDescription, long ppcm) {
      this.ppcm = ppcm;
      String[] split = monkeyDescription.split("\n");
      Stream.of(split[1].split(": ")[1].split(", "))
        .mapToLong(Long::parseLong)
        .forEachOrdered(inventory::addLast);
      this.operation = new Operation(split[2].trim());
      divisibilityTest = Integer.parseInt(split[3].trim().split(" ")[3]);
      monkeyTrue = Integer.parseInt(split[4].trim().split(" ")[5]);
      monkeyFalse = Integer.parseInt(split[5].trim().split(" ")[5]);
    }

    public Stream<Throw> throwInventory() {
      if (inventory.isEmpty())
        return Stream.empty();
      return LongStreamEx.produce(consumer -> {
          consumer.accept(inventory.removeFirst());
          return !inventory.isEmpty();
        }).map(operation::operate)
        .map(v -> v % ppcm)
        .mapToObj(v -> new Throw(v % divisibilityTest == 0 ? monkeyTrue : monkeyFalse, v));
    }

    public void receive(long item) {
      inventory.addLast(item);
    }

    public record Throw(int monkeyTarget, long itemValue) {
    }
  }
}
