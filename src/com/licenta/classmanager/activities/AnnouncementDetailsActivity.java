package com.licenta.classmanager.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import com.licenta.classmanager.R;
import com.licenta.classmanager.holders.Announcement;

public class AnnouncementDetailsActivity extends  ActionBarActivity {

	public static final String EXTRA_ANNOUNCEMENT = "com.licenta.classmanager.ANNOUNCEMENT";
	public static final String EXTRA_ANNOUNCEMENT_POSITION = "com.licenta.classmanager.ANNOUNCEMENT_POSITION";
	
	public static int request_code = 112;
	
	private TextView txt_title, txt_text, txt_dateAdded, txt_class;
	private Announcement announcement;
	private int position;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_announcement_details);
		linkUI();
		setData();
		setActions();
	}
	
	private void linkUI() {
		txt_title = (TextView) findViewById(R.id.txt_title);
		txt_text = (TextView) findViewById(R.id.txt_text);
		txt_class = (TextView) findViewById(R.id.txt_class);
		txt_dateAdded = (TextView) findViewById(R.id.txt_dateAdded);
	}
	
	private void setData() {
		Intent intent = getIntent();
		announcement = (Announcement) intent.getSerializableExtra(EXTRA_ANNOUNCEMENT);
		position = intent.getIntExtra(EXTRA_ANNOUNCEMENT_POSITION, -1);
		
		txt_title.setText(announcement.getTitle());
		txt_text.setText(announcement.getText());
		txt_class.setText(announcement.getLesson().getName());
		txt_dateAdded.setText(announcement.getDate().toString());
	}
	
	private void setActions() {
		
	}
}
