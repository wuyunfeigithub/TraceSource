package dataProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import configration.PathConfig;

public class DateGetter {
	
	public static Map<String, Set<String>> relations = new HashMap<>();
	
	static {
		System.out.println("开始加载好友关系...");
		String networkPath = PathConfig.network;
		BufferedReader reader = null;
		int count = 0;
		try {
			reader = new BufferedReader(new FileReader(new File(networkPath)));
			String line = null;
			while( (line = reader.readLine()) != null ){
				if(count++ % 100 == 0) System.out.println("已加载" + count + "条！");
				String[] data = line.split(",");
				if(relations.containsKey(data[1])){
					relations.get(data[1]).add(data[0]);
				}
				else {
					relations.put(data[1], new HashSet<String>(Arrays.asList(data[0])));
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			System.out.println("加载好友关系结束！");
		}
		
	}
	
	public static Set<String> getRelations(String uid){
		
		Iterator<Entry<String, Set<String>>> it = relations.entrySet().iterator();
//		int max = 0;
//		while(it.hasNext()){
//			max = Math.max(max, it.next().getValue().size());
//		}
//		System.out.println(max);
		return relations.get(uid);
	}
	
	
}
