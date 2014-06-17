package com.licenta.classmanager.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.licenta.classmanager.R;
import com.licenta.classmanager.activities.ClassAddEditActivity;
import com.licenta.classmanager.activities.ClassDetailsActivity;
import com.licenta.classmanager.activities.TaskAddEditActivity;
import com.licenta.classmanager.activities.TaskDetailsActivity;
import com.licenta.classmanager.adapters.listadapters.ClassesListAdapter;
import com.licenta.classmanager.adapters.listadapters.TasksListAdapter;
import com.licenta.classmanager.dao.TasksDao;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Task;

import de.timroes.android.listview.EnhancedListView;

public class TasksListFragment extends Fragment {

	public static final String PAGER_COUNT = "PAGER_COUNT";

	private ArrayList<Task> tasks;
	private EnhancedListView elv_tasks;
	private TasksListAdapter tasksAdapter;
	private TasksDao dao;
	private LayoutInflater inflater;
	private int count;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tasks_list, container, false);
		this.inflater = inflater;
		setHasOptionsMenu(true);
		linkUI(rootView);
		setData();
		setActions();
		return rootView;
	}
	
	private void linkUI(View rootView) {
		elv_tasks = (EnhancedListView) rootView.findViewById(R.id.elv_tasks);
	}
	
	private void setData() {
		dao = new TasksDao(getActivity());
		Bundle args = getArguments();
		count = args.getInt(PAGER_COUNT);
		tasks = dao.getTasks();
		if(count==1) {
			tasks = getIncompleteTasks(tasks);
		} else {
			tasks = getCompleteTasks(tasks);
		}
		
		tasksAdapter = new TasksListAdapter(inflater, elv_tasks, tasks);
		elv_tasks.setAdapter(tasksAdapter);
	}
	
	private void setActions() {
		elv_tasks.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), TaskDetailsActivity.class);
				intent.putExtra(TaskDetailsActivity.EXTRA_TASK, tasks.get(position));
				intent.putExtra(TaskDetailsActivity.EXTRA_TASK_POSITION, position);
				startActivityForResult(intent, TaskDetailsActivity.request_code);
			}
			
		});
	}
	
	private ArrayList<Task> getCompleteTasks(ArrayList<Task> tasks) {
		ArrayList<Task> complete_tasks = new ArrayList<Task>();
		for(int i=0; i<tasks.size(); i++) {
			if(tasks.get(i).isDone())
				complete_tasks.add(tasks.get(i));
		}
		return complete_tasks;
	}
	
	private ArrayList<Task> getIncompleteTasks(ArrayList<Task> tasks) {
		ArrayList<Task> incomplete_tasks = new ArrayList<Task>();
		for(int i=0; i<tasks.size(); i++) {
			if(!tasks.get(i).isDone())
				incomplete_tasks.add(tasks.get(i));
		}
		return incomplete_tasks;
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
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_add_task) {
			Intent addTaskIntent = new Intent(getActivity(), TaskAddEditActivity.class);
			getParentFragment().startActivityForResult(addTaskIntent, TaskAddEditActivity.add_request_code);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
		case TaskDetailsActivity.request_code: {
			if(resultCode == Activity.RESULT_OK) {
				Task task = (Task) data.getSerializableExtra(TaskDetailsActivity.EXTRA_TASK);
				int position = data.getIntExtra(TaskDetailsActivity.EXTRA_TASK_POSITION, -1);
				updateTask(task, position);
			}
		} break;
		case TaskAddEditActivity.add_request_code: {
			if(resultCode == Activity.RESULT_OK) {
				Task task = (Task) data.getSerializableExtra(TaskAddEditActivity.EXTRA_TASK);
				addTask(task);
			}
		} break;
		}
	}
	
	public void addTask(Task task) {
		dao.putTask(task);
		if(count==1 && !task.isDone()) {
			tasks.add(task);
		} else if(count == 2 && task.isDone()){
			tasks.add(task);
		}
		updateUI();
	}
	
	public void updateTask(Task task, int position) {
		dao.deleteTask(task);
		dao.putTask(task);
		tasks.set(position, task);
		updateUI();
	}
	
	public void deleteTask(Task task) {
		dao.deleteTask(task);
		tasks.remove(task);
		updateUI();
	}
	
	public void updateUI() {
		Task[] temp = tasks.toArray(new Task[tasks.size()]);
		Arrays.sort(temp);
		tasks = new ArrayList<Task>(Arrays.asList(temp));
		tasksAdapter = new TasksListAdapter(inflater, elv_tasks, tasks);
		elv_tasks.setAdapter(tasksAdapter);
	}


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
