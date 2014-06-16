package com.licenta.classmanager.activities;

import java.util.ArrayList;

import net.margaritov.preference.colorpicker.ColorPickerDialog;
import net.margaritov.preference.colorpicker.ColorPickerDialog.OnColorChangedListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.licenta.classmanager.R;
import com.licenta.classmanager.holders.Day;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Time;

public class ClassAddEditActivity extends ActionBarActivity {
	
	public static final String EXTRA_CLASS = "com.licenta.classmanager.CLASS";
	
	public static final int add_request_code = 107;
	public static final int edit_request_code = 108;
	
	private TimePicker tp_start_time, tp_end_time;
	private EditText et_className, et_details, et_classroom;
	private Button btn_color;
	private CheckBox cb_monday, cb_tuesday, cb_wednesday, cb_thursday, cb_friday, cb_saturday, cb_sunday;
	private int request_code;
	private String class_id;
	private Lesson lesson;
	public static int color = 0xff000000 + Integer.parseInt(Integer.toHexString(0),16);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_class_add);
		linkUI();
		setData();
		setActions();
	}
	
	private void linkUI() {
		et_className = (EditText) findViewById(R.id.et_className);
		et_details = (EditText) findViewById(R.id.et_details);
		et_classroom = (EditText) findViewById(R.id.et_classroom);
		
		tp_start_time = (TimePicker) findViewById(R.id.tp_start_time);
		tp_end_time = (TimePicker) findViewById(R.id.tp_end_time);
		
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
		Intent intent = getIntent();
		request_code = intent.getIntExtra(MainActivity.REQUEST_CODE, -1);
		if(request_code == edit_request_code) {
			lesson = (Lesson) intent.getSerializableExtra(EXTRA_CLASS);
			if(lesson!=null) {
				class_id = lesson.getLocal_id();
				et_className.setText(lesson.getName());
				et_details.setText(lesson.getDetails());
				et_classroom.setText(lesson.getClassroom());
				tp_start_time.setCurrentMinute(lesson.getStart_time().getMinute());
				tp_start_time.setCurrentHour(lesson.getStart_time().getHour());
				tp_end_time.setCurrentMinute(lesson.getEnd_time().getMinute());
				tp_end_time.setCurrentHour(lesson.getEnd_time().getHour());
				btn_color.setBackgroundColor(lesson.getColor());
				setDays(lesson.getDays());
			} else {
				Log.e("INTENT_ERROR", "Received object is null: lesson");
			}
		}
		tp_start_time.setIs24HourView(true);
		tp_end_time.setIs24HourView(true);
	}
	
	private void setActions() {;
		btn_color.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ColorPickerDialog cpd = new ColorPickerDialog(ClassAddEditActivity.this, 0);
				cpd.setAlphaSliderVisible(false);
				cpd.setHexValueEnabled(true);
				cpd.setOnColorChangedListener(new OnColorChangedListener() {
					
					@Override
					public void onColorChanged(int color) {
						ClassAddEditActivity.color = 0xff000000 + Integer.parseInt(Integer.toHexString(color),16);
						Toast.makeText(ClassAddEditActivity.this, "color:"+Integer.toHexString(color), Toast.LENGTH_LONG).show();
						btn_color.setBackgroundColor(ClassAddEditActivity.color);
					}
				});
				cpd.show();
			}
		});
	}
	
	private Lesson getClassData() {
		String name = et_className.getText().toString();
		String details = et_details.getText().toString();
		String classroom = et_classroom.getText().toString();
		ArrayList<Day> days = getDays();
		Time start_time = new Time(tp_start_time.getCurrentHour(), tp_start_time.getCurrentMinute());
		Time end_time = new Time(tp_end_time.getCurrentHour(), tp_end_time.getCurrentMinute());
		int id = 0;
		if(request_code == edit_request_code)
			id = lesson.getId();
		return new Lesson(id, name, details, classroom, days, start_time, end_time, color);
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
	
	private ArrayList<Day> getDays() {
		ArrayList<Day> days = new ArrayList<Day>();
		if(cb_monday.isChecked())
			days.add(Day.Monday);
		if(cb_tuesday.isChecked())
			days.add(Day.Tuesday);
		if(cb_wednesday.isChecked())
			days.add(Day.Wednesday);
		if(cb_thursday.isChecked())
			days.add(Day.Thursday);
		if(cb_friday.isChecked())
			days.add(Day.Friday);
		if(cb_saturday.isChecked())
			days.add(Day.Saturday);
		if(cb_sunday.isChecked())
			days.add(Day.Sunday);
		return days;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.action_save) {
			Lesson l = getClassData();
			l.setLocal_id(class_id);
			Intent result = new Intent();
			result.putExtra(EXTRA_CLASS, l);
			setResult(Activity.RESULT_OK, result);
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.add_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
}
