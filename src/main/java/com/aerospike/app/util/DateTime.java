
package com.aerospike.app.util;

import java.time.Instant;


public class DateTime {

  String dateTimeStr;
  String dateStr;
  String timeStr;
  String mm;
  String dd;
  String yyyy;

  Instant instant;

  long tmMilli;
  long tmSec;
  long tmHr;
  long tmDay;

  long tmNano;

  long tmBlockSize;
  long tmBlock;
  long tmOffset;

  public DateTime () {
    this.tmBlockSize = 20L;
  }

  public DateTime (long tmBlockSize) {
    this.tmBlockSize = tmBlockSize;
  }

  public void parse (String dateStr, String timeStr) {

    parse (dateStr + " " + timeStr);

  } // parse


  public void parse (String dateTimeStr) {

    // 12/21/2021 16:32:24.123456789
    // 12/21/21 16:32:24.123456789
    // 2021.12.21 16:32:24.123456789
    // 21.12.21 16:32:24.123456789
    // 21.12.21T16:32:24.123456789Z

    this.dateTimeStr = dateTimeStr;

    // <date> <time>, <date>T<time>, <date>T<time>Z
    String[] dateTimeArr = dateTimeStr.split("[ TZtz]");
    if (dateTimeArr.length != 2) {
      throw new IllegalArgumentException(String.format("Error: bad date-time format (%s); should be \"12/21/2021 16:32:24.123456789\"\n", dateTimeStr));
    }
    this.dateStr = dateTimeArr[0];
    this.timeStr = dateTimeArr[1];

    String[] dateArr = getYyyyMmDd(dateStr);
    yyyy = dateArr[0];
    mm = dateArr[1];
    dd = dateArr[2];

    String tm;
    String nano;
    String[] timeArr = timeStr.split("\\.");
    if (timeArr.length != 2) {
      tm = timeStr;
      nano = "0";
    } else {
      tm = timeArr[0];
      nano = timeArr[1];
    }

    // nano limited to 9 places
    if (nano.length() > 9) {
      nano = nano.substring(0,9);
    }

    String instantStr = yyyy + "-" + mm + "-" + dd + "T" + tm + "." + nano + "Z";
    this.instant = Instant.parse(instantStr);

    this.tmMilli = instant.toEpochMilli();            // epoch down to milli
    this.tmSec = instant.getEpochSecond();            // epoch down to sec
    this.tmHr = tmSec - tmSec % 3600;                 // epoch down to hr
    this.tmDay = tmSec - tmSec % (3600*24);           // epoch down to day
    this.tmNano = Long.parseLong(nano);               // just nanos

    this.tmBlock = tmMilli - tmMilli % tmBlockSize;
    this.tmOffset = tmNano - tmBlock % 1000 * 1000000;

    // dmf: to restore from record values
    long tmSecOrig = tmBlock/1000;
    long nanoOrig = tmBlock%1000*1000000+tmOffset;

  } // parse


  public String[] getYyyyMmDd(String dateStr) {

    // 12/21/2021, 12-21-2021, 12.21.2021
    String[] dateArr = dateStr.split("[./-]");
    if (dateArr.length != 3) {
      throw new IllegalArgumentException(String.format("Error: bad date format (%s); should be \"12/21/2021\"\n", dateStr));
    }
    this.mm = dateArr[0];
    this.dd = dateArr[1];
    this.yyyy = dateArr[2];

    // support yyyy.mm.dd instead of mm/dd/yyyy
    if (mm.length() == 4 || Integer.parseInt(mm) > 12) {
      String tmp = mm;
      mm = dd;
      dd = yyyy;
      yyyy = tmp;
    }

    // support yy instead of yyyy
    if (Integer.parseInt(yyyy) < 100) {
      yyyy = Integer.toString(Integer.parseInt(yyyy) + 2000);
    }

    dateArr[0] = yyyy;
    dateArr[1] = mm;
    dateArr[2] = dd;

    return dateArr;

  } // getYyyyMmDd


  public String getDateTimeStr() {
    return this.dateTimeStr;
  }
  public String getDateStr() {
    return this.dateStr;
  }
  public String getTimeStr() {
    return this.timeStr;
  }
  public String getMm() {
    return this.mm;
  }
  public String getDd() {
    return this.dd;
  }
  public String getYyyy() {
    return this.yyyy;
  }

  public Instant getInstant() {
    return this.instant;
  }
  public long getTmSec() {
    return this.tmSec;
  }
  public long getTmHr() {
    return this.tmHr;
  }
  public long getTmDay() {
    return this.tmDay;
  }
  public long getTmNano() {
    return this.tmNano;
  }

  public long getTmBlock() {
    return this.tmBlock;
  }
  public long getTmOffset() {
    return this.tmOffset;
  }

  public long getTmBlockSize() {
    return this.tmBlockSize;
  }

} // DateTime
