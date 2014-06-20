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

	public void getUser(String userId, BaseCallback callback) {
		net_worker.executeRequest(linkBuilder.getUser(userId), callback);
	}
}
