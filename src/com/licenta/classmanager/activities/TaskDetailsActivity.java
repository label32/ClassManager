package com.licenta.classmanager.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.licenta.classmanager.R;
import com.licenta.classmanager.holders.Task;

public class TaskDetailsActivity extends ActionBarActivity {

	public static final String EXTRA_TASK = "com.licenta.classmanager.EXTRA_TASK";
	public static final String EXTRA_TASK_POSITION = "com.licenta.classmanager.EXTRA_TASK_POSITION";
	
	public static final int request_code = 103;
	
	private TextView txt_class, txt_type, txt_deadline, txt_title, txt_details;
	private Task task;
	private int task_position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_task_details);
		linkUI();
		setData();
		setActions();
	}
	
	public void linkUI() {
		txt_class = (TextView) findViewById(R.id.txt_class);
		txt_type = (TextView) findViewById(R.id.txt_type);
		txt_deadline = (TextView) findViewById(R.id.txt_deadline);
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_details = (TextView) findViewById(R.id.txt_details);
	}
	
	public void setData() {
		Intent intent = getIntent();
		task = (Task) intent.getSerializableExtra(EXTRA_TASK);
		task_position = intent.getIntExtra(EXTRA_TASK_POSITION, -1);
		setTaskData();
	}
	
	public void setTaskData() {
		txt_class.setText(task.getLesson().getName());
		txt_type.setText(task.getType().toString());
		txt_title.setText(task.getTitle());
		txt_details.setText(task.getDetails());
		txt_deadline.setText(task.getDeadline().toString());
	}
	
	public void setActions() {
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.action_edit) {
			Intent intent = new Intent(this, TaskAddEditActivity.class);
			intent.putExtra(EXTRA_TASK, task);
			intent.putExtra(MainActivity.REQUEST_CODE, TaskAddEditActivity.edit_request_code);
			startActivityForResult(intent, TaskAddEditActivity.edit_request_code);
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
		if(requestCode == TaskAddEditActivity.edit_request_code) {
			if(resultCode == Activity.RESULT_OK) {
				task = (Task) data.getSerializableExtra(EXTRA_TASK);
				if(task!=null) {
					setTaskData();
					Intent intent = new Intent();
					intent.putExtra(EXTRA_TASK, task);
					intent.putExtra(EXTRA_TASK_POSITION, task_position);
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
