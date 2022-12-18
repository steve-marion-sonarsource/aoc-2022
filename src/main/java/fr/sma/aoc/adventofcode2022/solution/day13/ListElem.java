package fr.sma.aoc.adventofcode2022.solution.day13;

import com.google.common.collect.Iterators;
import com.google.common.collect.PeekingIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

record ListElem(List<Elem> elements) implements Elem {
  public ListElem(Elem... elems) {
    this(List.of(elems));
  }

  public static ListElem parse(String input) {
    return ListElem.parse(Iterators.peekingIterator(input.chars().skip(1).mapToObj(c -> (char) c).iterator()));
  }
  public static ListElem parse(PeekingIterator<Character> data) {
    List<Elem> elems = new ArrayList<>();
    do {
      char next = data.next();
      if (next == ']') {
        return new ListElem(elems);
      } else if (next == '[') {
        elems.add(ListElem.parse(data));
      } else if (next >= '0' && next <= '9') {
        String value = String.valueOf(next);
        if (data.hasNext() && data.peek() >= '0' && data.peek() <= '9') {
          value = value + data.next();
        }
        elems.add(new ValueElem(Integer.parseInt(value)));
      }
    } while (data.hasNext());
    return new ListElem(elems);
  }

  public boolean isDivider() {
    return (elements.size() == 1
      && elements.get(0) instanceof ListElem le
      && le.elements.size() == 1
      && le.elements.get(0) instanceof ValueElem ve
      && (ve.value() == 2 || ve.value() == 6));
  }

  @Override
  public String toString() {
    return elements.stream().map(Elem::toString).collect(Collectors.joining(", ", "[", "]"));
  }
}
