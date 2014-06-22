package com.licenta.classmanager.dao;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.content.Context;
import android.util.Log;

public class Dao {
	
	protected Context context;
	
	public Dao(Context context) {
		this.context = context;
	}
	
	protected void putData(File key, Serializable data) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(key)));
			oos.writeObject(data);
			oos.close();
		} catch (Exception e) {
			Log.e("FILE_WRITE_ERROR", e.toString());
		}
	}
	
	protected boolean deleteData(File key) {
		return key.delete();
	}

	protected Serializable getData(File key) {
		Serializable data = null;
		try {
			ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(key)));
			data = (Serializable) ois.readObject();
			ois.close();
		} catch (Exception e) {
			Log.e("FILE_READ_ERROR", e.toString());
		}
		return data;
	}
	
	public String getReason(File file) {
		try {
			if (!file.exists())
				return "It doesn't exist in the first place.";
			else if (file.isDirectory() && file.list().length > 0)
				return "It's a directory and it's not empty.";
			else
				return "Somebody else has it open, we don't have write permissions, or somebody stole my disk.";
		} catch (SecurityException e) {
			return "We're sandboxed and don't have filesystem access.";
		}
	}
}
