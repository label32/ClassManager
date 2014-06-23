package com.licenta.classmanager.activities;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.licenta.classmanager.R;
import com.licenta.classmanager.dao.ClassesDao;
import com.licenta.classmanager.holders.Date;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Note;

public class NoteAddEditActivity extends ActionBarActivity {
	
	public static final String EXTRA_NOTE = "com.licenta.classmanager.NOTE";
	
	public static final int add_request_code = 104;
	public static final int edit_request_code = 105;
	
	private Spinner spinner_class;
	private EditText et_title, et_text;
	private Note note;
	private int request_code;
	private ClassesDao dao;
	private ArrayList<Lesson> classes;
	private String local_id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_add);
		linkUI();
		setData();
		setActions();
	}
	
	public void linkUI() {
		et_title = (EditText) findViewById(R.id.et_title);
		et_text = (EditText) findViewById(R.id.et_text);
		spinner_class = (Spinner) findViewById(R.id.spinner_class);
	}
	
	public void setData() {
		Intent intent = new Intent();
		intent = getIntent();
		request_code = intent.getIntExtra(DashboardActivity.REQUEST_CODE, -1);
		setSpinner();
		if(request_code == edit_request_code) {
			note = (Note) intent.getSerializableExtra(EXTRA_NOTE);
			if (note != null) {
				local_id = note.getLocal_id();
				setNoteData();
			} else {
				Log.e("INTENT_ERROR", "Received object is null: note");
			}
		}
		
	}
	
	public void setActions() {
		
	}
	
	private void setNoteData() {
		int class_position = classes.indexOf(note.getLesson());
		spinner_class.setSelection(class_position);
		et_title.setText(note.getTitle());
		et_text.setText(note.getText());
	}
	
	private Note getNoteData() {
		int position = spinner_class.getSelectedItemPosition();
		Lesson l = classes.get(position);
		String title = et_title.getText().toString();
		String text = et_text.getText().toString();
		Calendar c = Calendar.getInstance();
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DATE);
		int year = c.get(Calendar.YEAR);
		int id = 0;
		if(request_code == edit_request_code) {
			day = note.getDate().getDay();
			year = note.getDate().getYear();
			month = note.getDate().getMonth();
			id = note.getId();
		}
		return new Note(id, title, text, l, new Date(day, month, year));
	}
	
	private void setSpinner() {
		dao = new ClassesDao(this);
		classes = dao.getClasses();

		ArrayAdapter<String> classes_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				makeArray(classes));
		classes_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_class.setAdapter(classes_adapter);
	}
	
	private String[] makeArray(ArrayList<Lesson> classes) {
		ArrayList<String> c = new ArrayList<String>();
		for (int i = 0; i < classes.size(); i++)
			c.add(classes.get(i).getName());
		return (String[]) c.toArray(new String[c.size()]);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id==R.id.action_save) {
			Note note = getNoteData();
			note.setLocal_id(local_id);
			Intent intent = new Intent();
			intent.putExtra(EXTRA_NOTE, note);
			setResult(RESULT_OK, intent);
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
