package com.licenta.classmanager.dao;

import java.io.File;

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

	public void deleteClass(Lesson contact) {
		if (!deleteData(new File(classes_dir, contact.getLocal_id()))) {

			Log.e("CONTACT_DELETE", "Class not deleted!");
			Log.e("CONTACT_DELETE", getReason(new File(classes_dir, contact.getLocal_id())));
		}
	}
}
