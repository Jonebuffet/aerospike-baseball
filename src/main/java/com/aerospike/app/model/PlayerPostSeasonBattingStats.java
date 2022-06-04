package com.aerospike.app.model;

public class PlayerPostSeasonBattingStats {
	
		String playerid;
	    int yearid;
	    String round;
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
	    
		public PlayerPostSeasonBattingStats(String playerid, int yearid, String round, int ab, int bb, int cs, int doubles, int games,
				int gidp, int hbp, int hits, int hr, double ba, int ibb, String lgid, int rbi, int runs, int sb, int sf,
				int sh, int so, int stint, String teamid, int triples, double obp, double slg, double ops,
				double ops_plus) {
			
			super();
			this.playerid = playerid;
			this.yearid = yearid;
			this.round = round;
			this.ab = ab;
			this.bb = bb;
			this.cs = cs;
			this.doubles = doubles;
			this.games = games;
			this.gidp = gidp;
			this.hbp = hbp;
			this.hits = hits;
			this.hr = hr;
			this.ba = ba;
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
		}
		
		public PlayerPostSeasonBattingStats(String[] playerPostSeasonBattingStats ) {
			//super();
			this.yearid = Integer.parseInt(playerPostSeasonBattingStats[0]);
			
			this.round = playerPostSeasonBattingStats[1];
			
			if (playerPostSeasonBattingStats[2].length() > 0) {
				this.playerid = playerPostSeasonBattingStats[2];
			}
			this.teamid = playerPostSeasonBattingStats[3];
			
			if (playerPostSeasonBattingStats[4].length() > 0) {
				this.lgid = playerPostSeasonBattingStats[4];
			}
		
			if (playerPostSeasonBattingStats[5].length() > 0) {
				if (playerPostSeasonBattingStats[5].contains(".0")) {
					this.games = Integer.parseInt(playerPostSeasonBattingStats[5].substring(0, playerPostSeasonBattingStats[5].length() -2 ));
				} else {
					this.games = Integer.parseInt(playerPostSeasonBattingStats[5]);
				}
			}
			
			if (playerPostSeasonBattingStats[6].length() > 0) {
				//System.out.println("playerid  = " + playerPostSeasonBattingStats[2] + ", ab = "+ playerPostSeasonBattingStats[6]);
				if (playerPostSeasonBattingStats[6].contains(".0")) {
					this.ab = Integer.parseInt(playerPostSeasonBattingStats[6].substring(0, playerPostSeasonBattingStats[6].length() -2 ));
				} else {
					this.ab = Integer.parseInt(playerPostSeasonBattingStats[6]);
				}
			}
			
			if (playerPostSeasonBattingStats[7].length() > 0) {
				if (playerPostSeasonBattingStats[7].contains(".0")) {
					this.runs = Integer.parseInt(playerPostSeasonBattingStats[7].substring(0, playerPostSeasonBattingStats[7].length() -2 ));
				} else {
					this.runs = Integer.parseInt(playerPostSeasonBattingStats[7]);
				}
			}
			
			if (playerPostSeasonBattingStats[8].length() > 0) {
				//this.hits = Integer.parseInt(playerPostSeasonBattingStats[8].substring(0, playerPostSeasonBattingStats[8].length() -2 ));
				if (playerPostSeasonBattingStats[8].contains(".0")) {
					this.hits = Integer.parseInt(playerPostSeasonBattingStats[8].substring(0, playerPostSeasonBattingStats[8].length() -2 ));
				} else {
					this.hits = Integer.parseInt(playerPostSeasonBattingStats[8]);
				}
			}
			
			if (playerPostSeasonBattingStats[9].length() > 0) {
				if (playerPostSeasonBattingStats[9].contains(".0")) {
					this.doubles = Integer.parseInt(playerPostSeasonBattingStats[9].substring(0, playerPostSeasonBattingStats[9].length() -2 ));
				} else {
					this.doubles = Integer.parseInt(playerPostSeasonBattingStats[9]);
				}
			}
			
			if (playerPostSeasonBattingStats[10].length() > 0) {
				if (playerPostSeasonBattingStats[10].contains(".0")) {
					this.triples = Integer.parseInt(playerPostSeasonBattingStats[9].substring(0, playerPostSeasonBattingStats[10].length() -2 ));
				} else {
					this.triples = Integer.parseInt(playerPostSeasonBattingStats[10]);
				}
			}
			
			if (playerPostSeasonBattingStats[11].length() > 0) {
				if (playerPostSeasonBattingStats[11].contains(".0")) {
					this.hr = Integer.parseInt(playerPostSeasonBattingStats[11].substring(0, playerPostSeasonBattingStats[11].length() -2 ));
				} else {
					this.hr = Integer.parseInt(playerPostSeasonBattingStats[11]);
				}
			}
			
			if (playerPostSeasonBattingStats[12].length() > 0) {
				if (playerPostSeasonBattingStats[12].contains(".0")) {
					this.rbi = Integer.parseInt(playerPostSeasonBattingStats[11].substring(0, playerPostSeasonBattingStats[12].length() -2 ));
				} else {
					this.rbi = Integer.parseInt(playerPostSeasonBattingStats[12]);
				}
			}
			
			if (playerPostSeasonBattingStats[13].length() > 0) {
				if (playerPostSeasonBattingStats[13].contains(".0")) {
					this.sb = Integer.parseInt(playerPostSeasonBattingStats[13].substring(0, playerPostSeasonBattingStats[13].length() -2 ));
				} else {
					this.sb = Integer.parseInt(playerPostSeasonBattingStats[13]);
				}
			}
			
			if (playerPostSeasonBattingStats[14].length() > 0 ) {
				if (playerPostSeasonBattingStats[14].contains(".0")) {
					this.cs = Integer.parseInt(playerPostSeasonBattingStats[14].substring(0, playerPostSeasonBattingStats[14].length() -2 ));
				} else {
					this.cs = Integer.parseInt(playerPostSeasonBattingStats[14]);
				}
			}
			
			if (playerPostSeasonBattingStats[15].length() > 0) {
				if (playerPostSeasonBattingStats[15].contains(".0")) {
					this.bb = Integer.parseInt(playerPostSeasonBattingStats[11].substring(0, playerPostSeasonBattingStats[15].length() -2 ));
				} else {
					this.bb = Integer.parseInt(playerPostSeasonBattingStats[15]);
				}
			}
			
			if (playerPostSeasonBattingStats[16].length() > 0) {
				if (playerPostSeasonBattingStats[16].contains(".0")) {
					this.so = Integer.parseInt(playerPostSeasonBattingStats[16].substring(0, playerPostSeasonBattingStats[16].length() -2 ));
				} else {
					this.so = Integer.parseInt(playerPostSeasonBattingStats[16]);
				}
				this.so = Integer.parseInt(playerPostSeasonBattingStats[16]);
			}
			
			if (playerPostSeasonBattingStats[17].length() > 0) {
				if (playerPostSeasonBattingStats[17].contains(".0")) {
					this.ibb = Integer.parseInt(playerPostSeasonBattingStats[17].substring(0, playerPostSeasonBattingStats[17].length() -2 ));
				} else {
					this.ibb = Integer.parseInt(playerPostSeasonBattingStats[17]);
				}
			}
			
			if (playerPostSeasonBattingStats[18].length() > 0) {
				if (playerPostSeasonBattingStats[18].contains(".0")) {
					this.hbp = Integer.parseInt(playerPostSeasonBattingStats[18].substring(0, playerPostSeasonBattingStats[18].length() -2 ));
				} else {
					this.hbp = Integer.parseInt(playerPostSeasonBattingStats[18]);
				}
			}
			
			if (playerPostSeasonBattingStats[19].length() > 0) {
				if (playerPostSeasonBattingStats[19].contains(".0")) {
					this.sh = Integer.parseInt(playerPostSeasonBattingStats[19].substring(0, playerPostSeasonBattingStats[19].length() -2 ));
				} else {
					this.sh = Integer.parseInt(playerPostSeasonBattingStats[19]);
				}
			}
			
			if (playerPostSeasonBattingStats[20].length() > 0) {
				if (playerPostSeasonBattingStats[20].contains(".0")) {
					this.sf = Integer.parseInt(playerPostSeasonBattingStats[20].substring(0, playerPostSeasonBattingStats[20].length() -2 ));
				} else {
					this.sf = Integer.parseInt(playerPostSeasonBattingStats[20]);
				}
			}
			
			if (playerPostSeasonBattingStats[21].length() > 0) {
				if (playerPostSeasonBattingStats[21].contains(".0")) {
					this.gidp = Integer.parseInt(playerPostSeasonBattingStats[21].substring(0, playerPostSeasonBattingStats[21].length() -2 ));
				} else {
					this.gidp = Integer.parseInt(playerPostSeasonBattingStats[21]);
				}
			}
			
			
			
		}
		public String getPlayerid() {
			return playerid;
		}
		public void setPlayerid(String playerid) {
			this.playerid = playerid;
		}
		public String getRound() {
			return round;
		}
		public void setRound(String round) {
			this.round = round;
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
			return "PlayerPostSeasonBattingStats [playerid=" + playerid + ", yearid=" + yearid + ", round=" + round
					+ ", ab=" + ab + ", bb=" + bb + ", cs=" + cs + ", doubles=" + doubles + ", games=" + games
					+ ", gidp=" + gidp + ", hbp=" + hbp + ", hits=" + hits + ", hr=" + hr + ", ba=" + ba + ", ibb="
					+ ibb + ", lgid=" + lgid + ", rbi=" + rbi + ", runs=" + runs + ", sb=" + sb + ", sf=" + sf + ", sh="
					+ sh + ", so=" + so + ", stint=" + stint + ", teamid=" + teamid + ", leagueid=" + leagueid
					+ ", triples=" + triples + ", obp=" + obp + ", slg=" + slg + ", ops=" + ops + ", ops_plus="
					+ ops_plus + "]";
		}
}
