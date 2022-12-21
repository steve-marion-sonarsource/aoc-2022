package fr.sma.aoc.adventofcode2022.solution.day17;

import fr.sma.aoc.adventofcode2022.solution.day16.BitField;
import java.util.Arrays;

class Cave {
  private static int width = 7;

  private byte[] cave;
  private int size;
  private long height = 0;
  private long nbPieceAdded = 0;

  public Cave() {
    this.cave = new byte[100];
    cave[0] = 0b0111_1111;
    this.size = 1;
  }

  Cave(byte[] initialCave) {
    this.cave = Arrays.copyOf(initialCave, initialCave.length);
    this.size = initialCave.length;
  }

  public boolean canFit(Piece piece, int row, int column) {
    if (column < 0 || column + piece.shape[0].length > width) {
      return false;
    }
    if ((row + piece.shape.length - 1) < 0) {
      return true;
    }
    if ((row + piece.shape.length) > size) {
      return false;
    }
    for (int r = Math.max(0, -row); r < piece.shape.length; r++) {
      for (int c = 0; c < piece.shape[r].length; c++) {
        if (piece.shape[r][c] && BitField.readBitAt(cave[row + r], column + c)) {
          return false;
        }
      }
    }
    return true;
  }

  private void expandCave(int nbRows) {
    byte[] newCave;
    if (nbRows + size > cave.length) {
      newCave = new byte[cave.length * 2];
    } else {
      newCave = cave;
    }
    System.arraycopy(cave, 0, newCave, nbRows, size);
    Arrays.fill(cave, 0, nbRows, (byte) 0);
    this.cave = newCave;
    this.size += nbRows;
    height += nbRows;
  }

  private void trimCave() {
    boolean[] check = new boolean[width];
    Arrays.fill(check, true);
    for (int i = 0; i < size; i++) {
      for (int c = 0; c < width; c++) {
        check[c] = check[c] && !BitField.readBitAt(cave[i], c);
      }
      for (int c = 0; c < width - 1; c++) {
        if (check[c] && !check[c + 1] && !BitField.readBitAt(cave[i], c + 1)) {
          check[c + 1] = true;
        }
      }
      for (int c = width - 1; c > 0; c--) {
        if (check[c] && !check[c - 1] && !BitField.readBitAt(cave[i], c - 1)) {
          check[c - 1] = true;
        }
      }
      boolean open = false;
      for (int c = 0; c < width; c++) {
        open = open || check[c];
      }

      if (!open && i < size - 1) {
        //System.out.printf("trimming %d lines, height is %d%n", cave.size() - i, height);
        size = i + 1;
        return;
      }
    }
  }

  public void place(Piece piece, int row, int column) {
    if (row < 0) {
      expandCave(-row);
      row = 0;
    }
    for (int r = 0; r < piece.shape.length; r++) {
      for (int c = 0; c < piece.shape[r].length; c++) {
        if (piece.shape[r][c]) {
          cave[row + r] = BitField.setBitAt(cave[row + r], column + c);
        }
      }
    }
    trimCave();
    nbPieceAdded++;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < Math.min(100, size); i++) {
      sb.append(String.format("%7s", Integer.toBinaryString(cave[i])).replace(" ", "0"))
        .append("\n");
    }
    return sb.toString();
  }

  public long getHeight() {
    return height;
  }

  public byte[] getHash() {
    return Arrays.copyOf(cave, size);
  }

  public long getNbPieceAdded() {
    return nbPieceAdded;
  }

  public void skip(long nbPiece, long heightGained) {
    height += heightGained;
    nbPieceAdded += nbPiece;
  }
}
