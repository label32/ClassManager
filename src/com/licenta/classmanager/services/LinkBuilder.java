package com.licenta.classmanager.services;


public class LinkBuilder {
	private String baseURL = "http://192.168.2.201/index.php?c=android_controller";

	public String login(String email, String password) {
		return baseURL + "&m=login&email="+email+"&pass="+password;
	}
	
	public String getUser(String userid) {
		return baseURL + "&m=get_user&id="+userid;
	}
	
	public String getUserClasses(String userid) {
		return baseURL + "&m=get_userClasses&userid="+userid;
	}
	
	public String getClassDays(String classid) {
		return baseURL + "&m=get_classDays&classid="+classid;
	}

}
