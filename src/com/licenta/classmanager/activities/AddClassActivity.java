package com.licenta.classmanager.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TimePicker;

import com.licenta.classmanager.R;

public class AddClassActivity extends Activity {
	
	public static final int request_code = 103;
	
	private TimePicker tp_start_time, tp_end_time;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_class);
		linkUI();
		setData();
	}
	
	private void linkUI() {
		tp_start_time = (TimePicker) findViewById(R.id.tp_start_time);
		tp_end_time = (TimePicker) findViewById(R.id.tp_end_time);
	}
	
	private void setData() {
		tp_start_time.setIs24HourView(true);
		tp_end_time.setIs24HourView(true);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.add_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
}
