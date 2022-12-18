package fr.sma.aoc.adventofcode2022.solution.day13;

import java.util.Iterator;

interface Elem extends Comparable<Elem> {
  static int compare(Elem left, Elem right) {
    if (left instanceof ValueElem) {
      if (right instanceof ValueElem) {
        return Integer.compare(((ValueElem) left).value(), ((ValueElem) right).value());
      } else {
        return Elem.compare(new ListElem(left), right);
      }
    } else {
      if (right instanceof ValueElem) {
        return Elem.compare(left, new ListElem(right));
      } else {
        Iterator<Elem> leftIte = ((ListElem) left).elements().listIterator();
        Iterator<Elem> rightIte = ((ListElem) right).elements().listIterator();
        while (leftIte.hasNext() && rightIte.hasNext()) {
          int r = compare(leftIte.next(), rightIte.next());
          if (r != 0) {
            return r;
          }
        }
        if (leftIte.hasNext()) {
          return 1;
        } else if (rightIte.hasNext()) {
          return -1;
        } else {
          return 0;
        }
      }
    }
  }
  default int compareTo(Elem o) {
    return Elem.compare(this, o);
  }
}
