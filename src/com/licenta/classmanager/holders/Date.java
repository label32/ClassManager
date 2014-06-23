package com.licenta.classmanager.holders;

import java.io.Serializable;

public class Date implements Serializable {
	
	private static final long serialVersionUID = 2939523492470054668L;
	
	private int day;
	private int month;
	private int year;

	public Date(int day, int month, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	public int getDay() {
		return day;
	}
	
	public void setDay(int day) {
		this.day = day;
	}
	
	public int getMonth() {
		return month;
	}
	
	public void setMonth(int month) {
		this.month = month;
	}
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + day;
		result = prime * result + month;
		result = prime * result + year;
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
		Date other = (Date) obj;
		if (day != other.day)
			return false;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	public String toString() {
		return ""+day+"."+month+"."+year;
	}
}
