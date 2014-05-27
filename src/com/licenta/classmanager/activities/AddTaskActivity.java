package com.licenta.classmanager.activities;

import com.licenta.classmanager.R;
import com.licenta.classmanager.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class AddTaskActivity extends Activity {

	public static final int request_code = 101;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
	}
}
