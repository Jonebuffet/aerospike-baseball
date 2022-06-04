
package com.aerospike.app.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class NumUtil {

  public NumUtil () {
  }


  // parse modifiers K, M, G, B from strings representing longs
  public static long parseInt(String numStr) {

    String regex = "([0-9\\.]*)([KkMmGgBb])";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(numStr);
    Float num;

    if (matcher.matches()) {

      // num = Long.parseLong(matcher.group(1));
      num = Float.parseFloat(matcher.group(1));
      String multiplier = matcher.group(2);

      if (multiplier.equals("K") || multiplier.equals("k")) {
        num *= 1000;
      }

      if (multiplier.equals("M") || multiplier.equals("m")) {
        num *= 1000000;
      }

      if (multiplier.equals("B") || multiplier.equals("b") ||
          multiplier.equals("G") || multiplier.equals("g")) {
        num *= 1000000000;
      }

    } else {

      num = Float.parseFloat(numStr);

    }

    return num.intValue();

  } // parseInt

  
  // parse modifiers K, M, G, B from strings representing longs
  public static long parseLong(String numStr) {

    String regex = "([0-9\\.]*)([KkMmGgBb])";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(numStr);
    Float num;

    if (matcher.matches()) {

      // num = Long.parseLong(matcher.group(1));
      num = Float.parseFloat(matcher.group(1));
      String multiplier = matcher.group(2);

      if (multiplier.equals("K") || multiplier.equals("k")) {
        num *= 1000;
      }

      if (multiplier.equals("M") || multiplier.equals("m")) {
        num *= 1000000;
      }

      if (multiplier.equals("B") || multiplier.equals("b") ||
          multiplier.equals("G") || multiplier.equals("g")) {
        num *= 1000000000;
      }

    } else {

      num = Float.parseFloat(numStr);

    }

    return num.longValue();

  } // parseLong
  

  // converts float with 8 place precision to a long *10^8
  // "118.12345678" becomes 11812345678
  // avoids float imprecision issues
  public static long strToLongX100M (String str) {
    String tmpStr = str.replace(".", "");
    return Long.parseLong(tmpStr);
  }
  public static String longDiv100MToStr (long lng) {
    String tmpStr = Long.toString(lng);
    int len = tmpStr.length();
    return tmpStr.substring(0, len-8) + "." + tmpStr.substring(len-8);
  }


} // NumUtil
