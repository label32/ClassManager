package com.licenta.classmanager.holders;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = -7800135208350955815L;
	public final static String USER_ID = "com.stagiu.agenda.activities.USER_ID";
	public final static String IS_STUDENT = "com.stagiu.agenda.activities.IS_STUDENT";
	
	private int id;
	private int type;
	private String name;
	private String email;
	private String password;
	
	public User() {
		
	}
	
	public User(int id, int type, String name, String email, String password) {
		this.id = id;
		this.type = type;
		this.name = name;
		this.email = email;
		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getPassword() {
		return password;
	}
	public int getType() {
		return type;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	
	

}