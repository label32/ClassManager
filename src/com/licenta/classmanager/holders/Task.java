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
	private Flag flag;

	// Reminder...

	public Task(String title) {
		this.title = title;
		id = this.hashCode();
	}

	public Task(Lesson lesson, TaskType type, Date deadline, String title, String details, boolean done) {
		this.lesson = lesson;
		this.type = type;
		this.deadline = deadline;
		this.title = title;
		this.details = details;
		this.done = done;
		id = this.hashCode();
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
	
	public Flag getFlag() {
		return flag;
	}
	
	public void setFlag(Flag flag) {
		this.flag = flag;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((deadline == null) ? 0 : deadline.hashCode());
		result = prime * result + ((details == null) ? 0 : details.hashCode());
		result = prime * result + (done ? 1231 : 1237);
		result = prime * result + ((lesson == null) ? 0 : lesson.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Task other = (Task) obj;
		if (deadline == null) {
			if (other.deadline != null)
				return false;
		} else if (!deadline.equals(other.deadline))
			return false;
		if (details == null) {
			if (other.details != null)
				return false;
		} else if (!details.equals(other.details))
			return false;
		if (done != other.done)
			return false;
		if (lesson == null) {
			if (other.lesson != null)
				return false;
		} else if (!lesson.equals(other.lesson))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	

}
