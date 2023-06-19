
package com.aerospike.app.aero.ops;

import com.aerospike.app.model.PlayerYearlyBattingStats;
import com.aerospike.app.aero.serde.SerdePlayerInfo;
import com.aerospike.app.aero.serde.SerdePlayerYearlyBattingStats;
import com.aerospike.app.util.*;

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

import java.util.List;

import javax.lang.model.type.TypeKind;

import org.apache.log4j.Logger;
import org.luaj.vm2.ast.Exp.BinopExp;

public class OpsPlayerYearlyBattingStats {

	static Logger log = Logger.getLogger("OpsPlayerInfo");

	public AerospikeClient client;
	SerdePlayerInfo sdPlayerInfo;

	public OpsPlayerYearlyBattingStats(AerospikeClient client) {
		this.client = client;
		this.sdPlayerInfo = new SerdePlayerInfo();
	}

	public void put(String ns, String set, String ukey, PlayerYearlyBattingStats pYearlyBattingStats) {

		WritePolicy writePolicy = client.getWritePolicyDefault();
		Key key = new Key(ns, set, ukey);
		Bin[] bins = SerdePlayerYearlyBattingStats.serialize(pYearlyBattingStats);

		client.put(writePolicy, key, bins);

	} // put

	@SuppressWarnings("unchecked")
	public PlayerYearlyBattingStats get(String ns, String set, String ukey) {

		Policy readPolicy = client.getReadPolicyDefault();
		Key key = new Key(ns, set, ukey);

		// Ensure that HITS and AB contain positive values. 
		Expression battingNums = 
				Exp.build(Exp.and(Exp.gt(Exp.bin(SerdePlayerYearlyBattingStats.AB, Type.INT), Exp.val(0)),
						Exp.gt(Exp.bin(SerdePlayerYearlyBattingStats.HITS, Type.INT), Exp.val(0))));

		// Batting Average:BA = H/AB
		Expression battingAvg = 
				Exp.build(Exp.div(Exp.toFloat(Exp.intBin(SerdePlayerYearlyBattingStats.HITS)),
						Exp.toFloat(Exp.intBin(SerdePlayerYearlyBattingStats.AB))));
		
		// Slugging Percentage:SLG = (1B + (2 * 2B) + (3 * 3B) + (4 + 4B))/AB
		Expression slgPct =
				// Begin: Exp Build statement
				Exp.build(
						Exp.div(
								Exp.toFloat(// Begin: Converting Exp results to Float.
										
										Exp.add(// Begin: Add total bases for the year.
												// Begin:Number of 1B total bases for the year.
												Exp.sub(Exp.intBin(SerdePlayerYearlyBattingStats.HITS),
														Exp.intBin(SerdePlayerYearlyBattingStats.DOUBLES),
														Exp.intBin(SerdePlayerYearlyBattingStats.TRIPLES),
														Exp.intBin(SerdePlayerYearlyBattingStats.HR)),
												// End:Number of 1B total bases for the year.
												// Begin:Number of 2B total bases for the year.
												Exp.mul(Exp.intBin(SerdePlayerYearlyBattingStats.DOUBLES), Exp.val(2)),
												// End:Number of 2B total bases for the year.
												// Begin:Number of 3B total bases for the year
												Exp.mul(Exp.intBin(SerdePlayerYearlyBattingStats.TRIPLES), Exp.val(3)),
												// End:Number of 3B total bases for the year
												// Begin:Number of HR total bases for the year
												
												Exp.mul(Exp.intBin(SerdePlayerYearlyBattingStats.HR), Exp.val(4))
												// End:Number of HR total bases for the year
											) // End: Add total bases for the year.
								)
								, Exp.toFloat(Exp.intBin(SerdePlayerYearlyBattingStats.AB)))

				); // End: Exp Build statement
		
		// On Base Percentage:OBP = (H + BB + HBP)/(AB + + BB + HBP + SF)
		Expression obPct =
		    // Begin: Exp Build statement
		    Exp.build(
		        Exp.div (
		            Exp.toFloat(// Begin: Converting Exp results to Float.
		                
		                Exp.add(// Begin: Add total bases for the year.
		                    Exp.intBin(SerdePlayerYearlyBattingStats.HITS),
		                    Exp.intBin(SerdePlayerYearlyBattingStats.BB),
		                    Exp.intBin(SerdePlayerYearlyBattingStats.HBP)
		                    // End:Number of HR total bases for the year
		                  ) // End: Add total bases for the year.
		            )
		            , Exp.toFloat(
		                Exp.add(// Begin: Add total bases for the year.
		                  Exp.intBin(SerdePlayerYearlyBattingStats.AB),
		                  Exp.intBin(SerdePlayerYearlyBattingStats.BB),
		                  Exp.intBin(SerdePlayerYearlyBattingStats.HBP),
		                  Exp.intBin(SerdePlayerYearlyBattingStats.SF)
		                )
		                // End:Number of HR total bases for the year
		              ) // End: Add total bases for the year.
		          )
		    ); // End: Exp Build statement

		Operation[] ops = new Operation[27];
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

		ops[onx++] = ExpOperation.read("nums_avail", battingNums, ExpReadFlags.DEFAULT);
		ops[onx++] = ExpOperation.read("batting_avg", battingAvg, ExpReadFlags.DEFAULT);
		ops[onx++] = ExpOperation.read("slugging_pct", slgPct, ExpReadFlags.DEFAULT);
		ops[onx++] = ExpOperation.read("onbase_pct", obPct, ExpReadFlags.DEFAULT);
		

		WritePolicy writePolicy = client.getWritePolicyDefault();

		Record record = client.operate(writePolicy, key, ops);

		//System.out.printf("dmf: record:%s\n", record);

		// Record record = client.get(readPolicy, key);

		PlayerYearlyBattingStats playerYearlyBattingStats;

		if (record == null) {
			playerYearlyBattingStats = null;
		} else {
			playerYearlyBattingStats = SerdePlayerYearlyBattingStats.deserialize(record);
		}

		return playerYearlyBattingStats;

	} // get

	public PlayerYearlyBattingStats[] getBatch(String ns, String set, String ukeys) {

		String[] ukeyArr = ukeys.split(",");
		Key[] keys = new Key[ukeyArr.length];
		BatchPolicy batchPolicy = client.getBatchPolicyDefault();
		Record[] records;
		PlayerYearlyBattingStats[] playerYearlyBattingStats;

		for (int i = 0; i < ukeyArr.length; i++) {
			keys[i] = new Key(ns, set, ukeyArr[i]);
		}

		records = client.get(batchPolicy, keys);

		playerYearlyBattingStats = new PlayerYearlyBattingStats[records.length];
		for (int i = 0; i < records.length; i++) {
			if (records[i] == null) {
				playerYearlyBattingStats[i] = null;
			} else {
				playerYearlyBattingStats[i] = SerdePlayerYearlyBattingStats.deserialize(records[i]);
			}
		}

		return playerYearlyBattingStats;

	} // getBatch

	public void scan(String ns, String set, ScanCallback cb) {

		ScanPolicy scanPolicy = client.getScanPolicyDefault();

		try {
			client.scanAll(scanPolicy, ns, set, cb);
		}

		catch (ScanTerminated e) {
			log.debug(String.format("Scan terminated"));
		}

		return;

	} // scan

} // OpsPlayerYearlyBattingStats
