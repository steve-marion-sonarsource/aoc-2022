package fr.sma.aoc.adventofcode2022.solution.day17;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day17Ex2 implements ExSolution {

  public static long nbFall = 1_000_000_000_000L;

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "17", "2");
  }

  @Override
  public String run(String input) throws Exception {
    Cave cave = new Cave();

    List<Jet> jets = input.trim().chars().mapToObj(j -> j == '<' ? Jet.LEFT : Jet.RIGHT).toList();
    int nbPieces = Piece.values().length;
    int cycleMinLength = nbPieces * jets.size();

    ArrayList<CaveState>[] previousStates = new ArrayList[cycleMinLength];
    Arrays.setAll(previousStates, i -> new ArrayList<>());

    int jetIndex = 0;
    for (int pIndex = 0; pIndex < nbPieces && cave.getNbPieceAdded() < nbFall; pIndex = (pIndex + 1) % nbPieces) {
      Piece p = Piece.values()[pIndex];
      int row = -(p.shape.length + 3);
      int column = 2;
      while (true) {
        Jet next = jets.get(jetIndex);
        jetIndex = (jetIndex + 1) % jets.size();
        int nextCol = column + next.direction;
        if (cave.canFit(p, row, nextCol)) {
          column = nextCol;
        }
        if (cave.canFit(p, row + 1, column)) {
          row++;
        } else {
          cave.place(p, row, column);
          if (previousStates != null) {
            ArrayList<CaveState> previousHashes = previousStates[(jets.size()*pIndex + jetIndex)];
            for (int i = 0; i < previousHashes.size(); i++) {
              CaveState prev = previousHashes.get(i);
              if (Arrays.equals(prev.caveHash, cave.getHash())) {
                long heightDiff = cave.getHeight() - prev.height;
                long cycleLength = cave.getNbPieceAdded() - prev.nbPiece;
                long nbCycleToSkip = (nbFall - cave.getNbPieceAdded()) / cycleLength;
                System.out.printf("found cycle of length %d at fall nb %d%n", cycleLength, cave.getNbPieceAdded());
                cave.skip(nbCycleToSkip * cycleLength, nbCycleToSkip * heightDiff);
                previousStates = null;
                break;
              }
            }
            previousHashes.add(0, new CaveState(cave.getHash(), cave.getHeight(), cave.getNbPieceAdded()));
          }
          break;
        }
      }
    }
    System.out.printf("height : %d%n", cave.getNbPieceAdded());
    return String.valueOf(cave.getHeight());
  }

  private record CaveState(byte[] caveHash, long height, long nbPiece) {
  }

  private enum Jet {
    LEFT(-1),
    RIGHT(1);

    public final int direction;

    Jet(int direction) {
      this.direction = direction;
    }
  }

}
