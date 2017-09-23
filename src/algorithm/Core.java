package algorithm;

import java.io.File;
import java.util.Comparator;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Set;

import configration.AlgorithmConfig;
import configration.PathConfig;
import dataProcess.DateGetter;
import model.Candidate;
import util.JsonHelper;

public class Core {

	long curMinTime = new Date().getTime();
	
	public PriorityQueue<Candidate> getTopKCandidate(){
		PriorityQueue<Candidate> pq = new PriorityQueue<>(AlgorithmConfig.size, new Comparator<Candidate>() {
			public int compare(Candidate o1, Candidate o2) {
				return o1.score - o2.score > 0 ? 1 : -1;
			}
		});
		
		initiateCandidate(pq);		
		
		for (int i = 0; i < AlgorithmConfig.iteration; i++) {
			updateCandidate(pq);
		}
		
		return pq;
	}
	
	private void initiateCandidate(PriorityQueue<Candidate> candidates){
	}
	
	private void updateCandidate(PriorityQueue<Candidate> candidates){
		
		for(Candidate candidate : candidates){
			Set<String> set = DateGetter.getRelations(candidate.id);
			
			for(String uid : set){
				String tweetPath = PathConfig.getTweetPath(uid);
				
				File file = new File(tweetPath);
				
				String content = null;
				Long time = Long.MAX_VALUE;
				int followeeCount = 0;
				if(file.isDirectory()){
					File[] name = file.listFiles();					
					for(File ele : name){
						JsonHelper jsonHelper = new JsonHelper(ele.getAbsolutePath());
						String contentTemp = jsonHelper.getStringProperty("text");
						Long timeTemp = jsonHelper.getLongProperty("createAt");
						if(!isValiditeOfTweet(content))	continue;
						if(timeTemp < time){
							content = contentTemp;
							time = timeTemp;							
							followeeCount = new JsonHelper(PathConfig.getUserPath(uid)).getIntProperty("friendsCount");							
						}
					}
				}
				if(content == null) continue;
				Candidate newCandidate = new Candidate(uid, followeeCount, time, curMinTime);
				Candidate peek = candidates.peek();
				if(peek.score < newCandidate.score) {
					candidates.remove();
					candidates.add(newCandidate);
				}
			}
			
		}
		
	}
	
	private boolean isValiditeOfTweet(String text){
		return text.indexOf(PathConfig.topic) >= 0 ? true : false;		
	}
}
