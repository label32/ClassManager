package com.licenta.classmanager.fragments;

import java.util.ArrayList;
import java.util.List;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.licenta.classmanager.R;
import com.licenta.classmanager.adapters.CustomSpinnerAdapter;
import com.licenta.classmanager.adapters.TasksPagerAdapter;
import com.licenta.classmanager.holders.Task;

public class TasksFragment extends BaseFragment {

	public static final String ARG_SECTION_NUMBER = "section_number";
	public static final String USERID = "com.licenta.classmanager.USERID";	

	private ArrayList<Task> tasks;
	private TasksPagerAdapter mTasksPagerAdapter;
	private ViewPager mViewPager;

	public TasksFragment() {

	}

//	public void setData(int sectionNumber) {
//		Bundle args = new Bundle();
//		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//		this.setArguments(args);
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tasks, container, false);
		setHasOptionsMenu(true);
		return rootView;
	}
	
	public void updateViewPager() {
		mViewPager.getAdapter().notifyDataSetChanged();
		mViewPager.invalidate();
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mTasksPagerAdapter = new TasksPagerAdapter(getChildFragmentManager(), getActivity(), getArguments().getInt(USERID, -1));
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
//				Toast.makeText(getActivity(), "spinner position = " + itemPosition, Toast.LENGTH_SHORT).show();
				return false;
			}
		};

		getActivity().getActionBar().setListNavigationCallbacks(mCustomSpinnerAdapter, mSpinnerOnNavigationListener);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		//((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}
}
