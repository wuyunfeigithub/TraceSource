package model;

import configration.AlgorithmConfig;

public class Candidate {

	public String id;
	public double score;
	public int followeeCount;
	public long time;
	public long lastTime;
	
	public Candidate(String id, int followeeCount, long time, long lastTime) {
		this.id = id;
		this.followeeCount = followeeCount;
		this.time = time;
		this.lastTime = lastTime;
		this.setScore(followeeCount, time, lastTime);
	}
	
	public void setScore(int followeeCount, long time, long lastTime) {
		this.score = AlgorithmConfig.score_m * followeeCount + AlgorithmConfig.score_n * (lastTime - time);
	}
	
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
		this.setScore(followeeCount, time, lastTime);
	}
	
}
