package com.licenta.classmanager.holders;

import java.io.Serializable;

public class Class implements Serializable {

	private static final long serialVersionUID = -3847846767934849303L;
	
	private String name;
	private String details;
	private String classroom;
	private Days days;
	private Time start_time;
	private Time end_time;
	private int id;
	private int color;
	
	public Class() {
		
	}
	
	public Class(int id, String name, String details, String classroom, Days days, Time start_time, Time end_time, int color) {
		this.id = id;
		this.name = name;
		this.details = details;
		this.classroom = classroom;
		this.days = days;
		this.start_time = start_time;
		this.end_time = end_time;
		this.color = color;
	}
	
	public String getClassroom() {
		return classroom;
	}
	
	public Days getDays() {
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
	
	public void setDays(Days days) {
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
}
