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

	private int id;
	private String local_id;
	private Random r;

	public Lesson(String name) {
		this.name = name;
		r = new Random();
		int rand = r.nextInt(Integer.MAX_VALUE);
		int hash = Utils.hash(name);
		local_id = "" + rand + hash;
	}

	public Lesson(int id, String name, String details, String classroom, ArrayList<Day> days, Time start_time,
			Time end_time, int color) {
		this.id = id;
		this.name = name;
		this.details = details;
		this.classroom = classroom;
		this.days = days;
		this.start_time = start_time;
		this.end_time = end_time;
		this.color = color;
		r = new Random();
		int rand = r.nextInt(Integer.MAX_VALUE);
		int hash = Utils.hash(name);
		local_id = "" + rand + hash;
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

	public String getLocal_id() {
		return local_id;
	}

	public void setLocal_id(String local_id) {
		this.local_id = local_id;
	}

}
