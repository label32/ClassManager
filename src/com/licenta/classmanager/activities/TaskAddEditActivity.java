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
import com.licenta.classmanager.dao.ClassesDao;
import com.licenta.classmanager.dao.TasksDao;
import com.licenta.classmanager.holders.Date;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Task;
import com.licenta.classmanager.holders.TaskType;

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
	private ClassesDao dao;

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
		setSpinners();
		if (request_code == edit_request_code) {
			task = (Task) intent.getSerializableExtra(EXTRA_TASK);
			if (task != null) {
				setTaskData();
			} else {
				Log.e("INTENT_ERROR", "Received object is null: lesson");
			}
		}
	}
	
	private void setTaskData() {
		int class_position = classes.indexOf(task.getLesson());
		spinner_class.setSelection(class_position);
		spinner_type.setSelection(task.getType().getCode()-1);
		dp_deadline.updateDate(task.getDeadline().getYear(), task.getDeadline().getMonth(), task.getDeadline().getDay());
		et_title.setText(task.getTitle());
		et_details.setText(task.getDetails());
	}
	
	private void setSpinners() {
		dao = new ClassesDao(this);
		classes = dao.getClasses();

		ArrayAdapter<String> classes_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,
				makeArray(classes));
		classes_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_class.setAdapter(classes_adapter);

		ArrayAdapter<CharSequence> type_adapter = ArrayAdapter.createFromResource(this, R.array.task_types,
				android.R.layout.simple_spinner_item);
		type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_type.setAdapter(type_adapter);

	}

	private Task getTaskData() {
		int position = spinner_class.getSelectedItemPosition();
		Lesson l = classes.get(position);
		TaskType type = TaskType.Assignment;
		switch (spinner_type.getSelectedItemPosition()) {
		case 1:
			type = TaskType.Project;
			break;
		case 2:
			type = TaskType.Exam;
			break;
		}
		String title = et_title.getText().toString();
		String details = et_details.getText().toString();
		int day = dp_deadline.getDayOfMonth();
		int month = dp_deadline.getMonth();
		int year = dp_deadline.getYear();
		return new Task(0, l, type, new Date(day, month, year), title, details, false);
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
			Task task = getTaskData();
			Intent intent = new Intent();
			intent.putExtra(EXTRA_TASK, task);
			setResult(RESULT_OK, intent);
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
