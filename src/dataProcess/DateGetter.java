package dataProcess;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import configration.PathConfig;

public class DateGetter {
	
	public static Map<String, Set<String>> relations = new HashMap<>();
	
	static {
		String networkPath = PathConfig.network;
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(new File(networkPath)));
			String line = null;
			while( (line = reader.readLine()) != null ){
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
		}
		
	}
	
	public static Set<String> getRelations(String uid){
		return relations.get(uid);
	}
	
	
}
