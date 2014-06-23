package com.licenta.classmanager.services;

import org.json.JSONObject;

import android.content.Context;

public abstract class BaseCallback {
	
	Context context;

	public void setContext(Context context) {
		this.context = context;
	}

	public abstract void finish(String url, JSONObject resutl, String msg);

	public Context getContext() {
		return context;
	}
}
