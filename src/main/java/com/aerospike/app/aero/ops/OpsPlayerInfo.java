
package com.aerospike.app.aero.ops;

import com.aerospike.app.model.PlayerInfo;
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
import com.aerospike.client.exp.ExpOperation;
import com.aerospike.client.exp.ExpReadFlags;
import com.aerospike.client.exp.ExpWriteFlags;
import com.aerospike.client.exp.Expression;
import com.aerospike.client.exp.Exp.Type;
import com.aerospike.client.policy.BatchPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.QueryPolicy;
import com.aerospike.client.policy.ScanPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.RegexFlag;
import com.aerospike.client.query.Statement;

import org.apache.log4j.Logger;


public class OpsPlayerInfo {

  static Logger log = Logger.getLogger("OpsPlayerInfo");

  public AerospikeClient client;
  SerdePlayerInfo sdPlayerInfo;


  public OpsPlayerInfo (AerospikeClient client) {
    this.client = client;
    this.sdPlayerInfo = new SerdePlayerInfo();
  }


  public void put (String ns, String set, String ukey, PlayerInfo playerInfo) {

    WritePolicy writePolicy = client.getWritePolicyDefault();
    Key key = new Key (ns, set, ukey);
    Bin[] bins = SerdePlayerInfo.serialize(playerInfo);
    
    client.put(writePolicy, key, bins);

  } // put


  public PlayerInfo get (String ns, String set, String ukey) {

    Policy readPolicy = client.getReadPolicyDefault();
    Key key = new Key (ns, set, ukey);

    Expression expSiz =
      Exp.build(
                Exp.deviceSize()
                );

    Operation[] ops = new Operation[26];
    int onx = 0;
   
    ops[onx++] = Operation.get(SerdePlayerInfo.ARM);
    ops[onx++] = Operation.get(SerdePlayerInfo.BATS);
    ops[onx++] = Operation.get(SerdePlayerInfo.BBREFID);
    ops[onx++] = Operation.get(SerdePlayerInfo.BIRTHCITY);
    ops[onx++] = Operation.get(SerdePlayerInfo.BIRTHCOUNTRY);
    ops[onx++] = Operation.get(SerdePlayerInfo.BIRTHDAY);
    ops[onx++] = Operation.get(SerdePlayerInfo.BIRTHMONTH);
    ops[onx++] = Operation.get(SerdePlayerInfo.BIRTHSTATE);
    ops[onx++] = Operation.get(SerdePlayerInfo.BIRTHYEAR);
    ops[onx++] = Operation.get(SerdePlayerInfo.DEATHCITY);
    ops[onx++] = Operation.get(SerdePlayerInfo.DEATHCOUNTRY);
    ops[onx++] = Operation.get(SerdePlayerInfo.DEATHDAY);
    ops[onx++] = Operation.get(SerdePlayerInfo.DEATHMONTH);
    ops[onx++] = Operation.get(SerdePlayerInfo.DEATHSTATE);
    ops[onx++] = Operation.get(SerdePlayerInfo.DEATHYEAR);
    ops[onx++] = Operation.get(SerdePlayerInfo.DEBUT);
    ops[onx++] = Operation.get(SerdePlayerInfo.FINALGAME);
    ops[onx++] = Operation.get(SerdePlayerInfo.HEIGHT);
    ops[onx++] = Operation.get(SerdePlayerInfo.NAMEFIRST);
    ops[onx++] = Operation.get(SerdePlayerInfo.NAMEGIVEN);
    ops[onx++] = Operation.get(SerdePlayerInfo.NAMELAST);
    ops[onx++] = Operation.get(SerdePlayerInfo.PLAYERID);
    ops[onx++] = Operation.get(SerdePlayerInfo.PYBS);
    ops[onx++] = Operation.get(SerdePlayerInfo.RETROID);
    ops[onx++] = Operation.get(SerdePlayerInfo.WEIGHT);
    ops[onx++] = ExpOperation.read("siz", expSiz, 0);
    
    WritePolicy writePolicy = client.getWritePolicyDefault();
    Record record = client.operate(writePolicy, key, ops);

    PlayerInfo playerInfo;

    if (record == null) {
    	playerInfo = null;
    } else {
    	playerInfo = SerdePlayerInfo.deserialize(record);
    }

    return playerInfo;

  } // get
  
  
  public RecordSet search (String ns, String set, String searchStr) {

	    Statement stmnt = new Statement();

	    stmnt.setNamespace(ns);
	    stmnt.setSetName(set);
	
	    QueryPolicy queryPolicy = new QueryPolicy();
	    
	    queryPolicy.filterExp = Exp.build(Exp.regexCompare(searchStr, RegexFlag.ICASE  | RegexFlag.NEWLINE,
				  Exp.stringBin("lname")));
	    
	    stmnt.setBinNames(SerdePlayerInfo.ARM,
	    				  SerdePlayerInfo.BATS,
	    				  SerdePlayerInfo.BBREFID, 
	    				  SerdePlayerInfo.BIRTHCITY,
	    				  SerdePlayerInfo.BIRTHCOUNTRY, 
	    				  SerdePlayerInfo.BIRTHDAY,
	    				  SerdePlayerInfo.BIRTHMONTH, 
	    				  SerdePlayerInfo.BIRTHSTATE,
	    				  SerdePlayerInfo.BIRTHYEAR, 
	    				  SerdePlayerInfo.DEATHCITY,
	    				  SerdePlayerInfo.DEATHCOUNTRY, 
	    				  SerdePlayerInfo.DEATHDAY,
	    				  SerdePlayerInfo.DEATHMONTH, 
	    				  SerdePlayerInfo.DEATHSTATE,
	    				  SerdePlayerInfo.DEATHYEAR, 
	    				  SerdePlayerInfo.DEBUT, 
	    				  SerdePlayerInfo.FINALGAME,
	    				  SerdePlayerInfo.HEIGHT, 
	    				  SerdePlayerInfo.NAMEFIRST, 
	    				  SerdePlayerInfo.NAMEGIVEN,
	    				  SerdePlayerInfo.NAMELAST,
	    				  SerdePlayerInfo.PLAYERID, 
	    				  SerdePlayerInfo.PYBS,
	    				  SerdePlayerInfo.RETROID,
	    				  SerdePlayerInfo.WEIGHT);
	    
	    RecordSet rSet = client.query(queryPolicy, stmnt);
	    
	    return rSet;

	  } // search
  
  
  public PlayerInfo getLname (String ns, String set, String ukey) {

	    Policy readPolicy = client.getReadPolicyDefault();
	    
	    QueryPolicy qPolicy = new QueryPolicy();
	    
	    Key key = new Key (ns, set, ukey);

	    Expression expSiz =
	      Exp.build(
	                Exp.deviceSize()
	                );
	    
	    // Ensure that HITS and AB contain positive values. 
	 	Expression lName = 
	 				Exp.build(
	 						Exp.regexCompare("^Y.*",RegexFlag.ICASE | RegexFlag.NEWLINE, Exp.stringBin("lname"))
	 						
	 						); // End build
	 	
	    Operation[] ops = new Operation[27];
	    int onx = 0;
	   
	    ops[onx++] = Operation.get(SerdePlayerInfo.ARM);
	    ops[onx++] = Operation.get(SerdePlayerInfo.BATS);
	    ops[onx++] = Operation.get(SerdePlayerInfo.BBREFID);
	    ops[onx++] = Operation.get(SerdePlayerInfo.BIRTHCITY);
	    ops[onx++] = Operation.get(SerdePlayerInfo.BIRTHCOUNTRY);
	    ops[onx++] = Operation.get(SerdePlayerInfo.BIRTHDAY);
	    ops[onx++] = Operation.get(SerdePlayerInfo.BIRTHMONTH);
	    ops[onx++] = Operation.get(SerdePlayerInfo.BIRTHSTATE);
	    ops[onx++] = Operation.get(SerdePlayerInfo.BIRTHYEAR);
	    ops[onx++] = Operation.get(SerdePlayerInfo.DEATHCITY);
	    ops[onx++] = Operation.get(SerdePlayerInfo.DEATHCOUNTRY);
	    ops[onx++] = Operation.get(SerdePlayerInfo.DEATHDAY);
	    ops[onx++] = Operation.get(SerdePlayerInfo.DEATHMONTH);
	    ops[onx++] = Operation.get(SerdePlayerInfo.DEATHSTATE);
	    ops[onx++] = Operation.get(SerdePlayerInfo.DEATHYEAR);
	    ops[onx++] = Operation.get(SerdePlayerInfo.DEBUT);
	    ops[onx++] = Operation.get(SerdePlayerInfo.FINALGAME);
	    ops[onx++] = Operation.get(SerdePlayerInfo.HEIGHT);
	    ops[onx++] = Operation.get(SerdePlayerInfo.NAMEFIRST);
	    ops[onx++] = Operation.get(SerdePlayerInfo.NAMEGIVEN);
	    ops[onx++] = Operation.get(SerdePlayerInfo.NAMELAST);
	    ops[onx++] = Operation.get(SerdePlayerInfo.PLAYERID);
	    ops[onx++] = Operation.get(SerdePlayerInfo.PYBS);
	    ops[onx++] = Operation.get(SerdePlayerInfo.RETROID);
	    ops[onx++] = Operation.get(SerdePlayerInfo.WEIGHT);
	    
	    ops[onx++] = ExpOperation.read("lName", lName, ExpReadFlags.DEFAULT);
	    ops[onx++] = ExpOperation.read("siz", expSiz, 0);
	    
	    WritePolicy writePolicy = client.getWritePolicyDefault();
	    Record record = client.operate(writePolicy, key, ops);
	    // System.out.printf("dmf: record:%s\n", record);

	    // Record record = client.get(readPolicy, key);

	    PlayerInfo playerInfo;

	    if (record == null) {
	    	playerInfo = null;
	    } else {
	    	playerInfo = SerdePlayerInfo.deserialize(record);
	    }

	    return playerInfo;

	  } // getLname

  public PlayerInfo[] getBatch (String ns, String set, String ukeys) {

    String[] ukeyArr = ukeys.split(",");
    Key[] keys = new Key[ukeyArr.length];
    BatchPolicy batchPolicy = client.getBatchPolicyDefault();
    Record[] records;
    PlayerInfo[] playerinfos;

    for (int i=0; i<ukeyArr.length; i++) {
      keys[i] = new Key (ns, set, ukeyArr[i]);
    }

    records = client.get(batchPolicy, keys);

    playerinfos = new PlayerInfo[records.length];
    for (int i=0; i<records.length; i++) {
      if (records[i] == null) {
    	  playerinfos[i] = null;
      } else {
    	  playerinfos[i] = sdPlayerInfo.deserialize(records[i]);
      }
    }

    return playerinfos;

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


} // OpsPlayerInfo
