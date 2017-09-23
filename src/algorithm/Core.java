package algorithm;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
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
			System.out.println("第" + i + "轮结束！");
		}
		
		return pq;
	}
	
	private void initiateCandidate(PriorityQueue<Candidate> candidates){
		
		List<String> list = Arrays.asList("465299066", "3010552459", "27112844");
		List<Candidate> listCandidate = new ArrayList<>();
		
		for(String uid : list){
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
					if(!isValiditeOfTweet(contentTemp))	continue;
					if(timeTemp < time){
						content = contentTemp;
						time = timeTemp;							
						followeeCount = new JsonHelper(PathConfig.getUserPath(uid)).getIntProperty("friendsCount");							
						curMinTime = Math.min(curMinTime, time);
					}
				}
			}
			if(content == null) continue;
			listCandidate.add(new Candidate(uid, followeeCount, time, curMinTime));
		}
		for(Candidate candidate : listCandidate){
			candidate.setLastTime(curMinTime);
			if(candidates.size() < AlgorithmConfig.size){
				candidates.add(candidate);
			}
			else {
				Candidate peek = candidates.peek();
				if(peek.score < candidate.score) {
					candidates.remove();
					candidates.add(candidate);
				}
			}
		}
	}
	
	private void updateCandidate(PriorityQueue<Candidate> candidates){
		int count = 0;
		List<Candidate> list = new ArrayList<>(candidates);
		for(Candidate candidate : list){
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
						if(!isValiditeOfTweet(contentTemp))	continue;
						if(timeTemp < time){
							content = contentTemp;
							time = timeTemp;							
							followeeCount = new JsonHelper(PathConfig.getUserPath(uid)).getIntProperty("friendsCount");							
						}
					}
				}
				if(content == null) continue;
				Candidate newCandidate = new Candidate(uid, followeeCount, time, curMinTime);
				if(candidates.size() < AlgorithmConfig.size){
					candidates.add(newCandidate);
				}
				else {
					Candidate peek = candidates.peek();
					if(peek.score < newCandidate.score) {
						candidates.remove();
						candidates.add(newCandidate);
					}
				}
				if(count == 358)
					System.out.println("已检测" + count + "个用户！");
				System.out.println("已检测" + count++ + "个用户！");
			}
		}
		
	}
	
	private boolean isValiditeOfTweet(String text){
		return text.indexOf(PathConfig.topic) >= 0 ? true : false;		
	}
}
