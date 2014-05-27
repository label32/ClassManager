package com.licenta.classmanager.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.licenta.classmanager.R;
import com.licenta.classmanager.activities.AddTaskActivity;
import com.licenta.classmanager.activities.MainActivity;

public class DashboardFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this
	 * fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	/**
	 * Returns a new instance of this fragment for the given section number.
	 */
	
	public void setData(int sectionNumber) {
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		this.setArguments(args);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		TextView textView = (TextView) rootView
				.findViewById(R.id.section_label);
		textView.setText(Integer.toString(getArguments().getInt(
				ARG_SECTION_NUMBER)));
		setHasOptionsMenu(true);
		return rootView;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
	   inflater.inflate(R.menu.dashboard, menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch(id) {
		case R.id.action_add_task:  {
				Intent addTaskIntent = new Intent(getActivity(), AddTaskActivity.class);
				startActivityForResult(addTaskIntent, AddTaskActivity.request_code);
			} break;
		case R.id.action_add_note: {
			Toast.makeText(getActivity(), "Adding note...", Toast.LENGTH_SHORT).show();
			} break;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
		case AddTaskActivity.request_code: {
				// do stuff
			} break;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(
				ARG_SECTION_NUMBER));
	}
}