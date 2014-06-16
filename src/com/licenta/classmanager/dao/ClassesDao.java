package com.licenta.classmanager.dao;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.licenta.classmanager.holders.Lesson;

public class ClassesDao extends Dao {

	private File classes_dir;
	public final String CLASSES_KEY = "classes";
	
	public ClassesDao(Context context) {
		super(context);
		classes_dir = new File(context.getFilesDir().getAbsolutePath() + File.separator + "classes");
		classes_dir.mkdir();
	}
	
	public void putClass(Lesson lesson) {
		putData(new File(classes_dir, lesson.getLocal_id()), lesson);
	}
	
	public Lesson getClass(File key) {
		return (Lesson) getData(new File(key.getAbsolutePath()));
	}

	public void deleteClass(Lesson lesson) {
		if (!deleteData(new File(classes_dir, lesson.getLocal_id()))) {

			Log.e("CONTACT_DELETE", "Class not deleted!");
			Log.e("CONTACT_DELETE", getReason(new File(classes_dir, lesson.getLocal_id())));
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
