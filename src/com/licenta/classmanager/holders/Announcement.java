package com.licenta.classmanager.holders;

import java.io.Serializable;
import java.util.Calendar;

public class Announcement implements Serializable, Comparable<Announcement> {

	private static final long serialVersionUID = 6048951092151509898L;
	
	private int id;
	private String title;
	private String text;
	private int class_id;
	private Lesson lesson;
	private Date date;
	private Flag flag;
	
	public Announcement(String title) {
		this.title = title;
		id = hashCode();
	}
	
	public Announcement(String title, String text, int class_id, Date date) {
		this.title = title;
		this.text = text;
		this.class_id = class_id;
		this.date = date;
		id = hashCode();
	}
	
	public int getClass_id() {
		return class_id;
	}
	
	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}
	
	public Lesson getLesson() {
		return lesson;
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
	
	public Flag getFlag() {
		return flag;
	}
	
	public void setFlag(Flag flag) {
		this.flag = flag;
	}	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + class_id;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((text == null) ? 0 : text.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Announcement other = (Announcement) obj;
		if (class_id != other.class_id)
			return false;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (text == null) {
			if (other.text != null)
				return false;
		} else if (!text.equals(other.text))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
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
