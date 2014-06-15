package com.licenta.classmanager.fragments;

import android.app.ActionBar;
import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.licenta.classmanager.R;
import com.licenta.classmanager.activities.TaskAddEditActivity;
import com.licenta.classmanager.activities.MainActivity;
import com.licenta.classmanager.adapters.CustomSpinnerAdapter;
import com.licenta.classmanager.adapters.TasksPagerAdapter;

public class TasksFragment extends Fragment {
	/**
	 * The fragment argument representing the section number for this fragment.
	 */
	private static final String ARG_SECTION_NUMBER = "section_number";

	private TasksPagerAdapter mTasksPagerAdapter;
	private ViewPager mViewPager;

	public TasksFragment() {

	}

	public void setData(int sectionNumber) {
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		this.setArguments(args);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
		setHasOptionsMenu(true);
		
		
		return rootView;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mTasksPagerAdapter = new TasksPagerAdapter(getChildFragmentManager(), getActivity());
		mViewPager = (ViewPager) getActivity().findViewById(R.id.pager);
		mViewPager.setAdapter(mTasksPagerAdapter);

	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.tasks, menu);

		getActivity().getActionBar().setDisplayShowTitleEnabled(false);
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		CustomSpinnerAdapter mCustomSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.spinner_layout,
				getResources().getStringArray(R.array.tasks_spinner_items), "Tasks");
		mCustomSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);

		OnNavigationListener mSpinnerOnNavigationListener = new OnNavigationListener() {

			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				Toast.makeText(getActivity(), "spinner position = " + itemPosition, Toast.LENGTH_SHORT).show();
				return false;
			}
		};

		getActivity().getActionBar().setListNavigationCallbacks(mCustomSpinnerAdapter, mSpinnerOnNavigationListener);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_add_task) {
			Intent addTaskIntent = new Intent(getActivity(), TaskAddEditActivity.class);
			startActivityForResult(addTaskIntent, TaskAddEditActivity.add_request_code);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == TaskAddEditActivity.add_request_code) {
			// do stuff...
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}
}
