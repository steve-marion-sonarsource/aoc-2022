package fr.sma.aoc.adventofcode2022.solution.day17;

import fr.sma.aoc.adventofcode2022.solution.day16.BitField;
import org.junit.jupiter.api.Test;

public class CaveTest {

  @Test
  public void testFit() {
    byte l0 = BitField.setBitAt((byte)0, 0);;
    l0 = BitField.setBitAt(l0, 2);
    Cave cave = new Cave(new byte[] {l0, (byte)0, l0});
    assert cave.canFit(Piece.CROSS, 0, 0);
    assert !cave.canFit(Piece.CROSS, 1, 0);
    assert !cave.canFit(Piece.CROSS, -1, 0);
    assert cave.canFit(Piece.CROSS, -2, 0);
    assert !cave.canFit(Piece.CROSS, 0, 1);
    assert !cave.canFit(Piece.CROSS, 0, -1);

    cave.place(Piece.CROSS, 0, 0);

    for (String line : cave.toString().split("\n")) {
      assert !line.contains(".");
    }
  }
}
