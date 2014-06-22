package com.licenta.classmanager.dao;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.licenta.classmanager.holders.Task;

public class TasksDao extends Dao {

	private File tasks_dir;
	public final String TASKS_KEY = "tasks";
	
	public TasksDao(Context context) {
		super(context);
		tasks_dir = new File(context.getFilesDir().getAbsolutePath() + File.separator + "tasks");
		tasks_dir.mkdir();
	}
	
	public void putTask(Task task) {
		putData(new File(tasks_dir, task.getLocal_id()), task);
	}
	
	public Task getTask(File key) {
		return (Task) getData(new File(key.getAbsolutePath()));
	}

	public void deleteTask(Task task) {
		if (!deleteData(new File(tasks_dir, task.getLocal_id()))) {

			Log.e("TASK_DELETE", "Task not deleted!");
			Log.e("TASK_DELETE", getReason(new File(tasks_dir, task.getLocal_id())));
		}
	}
	
	public void putTasks(ArrayList<Task> tasks) {
		for (int i = 0; i < tasks.size(); i++) {
			putTask(tasks.get(i));
		}
	}

	public ArrayList<Task> getTasks() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		for (File f : tasks_dir.listFiles()) {
			if (f == null) {
				return tasks;
			}
			tasks.add(getTask(f));
		}
		return tasks;
	}
}
