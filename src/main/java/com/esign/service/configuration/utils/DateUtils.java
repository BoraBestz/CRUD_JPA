package com.esign.service.configuration.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

  public static final String PAYMENT_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSX";
  private SimpleDateFormat dtFormat = null;

  /*public DateUtils(){
      throw new IllegalStateException("DateUtils class");
  }*/

  public static boolean isValidDateFormat(String date, String format) {
    try {
      LocalDateTime.parse(date, DateTimeFormatter.ofPattern(format));
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public static String dateInStringFormat() {
    SimpleDateFormat dtFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    String ret = dtFormat.format(new Date());
    return ret;
  }

  public String convertDateToString(Date date, String format) {
    if (date == null) return "";
    /*dtFormat = new SimpleDateFormat(format, new Locale("th", "TH"));*/
    dtFormat = new SimpleDateFormat(format, Locale.US);
    String ret = dtFormat.format(date);
    return ret;
  }
}
