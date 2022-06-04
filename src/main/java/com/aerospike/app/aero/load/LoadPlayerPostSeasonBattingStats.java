package com.aerospike.app.aero.load;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

//import com.aerospike.app.aero.load.LoadPlayerYearlyBattingStats.LoadPlayerYearlyBattingStatsFileTask;
import com.aerospike.app.aero.ops.OpsPlayerInfo;
import com.aerospike.app.aero.ops.OpsPlayerYearlyBattingStats;
import com.aerospike.app.aero.ops.OpsPlayerPostSeasonBattingStats;
import com.aerospike.app.model.PlayerInfo;
import com.aerospike.app.model.PlayerYearlyBattingStats;
import com.aerospike.app.model.PlayerPostSeasonBattingStats;
import com.aerospike.app.util.Timer;
import com.aerospike.client.AerospikeClient;
import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.policy.WritePolicy;
import com.opencsv.CSVReader;

public class LoadPlayerPostSeasonBattingStats {
	  static Logger log = Logger.getLogger("LoadObject1");
	  
	  private static final String DATA_DIR_PATH = "/Users/johnwalker/Development/projects/baseball/ProBaseballStats/data/2015/the-history-of-baseball/";
	  private static final String playerYearlyBattingStatsFile = "batting.csv";

	  AerospikeClient client;
	  OpsPlayerInfo opsPlayerInfo;
	  OpsPlayerPostSeasonBattingStats opsPlayerPostSeasonBattingStats;

	  public LoadPlayerPostSeasonBattingStats (AerospikeClient client) {
	    this.client = client;
	    this.opsPlayerPostSeasonBattingStats = new OpsPlayerPostSeasonBattingStats(client);
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
		      runnables[i] = new LoadPlayerPostSeasonBattingStatsFileTask(client, ns, set, file + "." + Integer.toString(i+1));

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
		        sumCnt += ((LoadPlayerPostSeasonBattingStatsFileTask)runnables[i]).getTotalCnt();
		      }
			
		  return sumCnt;
		  
	  }
	  
	  private class LoadPlayerPostSeasonBattingStatsFileTask implements Runnable {

		    Logger log = Logger.getLogger("LoadPlayerInfoFromFile.run");

		    AerospikeClient client;
		    String ns;
		    String set;
		    String file;
		    
		    long totalCnt = 0;
		    long toTotal = 0;
		    long errTotal = 0;


		    public LoadPlayerPostSeasonBattingStatsFileTask (AerospikeClient client, String ns, 
		    							   String set, String file) {

		        this.client = client;
		        this.ns = ns;
		        this.set = set;
		        this.file = file;
		        
		        System.out.println("Your file = " + this.file);
		       
		      long threadId = Thread.currentThread().getId();
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

			  FileReader battingpsfilereader=null;
			  
			  try {
				  
				  battingpsfilereader = new FileReader(file);
				
			  } catch (FileNotFoundException e) {
				  
					// TODO Auto-generated catch block
					e.printStackTrace();
			  }
		        
		      // create csvReader object passing
		      // file reader as a parameter
		      CSVReader csvBattingPSReader = new CSVReader(battingpsfilereader);
		          
		      String[] nextRecord;
		    
		      try {
				while ((nextRecord = csvBattingPSReader.readNext()) != null) {
				   
					
					//Instantiate PlayerInfo object
					PlayerPostSeasonBattingStats plyPSStats = new PlayerPostSeasonBattingStats(nextRecord);
				    
				   // System.out.println(plInfo.toString());

					opsPlayerPostSeasonBattingStats.put(ns, "playerpostseasonbattingstats", plyPSStats.getPlayerid() + ":" + plyPSStats.getRound() + ":" + plyPSStats.getYearid(), plyPSStats);
					this.totalCnt++;
					//PlayerInfo plInfo = opsPlayerInfo.get(ns, "playerinfo", plyStats.getPlayerid());
					/*
					if (plInfo == null) {
						System.out.println(plyStats.getPlayerid());
					}
					System.out.println(plInfo.toString());
					plInfo.addPlyrYrlyBatStats(plyStats.getPlayerid() + ":" + plyStats.getYearid());
					opsPlayerInfo.put(ns, "playerinfo", plInfo.getPlayerid(), plInfo);
					plyStats = null;
					plInfo = null;
					*/
				        
				  }
				  battingpsfilereader.close();
				  csvBattingPSReader.close();
				  
		      } catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		      } 
		    
		      return;

		    } // run

		  } // PlayerInfoLoadFileTask
}
