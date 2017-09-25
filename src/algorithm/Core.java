package algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import configration.AlgorithmConfig;
import configration.PathConfig;
import dataProcess.DateGetter;
import model.Candidate;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.JsonHelper;

public class Core {

	long curMinTime = new Date().getTime();
	Set<String> filter = new HashSet<>();
	
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
			filter.add(uid);
			String tweetPath = PathConfig.getTweetPath(uid);
			JSONArray jsonArray = JsonHelper.readJSONArrayFromFile(tweetPath);				
			String content = null;
			Long time = Long.MAX_VALUE;
			int followeeCount = 0;
			for(int i = 0; i < jsonArray.size(); i++){
				JSONObject jb = jsonArray.getJSONObject(i);
				String contentTemp = jb.getString("text");
				Long timeTemp = jb.getLong("createAt");
				if(!isValiditeOfTweet(contentTemp))	continue;
				if(timeTemp < time){
					content = contentTemp;
					time = timeTemp;							
					followeeCount = new JsonHelper(PathConfig.getUserPath(uid)).getIntProperty("friendsCount");							
					curMinTime = Math.min(curMinTime, time);
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
		List<Candidate> list = new LinkedList<>(candidates);
		for(Candidate candidate : candidates){
			if(candidate.visited == true) continue;
			candidate.visited = true;
			int count = 0;
			Set<String> set = DateGetter.getRelations(candidate.id);			
			for(String uid : set){	
				if(filter.contains(uid)) continue;
				else filter.add(uid);
				try {
					String tweetPath = PathConfig.getTweetPath(uid);					
					JSONArray jsonArray = JsonHelper.readJSONArrayFromFile(tweetPath);						
					String content = null;
					Long time = Long.MAX_VALUE;
					int followeeCount = 0;
					for(int i = 0; i < jsonArray.size(); i++){
						JSONObject jb = jsonArray.getJSONObject(i);
						String contentTemp = jb.getString("text");
						Long timeTemp = jb.getLong("createAt");
						if(!isValiditeOfTweet(contentTemp))	continue;
						if(timeTemp < time){
							content = contentTemp;
							time = timeTemp;							
							followeeCount = new JsonHelper(PathConfig.getUserPath(uid)).getIntProperty("friendsCount");							
							curMinTime = Math.min(curMinTime, time);
						}
					}	
					System.out.println("已检测" + count++ + "/" + set.size() + "个用户！");
					if(content == null) continue;
					list.add(new Candidate(uid, followeeCount, time, curMinTime));					
				}
				catch (Exception e) {
					e.printStackTrace();
				}				
			}
		}
		for(Candidate ele : list){
			if(candidates.size() < AlgorithmConfig.size){
				candidates.add(ele);
			}
			else {
				Candidate peek = candidates.peek();
				if(peek.score < ele.score) {
					candidates.remove();
					candidates.add(ele);
				}
			}
		}
		list.clear();
	}
	
	private boolean isValiditeOfTweet(String text){
		return text.indexOf(PathConfig.topic) >= 0 ? true : false;		
	}
}
