package com.licenta.classmanager.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.licenta.classmanager.R;
import com.licenta.classmanager.activities.LoginActivity;
import com.licenta.classmanager.activities.MainActivity;
import com.licenta.classmanager.activities.RegisterActivity;
import com.licenta.classmanager.activities.WelcomeActivity;
import com.licenta.classmanager.dao.UserDao;
import com.licenta.classmanager.holders.User;

public class SettingsFragment extends BaseFragment {

//	private static final String ARG_SECTION_NUMBER = "section_number";

	private Button btn_logout, btn_login, btn_createAccount, btn_changeData;
	private LinearLayout user_info;
	private TextView txt_name, txt_email;
	private SharedPreferences sharedPref;
	private UserDao dao;
	private User u;

	public SettingsFragment() {

	}
	
//	public void setData(int sectionNumber) {
//		Bundle args = new Bundle();
//		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//		this.setArguments(args);
//	}
	
	public void linkUI() {
		btn_logout = (Button) getActivity().findViewById(R.id.btn_logout);
		btn_changeData = (Button) getActivity().findViewById(R.id.btn_change);
		btn_login = (Button) getActivity().findViewById(R.id.btn_login);
		btn_createAccount = (Button) getActivity().findViewById(R.id.btn_createAccount);
		user_info = (LinearLayout) getActivity().findViewById(R.id.user_info);
		txt_name = (TextView) getActivity().findViewById(R.id.txt_name);
		txt_email = (TextView) getActivity().findViewById(R.id.txt_email);
	}
	
	public void setData() {
		dao = new UserDao(getActivity());
		u = dao.getUser();
		if(u!=null) {
			btn_login.setVisibility(View.GONE);
			btn_createAccount.setVisibility(View.GONE);
			user_info.setVisibility(View.VISIBLE);
			btn_logout.setVisibility(View.VISIBLE);
			btn_changeData.setVisibility(View.GONE);
			txt_name.setText(u.getName());
			txt_email.setText(u.getEmail());
		} else {
			user_info.setVisibility(View.GONE);
			btn_logout.setVisibility(View.GONE);
			btn_changeData.setVisibility(View.GONE);
			btn_login.setVisibility(View.VISIBLE);
			btn_createAccount.setVisibility(View.VISIBLE);
		}
	}
	
	public void setActions() {
		btn_logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file),
						Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putBoolean(WelcomeActivity.FIRST_TIME, true);
				editor.remove(MainActivity.EXTRA_USER_ID);
				editor.remove(MainActivity.EXTRA_USER_TYPE);
				editor.commit();
				
				dao.deleteUser();
				
				Intent intent = new Intent(getActivity(), WelcomeActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		
		btn_login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {		
				Intent intent = new Intent(SettingsFragment.this.getActivity(), LoginActivity.class);
				startActivity(intent);
				SettingsFragment.this.getActivity().finish();
			}
		});
		
		btn_createAccount.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(SettingsFragment.this.getActivity(), RegisterActivity.class);
				startActivity(intent);
				SettingsFragment.this.getActivity().finish();
			}
		});
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_settings, container,
				false);
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		linkUI();
		setData();
		setActions();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//		((MainActivity) activity).onSectionAttached(getArguments().getInt(
//				ARG_SECTION_NUMBER));
	}
}
