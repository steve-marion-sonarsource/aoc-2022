package fr.sma.aoc.adventofcode2022.solution.day11;

import java.util.function.BiFunction;
import java.util.function.IntFunction;
import java.util.function.LongFunction;

public class Operation {

  private final LongFunction<Long> leftOperand;
  private final LongFunction<Long> rightOperand;
  private final BiFunction<Long, Long, Long> operator;

  public Operation(String text) {
    String[] splitText = text.split(" ");
    leftOperand = buildOperand(splitText[3]);
    rightOperand = buildOperand(splitText[5]);
    operator = buildOperator(splitText[4]);
  }

  private static BiFunction<Long, Long, Long> buildOperator(String operatorText) {
    return switch (operatorText) {
      case "+" -> Long::sum;
      case "*" -> (a, b) -> a*b;
      default -> throw new IllegalArgumentException("unknown operator " + operatorText);
    };
  }

  private static LongFunction<Long> buildOperand(String operandText) {
    return switch (operandText) {
      case "old" -> i -> i;
      default ->  i -> Long.parseLong(operandText);
    };
  }

  public long operate(long old) {
    return operator.apply(leftOperand.apply(old), rightOperand.apply(old));
  }
}
