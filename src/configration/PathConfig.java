package configration;

public class PathConfig {
	
	public static final String seperator = "//";
	public static final String home = "F:";
	public static final String topic = "AddAWomanImproveAQuote";
	public static final String topicPath = home + seperator + "#" + topic;
	public static final String usersInfo = topicPath + seperator + "usersInfo";
	public static final String statuses = topicPath + seperator + "statuses";
	public static final String network = topicPath + seperator + "network" + seperator + "network.txt";
	public static final String subNetwork = topicPath + seperator + "network" + seperator + "sub_network.txt";
	
	public static String getTweetPath(String uid){
		return statuses + seperator + uid;
	}
	
	public static String getUserPath(String uid){
		return usersInfo + seperator + uid + ".json";
	}
}
