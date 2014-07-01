package com.licenta.classmanager.holders;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Date implements Serializable {
	
	private static final long serialVersionUID = 2939523492470054668L;
	
	private int day;
	private int month;
	private int year;

	public Date() {
		
	}
	
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
		String result = "";
		if(day<10) {
			result+="0"+day+".";
		} else {
			result+=day+".";
		}
		if(month<10) {
			result+="0"+month;
		} else {
			result+=month;
		}
		return result+"."+year;
	}
	
	public String toStringURL() {
		String result = "" + year;
		if(month<10) {
			result+="0"+month;
		} else {
			result+=month;
		}
		if(day<10) {
			result+="0"+day;
		} else {
			result+=day;
		}
		return result;
	}
	
	public static Date createFromString(String s, String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat (format);
		try {
			java.util.Date d = sdf.parse(s);
			Calendar cal = Calendar.getInstance();
			cal.setTime(d);
			date.setDay(cal.get(Calendar.DAY_OF_MONTH));
			date.setYear(cal.get(Calendar.YEAR));
			date.setMonth(cal.get(Calendar.MONTH)+1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
}
