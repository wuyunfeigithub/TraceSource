package util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonHelper {

	private JSONObject jsonObject;
	private String path;

	public JsonHelper(String path) {

		this.path = path;
		
		String jsonRaw = "";
		BufferedReader reader = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");// UTF-8
			reader = new BufferedReader(inputStreamReader);

			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				jsonRaw += tempString;
			}

			jsonObject = JSONObject.fromObject(jsonRaw);
			if (fileInputStream != null)
				fileInputStream.close();
			if (inputStreamReader != null)
				inputStreamReader.close();
			if (reader != null)
				reader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	public JSONObject getJSONObject() {
		return jsonObject;
	}
	
	public int getIntProperty(String propertyName) {
		return jsonObject.getInt(propertyName);
	}

	public boolean getBooleanProperty(String propertyName) {
		return jsonObject.getBoolean(propertyName);
	}

	public String getStringProperty(String propertyName) {
		return jsonObject.getString(propertyName);
	}
	
	public long getLongProperty(String propertyName) {
		return jsonObject.getLong(propertyName);
	}

	public ArrayList<String> getStringArrayProperty(String propertyName) {
		ArrayList<String> list = new ArrayList<>();
		JSONArray jsonArray = this.jsonObject.getJSONArray(propertyName);
		for (int i = 0; i < jsonArray.size(); i++) {
			list.add(jsonArray.getString(i));
		}
		return list;
	}

	public void setIntProperty(String propertyName, int value) {
		jsonObject.put(propertyName, value);
	}

	public void setBooleanProperty(String propertyName, boolean value) {
		jsonObject.put(propertyName, value);
	}

	public void setStringProperty(String propertyName, String value) {
		jsonObject.put(propertyName, value);
	}

	public void setStringArrayProperty(String propertyName, String[] value) {
		jsonObject.put(propertyName, value);
	}
	
	public void setStringArrayProperty(String propertyName, ArrayList<String> value) {
		String[] keys = new String[value.size()];
		for (int i = 0; i < keys.length; i++) {
			keys[i] = value.get(i);
		}			
		setStringArrayProperty(propertyName, keys);
	}
	
	public void execute(){
		try {
			FileUtil.storeJSON(jsonObject.toString(), path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static JSONArray readJSONArrayFromFile(String path) {
		String jsonRaw = "";
		BufferedReader reader = null;
		JSONArray json = null;
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");// UTF-8
			reader = new BufferedReader(inputStreamReader);

			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				jsonRaw += tempString;
			}

			json = JSONArray.fromObject(jsonRaw);
			if (fileInputStream != null)
				fileInputStream.close();
			if (inputStreamReader != null)
				inputStreamReader.close();
			if (reader != null)
				reader.close();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return json;
	}
}
