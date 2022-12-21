package fr.sma.aoc.adventofcode2022.solution.day18;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day18Ex1 implements ExSolution {

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "18", "1");
  }

  @Override
  public String run(String input) throws Exception {
    int maxX=0;
    int maxY=0;
    int maxZ=0;
    for(String l : input.trim().split("\n")) {
      String[] posText = l.split(",");
      int x = Integer.parseInt(posText[0]);
      if(maxX < x) {
        maxX = x;
      }
      int y = Integer.parseInt(posText[1]);
      if(maxY < y) {
        maxY = y;
      }
      int z = Integer.parseInt(posText[2]);
      if(maxZ < z) {
        maxZ = z;
      }
    }
    boolean[][][] droplet = new boolean[maxX+1][maxY+1][maxZ+1];
    for(String l : input.trim().split("\n")) {
      String[] posText = l.split(",");
      int x = Integer.parseInt(posText[0]);
      int y = Integer.parseInt(posText[1]);
      int z = Integer.parseInt(posText[2]);
      droplet[x][y][z] = true;
    }
    int count = 0;
    for (int x = 0; x < droplet.length; x++) {
      for (int y = 0; y < droplet[x].length; y++) {
        for (int z = 0; z < droplet[x][y].length; z++) {
          if(droplet[x][y][z]) {
            if (isEmpty(droplet, x-1, y, z)) {
              count++;
            }
            if (isEmpty(droplet, x, y-1, z)) {
              count++;
            }
            if (isEmpty(droplet, x, y+1, z)) {
              count++;
            }
            if (isEmpty(droplet, x, y, z-1)) {
              count++;
            }
            if (isEmpty(droplet, x, y, z+1)) {
              count++;
            }
            if (isEmpty(droplet, x+1, y, z)) {
              count++;
            }
          }
        }
      }
    }
    return String.valueOf(count);
  }

  private static boolean isEmpty(boolean[][][] droplet, int x, int y, int z) {
    if(x < 0 || x >= droplet.length){
      return true;
    }
    if(y < 0 || y >= droplet[x].length){
      return true;
    }
    if(z < 0 || z >= droplet[x][y].length){
      return true;
    }
    return !droplet[x][y][z];
  }
}
