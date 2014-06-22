package com.licenta.classmanager.activities;

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
import android.widget.TextView;

import com.licenta.classmanager.R;
import com.licenta.classmanager.holders.Note;

public class NoteDetailsActivity extends ActionBarActivity {
	
	public static final String EXTRA_NOTE = "com.licenta.classmanager.NOTE";
	public static final String EXTRA_NOTE_POSITION = "com.licenta.classmanager.NOTE_POSITION";
	public static final String EXTRA_NOTE_DELETED = "com.licenta.classmanager.NOTE_DELETED";
	
	public static final int request_code = 106;
	
	private TextView txt_title, txt_text, txt_class, txt_dateAdded;
	private Note note;
	private int note_position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note_details);
		linkUI();
		setData();
		setActions();
	}
	
	public void linkUI() {
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_text = (TextView) findViewById(R.id.txt_text);
		txt_class = (TextView) findViewById(R.id.txt_class);
		txt_dateAdded = (TextView) findViewById(R.id.txt_dateAdded);
	}
	
	public void setData() {
		Intent intent = getIntent();
		note = (Note) intent.getSerializableExtra(EXTRA_NOTE);
		note_position = intent.getIntExtra(EXTRA_NOTE_POSITION, -1);
		setNoteData();
		
	}
	
	private void setNoteData() {
		txt_class.setText(note.getLesson().getName());
		txt_title.setText(note.getTitle());
		txt_text.setText(note.getText());
		txt_dateAdded.setText(note.getDate().toString());
	}
	
	public void setActions() {
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id) {
		case R.id.action_edit: {
				Intent intent = new Intent(this, NoteAddEditActivity.class);
				intent.putExtra(EXTRA_NOTE, note);
				intent.putExtra(DashboardActivity.REQUEST_CODE, NoteAddEditActivity.edit_request_code);
				startActivityForResult(intent, NoteAddEditActivity.edit_request_code);
			} break;
		case R.id.action_delete: {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("Are you sure you want to delete this note?").setCancelable(true)
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent intent = new Intent();
					intent.putExtra(EXTRA_NOTE, note);
					intent.putExtra(EXTRA_NOTE_POSITION, note_position);
					intent.putExtra(EXTRA_NOTE_DELETED, true);
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
		if(requestCode == NoteAddEditActivity.edit_request_code) {
			if(resultCode == Activity.RESULT_OK) {
				note = (Note) data.getSerializableExtra(EXTRA_NOTE);
				if(note!=null) {
					setNoteData();
					Intent intent = new Intent();
					intent.putExtra(EXTRA_NOTE, note);
					intent.putExtra(EXTRA_NOTE_POSITION, note_position);
					setResult(RESULT_OK, intent);
				} else {
					Log.e("INTENT_ERROR", "Received object is null: note");
				}
			} else {
				setResult(RESULT_CANCELED);
			}
		} else {
			setResult(RESULT_CANCELED);
		}
	}
}
