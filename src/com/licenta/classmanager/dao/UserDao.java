package com.licenta.classmanager.dao;

import java.io.File;

import android.content.Context;
import android.util.Log;

import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.User;

public class UserDao extends Dao {

	public final String USER_KEY = "user";
	
	public UserDao(Context context) {
		super(context);
	}
	
	public void putUser(User user) {
		putData(new File(context.getFilesDir(), USER_KEY), user);
	}
	
	public User getUser() {
		return (User) getData(new File(context.getFilesDir(), USER_KEY));
	}

	public void deleteUser() {
		deleteData(new File(context.getFilesDir(), USER_KEY));
	}
}
