package com.licenta.classmanager.fragments;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.licenta.classmanager.R;
import com.licenta.classmanager.adapters.listadapters.LessonsListAdapter;
import com.licenta.classmanager.adapters.listadapters.TasksListAdapter;
import com.licenta.classmanager.dao.ClassesDao;
import com.licenta.classmanager.dao.TasksDao;
import com.licenta.classmanager.holders.Day;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Task;
import com.licenta.classmanager.utils.Utils;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import de.timroes.android.listview.EnhancedListView;
import de.timroes.android.listview.EnhancedListView.ListItemType;

public class MonthviewFragment extends Fragment {

	private CaldroidFragment caldroidFragment;
	private final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy");
	private ArrayList<Task> tasks, tasks_to_display;
	private ArrayList<Lesson> classes, classes_to_display;
	private ClassesDao classes_dao;
	private TasksDao tasks_dao;
	private TasksListAdapter tasksAdapter;
	private LessonsListAdapter classesAdapter;
	private Date current_date;
	
	private LayoutInflater inflater;
	private EnhancedListView elv_tasks, elv_classes;
	private TextView txt_date;
	private View rootView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_monthview, container, false);
		setHasOptionsMenu(true);
		this.rootView = rootView;
		this.inflater = inflater;
		linkUI(rootView);
		setData(savedInstanceState);
		setActions();
		return rootView;
	}

	private void linkUI(View rootView) {
		txt_date = (TextView) rootView.findViewById(R.id.txt_date);
		elv_tasks = (EnhancedListView) rootView.findViewById(R.id.elv_tasks);
		elv_classes = (EnhancedListView) rootView.findViewById(R.id.elv_classes);
	}

	private void setData(Bundle savedInstanceState) {
		classes_dao = new ClassesDao(getActivity());
		tasks_dao = new TasksDao(getActivity());
		caldroidFragment = new CaldroidFragment();

		tasks = tasks_dao.getTasks();
		classes = classes_dao.getClasses();

		if (savedInstanceState != null) {
			caldroidFragment.restoreStatesFromKey(savedInstanceState, "CALDROID_SAVED_STATE");
		} else {
			Bundle args = new Bundle();
			Calendar cal = Calendar.getInstance();
			args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
			args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
			args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
			args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);
			args.putInt(CaldroidFragment.START_DAY_OF_WEEK, CaldroidFragment.MONDAY);
			caldroidFragment.setArguments(args);
		}

		FragmentTransaction t = getChildFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		t.commit();
		
		current_date = new Date();
		updateUI(current_date);
		elv_tasks.setType(ListItemType.Task);
		elv_classes.setType(ListItemType.Simple);
		Utils.setListViewHeightBasedOnChildren2(elv_tasks);
		Utils.setListViewHeightBasedOnChildren2(elv_classes);
	}

	private void setActions() {
		caldroidFragment.setCaldroidListener(new CaldroidListener() {

			@Override
			public void onSelectDate(Date date, View view) {
				updateUI(date);
			}
		});
		caldroidFragment.getCaldroidListener().onSelectDate(current_date, null);
	}

	private void updateUI(Date date) {
		txt_date.setText(formatter.format(date));
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
		int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		int month = cal.get(Calendar.MONTH)+1;
		int year = cal.get(Calendar.YEAR);
		
		if(dayOfWeek==1)
			dayOfWeek = 7;
		else
			dayOfWeek--;

		tasks_to_display = new ArrayList<Task>();
		Task task;
		for (int i = 0; i < tasks.size(); i++) {
			task = tasks.get(i);
			if (task.getDeadline().getDay() == dayOfMonth && task.getDeadline().getMonth() == month
					&& task.getDeadline().getYear() == year) {
				tasks_to_display.add(task);
			}
		}
		
		classes_to_display = new ArrayList<Lesson>();
		Lesson lesson;
		ArrayList<Day> days;
		for(int i=0; i<classes.size(); i++) {
			lesson = classes.get(i);
			days = lesson.getDays();
			for(int j=0; j<days.size(); j++) 
				if((days.get(j).getCode()) == dayOfWeek)
					classes_to_display.add(lesson);
		}
		
		tasksAdapter = new TasksListAdapter(inflater, elv_tasks, tasks_to_display);
		elv_tasks.setAdapter(tasksAdapter);
		Utils.setListViewHeightBasedOnChildren2(elv_tasks);
		
		classesAdapter = new LessonsListAdapter(getActivity(), elv_classes, classes_to_display);
		elv_classes.setAdapter(classesAdapter);
		Utils.setListViewHeightBasedOnChildren2(elv_classes);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.calendar, menu);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		if (caldroidFragment != null) {
			caldroidFragment.saveStatesToKey(outState, "CALDROID_SAVED_STATE");
		}
	}
}
