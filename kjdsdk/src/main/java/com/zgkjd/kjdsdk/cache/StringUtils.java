package com.zgkjd.kjdsdk.cache;

/** 字符串工具 厚颜无耻地抄袭了 org.apache.commons.lang.StringUtils */
public class StringUtils {

  public static String leftPad(String str, int size, char padChar) {
    if (str == null) {
      return null;
    }
    int pads = size - str.length();
    if (pads <= 0) {
      return str; // returns original String when possible
    }
    return padding(pads, padChar).concat(str);
  }

  public static String rightPad(String str, int size, char padChar) {
    if (str == null) {
      return null;
    }
    int pads = size - str.length();
    if (pads <= 0) {
      return str; // returns original String when possible
    }
    return str.concat(padding(pads, padChar));
  }

  private static String padding(int repeat, char padChar) throws IndexOutOfBoundsException {
    if (repeat < 0) {
      throw new IndexOutOfBoundsException("Cannot pad a negative amount: " + repeat);
    }
    final char[] buf = new char[repeat];
    for (int i = 0; i < buf.length; i++) {
      buf[i] = padChar;
    }
    return new String(buf);
  }

}
