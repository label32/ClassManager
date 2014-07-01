package com.licenta.classmanager.services;

import com.licenta.classmanager.holders.Task;


public abstract class TaskCallback {
	
	public abstract void onGetTaskFinish(Task t, boolean hasLesson);

}
