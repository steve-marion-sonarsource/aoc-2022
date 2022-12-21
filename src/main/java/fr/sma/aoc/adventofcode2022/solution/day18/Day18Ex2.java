package fr.sma.aoc.adventofcode2022.solution.day18;

import fr.sma.aoc.adventofcode2022.ExSolution;
import fr.sma.aoc.adventofcode2022.ResolveApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

@Component
public class Day18Ex2 implements ExSolution {

  public static final char AIR = 'A';
  public static final char LAVA = 'L';

  public static void main(String[] args) {
    SpringApplication.run(ResolveApplication.class, "18", "2");
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
    char[][][] droplet = new char[maxX+3][maxY+3][maxZ+3];
    for(String l : input.trim().split("\n")) {
      String[] posText = l.split(",");
      int x = Integer.parseInt(posText[0]);
      int y = Integer.parseInt(posText[1]);
      int z = Integer.parseInt(posText[2]);
      droplet[x+1][y+1][z+1] = LAVA;
    }
    fillAir(droplet,0, 0, 0);
    int count = 0;
    for (int x = 0; x < droplet.length; x++) {
      for (int y = 0; y < droplet[x].length; y++) {
        for (int z = 0; z < droplet[x][y].length; z++) {
          if(droplet[x][y][z] == LAVA) {
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

  private void fillAir(char[][][] droplet, int x, int y, int z) {
    if(x < 0 || x >= droplet.length){
      return;
    }
    if(y < 0 || y >= droplet[x].length){
      return;
    }
    if(z < 0 || z >= droplet[x][y].length){
      return;
    }
    if(droplet[x][y][z] == 0) {
      droplet[x][y][z] = AIR;
      fillAir(droplet, x-1, y, z);
      fillAir(droplet, x, y-1, z);
      fillAir(droplet, x, y+1, z);
      fillAir(droplet, x, y, z-1);
      fillAir(droplet, x, y, z+1);
      fillAir(droplet, x+1, y, z);
    }
  }

  private static boolean isEmpty(char[][][] droplet, int x, int y, int z) {
    if(x < 0 || x >= droplet.length){
      return true;
    }
    if(y < 0 || y >= droplet[x].length){
      return true;
    }
    if(z < 0 || z >= droplet[x][y].length){
      return true;
    }
    return droplet[x][y][z] == AIR;
  }
}
