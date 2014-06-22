package com.licenta.classmanager.holders;

import java.io.Serializable;

public class Time implements Serializable {

	private static final long serialVersionUID = -1399417580929176278L;
	
	private int hour;
	private int minute;
	
	public Time() {
		
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + hour;
		result = prime * result + minute;
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
		Time other = (Time) obj;
		if (hour != other.hour)
			return false;
		if (minute != other.minute)
			return false;
		return true;
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
		String h = Integer.toString(hour);
		String m = Integer.toString(minute);
		if(minute<10)
			m = "0"+minute;
		if(hour<10)
			h = "0" + hour;
		return ""+h+":"+m;
	}

}
