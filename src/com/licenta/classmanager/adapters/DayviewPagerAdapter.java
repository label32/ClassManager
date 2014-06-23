package com.licenta.classmanager.adapters;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.licenta.classmanager.fragments.DayviewPageFragment;
import com.licenta.classmanager.holders.Date;
import com.licenta.classmanager.holders.Day;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Task;

public class DayviewPagerAdapter extends FragmentPagerAdapter {

	private ArrayList<Task> tasks;
	private ArrayList<Lesson> classes;
	private Calendar c;

	public DayviewPagerAdapter(FragmentManager fm, ArrayList<Task> tasks, ArrayList<Lesson> classes) {
		super(fm);
		this.tasks = tasks;
		this.classes = classes;
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = new DayviewPageFragment();
		Bundle args = new Bundle();
		args.putInt(DayviewPageFragment.PAGER_COUNT, i + 1);
		args.putSerializable(DayviewPageFragment.TASKS, getCurrentTasks(i));
		args.putSerializable(DayviewPageFragment.CLASSES, getCurrentClasses(i));
		fragment.setArguments(args);
		return fragment;
	}
	
	public ArrayList<Lesson> getCurrentClasses(int count) {
		ArrayList<Lesson> current_classes = new ArrayList<Lesson>();
		ArrayList<Day> days;
		for(int i=0; i<classes.size(); i++) {
			days = classes.get(i).getDays();
			for(int j=0; j<days.size(); j++) 
				if((count+1)==days.get(j).getCode())
					current_classes.add(classes.get(i));
		}
		return current_classes;
	}
	
	public ArrayList<Task> getCurrentTasks(int count) {
		ArrayList<Task> current_tasks = new ArrayList<Task>();
		Date deadline;
		c = Calendar.getInstance();
		for (int i = 0; i < tasks.size(); i++) {
			deadline = tasks.get(i).getDeadline();
			int current_week = c.get(Calendar.WEEK_OF_YEAR);
			int current_year = c.get(Calendar.YEAR);
			c.set(deadline.getYear(), deadline.getMonth() - 1, deadline.getDay());
			int deadline_week = c.get(Calendar.WEEK_OF_YEAR);
			int deadline_day_of_week = c.get(Calendar.DAY_OF_WEEK);
			if (current_week == deadline_week && current_year == deadline.getYear()) {
				if(count == deadline_day_of_week-2) {
					current_tasks.add(tasks.get(i));
				}
			}
		}
		return current_tasks;
	}

	@Override
	public int getCount() {
		return 7;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		CharSequence m = "";
		switch (position) {
		case 0: {
			m = "Monday";
		}
			break;
		case 1: {
			m = "Tuesday";
		}
			break;
		case 2: {
			m = "Wednesday";
		}
			break;
		case 3: {
			m = "Thursday";
		}
			break;
		case 4: {
			m = "Friday";
		}
			break;
		case 5: {
			m = "Saturday";
		}
			break;
		case 6: {
			m = "Sunday";
		}
			break;
		}
		return m;
	}
}
