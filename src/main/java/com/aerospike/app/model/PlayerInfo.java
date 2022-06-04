package com.aerospike.app.model;

import java.util.ArrayList;


public class PlayerInfo {
	
	String playerid;
    int birthyear;
    int birthmonth;
    int birthday;
    String birthcountry;
    String birthstate;
    String birthcity;
    int deathyear;
    int deathmonth;
    int deathday;
    String deathcountry;
    String deathstate;
    String deathcity;
    String namefirst;
    String namelast;
    String namegiven;
    int weight;
    int height;
    String bats;
    String arm; 
    String debut;
    String finalgame;
    String retroid;
    String bbrefid;
    ArrayList<String> playerYearlyBattingStats;
	
	public enum ObjType {
	    T1, T2, T3;
	}
	
	public PlayerInfo() {
		this.playerYearlyBattingStats = new ArrayList<>();
	}
	
	public PlayerInfo(String playerid, int birthyear, int birthmonth, int birthday, String birthcountry,
			String birthstate, String birthcity, int deathyear, int deathmonth, int deathday, String deathcountry,
			String deathstate, String deathcity, String namefirst, String namelast, String namegiven, int weight,
			int height, String bats, String arm, String debut, String finalgame, String retroid, String bbrefid, ArrayList<String> pybsId ) {
		
		this.playerid = playerid;
		this.birthyear = birthyear;
		this.birthmonth = birthmonth;
		this.birthday = birthday;
		this.birthcountry = birthcountry;
		this.birthstate = birthstate;
		this.birthcity = birthcity;
		this.deathyear = deathyear;
		this.deathmonth = deathmonth;
		this.deathday = deathday;
		this.deathcountry = deathcountry;
		this.deathstate = deathstate;
		this.deathcity = deathcity;
		this.namefirst = namefirst;
		this.namelast = namelast;
		this.namegiven = namegiven;
		this.weight = weight;
		this.height = height;
		this.bats = bats;
		this.arm = arm;
		this.debut = debut;
		this.finalgame = finalgame;
		this.retroid = retroid;
		this.bbrefid = bbrefid;
		this.playerYearlyBattingStats = pybsId;
		
		/*
		if (pybsId .length() > 0) {
			this.playerYearlyBattingStats = new ArrayList<>();
			this.playerYearlyBattingStats.add(pybsId);	
		}
		*/
		
	}

	public PlayerInfo(String playerid, int birthyear, int birthmonth, int birthday, String birthcountry,
			String birthstate, String birthcity, int deathyear, int deathmonth, int deathday, String deathcountry,
			String deathstate, String deathcity, String namefirst, String namelast, String namegiven, int weight,
			int height, String bats, String arm, String debut, String finalgame, String retroid, String bbrefid, String pybsId ) {
		
		this.playerid = playerid;
		this.birthyear = birthyear;
		this.birthmonth = birthmonth;
		this.birthday = birthday;
		this.birthcountry = birthcountry;
		this.birthstate = birthstate;
		this.birthcity = birthcity;
		this.deathyear = deathyear;
		this.deathmonth = deathmonth;
		this.deathday = deathday;
		this.deathcountry = deathcountry;
		this.deathstate = deathstate;
		this.deathcity = deathcity;
		this.namefirst = namefirst;
		this.namelast = namelast;
		this.namegiven = namegiven;
		this.weight = weight;
		this.height = height;
		this.bats = bats;
		this.arm = arm;
		this.debut = debut;
		this.finalgame = finalgame;
		this.retroid = retroid;
		this.bbrefid = bbrefid;
		
		if (pybsId .length() > 0) {
			this.playerYearlyBattingStats = new ArrayList<String>();
			this.playerYearlyBattingStats.add(pybsId);
		}
	}
	
	public PlayerInfo(String[] playerInfoRecord ) {
		//super();
		
		this.playerid = playerInfoRecord[0];
		
		if (playerInfoRecord[1] !=null && playerInfoRecord[1].length() > 0) {
			this.birthyear = Integer.parseInt(playerInfoRecord[1].substring(0, playerInfoRecord[1].length() -2 ));
		}
		
		if (playerInfoRecord[2].length() > 0) {
			this.birthmonth = Integer.parseInt(playerInfoRecord[2].substring(0, playerInfoRecord[2].length() -2 ));
		}

		if (playerInfoRecord[3].length() > 0) {
			this.birthday = Integer.parseInt(playerInfoRecord[3].substring(0, playerInfoRecord[3].length() -2 ));
		}

		this.birthcountry = playerInfoRecord[4];
		this.birthstate = playerInfoRecord[5];
		this.birthcity = playerInfoRecord[6];

		if (playerInfoRecord[7].length() > 0) {
			this.deathyear = Integer.parseInt(playerInfoRecord[7].substring(0, playerInfoRecord[7].length() -2 ));
		}
	
		if (playerInfoRecord[8].length() > 0) {
			
			this.deathmonth = Integer.parseInt(playerInfoRecord[8].substring(0, playerInfoRecord[8].length() -2 ));
		}

		if (playerInfoRecord[9].length() > 0) {
			this.deathday = Integer.parseInt(playerInfoRecord[9].substring(0, playerInfoRecord[9].length() -2 ));
		}
		
		
		if (playerInfoRecord[10].length() > 0) {
			this.deathcountry = playerInfoRecord[10];
		}
		
		if (playerInfoRecord[11].length() > 0) {
			this.deathstate = playerInfoRecord[11];
		}
		
		this.deathstate = playerInfoRecord[11];
		this.deathcity = playerInfoRecord[12];
		this.namefirst = playerInfoRecord[13];
		this.namelast = playerInfoRecord[14];
		this.namegiven = playerInfoRecord[15];

		if (playerInfoRecord[16].length() > 0) {
			
			this.weight = Integer.parseInt(playerInfoRecord[16].substring(0, playerInfoRecord[16].length() -2 ));
			
		}

		if (playerInfoRecord[17].length() > 0) {
			
			this.height = Integer.parseInt(playerInfoRecord[17].substring(0, playerInfoRecord[17].length() -2 ));
			
		}
		
		this.bats = playerInfoRecord[18];
		this.arm = playerInfoRecord[19];
		this.debut = playerInfoRecord[20];
		this.finalgame = playerInfoRecord[21];
		this.retroid = playerInfoRecord[22];
		this.bbrefid = playerInfoRecord[23];
		this.playerYearlyBattingStats = new ArrayList<String>();
		
	}
	
    public String getPlayerid() {
		return playerid;
	}

	public void setPlayerid(String playerid) {
		this.playerid = playerid;
	}

	public int getBirthyear() {
		return birthyear;
	}

	public void setBirthyear(int birthyear) {
		this.birthyear = birthyear;
	}

	public int getBirthmonth() {
		return birthmonth;
	}

	public void setBirthmonth(int birthmonth) {
		this.birthmonth = birthmonth;
	}

	public int getBirthday() {
		return birthday;
	}

	public void setBirthday(int birthday) {
		this.birthday = birthday;
	}

	public String getBirthcountry() {
		return birthcountry;
	}

	public void setBirthcountry(String birthcountry) {
		this.birthcountry = birthcountry;
	}

	public String getBirthstate() {
		return birthstate;
	}

	public void setBirthstate(String birthstate) {
		this.birthstate = birthstate;
	}

	public String getBirthcity() {
		return birthcity;
	}

	public void setBirthcity(String birthcity) {
		this.birthcity = birthcity;
	}

	public int getDeathyear() {
		return deathyear;
	}

	public void setDeathyear(int deathyear) {
		this.deathyear = deathyear;
	}

	public int getDeathmonth() {
		return deathmonth;
	}

	public void setDeathmonth(int deathmonth) {
		this.deathmonth = deathmonth;
	}

	public int getDeathday() {
		return deathday;
	}

	public void setDeathday(int deathday) {
		this.deathday = deathday;
	}

	public String getDeathcountry() {
		return deathcountry;
	}

	public void setDeathcountry(String deathcountry) {
		this.deathcountry = deathcountry;
	}

	public String getDeathstate() {
		return deathstate;
	}

	public void setDeathstate(String deathstate) {
		this.deathstate = deathstate;
	}

	public String getDeathcity() {
		return deathcity;
	}

	public void setDeathcity(String deathcity) {
		this.deathcity = deathcity;
	}

	public String getNamefirst() {
		return namefirst;
	}

	public void setNamefirst(String namefirst) {
		this.namefirst = namefirst;
	}

	public String getNamelast() {
		return namelast;
	}

	public void setNamelast(String namelast) {
		this.namelast = namelast;
	}

	public String getNamegiven() {
		return namegiven;
	}

	public void setNamegiven(String namegiven) {
		this.namegiven = namegiven;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getBats() {
		return bats;
	}

	public void setBats(String bats) {
		this.bats = bats;
	}

	public String getArm() {
		return arm;
	}

	public void setArm(String arm) {
		this.arm = arm;
	}

	public String getDebut() {
		return debut;
	}

	public void setDebut(String debut) {
		this.debut = debut;
	}

	public String getFinalgame() {
		return finalgame;
	}

	public void setFinalgame(String finalgame) {
		this.finalgame = finalgame;
	}

	public String getRetroid() {
		return retroid;
	}

	public void setRetroid(String retroid) {
		this.retroid = retroid;
	}

	public String getBbrefid() {
		return bbrefid;
	}

	public void setBbrefid(String bbrefid) {
		this.bbrefid = bbrefid;
	}
	
	public void addPlyrYrlyBatStats (String pybsId) {
		
		this.playerYearlyBattingStats.add(pybsId);
		this.playerYearlyBattingStats.sort(null);
	}
	

	public ArrayList<String> getlyrYrlyBatStats () {
	    return this.playerYearlyBattingStats;
}
	@Override
	public String toString() {
		return "PlayerInfo [playerid=" + playerid + ", birthyear=" + birthyear + ", birthmonth=" + birthmonth
				+ ", birthday=" + birthday + ", birthcountry=" + birthcountry + ", birthstate=" + birthstate
				+ ", birthcity=" + birthcity + ", deathyear=" + deathyear + ", deathmonth=" + deathmonth + ", deathday="
				+ deathday + ", deathcountry=" + deathcountry + ", deathstate=" + deathstate + ", deathcity="
				+ deathcity + ", namefirst=" + namefirst + ", namelast=" + namelast + ", namegiven=" + namegiven
				+ ", weight=" + weight + ", height=" + height + ", bats=" + bats + ", arm=" + arm + ", debut=" + debut
				+ ", finalgame=" + finalgame + ", retroid=" + retroid + ", bbrefid=" + bbrefid 
				+ ", playerYearlyBattingStats=" + playerYearlyBattingStats + "]";
	}

}


