package fr.sma.aoc.adventofcode2022.solution.day13;

import org.junit.jupiter.api.Test;

public class ElemTest {
  @Test
  public void run() {
    assert ListElem.parse("[[2]]").isDivider();
    assert ListElem.parse("[[6]]").isDivider();
    assert !ListElem.parse("[6]").isDivider();
    assert !ListElem.parse("[[[6]]]").isDivider();
  }
}
