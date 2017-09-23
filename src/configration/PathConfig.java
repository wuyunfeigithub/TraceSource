package configration;

public class PathConfig {
	
	public static final String seperator = "//";
	public static final String home = "G:";
	public static final String topic = "AddAWomanImproveAQuote";
	public static final String topicPath = home + seperator + "#" + topic;
	public static final String usersInfo = topicPath + "usersInfo";
	public static final String statuses = topicPath + "statuses";
	public static final String network = topicPath + "network" + seperator + "network";
	public static final String subNetwork = topicPath + "network" + seperator + "sub_network";
	
	public static String getTweetPath(String uid){
		return statuses + seperator + uid;
	}
	
	public static String getUserPath(String uid){
		return usersInfo + seperator + uid;
	}
}
