
package com.aerospike.app.aero.ops;

import com.aerospike.app.model.PlayerYearlyBattingStats;
import com.aerospike.app.aero.serde.SerdePlayerInfo;
import com.aerospike.app.aero.serde.SerdePlayerYearlyBattingStats;
import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException.ScanTerminated;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Operation;
import com.aerospike.client.Record;
import com.aerospike.client.ScanCallback;
import com.aerospike.client.exp.Exp;
import com.aerospike.client.exp.Exp.Type;
import com.aerospike.client.exp.ExpOperation;
import com.aerospike.client.exp.ExpReadFlags;
import com.aerospike.client.exp.ExpWriteFlags;
import com.aerospike.client.exp.Expression;
import com.aerospike.client.policy.BatchPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.ScanPolicy;
import com.aerospike.client.policy.WritePolicy;

import javax.lang.model.type.TypeKind;

import org.apache.log4j.Logger;
import org.luaj.vm2.ast.Exp.BinopExp;


public class OpsPlayerYearlyBattingStats {

  static Logger log = Logger.getLogger("OpsPlayerInfo");

  public AerospikeClient client;
  SerdePlayerInfo sdPlayerInfo;


  public OpsPlayerYearlyBattingStats (AerospikeClient client) {
    this.client = client;
    this.sdPlayerInfo = new SerdePlayerInfo();
  }


  public void put (String ns, String set, String ukey, PlayerYearlyBattingStats pYearlyBattingStats) {
	  /* 
	  Expression expBA =
	    	      Exp.build(
	    	                Exp.cond(
	    	                         Exp.binExists(SerdePlayerYearlyBattingStats.BA),
	    	                         Exp.cond(
	    	                                  Exp.eq(Exp.val(0),
	    	                                         Exp.intBin(SerdePlayerYearlyBattingStats.BA)),
	    	                                  Exp.val(Integer.parseInt(SerdePlayerYearlyBattingStats.HITS) / Integer.parseInt(SerdePlayerYearlyBattingStats.AB)),
	    	                         Exp.val(SerdePlayerYearlyBattingStats.BA))
	    	                )
	    );
        */
	
    WritePolicy writePolicy = client.getWritePolicyDefault();
    Key key = new Key (ns, set, ukey);
    Bin[] bins = SerdePlayerYearlyBattingStats.serialize(pYearlyBattingStats);

    client.put(writePolicy, key, bins);

  } // put


  public PlayerYearlyBattingStats get (String ns, String set, String ukey) {
   
    Policy readPolicy = client.getReadPolicyDefault();
    Key key = new Key (ns, set, ukey);

    Expression battingNums =
    		Exp.build(
    		  Exp.and( 
    				  Exp.gt(Exp.bin(SerdePlayerYearlyBattingStats.AB, Type.INT), Exp.val(0)) ,
    				  Exp.gt(Exp.bin(SerdePlayerYearlyBattingStats.HITS, Type.INT), Exp.val(0))
    				  )
                );
    /*
    Expression slgPct = 
    		Exp.build(
    					Exp.div(
    							Exp.add(
    									Exp.toFloat(Exp.intBin(SerdePlayerYearlyBattingStats.HITS)), Exp.toFloat(Exp.intBin(SerdePlayerYearlyBattingStats.DOUBLES)), 
    									Exp.toFloat(Exp.intBin(SerdePlayerYearlyBattingStats.TRIPLES)), Exp.toFloat(Exp.intBin(SerdePlayerYearlyBattingStats.HR))), 
    							Exp.toFloat(Exp.intBin(SerdePlayerYearlyBattingStats.AB))
    							)
    					
    		);
    */
    Expression battingAvg = 
    		Exp.build(
    					Exp.div(
    							Exp.add(
    									Exp.toFloat(Exp.intBin(SerdePlayerYearlyBattingStats.HITS))), 
    							Exp.toFloat(Exp.intBin(SerdePlayerYearlyBattingStats.AB))
    							)
    					
    		);
    
    Operation[] ops = new Operation[30];
    int onx = 0;
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.PLAYERID);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.YEARID);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.AB);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.BB);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.CS);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.DOUBLES);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.GAMES);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.GIDP);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.HBP);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.HITS);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.HR);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.BA);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.IBB);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.LGID);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.RBI);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.RUNS);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.SB);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.SF);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.SH);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.SO);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.STINT);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.TEAMID);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.TRIPLES);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.BBREFID);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.OBP);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.SLG);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.OPS);
    ops[onx++] = Operation.get(SerdePlayerYearlyBattingStats.OPS_PLUS);
    ops[onx++] = ExpOperation.read("nums_avail", battingNums, ExpReadFlags.DEFAULT);
    ops[onx++] = ExpOperation.read("batting_avg", battingAvg, ExpReadFlags.DEFAULT);
    
    /*
    ops[onx++] = Operation.get(SerdePlayerInfo.FIELD1);
    ops[onx++] = Operation.get(SerdePlayerInfo.FIELD2);
    ops[onx++] = Operation.get(SerdePlayerInfo.OBJTYPE);
    ops[onx++] = Operation.get(SerdePlayerInfo.INT1);
    ops[onx++] = Operation.get(SerdePlayerInfo.STRLIST);
    ops[onx++] = ExpOperation.read("siz", expSiz, 0);
    */
    WritePolicy writePolicy = client.getWritePolicyDefault();
    
    Record record = client.operate(writePolicy, key, ops);
    
    System.out.printf("dmf: record:%s\n", record);

    // Record record = client.get(readPolicy, key);

    PlayerYearlyBattingStats playerYearlyBattingStats;

    if (record == null) {
    	playerYearlyBattingStats = null;
    } else {
    	playerYearlyBattingStats = SerdePlayerYearlyBattingStats.deserialize(record);
    }

    return playerYearlyBattingStats;

  } // get


  public PlayerYearlyBattingStats[] getBatch (String ns, String set, String ukeys) {

    String[] ukeyArr = ukeys.split(",");
    Key[] keys = new Key[ukeyArr.length];
    BatchPolicy batchPolicy = client.getBatchPolicyDefault();
    Record[] records;
    PlayerYearlyBattingStats[] playerYearlyBattingStats;

    for (int i=0; i<ukeyArr.length; i++) {
      keys[i] = new Key (ns, set, ukeyArr[i]);
    }

    records = client.get(batchPolicy, keys);

    playerYearlyBattingStats = new PlayerYearlyBattingStats[records.length];
    for (int i=0; i<records.length; i++) {
      if (records[i] == null) {
    	  playerYearlyBattingStats[i] = null;
      } else {
    	  playerYearlyBattingStats[i] = SerdePlayerYearlyBattingStats.deserialize(records[i]);
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


} // OpsObject1
