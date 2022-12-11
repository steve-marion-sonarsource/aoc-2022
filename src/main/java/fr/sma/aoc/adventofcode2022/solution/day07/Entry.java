package fr.sma.aoc.adventofcode2022.solution.day07;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Entry {
  public long weight = 0;
  final Entry parent;
  private final Map<String, Entry> entries;

  public Entry(Entry parent) {
    this.parent = parent;
    this.entries = new HashMap<>();
  }

  public Entry(Entry parent, int weight) {
    this.parent = parent;
    this.entries = Map.of();
    this.weight = weight;
  }

  public Entry getDir(String dir) {
    return entries.get(dir);
  }

  public Entry addDir(String name) {
    return entries.computeIfAbsent(name, s -> new Entry(this));
  }

  public void addFile(String name, int weight) {
    if(!entries.containsKey(name)) {
      entries.put(name, new Entry(this, weight));
      this.updateWeight(weight);
    }
  }

  public void walk(Consumer<Entry> callback) {
    callback.accept(this);
    entries.values().forEach(e -> e.walk(callback));
  }

  public boolean isFolder() {
    return entries.size() != 0 || weight == 0;
  }

  private void updateWeight(int additionalWeight) {
    this.weight += additionalWeight;
    if(parent != null) {
      this.parent.updateWeight(additionalWeight);
    }
  }
}
