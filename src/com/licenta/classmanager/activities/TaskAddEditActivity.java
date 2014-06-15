package com.licenta.classmanager.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;

import com.licenta.classmanager.R;

public class TaskAddEditActivity extends ActionBarActivity {

	public static final int add_request_code = 101;
	public static final int edit_request_code = 102;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.add_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
}