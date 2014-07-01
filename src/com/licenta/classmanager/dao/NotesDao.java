package com.licenta.classmanager.dao;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

import com.licenta.classmanager.holders.Note;

public class NotesDao extends Dao {

	private File notes_dir;
	public final String NOTES_KEY = "notes";
	
	public NotesDao(Context context) {
		super(context);
		notes_dir = new File(context.getFilesDir().getAbsolutePath() + File.separator + "notes");
		notes_dir.mkdir();
	}
	
	public void putNote(Note note) {
		putData(new File(notes_dir, Integer.toString(note.getId())), note);
	}
	
	public Note getNote(File key) {
		return (Note) getData(new File(key.getAbsolutePath()));
	}

	public void deleteNote(Note note) {
		if (!deleteData(new File(notes_dir, Integer.toString(note.getId())))) {

			Log.e("NOTE_DELETE", "Note not deleted!");
			Log.e("NOTE_DELETE", getReason(new File(notes_dir, Integer.toString(note.getId()))));
		}
	}
	
	public void putNotes(ArrayList<Note> notes) {
		for (int i = 0; i < notes.size(); i++) {
			putNote(notes.get(i));
		}
	}

	public ArrayList<Note> getNotes() {
		ArrayList<Note> notes = new ArrayList<Note>();
		for (File f : notes_dir.listFiles()) {
			if (f == null) {
				return notes;
			}
			notes.add(getNote(f));
		}
		return notes;
	}
}
