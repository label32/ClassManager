package com.licenta.classmanager.services;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.licenta.classmanager.holders.Day;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Time;
import com.licenta.classmanager.holders.User;

public class JsonParser {
	
	private DataService dataService;
	private Context context;
	private ArrayList<Day> days;
	public static boolean not_ready;
	
	public JsonParser(Context context) {
		this.context = context;
	}
	
	public ArrayList<Day> getDaysFromJSON(JSONObject result) {
		ArrayList<Day> days = new ArrayList<Day>();
		Day e_day = null;
		JSONArray json_days = result.optJSONArray("days");
		if (json_days.length() == 0)
			return days;
		for (int i = 0; i < json_days.length(); i++) {
			int day = json_days.optInt(i);
			switch(day) {
			case 1: e_day = Day.Monday; break;
			case 2: e_day = Day.Tuesday; break;
			case 3: e_day = Day.Wednesday; break;
			case 4: e_day = Day.Thursday; break;
			case 5: e_day = Day.Friday; break;
			case 6: e_day = Day.Saturday; break;
			case 7: e_day = Day.Sunday; break;
			}
			days.add(e_day);
		}
		return days;
	}
	
	public User getUserFromJSON(JSONObject json_user) {
		User u = new User();
		u.setId(json_user.optInt("Id"));
		u.setType(json_user.optInt("Type"));
		u.setEmail(json_user.optString("Email"));
		u.setPassword(json_user.optString("Password"));
		u.setName(json_user.optString("Name"));
		return u;
	}
	
	public void getLessonFromJSON(JSONObject json_lesson, final DaysCallback callback) {
		final Lesson l = new Lesson(json_lesson.optString("Name"));
		l.setId(json_lesson.optInt("Id"));
		l.setClassroom(json_lesson.optString("Classroom"));
		l.setColor(json_lesson.optInt("Color"));
		l.setDetails(json_lesson.optString("Details"));
		l.setStart_time(Time.createFromString(json_lesson.optString("StartTime")));
		l.setEnd_time(Time.createFromString(json_lesson.optString("EndTime")));
		
		dataService = new DataService(context);
		not_ready = true;

		dataService.getClassDays(l.getId(), new ServiceCallback(context) {
			
			@Override
			public void onSuccess(JSONObject result) {
				callback.onDaysFinish(result, l);
			}
			
			@Override
			public void onOffline() {
				
			}
		});

	}
}
