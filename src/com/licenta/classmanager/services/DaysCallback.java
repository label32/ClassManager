package com.licenta.classmanager.services;

import org.json.JSONObject;

import com.licenta.classmanager.holders.Lesson;


public abstract class DaysCallback {
	
	public abstract void onDaysFinish(JSONObject json_days, Lesson l);

}
