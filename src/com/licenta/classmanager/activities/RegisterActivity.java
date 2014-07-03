package com.licenta.classmanager.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.licenta.classmanager.R;
import com.licenta.classmanager.dao.UserDao;
import com.licenta.classmanager.holders.User;
import com.licenta.classmanager.services.DataService;
import com.licenta.classmanager.services.JsonParser;
import com.licenta.classmanager.services.ServiceCallback;
import com.licenta.classmanager.utils.Utils;

public class RegisterActivity extends Activity {

	private EditText et_name, et_email, et_password;
	private Spinner spinner_type;
	private Button btn_register;
	private DataService dataService;
	private JsonParser jsonParser;
	private UserDao dao;
	private String email;
	private String password;
	private String name;
	private int type;
	private SharedPreferences sharedPref;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		linkUI();
		setData();
		setActions();
	}
	
	public void linkUI() {
		et_name = (EditText) findViewById(R.id.et_name);
		et_email = (EditText) findViewById(R.id.et_email);
		et_password = (EditText) findViewById(R.id.et_password);
		spinner_type = (Spinner) findViewById(R.id.spinner_type);
		btn_register = (Button) findViewById(R.id.btn_register);
	}
	
	public void setData() {
		dao = new UserDao(this);
		dataService = new DataService(this);
		jsonParser = new JsonParser(this);
		ArrayAdapter<CharSequence> type_adapter = ArrayAdapter.createFromResource(this, R.array.user_types,
				android.R.layout.simple_spinner_item);
		type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner_type.setAdapter(type_adapter);
	}
	
	public void setActions() {
		btn_register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				email = et_email.getText().toString();
				password = Utils.md5(et_password.getText().toString());
				name = et_name.getText().toString();
				type = 2;
				if(spinner_type.getSelectedItemPosition()==1)
					type = 1;
				dataService.register(email, password, name, type, new ServiceCallback(RegisterActivity.this) {
					
					@Override
					public void onSuccess(JSONObject result) {
						int userid = result.optInt("userid");
						User u = new User(userid, type, name, email, password);
						dao.putUser(u);
						sharedPref = getSharedPreferences(getString(R.string.preference_file),
								Context.MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPref.edit();
						editor.putBoolean(WelcomeActivity.FIRST_TIME, false);
						editor.putInt(MainActivity.EXTRA_USER_ID, u.getId());
						editor.putInt(MainActivity.EXTRA_USER_TYPE, u.getType());
						editor.commit();
						Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
						intent.putExtra(MainActivity.EXTRA_USER, u);
						startActivity(intent);
						RegisterActivity.this.finish();
					}
					
					@Override
					public void onOffline() {
						Toast.makeText(RegisterActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}
}
