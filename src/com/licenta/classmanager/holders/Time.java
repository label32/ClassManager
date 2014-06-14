package com.licenta.classmanager.holders;

import java.io.Serializable;

public class Time implements Serializable {

	private static final long serialVersionUID = -1399417580929176278L;
	
	private int hour;
	private int minute;
	
	public Time() {
		
	}
	
	public Time(int hour, int minute) {
		this.hour = hour;
		this.minute = minute;
	}
	
	public void setHour(int hour) {
		this.hour = hour;
	}
	
	public void setMinute(int minute) {
		this.minute = minute;
	}
	
	public int getHour() {
		return hour;
	}
	
	public int getMinute() {
		return minute;
	}
	
	public String toString() {
		return ""+hour+":"+minute;
	}

}
