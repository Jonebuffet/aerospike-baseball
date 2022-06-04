
package com.aerospike.app.util;

import java.time.Instant;
import java.time.temporal.ChronoUnit;


public class Timer {

  Instant begin;


  public Timer() {

    begin = Instant.now();

  } // Timer


  public void reset() {

    begin = Instant.now();

  } // reset


  public long getSecs() {

    Instant end = Instant.now();

    return ChronoUnit.SECONDS.between(begin, end);

  } // getSecs


  public long getMillis() {

    Instant end = Instant.now();

    return ChronoUnit.MILLIS.between(begin, end);

  } // getMillis


  public long getNanos() {

    Instant end = Instant.now();

    return ChronoUnit.NANOS.between(begin, end);

  } // getNanos


  public String getNow() {

    return Instant.now().toString();

  } // getNow


  public String getDuration() {

    Instant end = Instant.now();
    long secs = ChronoUnit.SECONDS.between(begin, end);
    long h = secs / 3600 % 24;
    long m = secs / 60 % 60;
    long s = secs - h*3600 - m*60;

    return String.format("%02d:%02d:%02d", h, m, s);

  } // getDuration


  public String getDurationMs() {

    Instant end = Instant.now();
    long millis = getMillis();
    long h = millis / 1000 / 3600 % 24;
    long m = millis / 1000 / 60 % 60;
    long s = millis / 1000 - h*3600 - m*60;
    long mmm = millis % 1000 ;

    return String.format("%02d:%02d:%02d.%03d", h, m, s, mmm);

  } // getDurationMs


  public String getDurationNanos() {

    Instant end = Instant.now();
    long nanos = getNanos();
    long h = nanos / 1000000 / 3600 % 24;
    long m = nanos / 1000000 / 60 % 60;
    long s = nanos / 1000000 - h*3600 - m*60;
    long mmm = nanos % 1000000 ;

    return String.format("%02d:%02d:%02d.%03d", h, m, s, mmm);

  } // getDurationNanos


} // Timer

