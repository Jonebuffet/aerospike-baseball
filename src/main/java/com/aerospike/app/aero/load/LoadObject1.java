
package com.aerospike.app.aero.load;

import com.aerospike.app.aero.ops.OpsObject1;
import com.aerospike.app.model.Object1;
import com.aerospike.app.seed.SeedObject1;
import com.aerospike.app.util.Timer;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException.ScanTerminated;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.policy.WritePolicy;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit ;

import org.apache.log4j.Logger;

public class LoadObject1 {

  static Logger log = Logger.getLogger("LoadObject1");

  AerospikeClient client;
  OpsObject1 opsObject1;


  public LoadObject1 (AerospikeClient client) {
    this.client = client;
    this.opsObject1 = new OpsObject1(client);
  }


  public long load (String ns, String set,
                    long recStart, long recCnt,
                    int threadCnt,
                    int updateRate, boolean verbose) {

    List<Thread> threads = new ArrayList<Thread>();
    long recBlockSize = recCnt / threadCnt;
    long recRemainder = recCnt - recBlockSize * threadCnt;
    long recBlock;

    Runnable[] runnables = new Runnable[threadCnt];
    Thread thread;
    long currCnt = 0;
    long remCnt = 0;                     // remainder recs

    int addOne = 0;

    // loop creating threads to handle blocks of records
    for (int i=0; i<threadCnt; i++) {

      remCnt = recCnt - currCnt;
      recBlock = (i == threadCnt-1) ? recCnt - currCnt : recBlockSize;

      // a large remainder results in a large block size for final thread
      // just add 1 to all threads until that remainder is consumed
      // in essence, spread remainder over as many threads as possible
      if (recRemainder > 0) {
        addOne = 1;
        recRemainder -= 1;
      } else {
        addOne = 0;
      }

      runnables[i] = new Object1LoadTask(client, ns, set,
                                         recStart+currCnt, recBlock+addOne,
                                         verbose, updateRate);
      currCnt += recBlock + addOne;

      // new Thread(r).start();
      thread = new Thread(runnables[i]);
      threads.add(thread);
      thread.start();

    } // for i

    for (Thread thread1 : threads) {
      try {
        thread1.join();
      } catch (Exception e) {
        System.out.printf("Error: %s\n\n", e);
        e.printStackTrace();
      }
    }

    long sumCnt = 0;
    for (int i=0; i<threadCnt; i++) {
      sumCnt += ((Object1LoadTask)runnables[i]).getTotalCnt();
    }

    return sumCnt;

  } // load


  private class Object1LoadTask implements Runnable {

    Logger log = Logger.getLogger("LoadObject1.run");

    AerospikeClient client;
    String ns;
    String set;

    SeedObject1 seedObject1;
    Object1 object1;
    long recStart;
    long recCnt;
    boolean verbose;
    int updateRate;

    long totalCnt = 0;
    long toTotal = 0;
    long errTotal = 0;


    public Object1LoadTask (AerospikeClient client, String ns, String set,
                            long recStart, long recCnt,
                            boolean verbose, int updateRate) {

      this.seedObject1 = new SeedObject1();

      this.client = client;
      this.ns = ns;
      this.set = set;
      this.object1 = seedObject1.get();
      this.recStart = recStart;
      this.recCnt = recCnt;
      this.updateRate = updateRate;
      this.verbose = verbose;

      // long threadId = Thread.currentThread().getId();
      log.debug(String.format("created Object1LoadTask recStart:%d recCnt:%d", recStart, recCnt));

    }


    public long getStartCnt () {
      return this.recStart;
    }


    public long getRecCnt () {
      return this.recCnt;
    }


    public long getTotalCnt () {
      return this.totalCnt;
    }


    public void run () {

      String field1;
      String field2;
      Object1.ObjType objType;
      int int1;

      long cnt = 0;
      long to = 0;
      long err = 0;

      long millis;
      long millisTotal;
      double wps;
      double tps;

      String ukey;
      WritePolicy writePolicy = client.getWritePolicyDefault();
      Key key;
      Bin[] bins;

      long threadId = Thread.currentThread().getId();
      log.debug(String.format("run thread:%d cnt:%d start:%d", threadId, recCnt, recStart));

      Timer timerTotal = new Timer();
      Timer timerCnt = new Timer();

      for (cnt=recStart; totalCnt<recCnt; cnt++, totalCnt++) {

        field1 = String.valueOf(cnt);
        field2 = String.valueOf(cnt*10);
        if (cnt % 3 == 0) {
          objType = Object1.ObjType.T1;
        } else if (cnt % 3 == 1) {
          objType = Object1.ObjType.T2;
        } else {
          objType = Object1.ObjType.T3;
        }
        int1 = (int) cnt;

        seedObject1.update(object1, field1, field2, objType, int1);

        try {

          ukey = String.valueOf(cnt);
          opsObject1.put(ns, set, ukey, object1);

        } catch (AerospikeException e) {

          if (e instanceof AerospikeException.Timeout) {
            log.warn(String.format("put %d timedout: %s", totalCnt, e));
            to += 1;
            toTotal += 1;

          }

          else {
            log.error(String.format("put %d failure: %s", totalCnt, e));
            err += 1;
            errTotal += 1;
          }

        } // catch

        if (verbose && timerCnt.getMillis() >= updateRate) {

          millis = timerCnt.getMillis();
          millisTotal = timerTotal.getMillis();
          wps = (cnt - recStart) * 1000 / (millis == 0 ? 1 : millis);
          tps = totalCnt * 1000 / (millisTotal == 0 ? 1 : millisTotal);
          log.info(String.format("tid:%d "+
                                 "write(wps=%.0f to=%d err=%d cnt=%d) "+
                                 "read(rps=%d to=%d err=%d cnt=%d) "+
                                 "total(tps=%.0f to=%d err=%d cnt=%d)",
                                 threadId,
                                 wps, to, err, cnt-recStart,
                                 0, 0, 0, 0,
                                 tps, toTotal, errTotal, totalCnt));

          timerCnt.reset();
          cnt = recStart;
          to = 0;
          err = 0;

        } // timerCnt >= updateRate

      } // for cnt

      // if (verbose && (cnt - recStart) > 0) {
      //   millis = timerCnt.getMillis();
      //   millisTotal = timerTotal.getMillis();
      //   wps = (cnt - recStart) * 1000 / (millis == 0 ? 1 : millis);
      //   tps = totalCnt * 1000 / (millisTotal == 0 ? 1 : millisTotal);
      //   log.info(String.format(//"%d,%d "+
      //                          "write(wps=%.0f to=%d err=%d cnt=%d) "+
      //                          "read(rps=%d to=%d err=%d cnt=%d) "+
      //                          "final(tps=%.0f to=%d err=%d cnt=%d)",
      //                          //threadId, timerCnt.getMillis(),
      //                          wps, to, err, cnt-recStart,
      //                          0, 0, 0, 0,
      //                          tps, toTotal, errTotal, totalCnt));
      // }

      return;

    } // run

  } // Object1LoadTask


} // LoadObject1
