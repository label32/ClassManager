package com.licenta.classmanager.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.licenta.classmanager.R;
import com.licenta.classmanager.adapters.listadapters.LessonsListAdapter;
import com.licenta.classmanager.adapters.listadapters.TasksListAdapter;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Task;

import de.timroes.android.listview.EnhancedListView;
import de.timroes.android.listview.EnhancedListView.ListItemType;

public class DayviewPageFragment extends Fragment {

	public static final String PAGER_COUNT = "PAGER_COUNT";
	public static final String TASKS = "com.licenta.classmanager.TASKS";
	public static final String CLASSES = "com.licenta.classmanager.CLASSES";
	
	private ArrayList<Task> tasks;
	private ArrayList<Lesson> classes;
	private TasksListAdapter tasksAdapter;
	private LessonsListAdapter classesAdapter; 
	
	private LayoutInflater inflater;
	private EnhancedListView elv_tasks;
	private EnhancedListView elv_classes;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dayview_page, container, false);
		this.inflater = inflater;
		setHasOptionsMenu(true);
		linkUI(rootView);
		setData();
		setActions();
		return rootView;
	}
	
	private void linkUI(View rootView) {
		elv_tasks = (EnhancedListView) rootView.findViewById(R.id.elv_tasks);
		elv_classes = (EnhancedListView) rootView.findViewById(R.id.elv_classes);
	}
	
	private void setData() {
		Bundle b = getArguments();
		tasks = (ArrayList<Task>) b.getSerializable(TASKS);
		classes = (ArrayList<Lesson>) b.getSerializable(CLASSES);
		tasksAdapter = new TasksListAdapter(inflater, elv_tasks, tasks);
		classesAdapter = new LessonsListAdapter(getActivity(), elv_classes, classes);
		elv_tasks.setAdapter(tasksAdapter);
		elv_classes.setAdapter(classesAdapter);
		elv_tasks.setType(ListItemType.Task);
	}
	
	private void setActions() {
		
	}

}
