package com.licenta.classmanager.fragments;

import java.util.ArrayList;

import android.os.Bundle;
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
import com.licenta.classmanager.holders.Flag;
import com.licenta.classmanager.holders.Task;

public class DayviewFragment extends BaseFragment {

	private static final String ARG_SECTION_NUMBER = "section_number";
	
	private ViewPager mViewPager;
	private DayviewPagerAdapter mDayviewPagerAdapter;
	private TasksDao tasksDao;
	private ArrayList<Task> tasks;
	private ClassesDao classesDao;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dayview, container, false);
		setHasOptionsMenu(true);
        tasksDao = new TasksDao(getActivity());
        classesDao = new ClassesDao(getActivity());
        tasks = tasksDao.getTasks();
        filterTasks();
        mDayviewPagerAdapter = new DayviewPagerAdapter(getChildFragmentManager(), tasks, classesDao.getClasses());
		mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
		mViewPager.setAdapter(mDayviewPagerAdapter);

		return rootView;
	}
	
	private void filterTasks() {
		int diff = 0;
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i - diff).isDone()) {
				tasks.remove(i - diff);
				diff++;
			}
		}
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
