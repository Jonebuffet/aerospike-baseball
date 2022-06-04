
package com.aerospike.app.aero;

import com.aerospike.app.aero.load.LoadObject1;
import com.aerospike.app.aero.load.LoadPlayerInfo;
import com.aerospike.app.aero.load.LoadPlayerYearlyBattingStats;
import com.aerospike.app.aero.load.LoadPlayerPostSeasonBattingStats;

import com.aerospike.app.aero.ops.OpsObject1;
import com.aerospike.app.aero.ops.OpsPlayerInfo;
import com.aerospike.app.aero.ops.OpsPlayerYearlyBattingStats;
import com.aerospike.app.aero.ops.OpsPlayerPostSeasonBattingStats;

import com.aerospike.app.aero.serde.SerdeObject1;
import com.aerospike.app.aero.serde.SerdePlayerInfo;
import com.aerospike.app.aero.serde.SerdePlayerYearlyBattingStats;
import com.aerospike.app.aero.serde.SerdePlayerPostSeasonBattingStats;

import com.aerospike.app.model.Object1;
import com.aerospike.app.model.PlayerInfo;
import com.aerospike.app.model.PlayerYearlyBattingStats;
import com.aerospike.app.model.PlayerPostSeasonBattingStats;

import com.aerospike.app.seed.SeedObject1;
import com.aerospike.app.util.Timer;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException.ScanTerminated;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.policy.BatchPolicy;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.CommitLevel;
import com.aerospike.client.policy.InfoPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.RecordExistsAction;
import com.aerospike.client.policy.ScanPolicy;
import com.aerospike.client.policy.WritePolicy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;


public class Aero {

  static Logger log = Logger.getLogger("Aero");

  public AerospikeClient client;
  public LoadObject1 loadObject1;
  
  public LoadPlayerInfo loadPlayerInfo;
  public LoadPlayerYearlyBattingStats loadPlayerYearlyBattingStats;
  public LoadPlayerPostSeasonBattingStats loadPlayerPostSeasonBattingStats;
  
  public OpsPlayerInfo opsPlayerInfo;
  public OpsPlayerYearlyBattingStats opsPlayerYearlyBattingStats;
  public OpsPlayerPostSeasonBattingStats opsPlayerPostSeasonBattingStats;
 
  public SerdePlayerInfo serdePlayerInfo;
  public SerdePlayerYearlyBattingStats serdePlayerYearlyBattingStats;
  public SerdePlayerPostSeasonBattingStats serdePlayerPostSeasonBattingStats;
  
  public long scanLimit = 0;
  public long scanCnt = 0;


  public Aero (String host, int port) {

    log.info(String.format("  host\t\t\t%s\n"+
                           "  port\t\t\t%d\n"+
                           "",
                           host, port));

    this.client = connect(host, port);

    this.loadPlayerInfo = new LoadPlayerInfo(client);
    this.opsPlayerInfo = new OpsPlayerInfo(client);
    this.serdePlayerInfo = new SerdePlayerInfo();
    
    this.loadPlayerYearlyBattingStats = new LoadPlayerYearlyBattingStats(client);
    this.serdePlayerYearlyBattingStats = new SerdePlayerYearlyBattingStats();
    this.opsPlayerYearlyBattingStats = new OpsPlayerYearlyBattingStats(client);
    
    this.loadPlayerPostSeasonBattingStats = new LoadPlayerPostSeasonBattingStats(client);
    this.serdePlayerPostSeasonBattingStats = new SerdePlayerPostSeasonBattingStats();
    this.opsPlayerPostSeasonBattingStats = new OpsPlayerPostSeasonBattingStats(client);

  } // Aero


  public AerospikeClient connect (String host, int port) {

    Policy readPolicy = new Policy();
    readPolicy.socketTimeout = 3000; // ms
    readPolicy.totalTimeout = 9000; // ms
    readPolicy.sendKey = true;

    WritePolicy writePolicy = new WritePolicy();
    writePolicy.durableDelete = false;
    writePolicy.commitLevel = CommitLevel.COMMIT_ALL; // vs COMMIT_MASTER
    writePolicy.expiration = 0; // -2 no chng, -1 never, 0 ns config, >0 ttl
    writePolicy.respondAllOps = true;
    // UPDATE, REPLACE, CREATE_ONLY, UPDATE_ONLY
    writePolicy.recordExistsAction = RecordExistsAction.UPDATE;
    writePolicy.sendKey = true;
    

    ScanPolicy scanPolicy = new ScanPolicy();
    scanPolicy.maxRecords = 0; // no limit
    scanPolicy.recordsPerSecond = 0; // no limit
    scanPolicy.concurrentNodes = true;

    BatchPolicy batchPolicy = new BatchPolicy();
    batchPolicy.allowInline = true;
    batchPolicy.maxConcurrentThreads = 300;
    batchPolicy.sendSetName = true;
    
    ClientPolicy clientPolicy = new ClientPolicy();
    clientPolicy.readPolicyDefault = readPolicy;
    clientPolicy.writePolicyDefault = writePolicy;
    clientPolicy.batchPolicyDefault = batchPolicy;
    clientPolicy.scanPolicyDefault = scanPolicy;
    clientPolicy.timeout = 1000; // ms

    // clientPolicy.clusterName = "myCluster";
    // clientPolicy.user = user;
    // clientPolicy.password = password;

    return new AerospikeClient(clientPolicy, host, port);

  } // connect

 /* 
  public void put (String ns, String set, String ukey,
                   String f1, String f2, String typ, int int1,
                   String strLstStr) {

    log.info(String.format("  ns\t\t\t%s\n"+
                           "  set\t\t\t%s\n"+
                           "  ukey\t\t\t%s\n",
                           ns, set, ukey));

    Object1.ObjType otyp = Object1.getObjType(typ);
    List<String> strLst = new ArrayList<>();
    for (String str : strLstStr.split(",")) {
      strLst.add(str);
    }

    Object1 object1 = new Object1(f1, f2, otyp, int1, strLst);

    opsObject1.put(ns, set, ukey, object1);

    log.info(String.format("Wrote 1 record to %s.%s", ns, set));

  } // put
*/

  
  public void put (String ns, String set, String ukey, PlayerInfo pInfo) {

    log.info(String.format("  ns\t\t\t%s\n"+
                           "  set\t\t\t%s\n"+
                           "  ukey\t\t\t%s\n",
                           ns, set, ukey));

    opsPlayerInfo.put(ns, set, ukey, pInfo);

    log.info(String.format("Wrote 1 record to %s.%s", ns, set));

  } // put

  public void put (String ns, String set, String ukey, PlayerYearlyBattingStats pYrlyBattingStats) {

	    log.info(String.format("  ns\t\t\t%s\n"+
	                           "  set\t\t\t%s\n"+
	                           "  ukey\t\t\t%s\n" +
	                           "  pYrlyBattingStatsp\t\t\t%s\n",
	                           ns, set, ukey, pYrlyBattingStats.toString()));

	    //Object1 object1 = new Object1(f1, f2, otyp, int1, strLst);
	    
	    opsPlayerYearlyBattingStats.put(ns, set, ukey, pYrlyBattingStats);

	    log.info(String.format("Wrote 1 record to %s.%s", ns, set));

	  } // put
  
  public void put (String ns, String set, String ukey, PlayerPostSeasonBattingStats plyPSStats) {

	    log.info(String.format("  ns\t\t\t%s\n"+
	                           "  set\t\t\t%s\n"+
	                           "  ukey\t\t\t%s\n" +
	                           "  pYrlyBattingStatsp\t\t\t%s\n",
	                           ns, set, ukey, plyPSStats.toString()));

	    //Object1 object1 = new Object1(f1, f2, otyp, int1, strLst);
	    
	    opsPlayerPostSeasonBattingStats.put(ns, set, ukey, plyPSStats);

	    log.info(String.format("Wrote 1 record to %s.%s", ns, set));

	  } // put
  
  /*
  public void put (String ns, String set, String ukey, PlayerInfo pInfo) {

	log.info(String.format("  ns\t\t\t%s\n"+
                  "  set\t\t\t%s\n"+
                  "  ukey\t\t\t%s\n",
                  ns, set, ukey));

	Object1.ObjType otyp = Object1.getObjType(typ);
	List<String> strLst = new ArrayList<>();
	for (String str : strLstStr.split(",")) {
		strLst.add(str);
	}

	Object1 object1 = new Object1(f1, f2, otyp, int1, strLst);

	psPlayerInfo.put(ns, set, ukey, PlayerInfo);

	log.info(String.format("Wrote 1 record to %s.%s", ns, set));

} // put
*/
  
  public PlayerInfo	getPlayerInfo (String ns, String set, String ukey) {

    log.info(String.format("  ns\t\t\t%s\n"+
                           "  set\t\t\t%s\n"+
                           "  ukey\t\t\t%s\n",
                           ns, set, ukey));

    PlayerInfo playerInfo = opsPlayerInfo.get(ns, set, ukey);

    if (playerInfo != null) {
      log.info(String.format("%s", playerInfo));
    } else {
      log.info(String.format("ukey %s not found", ukey));
    }

    return playerInfo;

  } // get

  public PlayerYearlyBattingStats getPlayerYearlyBattingStats (String ns, String set, String ukey) {

	log.info(String.format("  ns\t\t\t%s\n"+
	                       "  set\t\t\t%s\n"+
	                       "  ukey\t\t\t%s\n",
	                       ns, set, ukey));

	PlayerYearlyBattingStats plBattingStats = opsPlayerYearlyBattingStats.get(ns, set, ukey);

	if (plBattingStats != null) {
	  log.info(String.format("%s", plBattingStats));
	} else {
	  log.info(String.format("ukey %s not found", ukey));
	}

	return plBattingStats;

} // get

  public void getBatch (String ns, String set, String ukeys) {

    String[] ukeyArr = ukeys.split(",");

    log.info(String.format("  ns\t\t\t%s\n"+
                           "  set\t\t\t%s\n"+
                           "  ukeys\t\t\t%s\n",
                           ns, set, ukeys));

    PlayerInfo[] playinfos = opsPlayerInfo.getBatch(ns, set, ukeys);

    for (int i=0; i<playinfos.length; i++) {

      if (playinfos[i] != null) {
        log.info(String.format("%s",playinfos[i]));
      }      else {
        log.info(String.format("ukey %s not found", ukeyArr[i]));
      }

    }

  } // getBatch

  public void load (String ns, String set,
                    long recStart, long recCnt,
                    int threadCnt,
                    int updateRate, boolean verbose) {

    log.info(String.format("  ns\t\t\t%s\n"+
                           "  set\t\t\t%s\n"+
                           "  recStart\t\t%d\n"+
                           "  recCnt\t\t%d\n"+
                           "  threadCnt\t\t%d\n"+
                           "  updateRate\t\t\t%d\n"+
                           "  verbose\t\t%s\n"+
                           "",
                           ns, set,
                           recStart, recCnt,
                           threadCnt,
                           updateRate, verbose));

    Timer timer = new Timer();

    long loadCnt = loadObject1.load(ns, set,
                                    recStart, recCnt,
                                    threadCnt,
                                    updateRate, verbose);

    long millis = timer.getMillis();
    double rate = loadCnt * 1000 / (millis == 0 ? 1 : millis);

    log.info(String.format("Wrote %,d records in %s hms @ %.0f rec/s",
                           loadCnt, timer.getDuration(), rate));

  } // load

  public void load (String ns, String set, String file) {

	  log.info(String.format("  ns\t\t\t%s\n"+
                 "  set\t\t\t%s\n"+
                 "  file\t\t%s\n"+
                 "",
                 ns, set,
                 file));
	  
	  long loadCnt = 0;
	  
	  if (file.contentEquals("/Users/johnwalker/Development/projects/baseball/ProBaseballStats/data/2015/the-history-of-baseball/split_data/batting.csv")) {
		  //TODO: Include load batting logic.
		  loadCnt = loadPlayerYearlyBattingStats.load(ns, set, file);
	  }
	  else if (file.contentEquals("/Users/johnwalker/Development/projects/baseball/ProBaseballStats/data/2015/the-history-of-baseball/split_data/player.csv")) {
		  //TODO: Include load batting logic.
		  loadCnt = loadPlayerInfo.load(ns, set, file);
	  }
	  else if (file.contentEquals("/Users/johnwalker/Development/projects/baseball/ProBaseballStats/data/2015/the-history-of-baseball/split_data/batting_postseason.csv")) {
		  loadCnt = loadPlayerPostSeasonBattingStats.load(ns, set, file);
	  }
	  Timer timer = new Timer();

	  long millis = timer.getMillis();
	  double rate = loadCnt * 1000 / (millis == 0 ? 1 : millis);

	  log.info(String.format("Wrote %,d records in %s hms @ %.0f rec/s",
                 loadCnt, timer.getDuration(), rate));

} // load

  public void scan (String ns, String set, long limit) {

    this.scanLimit = limit;

    log.info(String.format("  ns\t\t\t%s\n"+
                           "  set\t\t\t%s\n"+
                           "  limit\t\t\t%d\n",
                           ns, set, this.scanLimit));

    opsPlayerInfo.scan(ns, set, this::scanCB);

  } // scan

  public void scanCB (Key key, Record record) {

    // log.info(String.format("%s", record));
    PlayerInfo playerInfo = SerdePlayerInfo.deserialize(record);
    log.info(String.format("%s", playerInfo));

    if (this.scanLimit > 0 && this.scanCnt++ >= this.scanLimit-1) {
      throw new ScanTerminated();
    }

  } // scanCB

  public void trunc (String ns, String sets) {

    log.info(String.format("  ns\t\t\t%s\n"+
                           "  set\t\t\t%s\n",
                           ns, sets));

    InfoPolicy infoPolicy = new InfoPolicy();
    infoPolicy.timeout = 1000; // ms

    Calendar calendar = null; // truncate since some time

    for (String set : sets.split(",")) {
      this.client.truncate(infoPolicy, ns, set, calendar);
      log.info(String.format("Truncated set %s:%s", ns, set));
    }

  } // trunc

  public void close () {

    if (this.client != null) {
      this.client.close();
    }

  } // close


} // Aero
