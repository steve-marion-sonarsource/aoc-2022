package fr.sma.aoc.adventofcode2022.solution.day13;

record ValueElem(int value) implements Elem {
  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
