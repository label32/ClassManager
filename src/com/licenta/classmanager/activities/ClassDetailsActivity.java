package com.licenta.classmanager.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.licenta.classmanager.R;
import com.licenta.classmanager.holders.Day;
import com.licenta.classmanager.holders.Lesson;

public class ClassDetailsActivity extends ActionBarActivity {
	
	public static final int request_code = 103;
	
	public static final String EXTRA_CLASS = "com.licenta.classmanager.CLASS";
	public static final String EXTRA_CLASS_POSITION = "com.licenta.classmanager.CLASS_POSITION";
	public static final String CLASS_MODIFIED = "com.licenta.classmanager.AddEditClassActivity.CLASS_MODIFIED";
	public static final String CLASS_DELETED = "com.licenta.classmanager.ClassDetailsActivity.CLASS_DELETED";

	private TextView txt_className, txt_classroom, txt_details, txt_startTime, txt_endTime;
	private Button btn_color;
	private CheckBox cb_monday, cb_tuesday, cb_wednesday, cb_thursday, cb_friday, cb_saturday, cb_sunday;
	private Lesson lesson;
	private int class_position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_details);
		linkUI();
		setData();
		setActions();
	}

	private void linkUI() {
		txt_className = (TextView) findViewById(R.id.txt_className);
		txt_classroom = (TextView) findViewById(R.id.txt_classroom);
		txt_details = (TextView) findViewById(R.id.txt_details);
		txt_startTime = (TextView) findViewById(R.id.txt_startTime);
		txt_endTime = (TextView) findViewById(R.id.txt_endTime);
		
		btn_color = (Button) findViewById(R.id.btn_color);

		cb_monday = (CheckBox) findViewById(R.id.cb_monday);
		cb_tuesday = (CheckBox) findViewById(R.id.cb_tuesday);
		cb_wednesday = (CheckBox) findViewById(R.id.cb_wednesday);
		cb_thursday = (CheckBox) findViewById(R.id.cb_thursday);
		cb_friday = (CheckBox) findViewById(R.id.cb_friday);
		cb_saturday = (CheckBox) findViewById(R.id.cb_saturday);
		cb_sunday = (CheckBox) findViewById(R.id.cb_sunday);
	}

	private void setData() {
		Intent intent = new Intent();
		intent = getIntent();
		lesson = (Lesson) intent.getSerializableExtra(EXTRA_CLASS);
		class_position = intent.getIntExtra(EXTRA_CLASS_POSITION, -1);
		if(lesson != null) {
			setClassData();
		} else {
			Log.e("INTENT_ERROR", "Received object is null: lesson");
		}
	}
	
	private void setClassData() {
		txt_className.setText(lesson.getName());
		txt_classroom.setText(lesson.getClassroom());
		txt_details.setText(lesson.getDetails());
		txt_startTime.setText(lesson.getStart_time().toString());
		txt_endTime.setText(lesson.getEnd_time().toString());
		btn_color.setBackgroundColor(lesson.getColor());
		setDays(lesson.getDays());
	}

	private void setActions() {

	}
	
	private void setDays(ArrayList<Day> days) {
		Day day;
		for(int i=0; i<days.size(); i++) {
			day = days.get(i);
			switch(day) {
			case Monday: cb_monday.setChecked(true); break;
			case Tuesday: cb_tuesday.setChecked(true); break;
			case Wednesday: cb_wednesday.setChecked(true); break;
			case Thursday: cb_thursday.setChecked(true); break;
			case Friday: cb_friday.setChecked(true); break;
			case Saturday: cb_saturday.setChecked(true); break;
			case Sunday: cb_sunday.setChecked(true); break;
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id) {
		case R.id.action_edit: {
				Intent intent = new Intent(this, ClassAddEditActivity.class);
				intent.putExtra(DashboardActivity.REQUEST_CODE, ClassAddEditActivity.edit_request_code);
				intent.putExtra(ClassAddEditActivity.EXTRA_CLASS, lesson);
				startActivityForResult(intent, ClassAddEditActivity.edit_request_code);
			} break;
		case R.id.action_delete: {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Are you sure you want to delete this class?\nAll tasks and notes associated with this class will be also deleted.").setCancelable(true)
				.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent intent = new Intent();
						intent.putExtra(EXTRA_CLASS, lesson);
						intent.putExtra(EXTRA_CLASS_POSITION, class_position);
						intent.putExtra(CLASS_DELETED, true);
						setResult(RESULT_OK, intent);
						finish();
					}
				}).setNegativeButton("Cancel", null);
				
				AlertDialog alert = builder.create();
				alert.show();
			} break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.details_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == ClassAddEditActivity.edit_request_code) {
			if(resultCode == Activity.RESULT_OK) {
				lesson = (Lesson) data.getSerializableExtra(EXTRA_CLASS);
				if(lesson!=null) {
					setClassData();
					Intent intent = new Intent();
					intent.putExtra(EXTRA_CLASS, lesson);
					intent.putExtra(EXTRA_CLASS_POSITION, class_position);
					setResult(RESULT_OK, intent);
				} else {
					Log.e("INTENT_ERROR", "Received object is null: lesson");
				}
			} else {
				setResult(RESULT_CANCELED);
			}
		} else {
			setResult(RESULT_CANCELED);
		}
	}
}
