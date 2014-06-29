package com.licenta.classmanager.dao;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Note;
import com.licenta.classmanager.holders.Task;

public class ClassesDao extends Dao {

	private File classes_dir;
	public final String CLASSES_KEY = "classes";
	
	public ClassesDao(Context context) {
		super(context);
		classes_dir = new File(context.getFilesDir().getAbsolutePath() + File.separator + "classes");
		classes_dir.mkdir();
	}
	
	public void putClass(Lesson lesson) {
		putData(new File(classes_dir, Integer.toString(lesson.getId())), lesson);
	}
	
	public Lesson getClass(File key) {
		return (Lesson) getData(new File(key.getAbsolutePath()));
	}

	public void deleteClass(Lesson lesson) {
		if (!deleteData(new File(classes_dir, Integer.toString(lesson.getId())))) {
			Log.e("CLASS_DELETE", "Class not deleted!");
			Log.e("CLASS_DELETE", getReason(new File(classes_dir, Integer.toString(lesson.getId()))));
		} else {
			
			TasksDao tasksDao = new TasksDao(super.context);
			ArrayList<Task> tasks = tasksDao.getTasks();
			Task task;
			for(int i=0; i<tasks.size(); i++) {
				task = tasks.get(i);
				if(task.getLesson().equals(lesson))
					tasksDao.deleteTask(task);
			}
			
			NotesDao notesDao = new NotesDao(super.context);
			ArrayList<Note> notes = notesDao.getNotes();
			Note note;
			for(int i=0; i<notes.size(); i++) {
				note = notes.get(i);
				if(note.getLesson().equals(lesson))
					notesDao.deleteNote(note);
			}
		}
	}
	
	public void putClasses(ArrayList<Lesson> lessons) {
		for (int i = 0; i < lessons.size(); i++) {
			putClass(lessons.get(i));
		}
	}

	public ArrayList<Lesson> getClasses() {
		ArrayList<Lesson> classes = new ArrayList<Lesson>();
		for (File f : classes_dir.listFiles()) {
			if (f == null) {
				return classes;
			}
			classes.add(getClass(f));
		}
		return classes;
	}
}
