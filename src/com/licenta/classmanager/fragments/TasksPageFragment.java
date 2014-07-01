package com.licenta.classmanager.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.licenta.classmanager.R;
import com.licenta.classmanager.activities.TaskAddEditActivity;
import com.licenta.classmanager.activities.TaskDetailsActivity;
import com.licenta.classmanager.adapters.listadapters.TasksListAdapter;
import com.licenta.classmanager.dao.TasksDao;
import com.licenta.classmanager.holders.Flag;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Task;
import com.licenta.classmanager.services.DataService;
import com.licenta.classmanager.services.DaysCallback;
import com.licenta.classmanager.services.JsonParser;
import com.licenta.classmanager.services.NetworkWorker;
import com.licenta.classmanager.services.ServiceCallback;
import com.licenta.classmanager.services.SyncCallback;
import com.licenta.classmanager.services.TaskCallback;

import de.timroes.android.listview.EnhancedListView;
import de.timroes.android.listview.EnhancedListView.ListItemType;
import de.timroes.android.listview.EnhancedListView.OnDismissCallback;
import de.timroes.android.listview.EnhancedListView.UndoStyle;
import de.timroes.android.listview.EnhancedListView.Undoable;

public class TasksPageFragment extends BaseFragment {

	public static final String PAGER_COUNT = "PAGER_COUNT";
	public static final String USERID = "com.licenta.classmanager.USERID";

	private ArrayList<Task> tasks;
	private EnhancedListView elv_tasks;
	private TasksListAdapter tasksAdapter;
	private TasksDao dao;
	private LayoutInflater inflater;
	private int count;
	private int userid;
	private DataService dataService;
	private JsonParser jsonParser;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_tasks_page, container, false);
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
		dataService = new DataService(getActivity());
		jsonParser = new JsonParser(getActivity());
		Bundle args = getArguments();
		count = args.getInt(PAGER_COUNT);
		userid = args.getInt(USERID);
		tasks = new ArrayList<Task>();

		if (NetworkWorker.checkConnection(getActivity()) && !tasks_synced) {
			dataService.syncTasks(userid, new SyncCallback() {

				@Override
				public void onSuccess(JSONObject result) {
					Toast.makeText(getActivity(), "Tasks synced!", Toast.LENGTH_SHORT).show();
					tasks_synced = true;
					getTasks();
				}
			});
		} else {
			getTasks();
		}
	}

	private void getTasks() {

		dataService.getTasks(userid, new ServiceCallback(getActivity()) {

			@Override
			public void onSuccess(JSONObject result) {
				JSONArray json_tasks_data = result.optJSONArray("tasks_data");
				if (json_tasks_data.length() == 0)
					return;
				for (int i = 0; i < json_tasks_data.length(); i++) {
					JSONObject json_task_data = json_tasks_data.optJSONObject(i);
					Task t = jsonParser.getTaskFromJSON(json_task_data);
					tasks.add(t);
				}
				if (count == 1) {
					tasks = getIncompleteTasks(tasks);
					dao.putTasks(tasks);
					updateUI();
				} else {
					tasks = getCompleteTasks(tasks);
					dao.putTasks(tasks);
					updateUI();
				}
			}

			@Override
			public void onOffline() {
				tasks = dao.getTasks();
				if (count == 1) {
					tasks = getIncompleteTasks(tasks);
				} else {
					tasks = getCompleteTasks(tasks);
				}
				updateUI();
			}
		});
	}

	private void setActions() {
		elv_tasks.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), TaskDetailsActivity.class);
				intent.putExtra(TaskDetailsActivity.EXTRA_TASK, tasks.get(position));
				intent.putExtra(TaskDetailsActivity.EXTRA_TASK_POSITION, position);
				intent.putExtra(PAGER_COUNT, count);
				getParentFragment().startActivityForResult(intent, TaskDetailsActivity.request_code);
			}

		});

		elv_tasks.setDismissCallback(new OnDismissCallback() {

			@Override
			public Undoable onDismiss(EnhancedListView listView, int position) {
				final Task task = (Task) tasksAdapter.getItem(position);
//				dao.deleteTask(task);
				task.setDone(!task.isDone());
//				dao.putTask(task);
				tasksAdapter.remove(position);
				updateTask(task, position);
				TasksFragment f = (TasksFragment) getParentFragment();
				f.updateViewPager();
				tasks_synced = false;
				tasks_modified = true;
				return null;
			}
		});

		elv_tasks.enableSwipeToDismiss();
		elv_tasks.setUndoStyle(UndoStyle.COLLAPSED_POPUP);
	}

	private ArrayList<Task> getCompleteTasks(ArrayList<Task> tasks) {
		ArrayList<Task> complete_tasks = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			if (tasks.get(i).isDone())
				complete_tasks.add(tasks.get(i));
		}
		return complete_tasks;
	}

	private ArrayList<Task> getIncompleteTasks(ArrayList<Task> tasks) {
		ArrayList<Task> incomplete_tasks = new ArrayList<Task>();
		for (int i = 0; i < tasks.size(); i++) {
			if (!tasks.get(i).isDone())
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
		switch (requestCode) {
		case TaskDetailsActivity.request_code: {
			if (resultCode == Activity.RESULT_OK) {
				Task task = (Task) data.getSerializableExtra(TaskDetailsActivity.EXTRA_TASK);
				int position = data.getIntExtra(TaskDetailsActivity.EXTRA_TASK_POSITION, -1);
				if (count == data.getIntExtra(PAGER_COUNT, -1))
					updateTask(task, position);
			}
		}
			break;
		case TaskAddEditActivity.add_request_code: {
			if (resultCode == Activity.RESULT_OK) {
				Task task = (Task) data.getSerializableExtra(TaskAddEditActivity.EXTRA_TASK);
				addTask(task);
			}
		}
			break;
		}
	}

	public void addTask(final Task task) {
		
		if((count==1 && !task.isDone()) || (count == 2 && task.isDone())) {
			dataService.addTask(userid, task, new ServiceCallback(getActivity()) {

				@Override
				public void onSuccess(JSONObject result) {
					task.setId(result.optInt("taskid"));
					tasks_modified = true;
					dao.putTask(task);
					tasks.add(task);
					updateUI();
				}

				@Override
				public void onOffline() {
					tasks_modified = true;
					tasks_synced = false;
					task.setFlag(Flag.ADDED);
					dao.putTask(task);
					tasks.add(task);
					updateUI();
				}
			});
		}
	}

	public void updateTask(final Task task, final int position) {

		dataService.updateTask(task, new ServiceCallback(getParentFragment().getActivity()) {

			@Override
			public void onSuccess(JSONObject result) {
				tasks_modified = true;
				dao.deleteTask(task);
				dao.putTask(task);
//				tasks.set(position, task);
				updateUI();
			}

			@Override
			public void onOffline() {
				if (task.getFlag() != Flag.ADDED) {
					task.setFlag(Flag.MODIFIED);
				}
				dao.deleteTask(task);
				dao.putTask(task);
				tasks.set(position, task);
				tasks_modified = true;
				tasks_synced = false;
				updateUI();
			}
		});
	}

	public void deleteTask(final Task task) {

		dataService.deleteTask(task.getId(), new ServiceCallback(getActivity()) {

			@Override
			public void onSuccess(JSONObject result) {
				dao.deleteTask(task);
				tasks.remove(task);
				tasks_modified = true;
				updateUI();
			}

			@Override
			public void onOffline() {
				int position = tasks.indexOf(task);
				if (tasks.get(position).getFlag() == Flag.ADDED) {
					dao.deleteTask(tasks.get(position));
				} else {
					tasks.get(position).setFlag(Flag.DELETED);
					dao.putTask(tasks.get(position));
				}
				tasks_modified = true;
				tasks_synced = false;
				tasks.remove(position);
				updateUI();
			}
		});

	}

	public void updateUI() {
		Task[] temp = tasks.toArray(new Task[tasks.size()]);
		Arrays.sort(temp);
		tasks = new ArrayList<Task>(Arrays.asList(temp));
		tasksAdapter = new TasksListAdapter(inflater, elv_tasks, tasks);
		elv_tasks.setAdapter(tasksAdapter);
		elv_tasks.setType(ListItemType.Task);
	}

	// elv_tasks.setDismissCallback(new EnhancedListView.OnDismissCallback() {
	//
	// @Override
	// public EnhancedListView.Undoable onDismiss(EnhancedListView listView,
	// final int position) {
	//
	// final String item = (String) tasksAdapter.getItem(position);
	// tasksAdapter.remove(position);
	// return new EnhancedListView.Undoable() {
	//
	// @Override
	// public void undo() {
	// tasksAdapter.insert(position, item);
	// }
	//
	// };
	// }
	// });

}
