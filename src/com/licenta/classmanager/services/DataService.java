package com.licenta.classmanager.services;

import android.content.Context;

public class DataService {
	private LinkBuilder linkBuilder;
	private NetworkWorker net_worker;
	private Context context;
	private int i = 0;

	public DataService(Context context) {
		linkBuilder = new LinkBuilder();
		this.context = context;
		net_worker = new NetworkWorker();
	}
	
	public void getClassDays(String classid, BaseCallback callback) {
		net_worker.executeRequest(linkBuilder.getClassDays(classid), callback);
	}
	
	public void getUserClasses(String userid, BaseCallback callback) {
		net_worker.executeRequest(linkBuilder.getUserClasses(userid), callback);
	}

	public void getUser(String userId, BaseCallback callback) {
		net_worker.executeRequest(linkBuilder.getUser(userId), callback);
	}
	
	public void login(String email, String password, BaseCallback callback) {
		net_worker.executeRequest(linkBuilder.login(email, password), callback);
	}
}
