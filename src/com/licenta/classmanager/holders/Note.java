package com.licenta.classmanager.holders;

import java.io.Serializable;

public class Note implements Serializable, Comparable<Note> {

	private static final long serialVersionUID = -7692749894835827372L;
	
	private int id;
	private String title, text;
	private Lesson lesson;
	private Date date;
	private Flag flag;

	public Note(String title) {
		this.title = title;
		id = this.hashCode();
	}
	
	public Note(String title, String text, Lesson lesson, Date date) {
		this.title = title;
		this.text = text;
		this.lesson = lesson;
		this.date = date;
		id = this.hashCode();
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}
	
	public Lesson getLesson() {
		return lesson;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
	}
	
	public Flag getFlag() {
		return flag;
	}
	
	public void setFlag(Flag flag) {
		this.flag = flag;
	}
	
	@Override
	public int compareTo(Note note) {
		return note.getLesson().getName().compareTo(this.lesson.getName());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((lesson == null) ? 0 : lesson.hashCode());
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
		Note other = (Note) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (lesson == null) {
			if (other.lesson != null)
				return false;
		} else if (!lesson.equals(other.lesson))
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
	
	

}
