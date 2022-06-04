package com.aerospike.app.model;

public class PlayerYearlyBattingStats {
	
	String playerid;
    int yearid;
    int ab;
    int bb;
    int cs;
    int doubles;
    int games;
    int gidp;
    int hbp;
    int hits;
    int hr;
    double ba;
    int ibb;
    String lgid;
    int rbi;
    int runs;
    int sb;
    int sf;
    int sh;
    int so;
    int stint;
    String teamid;
    String leagueid;
    int triples;
    double obp; 
    double slg;
    double ops;
    double ops_plus;
    double batting_avg; 
    
	public PlayerYearlyBattingStats(String playerid, int yearid, int ab, int bb, int cs, int doubles, int games,
			int gidp, int hbp, int hits, int hr, int ibb, String lgid, int rbi, int runs, int sb, int sf,
			int sh, int so, int stint, String teamid, int triples, double obp, double slg, double ops,
			double ops_plus, double batting_avg) {
		
		super();
		this.playerid = playerid;
		this.yearid = yearid;
		this.ab = ab;
		this.bb = bb;
		this.cs = cs;
		this.doubles = doubles;
		this.games = games;
		this.gidp = gidp;
		this.hbp = hbp;
		this.hits = hits;
		this.hr = hr;
		this.ba = batting_avg;
		this.ibb = ibb;
		this.lgid = lgid;
		this.rbi = rbi;
		this.runs = runs;
		this.sb = sb;
		this.sf = sf;
		this.sh = sh;
		this.so = so;
		this.stint = stint;
		this.teamid = teamid;
		this.triples = triples;
		this.obp = obp;
		this.slg = slg;
		this.ops = ops;
		this.ops_plus = ops_plus;
		this.batting_avg = batting_avg;
	}
	
	public PlayerYearlyBattingStats(String[] playerYearlyBattingStats ) {
		//super();
		
		this.playerid = playerYearlyBattingStats[0];
		
		this.yearid = Integer.parseInt(playerYearlyBattingStats[1]);
		
		if (playerYearlyBattingStats[2].length() > 0) {
			this.stint = Integer.parseInt(playerYearlyBattingStats[2]);
		}
		this.teamid = playerYearlyBattingStats[3];
		
		if (playerYearlyBattingStats[4].length() > 0) {
			this.lgid = playerYearlyBattingStats[4];
		}
	
		if (playerYearlyBattingStats[5].length() > 0) {
			this.games = Integer.parseInt(playerYearlyBattingStats[5]);
		}
		
		if (playerYearlyBattingStats[6].length() > 0) {
			this.ab = Integer.parseInt(playerYearlyBattingStats[6].substring(0, playerYearlyBattingStats[6].length() -2 ));
		}
		
		if (playerYearlyBattingStats[7].length() > 0) {
			this.runs = Integer.parseInt(playerYearlyBattingStats[7].substring(0, playerYearlyBattingStats[7].length() -2 ));
		}
		
		if (playerYearlyBattingStats[8].length() > 0) {
			this.hits = Integer.parseInt(playerYearlyBattingStats[8].substring(0, playerYearlyBattingStats[8].length() -2 ));
		}
		
		if (playerYearlyBattingStats[9].length() > 0) {
			this.doubles = Integer.parseInt(playerYearlyBattingStats[9].substring(0, playerYearlyBattingStats[9].length() -2 ));
		}
		
		if (playerYearlyBattingStats[10].length() > 0) {
			this.triples = Integer.parseInt(playerYearlyBattingStats[10].substring(0, playerYearlyBattingStats[10].length() -2 ));
		}
		
		if (playerYearlyBattingStats[11].length() > 0) {
			this.hr = Integer.parseInt(playerYearlyBattingStats[11].substring(0, playerYearlyBattingStats[11].length() -2 ));
		}
		
		if (playerYearlyBattingStats[12].length() > 0) {
			this.rbi =Integer.parseInt(playerYearlyBattingStats[12].substring(0, playerYearlyBattingStats[12].length() -2 ));
		}
		
		if (playerYearlyBattingStats[13].length() > 0) {
			this.sb =Integer.parseInt(playerYearlyBattingStats[13].substring(0, playerYearlyBattingStats[13].length() -2 ));
		}
		
		if (playerYearlyBattingStats[14].length() > 0) {

			this.cs = Integer.parseInt(playerYearlyBattingStats[14].substring(0, playerYearlyBattingStats[14].length() -2 ));
		}
		
		if (playerYearlyBattingStats[15].length() > 0) {
			this.bb = Integer.parseInt(playerYearlyBattingStats[15].substring(0, playerYearlyBattingStats[15].length() -2 ));
		}
		
		if (playerYearlyBattingStats[16].length() > 0) {
			this.so = Integer.parseInt(playerYearlyBattingStats[16].substring(0, playerYearlyBattingStats[16].length() -2 ));
		}
		
		if (playerYearlyBattingStats[17].length() > 0) {
			this.ibb = Integer.parseInt(playerYearlyBattingStats[17].substring(0, playerYearlyBattingStats[17].length() -2 ));
		}
		
		if (playerYearlyBattingStats[18].length() > 0) {
			this.hbp = Integer.parseInt(playerYearlyBattingStats[18].substring(0, playerYearlyBattingStats[18].length() -2 ));
		}
		
		if (playerYearlyBattingStats[19].length() > 0) {
			this.sh = Integer.parseInt(playerYearlyBattingStats[19].substring(0, playerYearlyBattingStats[19].length() -2 ));
		}
		
		if (playerYearlyBattingStats[20].length() > 0) {
			this.sf = Integer.parseInt(playerYearlyBattingStats[20].substring(0, playerYearlyBattingStats[20].length() -2 ));
		}
		
		if (playerYearlyBattingStats[21].length() > 0) {
			this.gidp = Integer.parseInt(playerYearlyBattingStats[21].substring(0, playerYearlyBattingStats[21].length() -2 ));
		}
		
		
		
	}
	public String getPlayerid() {
		return playerid;
	}
	public void setPlayerid(String playerid) {
		this.playerid = playerid;
	}
	public int getYearid() {
		return yearid;
	}
	public void setYearid(int yearid) {
		this.yearid = yearid;
	}
	public int getAb() {
		return ab;
	}
	public void setAb(int ab) {
		this.ab = ab;
	}
	public int getBb() {
		return bb;
	}
	public void setBb(int bb) {
		this.bb = bb;
	}
	public int getCs() {
		return cs;
	}
	public void setCs(int cs) {
		this.cs = cs;
	}
	public int getDoubles() {
		return doubles;
	}
	public void setDoubles(int doubles) {
		this.doubles = doubles;
	}
	public int getGames() {
		return games;
	}
	public void setGames(int games) {
		this.games = games;
	}
	public int getGidp() {
		return gidp;
	}
	public void setGidp(int gidp) {
		this.gidp = gidp;
	}
	public int getHbp() {
		return hbp;
	}
	public void setHbp(int hbp) {
		this.hbp = hbp;
	}
	public int getHits() {
		return hits;
	}
	public void setHits(int hits) {
		this.hits = hits;
	}
	public int getHr() {
		return hr;
	}
	public void setHr(int hr) {
		this.hr = hr;
	}
	public double getBa() {
		return ba;
	}
	public void setBa(double ba) {
		this.ba = ba;
	}
	public int getIbb() {
		return ibb;
	}
	public void setIbb(int ibb) {
		this.ibb = ibb;
	}
	public String getLgid() {
		return lgid;
	}
	public void setLgid(String lgid) {
		this.lgid = lgid;
	}
	public int getRbi() {
		return rbi;
	}
	public void setRbi(int rbi) {
		this.rbi = rbi;
	}
	public int getRuns() {
		return runs;
	}
	public void setRuns(int runs) {
		this.runs = runs;
	}
	public int getSb() {
		return sb;
	}
	public void setSb(int sb) {
		this.sb = sb;
	}
	public int getSf() {
		return sf;
	}
	public void setSf(int sf) {
		this.sf = sf;
	}
	public int getSh() {
		return sh;
	}
	public void setSh(int sh) {
		this.sh = sh;
	}
	public int getSo() {
		return so;
	}
	public void setSo(int so) {
		this.so = so;
	}
	public int getStint() {
		return stint;
	}
	public void setStint(int stint) {
		this.stint = stint;
	}
	public String getTeamid() {
		return teamid;
	}
	public void setTeamid(String teamid) {
		this.teamid = teamid;
	}
	public int getTriples() {
		return triples;
	}
	public void setTriples(int triples) {
		this.triples = triples;
	}
	public double getObp() {
		return obp;
	}
	public void setObp(double obp) {
		this.obp = obp;
	}
	public double getSlg() {
		return slg;
	}
	public void setSlg(double slg) {
		this.slg = slg;
	}
	public double getOps() {
		return ops;
	}
	public void setOps(double ops) {
		this.ops = ops;
	}
	public double getOps_plus() {
		return ops_plus;
	}
	public void setOps_plus(double ops_plus) {
		this.ops_plus = ops_plus;
	}
	
    @Override
	public String toString() {
		return "PlayerYearlyBattingStats [playerid=" + playerid + ", yearid=" + yearid + ", ab=" + ab + ", bb=" + bb
				+ ", cs=" + cs + ", doubles=" + doubles + ", games=" + games + ", gidp=" + gidp + ", hbp=" + hbp
				+ ", hits=" + hits + ", hr=" + hr + ", ba=" + ba + ", ibb=" + ibb + ", lgid=" + lgid + ", rbi=" + rbi
				+ ", runs=" + runs + ", sb=" + sb + ", sf=" + sf + ", sh=" + sh + ", so=" + so + ", stint=" + stint
				+ ", teamid=" + teamid + ", leagueid=" + leagueid + ", triples=" + triples + ", obp=" + obp + ", slg="
				+ slg + ", ops=" + ops + ", ops_plus=" + ops_plus + ", batting_avg=" + batting_avg + "]";
	}

}
