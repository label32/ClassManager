package com.licenta.classmanager.services;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;

import com.licenta.classmanager.activities.ClassAddEditActivity;
import com.licenta.classmanager.holders.Announcement;
import com.licenta.classmanager.holders.Date;
import com.licenta.classmanager.holders.Day;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Task;
import com.licenta.classmanager.holders.TaskType;
import com.licenta.classmanager.holders.Time;
import com.licenta.classmanager.holders.User;

public class JsonParser {

	private DataService dataService;
	private Context context;
	private ArrayList<Day> days;

	public JsonParser(Context context) {
		this.context = context;
		dataService = new DataService(context);
	}
	
	public Announcement getAnnouncementFromJSON(JSONObject json_announcement_data) {
		
		JSONObject json_announcement = json_announcement_data.optJSONObject("announcement");
		JSONObject json_lesson = json_announcement_data.optJSONObject("announcement_class");
		JSONArray json_class_days = json_announcement_data.optJSONArray("announcement_class_days");
		
		Announcement a = new Announcement(json_announcement.optString("Title"));
		a.setClass_id(json_announcement.optInt("classid"));
		a.setText(json_announcement.optString("Text"));
		a.setId(json_announcement.optInt("Id"));
		a.setDate(Date.createFromString(json_announcement.optString("Date"), "yyyy-MM-dd HH:mm:ss"));
		
		Lesson l = getLessonFromJSON(json_lesson);
		ArrayList<Day> days = getDaysFromJSON(json_class_days);
		
		l.setDays(days);
		a.setLesson(l);
		
		return a;		
	}

	public Task getTaskFromJSON(JSONObject json_task_data) {
		
		JSONObject json_task = json_task_data.optJSONObject("task");
		JSONObject json_lesson = json_task_data.optJSONObject("task_class");
		JSONArray json_class_days = json_task_data.optJSONArray("task_class_days");
		
		Task t = new Task(json_task.optString("Title"));
		t.setId(json_task.optInt("Id"));
		t.setDetails(json_task.optString("Details"));
		t.setDeadline(Date.createFromString(json_task.optString("Deadline"), "yyyy-MM-dd"));
		
		if (json_task.optInt("Done") == 1) {
			t.setDone(true);
		} else {
			t.setDone(false);
		}
		
		int type_int = json_task.optInt("Type");
		TaskType type = TaskType.Assignment;
		switch (type_int) {
		case 1:
			type = TaskType.Assignment;
			break;
		case 2:
			type = TaskType.Project;
			break;
		case 3:
			type = TaskType.Exam;
			break;
		}
		t.setType(type);
		
		Lesson l = getLessonFromJSON(json_lesson);
		ArrayList<Day> days = getDaysFromJSON(json_class_days);
		
		l.setDays(days);
		t.setLesson(l);
		
		return t;
	}

	public ArrayList<Day> getDaysFromJSON(JSONArray json_days) {
		ArrayList<Day> days = new ArrayList<Day>();
		Day e_day = null;
		if (json_days.length() == 0)
			return days;
		for (int i = 0; i < json_days.length(); i++) {
			int day = json_days.optInt(i);
			switch (day) {
			case 1:
				e_day = Day.Monday;
				break;
			case 2:
				e_day = Day.Tuesday;
				break;
			case 3:
				e_day = Day.Wednesday;
				break;
			case 4:
				e_day = Day.Thursday;
				break;
			case 5:
				e_day = Day.Friday;
				break;
			case 6:
				e_day = Day.Saturday;
				break;
			case 7:
				e_day = Day.Sunday;
				break;
			}
			days.add(e_day);
		}
		return days;
	}

	public Lesson getLessonFromJSON(final JSONObject json_lesson) {
		
		Lesson l = new Lesson(json_lesson.optString("Name"));
		l.setId(json_lesson.optInt("Id"));
		l.setClassroom(json_lesson.optString("Classroom"));
		l.setDetails(json_lesson.optString("Details"));
		l.setStart_time(Time.createFromString(json_lesson.optString("StartTime")));
		l.setEnd_time(Time.createFromString(json_lesson.optString("EndTime")));
		
		int offline = json_lesson.optInt("Offline");
		if(offline==0)
			l.setOffline(false);
		else
			l.setOffline(true);
		
		int color_int = (json_lesson.optInt("Color"));
		if(l.isOffline())
			l.setColor(color_int);
		else {
			int true_color = 0xff000000 + Integer.parseInt(Integer.toHexString(color_int), 16);
			l.setColor(true_color);
		}
		
		return l;
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

}
