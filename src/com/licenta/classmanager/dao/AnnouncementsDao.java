package com.licenta.classmanager.dao;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.licenta.classmanager.holders.Announcement;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Note;
import com.licenta.classmanager.holders.Task;

public class AnnouncementsDao extends Dao {
	
	private File announcements_dir;
	public final String ANNOUNCEMENTS_KEY = "announcements";
	
	public AnnouncementsDao(Context context) {
		super(context);
		announcements_dir = new File(context.getFilesDir().getAbsolutePath() + File.separator + "announcements");
		announcements_dir.mkdir();
	}
	
	public void putAnnouncement(Announcement announcement) {
		putData(new File(announcements_dir, Integer.toString(announcement.getId())), announcement);
	}
	
	public Announcement getAnnouncement(File key) {
		return (Announcement) getData(new File(key.getAbsolutePath()));
	}

	public void deleteAnnouncement(Announcement announcement) {
		if (!deleteData(new File(announcements_dir, Integer.toString(announcement.getId())))) {
			Log.e("ANNOUNCEMENT_DELETE", "Announcement not deleted!");
			Log.e("ANNOUNCEMENT_DELETE", getReason(new File(announcements_dir, Integer.toString(announcement.getId()))));
		} 
	}
	
	public void putAnnouncements(ArrayList<Announcement> announcements) {
		for (int i = 0; i < announcements.size(); i++) {
			putAnnouncement(announcements.get(i));
		}
	}

	public ArrayList<Announcement> getAnnouncements() {
		ArrayList<Announcement> announcements = new ArrayList<Announcement>();
		for (File f : announcements_dir.listFiles()) {
			if (f == null) {
				return announcements;
			}
			announcements.add(getAnnouncement(f));
		}
		return announcements;
	}

}
