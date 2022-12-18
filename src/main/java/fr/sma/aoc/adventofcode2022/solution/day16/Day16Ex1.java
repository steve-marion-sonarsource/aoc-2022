package fr.sma.aoc.adventofcode2022.solution.day16;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import one.util.streamex.EntryStream;
import one.util.streamex.StreamEx;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day16Ex1 implements ExSolution {

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "16", "1");
  }

  @Override
  public String run(String input) throws Exception {
    Map<String, Integer> flowRates = new HashMap<>();

    StreamEx.split(input, "\n").forEach(textualInput -> {
      String[] split = textualInput.split(" ");
      String name = split[1];
      int flowRate = Integer.parseInt(split[4].replace("rate=", "").replace(";", ""));
      flowRates.put(name, flowRate);
    });

    AdjacencyMap<String> valveGraph = new AdjacencyMap<>(flowRates.keySet().stream().sorted().toList());

    StreamEx.split(input, "\n").forEach(textualInput -> {
      String[] split = textualInput.split(" ");
      String name = split[1];

      for (int i = 9; i < split.length; i++) {
        String target = split[i].replace(",", "");
        valveGraph.setEdge(name, target, 1);
      }
    });
    valveGraph.calculateAll();
    System.out.println(valveGraph);

    return String.valueOf(visitValve(
      flowRates, valveGraph, "AA",
      EntryStream.of(flowRates).filterValues(f -> f>0).keys().collect(Collectors.toSet()), 30, 0));
  }

  private int visitValve(Map<String, Integer> flowRates, AdjacencyMap<String> distances, String current, Set<String> toVisit, int timeRemaining, int totalSteam) {
    return StreamEx.of(toVisit).mapToInt(to -> {
      int distance = distances.distance(current, to);
      int timeRemainingAfter = timeRemaining - (distance + 1);
      if (timeRemainingAfter >= 0) {
        int totalSteamAfter = totalSteam + flowRates.get(to) * timeRemainingAfter;
        Set<String> toVisitAfter = new HashSet<>(toVisit);
        toVisitAfter.remove(to);
        if (toVisitAfter.isEmpty()) {
          return totalSteamAfter;
        }
        return visitValve(flowRates, distances, to, toVisitAfter, timeRemainingAfter, totalSteamAfter);
      } else {
        return totalSteam;
      }
    }).max().orElse(0);
  }
}
