package fr.sma.aoc.adventofcode2022.solution.day16;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import one.util.streamex.StreamEx;

public class AdjacencyMap<T> {
  final int[][] edges;
  final Map<T, Integer> pos;

  public AdjacencyMap(Iterable<T> nodes) {
    AtomicInteger counter = new AtomicInteger();
    pos = StreamEx.of(nodes.iterator())
      .toMap(s -> counter.getAndIncrement());

    this.edges = new int[pos.size()][pos.size()];
    for (int[] l : this.edges) {
      Arrays.fill(l, -1);
    }
  }

  public void setEdge(T from, T to, int cost) {
    this.setEdge(pos.get(from), pos.get(to), cost);
  }

  public void setEdge(int from, int to, int cost) {
    edges[from][to] = cost;
    edges[to][from] = cost;
  }

  public void calculateAll() {
    for (int from = 0; from < edges.length; from++) {
      int[] fromEdges = edges[from];
      int currentDistance = 0;
      Set<Integer> reachable = new HashSet<>();
      reachable.add(from);
      setEdge(from, from, 0);
      while (reachable.size() < edges.length) {  // while all nodes haven't been reached
        currentDistance++;
        reachable.addAll(reachable.stream()  // for each node reached
          .map(r -> edges[r])
          // find the next nodes reachable in one hop
          .flatMapToInt(rEdges -> IntStream.range(0, rEdges.length).filter(i -> rEdges[i] == 1))
          .distinct()
          .boxed().collect(Collectors.toSet()));
        for (Integer to : reachable) {  // set new shortest path
          if (fromEdges[to] == -1) {
            setEdge(from, to, currentDistance);
          }
        }
      }
    }
  }

  public int distance(T from, T to) {
    return this.distance(pos.get(from),pos.get(to));
  }

  public int distance(int from, int to) {
    return this.edges[from][to];
  }

  @Override
  public String toString() {
    return StreamEx.of(pos.keySet().stream().sorted().toList())
      .map(n -> IntStream.of(edges[pos.get(n)])
        .mapToObj(i -> String.format("%2d", i))
        .collect(Collectors.joining(" ", String.format("%5s", n), "")))
      .collect(Collectors.joining("\n"));
  }
}
