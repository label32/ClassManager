package com.licenta.classmanager.services;


public class LinkBuilder {
	private String baseURL = "http://192.168.2.201/android";

	public String getUser(String userId) {
		return baseURL + "/get_user/" + userId;
	}

}

