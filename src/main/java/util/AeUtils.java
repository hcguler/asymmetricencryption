package util;

public class AeUtils {
  public static String ByteToString(byte[] buffer) {
    StringBuilder s = new StringBuilder();
    for (byte b : buffer) {
      String temp = Integer.toHexString(0x00FF & b);
      if (temp.length() == 1) {
        s.append("0" + temp);
      } else {
        s.append(temp);
      }
    }
    return s.toString();
  }
}
