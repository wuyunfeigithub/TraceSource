package dataProcess;

import java.io.File;
import java.io.IOException;

import configration.PathConfig;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import util.FileUtil;
import util.JsonHelper;

public class combineTweets {

	public static void main(String[] args) {
		String filesPath = PathConfig.statuses;
		try {
			File files = new File(filesPath);
			if(files.isDirectory()) {
				int count = 0;
				File[] tweets = files.listFiles();
				for(File file : tweets){
					combine(file.getAbsolutePath());
					System.out.println("finished " + (++count) + "users");
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void combine(String filesPath) {		
		JSONArray jsonArray = new JSONArray();
		try {
			File files = new File(filesPath);
			if(files.isDirectory()) {
				File[] tweets = files.listFiles();
				for(File file : tweets){
					JSONObject json = new JsonHelper(file.getAbsolutePath()).getJSONObject();
					jsonArray.add(json);
				}
			}
			FileUtil.storeJSON(jsonArray.toString(), filesPath + "//" + "combine.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
