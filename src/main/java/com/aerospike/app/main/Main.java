
package com.aerospike.app.main;

import com.aerospike.app.main.CmdlnParser;
import com.aerospike.app.util.Cmdln;
import com.aerospike.client.Record;
import com.aerospike.client.query.RecordSet;
import com.aerospike.app.aero.Aero;
import com.aerospike.app.aero.ops.OpsPlayerPostSeasonBattingStats;
import com.aerospike.app.model.PlayerInfo;
import com.aerospike.app.util.*;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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
  
  private static Aero as = null;

  public static void main(String[] args) {
	
      String host = "127.0.0.1";
      int port = 3000;
      String ns = "test";
      String set = "playerinfo";
      
      Cmdln cl = null;
      
      // Create Aero object
      as = new Aero(host, port);
      
    try {
    
		/*
		 * cl = new CmdlnParser(args).cl;
		 * 
		 * // String cmd = cl.getStr("--command");
		 * 
		 * long recStartId = cl.getLong("--recStartId"); long recCount =
		 * cl.getLong("--recCount"); String f1 = cl.getStr("--object1Field1"); String f2
		 * = cl.getStr("--object1Field2"); String otyp = cl.getStr("--object1Type"); int
		 * int1 = cl.getInt("--object1Int1"); String strLst =
		 * cl.getStr("--object1StringList");
		 * 
		 * String host = cl.getStr("--host"); int port = cl.getInt("--port"); String ns
		 * = cl.getStr("--namespace"); String set = cl.getStr("--set"); String ukey =
		 * cl.getStr("--userKey");
		 * 
		 * long limit = cl.getLong("--limit");
		 * 
		 * boolean verbose = cl.getBool("--verbose"); int updateRate =
		 * cl.getInt("--updateRate");
		 * 
		 * int threadCount = cl.getInt("--threadCount");
		 * 
		 * log.info(String.format("~~~~ Starting %s ~~~~\n", cmd));
		 */
      
 
   
      /* String ukey = cl.getStr("--userKey"); */
      String ukey = "yastrca01";
      //String searchStr = "^yastrzemsk.*";
      String searchStr = "^smith.*";
      //String searchStr = "^roof.*";

      
      String cmd = "search";

      // Run chosen command
      switch (cmd) {
     
        case "loadplayer":
          
        	as.load(ns, set, DATA_DIR_PATH + playerFile);
        	
        	break;
        	
        case "loadplayerybs":
            
        	as.load(ns, set, DATA_DIR_PATH + playerYearlyBattingStatsFile);
        	
        	break;
        	
        case "loadplayerpsbs":
            
        	as.load(ns, set, DATA_DIR_PATH + playerPostSeasonBattingStatsFile);
        	
        	break;
        	
        case "search":
            
        	Record record;
        	RecordSet rs = as.searchPlayerInfo(ns, set, searchStr);
        	while (rs.next()) {
        		record = rs.getRecord();
        		System.out.println(new PlayerInfo(record).toString());
        	}
        	
        	record = null;
        	rs = null;
        	
        	break;
    
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

