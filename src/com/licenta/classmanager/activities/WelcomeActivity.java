package com.licenta.classmanager.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.licenta.classmanager.R;

public class WelcomeActivity extends Activity {

	public static final String FIRST_TIME = "com.licenta.classmanager.activity.FIRST_TIME";

	private Button btn_create_account;
	private Button btn_login;
	private Button btn_use_offline;
	private SharedPreferences sharedPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		sharedPref = getSharedPreferences(getString(R.string.preference_file),
				Context.MODE_PRIVATE);
		if (firstTime()) {
			linkUI();
			setData();
			setActions();
		} else {
			Intent intent = new Intent(this, DashboardActivity.class);
			startActivity(intent);
			this.finish();
		}
	}

	private boolean firstTime() {
		return sharedPref.getBoolean(FIRST_TIME, true);
	}

	private void setFirstTime() {
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putBoolean(FIRST_TIME, false);
		editor.commit();
	}

	private void linkUI() {
		btn_create_account = (Button) findViewById(R.id.btn_create_account);
		btn_login = (Button) findViewById(R.id.btn_login);
		btn_use_offline = (Button) findViewById(R.id.btn_use_offline);
	}

	private void setData() {

	}

	private void setActions() {
		btn_create_account.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// check connectivity
			}
		});

		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// check connectivity
			}
		});

		btn_use_offline.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setFirstTime();
				Intent intent = new Intent(WelcomeActivity.this,
						DashboardActivity.class);
				intent.putExtra(DashboardActivity.OFFLINE, true);
				startActivity(intent);
				WelcomeActivity.this.finish();
			}
		});
	}
}
