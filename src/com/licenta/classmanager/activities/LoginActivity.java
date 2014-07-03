package com.licenta.classmanager.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.licenta.classmanager.R;
import com.licenta.classmanager.dao.UserDao;
import com.licenta.classmanager.holders.User;
import com.licenta.classmanager.services.DataService;
import com.licenta.classmanager.services.JsonParser;
import com.licenta.classmanager.services.ServiceCallback;
import com.licenta.classmanager.utils.Utils;

public class LoginActivity extends Activity {

	private EditText et_email, et_password;
	private Button btn_login;
	private DataService dataService;
	private User u;
	private JsonParser jparser;
	private SharedPreferences sharedPref;
	private UserDao dao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		linkUI();
		setData();
		setActions();
	}
	
	public void linkUI() {
		et_email = (EditText) findViewById(R.id.et_email);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_login = (Button) findViewById(R.id.btn_login);
	}
	
	public void setData() {
		dataService = new DataService(this);
		jparser = new JsonParser(this);
		dao = new UserDao(this);
	}
	
	public void setActions() {
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				dataService.login(et_email.getText().toString(), Utils.md5(et_password.getText().toString()), new ServiceCallback(LoginActivity.this) {
					
					@Override
					public void onSuccess(JSONObject result) {
						u = jparser.getUserFromJSON(result);
						
						dao.putUser(u);
						
						sharedPref = getSharedPreferences(getString(R.string.preference_file),
								Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPref.edit();
						editor.putBoolean(WelcomeActivity.FIRST_TIME, false);
						editor.putInt(MainActivity.EXTRA_USER_ID, u.getId());
						editor.putInt(MainActivity.EXTRA_USER_TYPE, u.getType());
						editor.commit();
						
						Intent intent = new Intent(LoginActivity.this, MainActivity.class);
						intent.putExtra(MainActivity.EXTRA_USER, u);
						startActivity(intent);
						LoginActivity.this.finish();
					}
					
					@Override
					public void onOffline() {
						Toast.makeText(LoginActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
}
