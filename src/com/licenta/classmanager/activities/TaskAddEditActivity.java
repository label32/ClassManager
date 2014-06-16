package com.licenta.classmanager.activities;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.licenta.classmanager.R;
import com.licenta.classmanager.adapters.CustomSpinnerAdapter;
import com.licenta.classmanager.adapters.TaskClassSpinnerAdapter;
import com.licenta.classmanager.dao.ClassesDao;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Task;

public class TaskAddEditActivity extends ActionBarActivity {

	public static final String EXTRA_TASK = "com.licenta.classmanager.TASK";

	public static final int add_request_code = 101;
	public static final int edit_request_code = 102;

	private Spinner spinner_class, spinner_type;
	private DatePicker dp_deadline;
	private EditText et_title, et_details;
	private int request_code;
	private Task task;
	private ArrayList<Lesson> classes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_add);
		linkUI();
		setData();
		setActions();
	}

	private void linkUI() {
		spinner_class = (Spinner) findViewById(R.id.spinner_class);
		spinner_type = (Spinner) findViewById(R.id.spinner_type);
		dp_deadline = (DatePicker) findViewById(R.id.dp_deadline);
		et_title = (EditText) findViewById(R.id.et_title);
		et_details = (EditText) findViewById(R.id.et_details);
	}

	private void setData() {
		Intent intent = getIntent();
		request_code = intent.getIntExtra(MainActivity.REQUEST_CODE, -1);
		if (request_code == edit_request_code) {
			task = (Task) intent.getSerializableExtra(EXTRA_TASK);
			if (task != null) {

			} else {
				Log.e("INTENT_ERROR", "Received object is null: lesson");
			}
		} else {
			ClassesDao dao = new ClassesDao(this);
			classes = dao.getClasses();
			TaskClassSpinnerAdapter classSpinnerAdapter = new TaskClassSpinnerAdapter(this,
					android.R.layout.simple_spinner_item, classes, getString(R.string.class_spinner_title));
			classSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			ArrayAdapter<String> aa = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
					makeArray(classes));
			aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_class.setAdapter(aa);
			spinner_class.setSelection(-1);
		}
	}

	private String[] makeArray(ArrayList<Lesson> classes) {
		ArrayList<String> c = new ArrayList<String>();
		for (int i = 0; i < classes.size(); i++)
			c.add(classes.get(i).getName());
		return (String[]) c.toArray(new String[c.size()]);
	}

	private void setActions() {
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

		}
		return super.onOptionsItemSelected(item);
	}
}
