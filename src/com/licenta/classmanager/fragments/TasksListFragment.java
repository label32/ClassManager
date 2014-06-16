package com.licenta.classmanager.fragments;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.licenta.classmanager.R;
import com.licenta.classmanager.adapters.listadapters.TasksListAdapter;
import com.licenta.classmanager.holders.Task;

import de.timroes.android.listview.EnhancedListView;

public class TasksListFragment extends Fragment {

	public static final String PAGER_COUNT = "PAGER_COUNT";

	private ArrayList<Task> tasks;
	private EnhancedListView elv_tasks;
	private TasksListAdapter tasksAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tasks_list, container, false);
		Bundle args = getArguments();
		int count = args.getInt(PAGER_COUNT);
//		((TextView) rootView.findViewById(R.id.txt_pagerCount)).setText(Integer.toString(args.getInt(PAGER_COUNT)));
		elv_tasks = (EnhancedListView) rootView.findViewById(R.id.elv_tasks);
		tasks = new ArrayList<Task>();
		tasks.add(new Task("Task 1"));
		tasks.add(new Task("Task 2"));
		if(count==1) {
			tasks.add(new Task("Task 3"));
		}
		tasksAdapter = new TasksListAdapter(inflater, elv_tasks,tasks);
//		tasksAdapter.resetItems();
		elv_tasks.setAdapter(tasksAdapter);
		return rootView;
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		

//		elv_tasks.setDismissCallback(new EnhancedListView.OnDismissCallback() {
//
//			@Override
//			public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
//
//				final String item = (String) tasksAdapter.getItem(position);
//				tasksAdapter.remove(position);
//				return new EnhancedListView.Undoable() {
//
//					@Override
//					public void undo() {
//						tasksAdapter.insert(position, item);
//					}
//
//				};
//			}
//		});

	}

}
