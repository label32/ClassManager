package com.licenta.classmanager.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.licenta.classmanager.R;
import com.licenta.classmanager.adapters.DayviewPagerAdapter;
import com.licenta.classmanager.dao.ClassesDao;
import com.licenta.classmanager.dao.TasksDao;

public class DayviewFragment extends BaseFragment {

	private static final String ARG_SECTION_NUMBER = "section_number";
	
	private ViewPager mViewPager;
	private DayviewPagerAdapter mDayviewPagerAdapter;
	private TasksDao tasksDao;
	private ClassesDao classesDao;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dayview, container, false);
		setHasOptionsMenu(true);
        tasksDao = new TasksDao(getActivity());
        classesDao = new ClassesDao(getActivity());
        mDayviewPagerAdapter = new DayviewPagerAdapter(getChildFragmentManager(), tasksDao.getTasks(), classesDao.getClasses());
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(mDayviewPagerAdapter);

		return rootView;
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.calendar, menu);
	}
	
	public void setData(int sectionNumber) {
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		this.setArguments(args);
	}
}
