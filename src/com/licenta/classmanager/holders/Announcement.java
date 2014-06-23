package com.licenta.classmanager.holders;

import java.io.Serializable;
import java.util.Calendar;

public class Announcement implements Serializable, Comparable<Announcement> {

	private static final long serialVersionUID = 6048951092151509898L;
	
	private int id;
	private String title;
	private String text;
	private int class_id;
	private int user_id;
	private Date date;
	
	public Announcement() {
		
	}
	
	public Announcement(int id, String title, String text, int class_id, int user_id, Date date) {
		this.id = id;
		this.title = title;
		this.text = text;
		this.class_id = class_id;
		this.user_id = user_id;
		this.date = date;
	}
	
	public int getClass_id() {
		return class_id;
	}
	
	public Date getDate() {
		return date;
	}
	
	public int getId() {
		return id;
	}
	
	public String getText() {
		return text;
	}
	
	public String getTitle() {
		return title;
	}
	
	public int getUser_id() {
		return user_id;
	}
	
	public void setClass_id(int class_id) {
		this.class_id = class_id;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	@Override
	public int compareTo(Announcement another) {
		boolean year_equal = false;
		boolean month_equal = false;
		if(this.date.getYear() == another.getDate().getYear()) {
			year_equal = true;
		} else {
			if(this.date.getYear() > another.getDate().getYear())
				return 1;
			else
				return -1;
		}
		if(year_equal && this.date.getMonth() == another.getDate().getMonth()) {
			month_equal = true;
		} else {
			if(this.date.getMonth() > another.getDate().getMonth())
				return 1;
			else
				return -1;
		}
		if(year_equal && month_equal && this.date.getDay() == another.getDate().getDay())
			return 0;
		else {
			if(this.date.getDay() > another.getDate().getDay())
				return 1;
			else
				return -1;
		}
	}

}
