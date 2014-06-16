package com.licenta.classmanager.holders;

public enum TaskType {
	Assignment(1), Project(2), Exam(3); 
	
	private int code;

	private TaskType(int c) {
		code = c;
	}

	public int getCode() {
		return code;
	}
}
