package com.licenta.classmanager.holders;

import java.io.Serializable;
import java.util.Random;

import com.licenta.classmanager.utils.Utils;

public class Task implements Serializable, Comparable<Task> {

	private static final long serialVersionUID = 9109316795649995185L;

	private int id;
	private Lesson lesson;
	private TaskType type;
	private Date deadline;
	private String title;
	private String details;
	private boolean done;
	private String local_id;
	private Random r;

	// Reminder...

	public Task(String title) {
		this.title = title;
		r = new Random();
		int rand = r.nextInt(Integer.MAX_VALUE);
		int hash = Utils.hash(title);
		local_id = "" + rand + hash;
	}

	public Task(int id, Lesson lesson, TaskType type, Date deadline, String title, String details, boolean done) {
		this.id = id;
		this.lesson = lesson;
		this.type = type;
		this.deadline = deadline;
		this.title = title;
		this.details = details;
		this.done = done;
		r = new Random();
		int rand = r.nextInt(Integer.MAX_VALUE);
		int hash = Utils.hash(title);
		local_id = "" + rand + hash;
	}
	
	public int compareTo(Task task) {
		return task.getLesson().getName().compareTo(this.lesson.getName());
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Lesson getLesson() {
		return lesson;
	}

	public void setLesson(Lesson lesson) {
		this.lesson = lesson;
	}
	
	public String getLocal_id() {
		return local_id;
	}
	
	public void setLocal_id(String local_id) {
		this.local_id = local_id;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public TaskType getType() {
		return type;
	}
	
	public void setType(TaskType type) {
		this.type = type;
	}
	
	public void setDone(boolean done) {
		this.done = done;
	}
	
	public boolean isDone() {
		return done;
	}

}
