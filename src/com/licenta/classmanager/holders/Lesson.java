package com.licenta.classmanager.holders;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

import com.licenta.classmanager.utils.Utils;

public class Lesson implements Serializable, Comparable<Lesson> {

	private static final long serialVersionUID = -3847846767934849303L;

	private String name;
	private String details;
	private String classroom;
	private ArrayList<Day> days;
	private Time start_time;
	private Time end_time;
	private int color;
	private Flag flag;
	private int id;

	public Lesson(String name) {
		this.name = name;
		id = this.hashCode();
	}

	public Lesson(String name, String details, String classroom, ArrayList<Day> days, Time start_time,
			Time end_time, int color) {
		this.name = name;
		this.details = details;
		this.classroom = classroom;
		this.days = days;
		this.start_time = start_time;
		this.end_time = end_time;
		this.color = color;
		id = this.hashCode();
	}
	
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classroom == null) ? 0 : classroom.hashCode());
		result = prime * result + color;
		result = prime * result + ((days == null) ? 0 : days.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + ((end_time == null) ? 0 : end_time.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((start_time == null) ? 0 : start_time.hashCode());
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
		Lesson other = (Lesson) obj;
		if (classroom == null) {
			if (other.classroom != null)
				return false;
		} else if (!classroom.equals(other.classroom))
			return false;
		if (color != other.color)
			return false;
		if (days == null) {
			if (other.days != null)
				return false;
		} else if (!days.equals(other.days))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (end_time == null) {
			if (other.end_time != null)
				return false;
		} else if (!end_time.equals(other.end_time))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (start_time == null) {
			if (other.start_time != null)
				return false;
		} else if (!start_time.equals(other.start_time))
			return false;
		return true;
	}

	public int compareTo(Lesson lesson) {
		return lesson.getName().compareTo(this.name);
	}

	public String getClassroom() {
		return classroom;
	}

	public ArrayList<Day> getDays() {
		return days;
	}

	public String getDetails() {
		return details;
	}

	public Time getEnd_time() {
		return end_time;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Time getStart_time() {
		return start_time;
	}

	public int getColor() {
		return color;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public void setDays(ArrayList<Day> days) {
		this.days = days;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setEnd_time(Time end_time) {
		this.end_time = end_time;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStart_time(Time start_time) {
		this.start_time = start_time;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public Flag getFlag() {
		return flag;
	}
	
	public void setFlag(Flag flag) {
		this.flag = flag;
	}

}
