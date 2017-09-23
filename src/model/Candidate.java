package model;

import configration.AlgorithmConfig;

public class Candidate {

	public String id;
	public double score;
	
	public Candidate(String id, int followeeCount, long time, long lastTime) {
		this.id = id;
		this.setScore(followeeCount, time, lastTime);
	}
	
	public void setScore(int followeeCount, long time, long lastTime) {
		this.score = AlgorithmConfig.score_m * followeeCount + AlgorithmConfig.score_n * (lastTime - time);
	}
	
	
}
