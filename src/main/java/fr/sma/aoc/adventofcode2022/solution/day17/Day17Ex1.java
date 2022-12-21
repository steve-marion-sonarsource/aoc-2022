package fr.sma.aoc.adventofcode2022.solution.day17;

import com.google.common.collect.Iterators;
import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import one.util.streamex.StreamEx;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day17Ex1 implements ExSolution {

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "17", "1");
  }

  @Override
  public String run(String input) throws Exception {
    Cave cave = new Cave();

    List<Jet> iterable = input.trim().chars().mapToObj(j -> j == '<' ? Jet.LEFT : Jet.RIGHT).toList();
    Iterator<Jet> jet = Iterators.cycle(iterable);
    StreamEx.of(Iterators.cycle(Piece.values()))
      .limit(2022)
      .forEachOrdered(p -> {
        int row = -(p.shape.length + 3);
        int column = 2;
        while (true) {
          int nextCol = column + jet.next().direction;
          if (cave.canFit(p, row, nextCol)) {
            column = nextCol;
          }
          if (cave.canFit(p, row + 1, column)) {
            row++;
          } else {
            cave.place(p, row, column);
            break;
          }
        }
      });
    return String.valueOf(cave.height);
  }

  private enum Jet {
    LEFT(-1),
    RIGHT(1);

    public final int direction;

    Jet(int direction) {
      this.direction = direction;
    }
  }

  private enum Piece {
    BAR(new boolean[][]{{true, true, true, true}}),
    CROSS(new boolean[][]{{false, true, false}, {true, true, true}, {false, true, false}}),
    L(new boolean[][]{{false, false, true}, {false, false, true}, {true, true, true}}),
    POLE(new boolean[][]{{true}, {true}, {true}, {true}}),
    SQUARE(new boolean[][]{{true, true}, {true, true}});

    public final boolean[][] shape;

    Piece(boolean[][] shape) {
      this.shape = shape;
    }
  }

  private class Cave {
    private final ArrayList<boolean[]> cave;
    private int height = 0;
    private int width = 7;

    private Cave() {
      this.cave = new ArrayList<>();
      cave.add(new boolean[width]);
      Arrays.fill(cave.get(0), true);
    }

    public boolean canFit(Piece piece, int row, int column) {
      if (column < 0 || column + piece.shape[0].length > width) {
        return false;
      }
      if ((row + piece.shape.length - 1) < 0) {
        return true;
      }
      for (int r = Math.max(0, -row); r < piece.shape.length; r++) {
        for (int c = 0; c < piece.shape[r].length; c++) {
          if (piece.shape[r][c] && cave.get(row + r)[column + c]) {
            return false;
          }
        }
      }
      return true;
    }

    private void expandCave(int nbRows) {
      boolean[][] rows = new boolean[nbRows][width];
      for (var row : rows) {
        Arrays.fill(row, false);
      }
      this.cave.addAll(0, List.of(rows));
      height += nbRows;
    }

    private void trimCave() {
      boolean[] check = new boolean[width];
      Arrays.fill(check, true);
      for (int i = 0; i < cave.size(); i++) {
        for (int c = 0; c < width; c++) {
          check[c] = check[c] && cave.get(i)[c];
        }
        for (int c = 0; c < width - 1; c++) {
          if (check[c] && !check[c + 1] && !cave.get(i)[c + 1]) {
            check[c + 1] = true;
          }
        }
        for (int c = width - 1; c > 0; c--) {
          if (check[c] && !check[c - 1] && !cave.get(i)[c - 1]) {
            check[c - 1] = true;
          }
        }
        boolean open = false;
        for (int c = 0; c < width; c++) {
          open = open || cave.get(i)[c];
        }

        if (!open && i < cave.size() - 1) {
          cave.subList(i + 1, cave.size()).clear();
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
            cave.get(row + r)[column + c] = true;
          }
        }
      }
    }
  }
}
