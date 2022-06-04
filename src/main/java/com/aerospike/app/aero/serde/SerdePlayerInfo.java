
package com.aerospike.app.aero.serde;

import com.aerospike.app.model.Object1;
import com.aerospike.app.model.PlayerInfo;
import com.aerospike.client.Bin;
import com.aerospike.client.Record;

import com.aerospike.app.util.SortedArrayList;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class SerdePlayerInfo {

  static Logger log = Logger.getLogger("SerdeObject1");

  static public String PLAYERID = "pid";
  static public String BIRTHYEAR = "byear";
  static public String BIRTHMONTH = "bmonth";
  static public String BIRTHDAY = "bday";
  static public String BIRTHCOUNTRY = "bcntry";
  static public String BIRTHSTATE = "bstate";
  static public String BIRTHCITY = "bcity";
  static public String DEATHYEAR = "dyear";
  static public String DEATHMONTH = "dmonth";
  static public String DEATHDAY = "dday";
  static public String DEATHCOUNTRY = "dcntry";
  static public String DEATHSTATE = "dstate";
  static public String DEATHCITY = "dcity";
  static public String NAMEFIRST = "fname";
  static public String NAMELAST = "lname";
  static public String NAMEGIVEN = "gname";
  static public String WEIGHT = "wt";
  static public String HEIGHT = "ht";
  static public String BATS = "bats";
  static public String ARM = "arm";
  static public String DEBUT = "debut";
  static public String FINALGAME = "fgame";
  static public String RETROID = "retroid";
  static public String BBREFID = "bbrefid";
  static public String PYBS = "pybsId";


  public SerdePlayerInfo () {
  }


  static public Bin[] serialize (PlayerInfo pInfo) {

    List<Bin> binList = new ArrayList<Bin>();

    binList.add(new Bin(PLAYERID, pInfo.getPlayerid())); 
    binList.add(new Bin(BIRTHYEAR, pInfo.getBirthyear())); 
    binList.add(new Bin(BIRTHMONTH, pInfo.getBirthmonth())); 
    binList.add(new Bin(BIRTHDAY, pInfo.getBirthday()));   
    binList.add(new Bin(BIRTHCOUNTRY, pInfo.getBirthcountry())); 
    binList.add(new Bin(BIRTHSTATE, pInfo.getBirthstate())); 
    binList.add(new Bin(BIRTHCITY, pInfo.getBirthcity())); 
    binList.add(new Bin(DEATHYEAR, pInfo.getDeathyear())); 
    binList.add(new Bin(DEATHMONTH, pInfo.getDeathmonth())); 
    binList.add(new Bin(DEATHDAY, pInfo.getDeathday())); 
    binList.add(new Bin(DEATHCOUNTRY, pInfo.getDeathcountry())); 
    binList.add(new Bin(DEATHSTATE, pInfo.getDeathstate())); 
    binList.add(new Bin(DEATHCITY, pInfo.getDeathcity())); 
    binList.add(new Bin(NAMEFIRST, pInfo.getNamefirst())); 
    binList.add(new Bin(NAMELAST, pInfo.getNamelast())); 
    binList.add(new Bin(NAMEGIVEN, pInfo.getNamegiven())); 
    binList.add(new Bin(WEIGHT, pInfo.getWeight())); 
    binList.add(new Bin(HEIGHT, pInfo.getHeight())); 
    binList.add(new Bin(BATS, pInfo.getBats())); 
    binList.add(new Bin(ARM, pInfo.getArm())); 
    binList.add(new Bin(DEBUT, pInfo.getDebut())); 
    binList.add(new Bin(FINALGAME, pInfo.getFinalgame())); 
    binList.add(new Bin(RETROID, pInfo.getRetroid())); 
    binList.add(new Bin(BBREFID, pInfo.getBbrefid())); 
    binList.add(new Bin(PYBS, pInfo.getlyrYrlyBatStats())); 

    return binList.toArray(new Bin[binList.size()]);

  } // serialize

  static public PlayerInfo deserialize (Record record) {

	    String playerid 			   = record.getString(PLAYERID);
	    int birthyear  	    		   = record.getInt(BIRTHYEAR);
	    int birthmonth  			   = record.getInt(BIRTHMONTH);
	    int birthday   				   = record.getInt(BIRTHDAY);
	    String birthcountry 		   = record.getString(BIRTHCOUNTRY);
	    String birthstate   		   = record.getString(BIRTHSTATE);
	    String birthcity			   = record.getString(BIRTHCITY);
	    int deathyear				   = record.getInt(DEATHYEAR);
	    int deathmonth				   = record.getInt(DEATHMONTH);
	    int deathday				   = record.getInt(DEATHDAY);
	    String deathcountry			   = record.getString(DEATHCOUNTRY);
	    String deathstate 			   = record.getString(DEATHSTATE);
	    String deathcity			   = record.getString(DEATHCITY);
	    String namefirst			   = record.getString(NAMEFIRST);
	    String namelast		  	       = record.getString(NAMELAST);
	    String namegiven			   = record.getString(NAMEGIVEN);
	    int weight					   = record.getInt(WEIGHT);
	    int height					   = record.getInt(HEIGHT);
	    String bats					   = record.getString(BATS);
	    String arm					   = record.getString(ARM);
	    String debut				   = record.getString(DEBUT);
	    String finalgame			   = record.getString(FINALGAME);
	    String retroid				   = record.getString(RETROID);
	    String bbrefid				   = record.getString(BBREFID);
	    ArrayList<String> pybsId = (ArrayList<String>)record.getList(PYBS);
	    //SortedArrayList<String> pybsId = record.getList(PYBS);

  	    return new PlayerInfo(playerid, birthyear, birthmonth, birthday, birthcountry,
							  birthstate, birthcity, deathyear, deathmonth, deathday, deathcountry,
							  deathstate, deathcity, namefirst, namelast, namegiven, weight,
							  height, bats, arm, debut, finalgame, retroid, bbrefid, pybsId);
	} // deserialize

} // SerdePlayerIno
