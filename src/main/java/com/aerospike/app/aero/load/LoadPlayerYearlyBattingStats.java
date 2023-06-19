
package com.aerospike.app.aero.load;

//import com.aerospike.app.aero.load.LoadObject1.Object1LoadTask;
import com.aerospike.app.aero.ops.OpsObject1;
import com.aerospike.app.aero.ops.OpsPlayerInfo;
import com.aerospike.app.aero.ops.OpsPlayerYearlyBattingStats;
import com.aerospike.app.model.Object1;
import com.aerospike.app.model.PlayerInfo;
import com.aerospike.app.model.PlayerYearlyBattingStats;
import com.aerospike.app.seed.SeedObject1;
import com.aerospike.app.util.Timer;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException.ScanTerminated;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.policy.WritePolicy;
import com.opencsv.CSVReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit ;

import org.apache.log4j.Logger;

public class LoadPlayerYearlyBattingStats {

  static Logger log = Logger.getLogger("LoadObject1");
  
  private static final String DATA_DIR_PATH = "/Users/johnwalker/Development/projects/baseball/ProBaseballStats/data/2015/the-history-of-baseball/";
  private static final String playerYearlyBattingStatsFile = "batting.csv";

  AerospikeClient client;
  OpsPlayerInfo opsPlayerInfo;
  OpsPlayerYearlyBattingStats opsPlayerYearlyBattingStats;

  public LoadPlayerYearlyBattingStats (AerospikeClient client) {
    this.client = client;
    this.opsPlayerYearlyBattingStats = new OpsPlayerYearlyBattingStats(client);
    this.opsPlayerInfo = new OpsPlayerInfo(client);
  }

  public long load (String ns, String set, String file) {
	  
	  int threadCnt=11;
	  long recCnt=0;
	  
	  List<Thread> threads = new ArrayList<Thread>();
	  Runnable[] runnables = new Runnable[threadCnt];
	  Thread thread;
	 
	  // loop creating threads to handle blocks of records
	  for (int i=0; i<threadCnt; i++) {

		  //remCnt = recCnt - currCnt;
	      //recBlock = (i == threadCnt-1) ? recCnt - currCnt : recBlockSize;

	      // a large remainder results in a large block size for final thread
	      // just add 1 to all threads until that remainder is consumed
	      // in essence, spread remainder over as many threads as possible
		  /*
	      if (recRemainder > 0) {
	        addOne = 1;
	        recRemainder -= 1;
	      } else {
	        addOne = 0;
	      }
			*/
		  
		  
	      runnables[i] = new LoadPlayerYearlyBattingStatsFileTask(client, ns, set, file + "." + Integer.toString(i+1));

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
	        sumCnt += ((LoadPlayerYearlyBattingStatsFileTask)runnables[i]).getTotalCnt();
	      }
		
	  return sumCnt;
	  
  }

  private class LoadPlayerYearlyBattingStatsFileTask implements Runnable {

	    Logger log = Logger.getLogger("LoadPlayerInfoFromFile.run");

	    AerospikeClient client;
	    String ns;
	    String set;
	    String file;
	    
	    long totalCnt = 0;

	    public LoadPlayerYearlyBattingStatsFileTask (AerospikeClient client, String ns, 
	    							   String set, String file) {

	        this.client = client;
	        this.ns = ns;
	        this.set = set;
	        this.file = file;
	        
	        System.out.println("Your file = " + this.file);
	       
	      // long threadId = Thread.currentThread().getId();
	      //log.debug(String.format("created Object1LoadTask recStart:%d recCnt:%d", recStart, recCnt));

	    }
	    public long getTotalCnt () {
	        return this.totalCnt;
	      }
	    
	    public void run () {
         
	      String ukey;
	      WritePolicy writePolicy = client.getWritePolicyDefault();
	      Key key;
	      Bin[] bins;

	      long threadId = Thread.currentThread().getId();
	      //log.debug(String.format("run thread:%d cnt:%d start:%d", threadId, recCnt, recStart));

	      Timer timerTotal = new Timer();
	      Timer timerCnt = new Timer();

		  FileReader battingfilereader=null;
		  
		  try {
			  
			  battingfilereader = new FileReader(file);
			
		  } catch (FileNotFoundException e) {
			  
				// TODO Auto-generated catch block
				e.printStackTrace();
		  }
	        
	      // create csvReader object passing
	      // file reader as a parameter
	      CSVReader csvBattingReader = new CSVReader(battingfilereader);
	          
	      String[] nextRecord;
	    
	      try {
	    	  
			while ((nextRecord = csvBattingReader.readNext()) != null) {
				
				//Instantiate PlayerInfo object
				PlayerYearlyBattingStats plyStats = new PlayerYearlyBattingStats(nextRecord);

				opsPlayerYearlyBattingStats.put(ns, "playeryearlybattingstats", plyStats.getPlayerid() + ":" + plyStats.getYearid(), plyStats);
				
				PlayerInfo plInfo = opsPlayerInfo.get(ns, "playerinfo", plyStats.getPlayerid());

				if (plInfo == null) {
					System.out.println(plyStats.getPlayerid());
				}
				
				plInfo.addPlyrYrlyBatStats(plyStats.getPlayerid() + ":" + plyStats.getYearid());
				
				opsPlayerInfo.put(ns, "playerinfo", plInfo.getPlayerid(), plInfo);
				this.totalCnt++;
				
				plyStats = null;
				plInfo = null;
			        
			  }
			  battingfilereader.close();
			  csvBattingReader.close();
			  
	      } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
	      } 
	    
	      return;

	    } // run

	  } // PlayerInfoLoadFileTask
  
} // LoadPlayerYearlyBattingStats
