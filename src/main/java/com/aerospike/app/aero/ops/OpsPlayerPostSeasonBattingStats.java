package com.aerospike.app.aero.ops;

import org.apache.log4j.Logger;

import com.aerospike.app.aero.serde.SerdePlayerInfo;
import com.aerospike.app.aero.serde.SerdePlayerPostSeasonBattingStats;
import com.aerospike.app.model.PlayerPostSeasonBattingStats;
import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Operation;
import com.aerospike.client.Record;
import com.aerospike.client.ScanCallback;
import com.aerospike.client.AerospikeException.ScanTerminated;
import com.aerospike.client.exp.Exp;
import com.aerospike.client.exp.Expression;
import com.aerospike.client.policy.BatchPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.ScanPolicy;
import com.aerospike.client.policy.WritePolicy;

public class OpsPlayerPostSeasonBattingStats {
	  static Logger log = Logger.getLogger("OpsPlayerInfo");

	  public AerospikeClient client;
	  SerdePlayerInfo sdPlayerInfo;


	  public OpsPlayerPostSeasonBattingStats (AerospikeClient client) {
	    this.client = client;
	    this.sdPlayerInfo = new SerdePlayerInfo();
	  }


	  public void put (String ns, String set, String ukey, PlayerPostSeasonBattingStats pPSBattingStats) {
		
	    WritePolicy writePolicy = client.getWritePolicyDefault();
	    Key key = new Key (ns, set, ukey);
	    Bin[] bins = SerdePlayerPostSeasonBattingStats.serialize(pPSBattingStats);

	    client.put(writePolicy, key, bins);

	  } // put


	  public PlayerPostSeasonBattingStats get (String ns, String set, String ukey) {
	   
	    Policy readPolicy = client.getReadPolicyDefault();
	    Key key = new Key (ns, set, ukey);

	    Expression expSiz =
	      Exp.build(
	                Exp.deviceSize()
	                );
	    
	    Operation[] ops = new Operation[28];
	    int onx = 0;
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.PLAYERID);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.YEARID);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.AB);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.BB);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.CS);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.DOUBLES);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.GAMES);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.GIDP);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.HBP);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.HITS);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.HR);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.BA);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.IBB);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.LGID);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.RBI);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.RUNS);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.SB);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.SF);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.SH);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.SO);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.STINT);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.TEAMID);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.TRIPLES);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.BBREFID);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.OBP);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.SLG);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.OPS);
	    ops[onx++] = Operation.get(SerdePlayerPostSeasonBattingStats.OPS_PLUS);
	    
	    
	    WritePolicy writePolicy = client.getWritePolicyDefault();
	    Record record = client.operate(writePolicy, key, ops);
	    // System.out.printf("dmf: record:%s\n", record);

	    // Record record = client.get(readPolicy, key);

	    PlayerPostSeasonBattingStats playerPSBattingStats;

	    if (record == null) {
	    	playerPSBattingStats = null;
	    } else {
	    	playerPSBattingStats = SerdePlayerPostSeasonBattingStats.deserialize(record);
	    }

	    return playerPSBattingStats;

	  } // get


	  public PlayerPostSeasonBattingStats[] getBatch (String ns, String set, String ukeys) {

	    String[] ukeyArr = ukeys.split(",");
	    Key[] keys = new Key[ukeyArr.length];
	    BatchPolicy batchPolicy = client.getBatchPolicyDefault();
	    Record[] records;
	    PlayerPostSeasonBattingStats[] playerYearlyBattingStats;

	    for (int i=0; i<ukeyArr.length; i++) {
	      keys[i] = new Key (ns, set, ukeyArr[i]);
	    }

	    records = client.get(batchPolicy, keys);

	    playerYearlyBattingStats = new PlayerPostSeasonBattingStats[records.length];
	    for (int i=0; i<records.length; i++) {
	      if (records[i] == null) {
	    	  playerYearlyBattingStats[i] = null;
	      } else {
	    	  playerYearlyBattingStats[i] = SerdePlayerPostSeasonBattingStats.deserialize(records[i]);
	      }
	    }

	    return playerYearlyBattingStats;

	  } // getBatch


	  public void scan (String ns, String set, ScanCallback cb) {

	    ScanPolicy scanPolicy = client.getScanPolicyDefault();

	    try {
	      client.scanAll(scanPolicy, ns, set, cb);
	    }

	    catch (ScanTerminated e) {
	      log.debug(String.format("Scan terminated"));
	    }

	    return;

	  } // scan
}
