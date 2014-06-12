package com.licenta.classmanager.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.licenta.classmanager.R;
import com.licenta.classmanager.activities.WelcomeActivity;
import com.licenta.classmanager.holders.User;

public class SettingsFragment extends Fragment {

//	private static final String ARG_SECTION_NUMBER = "section_number";

	private Button btn_logout;
	private Button btn_test;
	private SharedPreferences sharedPref;

	public SettingsFragment() {

	}
	
//	public void setData(int sectionNumber) {
//		Bundle args = new Bundle();
//		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//		this.setArguments(args);
//	}
	
	public void linkUI() {
		btn_logout = (Button) getActivity().findViewById(R.id.btn_logout);
		btn_test = (Button) getActivity().findViewById(R.id.btn_test);
	}
	
	public void setData() {
		
	}
	
	public void setActions() {
		btn_logout.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sharedPref = getActivity().getSharedPreferences(getString(R.string.preference_file),
						Context.MODE_PRIVATE);
				SharedPreferences.Editor editor = sharedPref.edit();
				editor.putBoolean(WelcomeActivity.FIRST_TIME, true);
				editor.commit();
				Intent intent = new Intent(getActivity(), WelcomeActivity.class);
				startActivity(intent);
				getActivity().finish();
			}
		});
		
		btn_test.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				User u = new User(0, 2, "Local json user", "email@email.com", "pass2");
				GsonBuilder builder = new GsonBuilder();
		        Gson gson = builder.create();
		        Toast.makeText(getActivity(), gson.toJson(u), Toast.LENGTH_LONG).show();
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
