package fr.sma.aoc.adventofcode2022.solution.day16;

import java.util.Arrays;

public class BitField {
  private final byte[] data;

  private final int size;

  public BitField(int size) {
    this.size = size;
    data = new byte[size / 8 + 1];
    Arrays.fill(data, (byte) 0);
  }

  private BitField(BitField other) {
    data = Arrays.copyOf(other.data, other.data.length);
    this.size = other.size;
  }

  public void set(int pos) {
    int index = pos / 8;
    int offset = pos % 8;
    data[index] = BitField.setBitAt(data[index], offset);
  }

  public void reset(int pos) {
    int index = pos / 8;
    int offset = pos % 8;
    data[index] = BitField.resetBitAt(data[index], offset);
  }

  public boolean get(int pos) {
    int index = pos / 8;
    int offset = pos % 8;
    return BitField.readBitAt(data[index], offset);
  }

  public BitField copy() {
    return new BitField(this);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    BitField bitField = (BitField) o;

    return Arrays.equals(data, bitField.data);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(data);
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    for (byte c : data) {
      sb.append(toString(c)).append(" ");
    }
    return sb.toString();
  }

  public int getSize() {
    return size;
  }

  public static String toString(byte data) {
    return String.format("%8s", Integer.toBinaryString(data)).replace(" ", "0");
  }

  public static byte resetBitAt(byte datum, int offset) {
    return (byte) (datum & ~(1 << offset));
  }

  public static boolean readBitAt(byte datum, int offset) {
    return (datum & 0xff & (1 << offset)) != 0;
  }

  public static byte setBitAt(byte datum, int offset) {
    return (byte) (datum & 0xff | (1 << offset));
  }
}
