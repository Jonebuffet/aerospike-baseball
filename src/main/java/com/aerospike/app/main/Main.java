
package com.aerospike.app.main;

import com.aerospike.app.main.CmdlnParser;
import com.aerospike.app.util.Cmdln;
import com.aerospike.app.aero.Aero;
import com.aerospike.app.aero.ops.OpsPlayerPostSeasonBattingStats;
import com.aerospike.app.model.*;
import com.aerospike.app.util.*;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import com.opencsv.CSVReader;

import org.apache.log4j.Logger;


public class Main {

  static Logger log = Logger.getLogger("Main");
  private static final String COMMA_DELIMITER = ",";
  private static final String DATA_DIR_PATH = "/Users/johnwalker/Development/projects/baseball/ProBaseballStats/data/2015/the-history-of-baseball/split_data/";
	
  private static final String playerYearlyBattingStatsFile = "batting.csv";
  private static final String playerPostSeasonBattingStatsFile = "batting_postseason.csv";
  private static final String playerFile = "player.csv";

  public static void main(String[] args) {

    Cmdln cl = null;
    Aero as = null;

    try {
    /*
     * 
      cl = new CmdlnParser(args).cl;
 
     // String cmd = cl.getStr("--command");
      

      long recStartId = cl.getLong("--recStartId");
      long recCount = cl.getLong("--recCount");
      String f1 = cl.getStr("--object1Field1");
      String f2 = cl.getStr("--object1Field2");
      String otyp = cl.getStr("--object1Type");
      int int1 = cl.getInt("--object1Int1");
      String strLst = cl.getStr("--object1StringList");

      String host = cl.getStr("--host");
      int port = cl.getInt("--port");
      String ns = cl.getStr("--namespace");
      String set = cl.getStr("--set");
      String ukey = cl.getStr("--userKey");

      long limit = cl.getLong("--limit");

      boolean verbose = cl.getBool("--verbose");
      int updateRate = cl.getInt("--updateRate");

      int threadCount = cl.getInt("--threadCount");

      log.info(String.format("~~~~ Starting %s ~~~~\n", cmd));
      
    */
    	
      String host = "127.0.0.1";
      int port = 3000;
      String ns = "test";
      String set = "playerinfo";
      /* String ukey = cl.getStr("--userKey"); */
      //String ukey = "ruthba01";
      String ukey = "willite01";
      //String ukey = "yastrca01";
        
      // Create an object of filereader
      // class with CSV file as a parameter.
      FileReader playerinfofilereader = new FileReader(DATA_DIR_PATH + playerFile);
 
      // create csvReader object passing
      // file reader as a parameter
      CSVReader csvPlayerInfoReader = new CSVReader(playerinfofilereader);
        
      String[] nextRecord;
      String[] nextBattingRecord;
        
      int counter=0;
      
      String cmd = "load";

      // Create Aero object
      as = new Aero(host, port);

      // Run chosen command
      switch (cmd) {
     
        case "load":
          //as.load(ns, set, DATA_DIR_PATH + playerFile);
          //as.load(ns, set, DATA_DIR_PATH + playerYearlyBattingStatsFile);
          //as.load(ns, set, DATA_DIR_PATH + playerPostSeasonBattingStatsFile);
          break;
        /*
        case "scan":
          as.scan(ns, set, limit);
          break;
	    
	    */
        case "put":
          /*
          as.put(ns, set, ukey,
                 f1, f2, otyp, int1, strLst);
              */
          	  OpsPlayerPostSeasonBattingStats opsPlayerPostSeasonBattingStats;
              FileReader battingpsfilereader = new FileReader(DATA_DIR_PATH + playerPostSeasonBattingStatsFile);

		      // create csvReader object passing
		      // file reader as a parameter
		      CSVReader csvBattingPSReader = new CSVReader(battingpsfilereader);
		          
		      //String[] nextRecord;
		      nextRecord = csvBattingPSReader.readNext();
		      try {
				while ((nextRecord = csvBattingPSReader.readNext()) != null) {
				   
					
					//Instantiate PlayerInfo object
					PlayerPostSeasonBattingStats plyPSStats = new PlayerPostSeasonBattingStats(nextRecord);
				    
				    
				   // System.out.println(plInfo.toString());

					//opsPlayerPostSeasonBattingStats.put(ns, "playerpostseasonbattingstats", plyPSStats.getPlayerid() + ":" + plyPSStats.getYearid(), plyPSStats);
					
					as.put(ns, "playerpostseasonbattingstats", plyPSStats.getPlayerid() + ":" + plyPSStats.getYearid(), plyPSStats);
					
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
		    
                 
          break;
   
        case "get":
            PlayerInfo plInfo;
            PlayerYearlyBattingStats plBattingStats;
            
        	plInfo = as.getPlayerInfo(ns, set, ukey);
        	
        	//System.out.println(plInfo.toString());
        	
        	
        	Iterator<String> it = plInfo.getlyrYrlyBatStats().iterator();
        	while (it.hasNext()) {
        		plBattingStats = as.getPlayerYearlyBattingStats("test", "playeryearlybattingstats", it.next());
        		plBattingStats.toString();
        	}
          
          break;
          /* 
        case "getbatch":
          as.getBatch(ns, set, ukey);
          break;

        case "trunc":
          as.trunc(ns, set);
          break;
    */
      } // switch

    } // try

    catch (Exception e) {
      log.error(String.format("Error: %s", e.getMessage()));
      if (cl==null || cl.getBool("--stackTrace")) {
        e.printStackTrace();
      }
    }

    finally {
      if (as != null) {
        as.close();
      }
    }

  } // main


} // Main

