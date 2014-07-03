package com.licenta.classmanager.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.licenta.classmanager.R;
import com.licenta.classmanager.dao.ClassesDao;
import com.licenta.classmanager.holders.Announcement;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Task;

public class AnnouncementAddActivity extends ActionBarActivity {

	public static int request_code = 110;
	
	private Spinner spinner_class;
	private EditText et_title, et_text;
	private Announcement announcement;
	private ArrayList<Lesson> classes;
	private ClassesDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announcement_add_edit);
		linkUI();
		setData();
		setActions();
	}
	
	private void linkUI() {
		et_text = (EditText) findViewById(R.id.et_text);
		et_title = (EditText) findViewById(R.id.et_title);
		spinner_class = (Spinner) findViewById(R.id.spinner_class);
	}
	
	private void setData() {
		dao = new ClassesDao(this);
		classes = dao.getClasses();

		ArrayAdapter<String> classes_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				makeArray(classes));
		classes_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_class.setAdapter(classes_adapter);
	}
	
	private void setActions() {
		
	}
	
	private Announcement getAnnouncementData() {
		Announcement a = new Announcement(et_title.getText().toString());
		a.setText(et_text.getText().toString());
		int position = spinner_class.getSelectedItemPosition();
		Lesson l = classes.get(position);
		a.setLesson(l);
		return a;
	}
	

	private String[] makeArray(ArrayList<Lesson> classes) {
		ArrayList<String> c = new ArrayList<String>();
		for (int i = 0; i < classes.size(); i++)
			c.add(classes.get(i).getName());
		return (String[]) c.toArray(new String[c.size()]);
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.add_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_save) {
			Announcement announcement = getAnnouncementData();
			Intent intent = new Intent();
			intent.putExtra(AnnouncementDetailsActivity.EXTRA_ANNOUNCEMENT, announcement);
			setResult(RESULT_OK, intent);
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
