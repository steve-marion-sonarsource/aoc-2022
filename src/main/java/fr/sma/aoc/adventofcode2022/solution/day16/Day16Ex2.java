package fr.sma.aoc.adventofcode2022.solution.day16;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day16Ex2 implements ExSolution {

  public static final int TIME_TO_VISIT = 26;

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "16", "2");
  }

  @Override
  public String run(String input) throws Exception {

    Map<String, Integer> indexes = new HashMap<>();

    String[] inputLines = input.split("\n");
    int[] flowRates = new int[inputLines.length];
    for (int i = 0; i < inputLines.length; i++) {
      String[] split = inputLines[i].split(" ");
      String name = split[1];
      flowRates[i] = Integer.parseInt(split[4].replace("rate=", "").replace(";", ""));
      indexes.put(name, i);
    }

    AdjacencyMap<String> valveGraph = new AdjacencyMap<>(EntryStream.of(indexes).sorted(Map.Entry.comparingByValue()).keys());

    StreamEx.split(input, "\n").forEach(textualInput -> {
      String[] split = textualInput.split(" ");
      String name = split[1];
      for (int i = 9; i < split.length; i++) {
        String target = split[i].replace(",", "");
        valveGraph.setEdge(name, target, 1);
      }
    });
    valveGraph.calculateAll();

    BitField valvesToVisit = new BitField(flowRates.length);
    for (int i = 0; i < flowRates.length; i++) {
      if (flowRates[i] > 0) {
        valvesToVisit.set(i);
      }
    }

    List<Visitor> visitors = StreamEx.generate(() -> new Visitor(indexes.get("AA"), TIME_TO_VISIT, 0, flowRates.length))
      .limit(2)
      .collect(Collectors.toCollection(ArrayList::new));

    ValveSystem valveSystem = new ValveSystem(flowRates, valveGraph);
    int result = valveSystem.visitValve(valvesToVisit, visitors);
    System.out.println("nb branch explored = " + valveSystem.getCounter());
    return String.valueOf(result);
  }

  private class Visitor {
    private final int destination;
    private final int timeRemaining;
    private final int totalSteam;
    private final BitField visited;

    public Visitor(int destination, int timeRemaining, int totalSteam, int nbDestination) {
      this(destination, timeRemaining, totalSteam, new BitField(nbDestination));
      visited.set(destination);
    }

    public int getTimeRemaining() {
      return timeRemaining;
    }

    private Visitor(int destination, int timeRemaining, int totalSteam, BitField visited) {
      this.destination = destination;
      this.timeRemaining = timeRemaining;
      this.totalSteam = totalSteam;
      this.visited = visited;
    }

    public Visitor move(int nextDestination, int distance, int destinationFlow) {
      int timeRemainingAfter = timeRemaining - distance - 1;
      BitField visitedAfter = visited.copy();
      visitedAfter.set(nextDestination);
      return new Visitor(nextDestination, timeRemainingAfter,
        totalSteam + destinationFlow * timeRemainingAfter, visitedAfter);
    }

    public int getTotalSteam() {
      return totalSteam;
    }

    public int getDestination() {
      return destination;
    }

    public BitField getVisited() {
      return visited;
    }
  }

  public class ValveSystem {
    private final int[] flowRates;
    private final AdjacencyMap<String> distances;
    private final Map<BitField, Integer>[] visitedCache;

    private final AtomicLong counter = new AtomicLong();

    public ValveSystem(int[] flowRates, AdjacencyMap<String> distances) {
      this.flowRates = flowRates;
      this.distances = distances;
      this.visitedCache = new HashMap[flowRates.length];
      Arrays.setAll(visitedCache, i -> new HashMap<>());
    }

    public long getCounter() {
      return counter.get();
    }

    public int visitValve(BitField toVisit, List<Visitor> visitors) {
      Visitor visitor = visitors.stream().max(Comparator.comparingInt(Visitor::getTimeRemaining)).get();
      int maxSteam = visitors.stream().mapToInt(Visitor::getTotalSteam).sum();
      for (int to = 0; to < toVisit.getSize(); to++) {
        if (!toVisit.get(to)) {
          continue;
        }
        int distance = distances.distance(visitor.getDestination(), to);
        if (distance + 1 > visitor.getTimeRemaining()) {
          continue;
        }
        Visitor visitorAfter = visitor.move(to, distance, flowRates[to]);
        int steam = visitedCache[to].compute(visitor.getVisited(), (visited, prevSteam) -> {
          if (prevSteam == null || prevSteam < visitorAfter.totalSteam) {
            return visitorAfter.totalSteam;
          } else {
            return prevSteam;
          }
        });
        if (steam == visitorAfter.totalSteam) {
          BitField toVisitAfter = toVisit.copy();
          toVisitAfter.reset(to);
          List<Visitor> visitorsAfter = visitors.stream()
            .map(v -> v == visitor ? visitorAfter : v)
            .toList();
          steam = visitValve(toVisitAfter, visitorsAfter);
        }
        if (steam > maxSteam) {
          maxSteam = steam;
        }
      }
      counter.incrementAndGet();
      return maxSteam;
    }
  }
}
