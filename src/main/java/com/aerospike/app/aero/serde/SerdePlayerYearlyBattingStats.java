package com.aerospike.app.aero.serde;

import com.aerospike.app.model.PlayerYearlyBattingStats;
import com.aerospike.client.Bin;
import com.aerospike.client.Record;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;


public class SerdePlayerYearlyBattingStats {

  static Logger log = Logger.getLogger("SerdePlayerYearlyBattingStats");

  static public String PLAYERID = "pid";
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
  static public String BA_AVG	= "batting_avg";

  public SerdePlayerYearlyBattingStats () {
  }


  static public Bin[] serialize (PlayerYearlyBattingStats pYearlyBattingStats) {

    List<Bin> binList = new ArrayList<Bin>();

    binList.add(new Bin(PLAYERID, pYearlyBattingStats.getPlayerid()));
    binList.add(new Bin(YEARID, pYearlyBattingStats.getYearid()));
    binList.add(new Bin(AB, pYearlyBattingStats.getAb()));
    binList.add(new Bin(BB, pYearlyBattingStats.getBb()));
    binList.add(new Bin(CS, pYearlyBattingStats.getCs()));
    binList.add(new Bin(DOUBLES, pYearlyBattingStats.getDoubles()));
    binList.add(new Bin(GAMES, pYearlyBattingStats.getGames()));
    binList.add(new Bin(GIDP, pYearlyBattingStats.getGidp()));
    binList.add(new Bin(HBP, pYearlyBattingStats.getHbp()));
    binList.add(new Bin(HITS, pYearlyBattingStats.getHits()));
    binList.add(new Bin(HR, pYearlyBattingStats.getHr()));
    binList.add(new Bin(BA, pYearlyBattingStats.getBa()));
    binList.add(new Bin(IBB, pYearlyBattingStats.getIbb()));
    binList.add(new Bin(LGID, pYearlyBattingStats.getLgid()));
    binList.add(new Bin(RBI, pYearlyBattingStats.getRbi()));
    binList.add(new Bin(RUNS, pYearlyBattingStats.getRuns()));
    binList.add(new Bin(SB, pYearlyBattingStats.getSb()));
    binList.add(new Bin(SF, pYearlyBattingStats.getSf()));
    binList.add(new Bin(SH, pYearlyBattingStats.getSh()));
    binList.add(new Bin(SO, pYearlyBattingStats.getSo()));
    binList.add(new Bin(STINT, pYearlyBattingStats.getStint()));
    binList.add(new Bin(TEAMID, pYearlyBattingStats.getTeamid()));
    binList.add(new Bin(TRIPLES, pYearlyBattingStats.getTriples()));
    binList.add(new Bin(OBP, pYearlyBattingStats.getObp()));
    binList.add(new Bin(SLG, pYearlyBattingStats.getSlg()));
    binList.add(new Bin(OPS, pYearlyBattingStats.getOps()));
    binList.add(new Bin(OPS_PLUS, pYearlyBattingStats.getOps_plus()));

    return binList.toArray(new Bin[binList.size()]);

  } // serialize

  static public PlayerYearlyBattingStats deserialize (Record record) {

	    String playerid 	= record.getString(PLAYERID);
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
	    double batting_avg	= record.getDouble(BA_AVG);


	    return new PlayerYearlyBattingStats(playerid, yearid, ab, bb, cs, doubles, games,
	    		gidp, hbp, hits, hr, ba, ibb, lgid, rbi, runs, sb, sf,
	    		sh, so, stint, teamid, triples, obp, slg, ops,
	    		ops_plus, batting_avg);

	} // deserialize

} //SerdePlayerYearlyBattingStats
