package com.licenta.classmanager.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.licenta.classmanager.R;
import com.licenta.classmanager.adapters.listadapters.ClassesListAdapter;
import com.licenta.classmanager.holders.Announcement;
import com.licenta.classmanager.utils.Utils;

import de.timroes.android.listview.EnhancedListView;

public class TasksListFragment extends Fragment {

	public static final String PAGER_COUNT = "PAGER_COUNT";

	private EnhancedListView elv_tasks;
	private ClassesListAdapter tasksAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tasks_list, container, false);
//		Bundle args = getArguments();
//		((TextView) rootView.findViewById(R.id.txt_pagerCount)).setText(Integer.toString(args.getInt(PAGER_COUNT)));
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
		elv_tasks = (EnhancedListView) getActivity().findViewById(R.id.elv_tasks);
		tasksAdapter = new ClassesListAdapter(getActivity(), elv_tasks);
		tasksAdapter.resetItems();
		elv_tasks.setAdapter(tasksAdapter);

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
