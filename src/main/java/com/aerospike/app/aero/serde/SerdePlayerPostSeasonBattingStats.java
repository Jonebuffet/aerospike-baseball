package com.aerospike.app.aero.serde;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.aerospike.app.model.PlayerPostSeasonBattingStats;
import com.aerospike.app.model.PlayerYearlyBattingStats;
import com.aerospike.client.Bin;
import com.aerospike.client.Record;

public class SerdePlayerPostSeasonBattingStats {
	  static Logger log = Logger.getLogger("SerdePlayerYearlyBattingStats");

	  static public String PLAYERID = "pid";
	  static public String ROUND	= "rnd";
	  static public String YEARID   = "yid";
	  static public String AB       = "ab";
	  static public String BB       = "bb";
	  static public String CS       = "cs";
	  static public String DOUBLES  = "dbles";
	  static public String GAMES    = "gms";
	  static public String GIDP     = "gidp";
	  static public String HBP      = "hbp";
	  static public String HITS     = "hits";
	  static public String HR       = "hr";
	  static public String BA       = "ba";
	  static public String IBB      = "ibb";
	  static public String LGID     = "lgid";
	  static public String RBI      = "rbi";
	  static public String RUNS     = "runs";
	  static public String SB       = "sb";
	  static public String SF       = "sf";
	  static public String SH       = "sh";
	  static public String SO       = "so";
	  static public String STINT    = "stint";
	  static public String TEAMID   = "tmid";
	  static public String TRIPLES  = "trpls";
	  static public String BBREFID  = "bbrefid";
	  static public String OBP      = "obp";
	  static public String SLG      = "slg";
	  static public String OPS      = "ops";
	  static public String OPS_PLUS = "ops_plus";

	  public SerdePlayerPostSeasonBattingStats () {
	  }


	  static public Bin[] serialize (PlayerPostSeasonBattingStats pPSBattingStats) {

	    List<Bin> binList = new ArrayList<Bin>();

	    binList.add(new Bin(PLAYERID, pPSBattingStats.getPlayerid()));
	    binList.add(new Bin(ROUND, pPSBattingStats.getRound()));
	    binList.add(new Bin(YEARID, pPSBattingStats.getYearid()));
	    binList.add(new Bin(AB, pPSBattingStats.getAb()));
	    binList.add(new Bin(BB, pPSBattingStats.getBb()));
	    binList.add(new Bin(CS, pPSBattingStats.getCs()));
	    binList.add(new Bin(DOUBLES, pPSBattingStats.getDoubles()));
	    binList.add(new Bin(GAMES, pPSBattingStats.getGames()));
	    binList.add(new Bin(GIDP, pPSBattingStats.getGidp()));
	    binList.add(new Bin(HBP, pPSBattingStats.getHbp()));
	    binList.add(new Bin(HITS, pPSBattingStats.getHits()));
	    binList.add(new Bin(HR, pPSBattingStats.getHr()));
	    binList.add(new Bin(BA, pPSBattingStats.getBa()));
	    binList.add(new Bin(IBB, pPSBattingStats.getIbb()));
	    binList.add(new Bin(LGID, pPSBattingStats.getLgid()));
	    binList.add(new Bin(RBI, pPSBattingStats.getRbi()));
	    binList.add(new Bin(RUNS, pPSBattingStats.getRuns()));
	    binList.add(new Bin(SB, pPSBattingStats.getSb()));
	    binList.add(new Bin(SF, pPSBattingStats.getSf()));
	    binList.add(new Bin(SH, pPSBattingStats.getSh()));
	    binList.add(new Bin(SO, pPSBattingStats.getSo()));
	    binList.add(new Bin(STINT, pPSBattingStats.getStint()));
	    binList.add(new Bin(TEAMID, pPSBattingStats.getTeamid()));
	    binList.add(new Bin(TRIPLES, pPSBattingStats.getTriples()));
	    binList.add(new Bin(OBP, pPSBattingStats.getObp()));
	    binList.add(new Bin(SLG, pPSBattingStats.getSlg()));
	    binList.add(new Bin(OPS, pPSBattingStats.getOps()));
	    binList.add(new Bin(OPS_PLUS, pPSBattingStats.getOps_plus()));

	    return binList.toArray(new Bin[binList.size()]);

	  } // serialize

	  static public PlayerPostSeasonBattingStats deserialize (Record record) {

		    String playerid 	= record.getString(PLAYERID);
		    String round		= record.getString(ROUND);
		    int yearid			= record.getInt(YEARID);
		    int ab				= record.getInt(AB);
		    int bb				= record.getInt(BB);
		    int cs				= record.getInt(CS);
		    int doubles			= record.getInt(DOUBLES);
		    int games			= record.getInt(GAMES);
		    int gidp			= record.getInt(GIDP);
		    int hbp				= record.getInt(HBP);
		    int hits			= record.getInt(HITS);
		    int hr				= record.getInt(HR);
		    double ba			= record.getDouble(BA);
		    int ibb				= record.getInt(IBB);
		    String lgid			= record.getString(LGID);
		    int rbi				= record.getInt(RBI);
		    int runs			= record.getInt(RUNS);
		    int sb				= record.getInt(SB);
		    int sf				= record.getInt(SF);
		    int sh				= record.getInt(SH);
		    int so				= record.getInt(SO);
		    int stint			= record.getInt(STINT);
		    String teamid		= record.getString(TEAMID);
		    String leagueid		= record.getString(LGID);
		    int triples			= record.getInt(TRIPLES);
		    double obp 			= record.getDouble(OBP);
		    double slg			= record.getDouble(SLG);
		    double ops			= record.getDouble(OPS);
		    double ops_plus		= record.getDouble(OPS_PLUS);


		    return new PlayerPostSeasonBattingStats(playerid, yearid, round, ab, bb, cs, doubles, games,
		    		gidp, hbp, hits, hr, ba, ibb, lgid, rbi, runs, sb, sf,
		    		sh, so, stint, teamid, triples, obp, slg, ops,
		    		ops_plus);

		} // deserialize
}
