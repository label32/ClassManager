package com.licenta.classmanager.services;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Task;

public class LinkBuilder {
	private String baseURL = "http://192.168.2.203/index.php?c=android_controller";

	/* User operations */

	public String login(String email, String password) {
		return baseURL + "&m=login&email=" + email + "&pass=" + password;
	}
	
	public String register(String email, String password, String name, int type) {
		String result = baseURL;
		try {
			result += "&m=register&type="+type;
			result += "&email=" + URLEncoder.encode(email, "utf-8");
			result += "&pass=" + URLEncoder.encode(password, "utf-8" );
			result += "&name=" + URLEncoder.encode(name, "utf-8");
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getUser(int userid) {
		return baseURL + "&m=get_user&id=" + userid;
	}

	public String getAnnouncements(int userid) {
		return baseURL + "&m=get_announcements&userid=" + userid;
	}
	
	public String addAnnouncement(String title, String text, int classid) {
		String result = baseURL + "&m=add_announcement";
		try {
			result += "&classid="+classid;
			result += "&title=" + URLEncoder.encode(title, "utf-8");
			result += "&text=" + URLEncoder.encode(text, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String getUserClasses(int userid) {
		return baseURL + "&m=get_userClasses&userid=" + userid;
	}

	/* Class operations */

	public String getClassDays(int classid) {
		return baseURL + "&m=get_classDays&classid=" + classid;
	}

	public String getLesson(int classid) {
		return baseURL + "&m=get_class&classid=" + classid;
	}

	public String addClass(int userid, Lesson l) {
		String result = baseURL + "&m=add_offlineClass";
		try {
			result += "&userid=" + userid;
			result += "&name=" + URLEncoder.encode(l.getName(), "utf-8");
			result += "&details=" + URLEncoder.encode(l.getDetails(), "utf-8");
			result += "&classroom=" + URLEncoder.encode(l.getClassroom(), "utf-8");
			result += "&color=" + l.getColor();
			result += "&start_time=" + URLEncoder.encode(l.getStart_time().toString(), "utf-8");
			result += "&end_time=" + URLEncoder.encode(l.getEnd_time().toString(), "utf-8");

			String days_str = "";
			int days_int;
			for (int i = 0; i < l.getDays().size(); i++) {
				days_str += l.getDays().get(i).getCode();
			}
			if (l.getDays().size() == 0) {
				days_int = -1;
			} else {
				days_int = Integer.parseInt(days_str);
			}
			result += "&days=" + days_int;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String updateClass(Lesson l) {
		String result = baseURL + "&m=update_offlineClass";
		try {
			result += "&classid=" + l.getId();
			result += "&name=" + URLEncoder.encode(l.getName(), "utf-8");
			result += "&details=" + URLEncoder.encode(l.getDetails(), "utf-8");
			result += "&classroom=" + URLEncoder.encode(l.getClassroom(), "utf-8");
			result += "&color=" + l.getColor();
			result += "&start_time=" + URLEncoder.encode(l.getStart_time().toString(), "utf-8");
			result += "&end_time=" + URLEncoder.encode(l.getEnd_time().toString(), "utf-8");

			String days_str = "";
			int days_int;
			for (int i = 0; i < l.getDays().size(); i++) {
				days_str += l.getDays().get(i).getCode();
			}
			if (l.getDays().size() == 0) {
				days_int = -1;
			} else {
				days_int = Integer.parseInt(days_str);
			}
			result += "&days=" + days_int;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String deleteClass(int classid) {
		return baseURL + "&m=delete_class&classid=" + classid;
	}

	/* Task operations */

	public String getTasks(int userid) {
		return baseURL + "&m=get_tasks&userid=" + userid;
	}

	public String addTask(int userid, Task t) {
		String result = baseURL + "&m=add_task";
		try {
			result += "&userid=" + userid;
			result += "&classid=" + t.getLesson().getId();
			result += "&type=" + t.getType().getCode();
			result += "&title=" + URLEncoder.encode(t.getTitle(), "utf-8");
			result += "&details=" + URLEncoder.encode(t.getDetails(), "utf-8");
			result += "&deadline=" + t.getDeadline().toStringURL();

			int done = 0;
			if (t.isDone())
				done = 1;
			result += "&done=" + done;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String updateTask(Task t) {
		String result = baseURL + "&m=update_task";
		try {
			result += "&classid=" + t.getLesson().getId();
			result += "&type=" + t.getType().getCode();
			result += "&title=" + URLEncoder.encode(t.getTitle(), "utf-8");
			result += "&details=" + URLEncoder.encode(t.getDetails(), "utf-8");
			result += "&taskid=" + t.getId();
			result += "&deadline=" + t.getDeadline().toStringURL();

			int done = 0;
			if (t.isDone())
				done = 1;
			result += "&done=" + done;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public String deleteTask(int taskid) {
		return baseURL + "&m=delete_task&taskid=" + taskid;
	}

}
