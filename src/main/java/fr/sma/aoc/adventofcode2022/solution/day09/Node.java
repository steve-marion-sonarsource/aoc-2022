package fr.sma.aoc.adventofcode2022.solution.day09;

import java.awt.geom.Point2D;
import java.util.Optional;

public class Node {
  public int x, y;
  private final Node next;

  public Node(int length) {
    this.x = 0;
    this.y = 0;
    if (length > 1) {
      this.next = new Node(length - 1);
    } else {
      this.next = null;
    }
  }

  public Optional<Node> move(int curDx, int curDy) {
    x += curDx;
    y += curDy;
    if (next == null) {
      return Optional.of(this);
    }
    if (distance(this, this.next) > 1.999) {
      int rdx = 0, rdy = 0;
      double bestDistance = 10;
      for (int dx = -1; dx <= 1; dx++) {
        for (int dy = -1; dy <= 1; dy++) {
          double nDistance = distance(this, next.x + dx, next.y + dy);
          if (nDistance < bestDistance) {
            bestDistance = nDistance;
            rdx = dx;
            rdy = dy;
          }
        }
      }
      return next.move(rdx, rdy);
    }
    return Optional.empty();
  }

  private static double distance(Node n1, Node n2) {
    return Point2D.distance(n1.x, n1.y, n2.x, n2.y);
  }

  private static double distance(Node n1, int x, int y) {
    return Point2D.distance(n1.x, n1.y, x, y);
  }
}
