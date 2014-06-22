package com.licenta.classmanager.holders;

import java.io.Serializable;
import java.util.Random;

import com.licenta.classmanager.utils.Utils;

public class Note implements Serializable, Comparable<Note> {

	private static final long serialVersionUID = -7692749894835827372L;
	
	private int id;
	private String title, text;
	private Lesson lesson;
	private Date date;
	private String local_id;
	private Random r;

	public Note(String title) {
		this.title = title;
		r = new Random();
		int rand = r.nextInt(Integer.MAX_VALUE);
		int hash = Utils.hash(title);
		local_id = "" + rand + hash;
	}
	
	public Note(int id, String title, String text, Lesson lesson, Date date) {
		this.id = id;
		this.title = title;
		this.text = text;
		this.lesson = lesson;
		this.date = date;
		r = new Random();
		int rand = r.nextInt(Integer.MAX_VALUE);
		int hash = Utils.hash(title);
		local_id = "" + rand + hash;
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
	
	public void setLocal_id(String local_id) {
		this.local_id = local_id;
	}
	
	public String getLocal_id() {
		return local_id;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public String getText() {
		return text;
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
		result = prime * result + id;
		result = prime * result + ((lesson == null) ? 0 : lesson.hashCode());
		result = prime * result + ((local_id == null) ? 0 : local_id.hashCode());
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
		if (id != other.id)
			return false;
		if (lesson == null) {
			if (other.lesson != null)
				return false;
		} else if (!lesson.equals(other.lesson))
			return false;
		if (local_id == null) {
			if (other.local_id != null)
				return false;
		} else if (!local_id.equals(other.local_id))
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
