package com.licenta.classmanager.fragments;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.licenta.classmanager.R;
import com.licenta.classmanager.activities.NoteAddEditActivity;
import com.licenta.classmanager.activities.TaskAddEditActivity;
import com.licenta.classmanager.adapters.listadapters.AnnouncementsListAdapter;
import com.licenta.classmanager.adapters.listadapters.LessonsListAdapter;
import com.licenta.classmanager.adapters.listadapters.TasksListAdapter;
import com.licenta.classmanager.adapters.listadapters.UpcomingListAdapter;
import com.licenta.classmanager.dao.AnnouncementsDao;
import com.licenta.classmanager.dao.ClassesDao;
import com.licenta.classmanager.dao.TasksDao;
import com.licenta.classmanager.holders.Announcement;
import com.licenta.classmanager.holders.Day;
import com.licenta.classmanager.holders.Flag;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Task;
import com.licenta.classmanager.services.DataService;
import com.licenta.classmanager.services.JsonParser;
import com.licenta.classmanager.services.ServiceCallback;
import com.licenta.classmanager.utils.Utils;

import de.timroes.android.listview.EnhancedListView;
import de.timroes.android.listview.EnhancedListView.UndoStyle;

public class DashboardFragment extends BaseFragment {

	public static final String EXTRA_CLASSES = "com.licenta.classmanager.CLASSES";

	public static final int note_add_request_code = 104;
	public static final int task_add_request_code = 101;

	private TextView txt_announcements;
	private EnhancedListView elv_announcements;
	private EnhancedListView elv_lessons;
	private EnhancedListView elv_upcoming;
	private AnnouncementsListAdapter announcementsAdapter;
	private LessonsListAdapter lessonsAdapter;
	private TasksListAdapter upcomingAdapter;
	private ArrayList<Announcement> announcements;
	private ArrayList<Lesson> classes;
	private ArrayList<Lesson> todays_classes;
	private ArrayList<Task> tasks;
	private ArrayList<Task> upcoming;
	private DataService dataService;
	private JsonParser jsonParser;
	private AnnouncementsDao announcementsDao;
	private ClassesDao classesDao;
	private TasksDao tasksDao;
	private LayoutInflater inflater;
	public static int userid;

	// public void setArguments(Bundle args) {
	// this.setArguments(args);
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
		setHasOptionsMenu(true);
		this.inflater = inflater;
		linkUI(rootView);
		setData();
		setActions();
		return rootView;
	}

	public void linkUI(View rootView) {
		elv_announcements = (EnhancedListView) rootView.findViewById(R.id.elv_announcements);
		elv_lessons = (EnhancedListView) rootView.findViewById(R.id.elv_lessons);
		elv_upcoming = (EnhancedListView) rootView.findViewById(R.id.elv_upcoming);
		txt_announcements = (TextView) rootView.findViewById(R.id.txt_announcements);
	}

	public void setData() {
		announcementsDao = new AnnouncementsDao(getActivity());
		dataService = new DataService(getActivity());
		jsonParser = new JsonParser(getActivity());
		announcements = new ArrayList<Announcement>();

		dataService.getAnnouncements(userid, new ServiceCallback(getActivity()) {

			@Override
			public void onSuccess(JSONObject result) {
				JSONArray json_announcements_data = result.optJSONArray("announcements_data");
				if (json_announcements_data.length() == 0)
					return;
				for (int i = 0; i < json_announcements_data.length(); i++) {
					JSONObject json_announcement_data = json_announcements_data.optJSONObject(i);
					Announcement a = jsonParser.getAnnouncementFromJSON(json_announcement_data);
					announcements.add(a);
					announcementsDao.putAnnouncement(a);
				}
				if (announcements.size() == 0) {
					txt_announcements.setVisibility(View.GONE);
				}
				updateAnnouncementsList();
				getLessons();
			}

			@Override
			public void onOffline() {
				announcements = announcementsDao.getAnnouncements();
				int diff = 0;
				for (int i = 0; i < announcements.size(); i++) {
					if (announcements.get(i - diff).getFlag() == Flag.DELETED) {
						announcements.remove(i - diff);
						diff++;
					}
				}
				updateAnnouncementsList();
				getLessons();
			}
		});

	}

	public void getLessons() {
		classes = new ArrayList<Lesson>();
		classesDao = new ClassesDao(getActivity());

		dataService.getUserClasses(userid, new ServiceCallback(getActivity()) {

			@Override
			public void onSuccess(JSONObject result) {
				JSONArray json_classes = result.optJSONArray("classes");
				JSONArray json_days = result.optJSONArray("days");
				if (json_classes.length() == 0)
					return;
				for (int i = 0; i < json_classes.length(); i++) {
					JSONObject json_class = json_classes.optJSONObject(i);
					JSONArray json_class_days = json_days.optJSONArray(i);
					Lesson lesson = jsonParser.getLessonFromJSON(json_class);
					ArrayList<Day> class_days = jsonParser.getDaysFromJSON(json_class_days);
					if (lesson != null) {
						lesson.setDays(class_days);
						classes.add(lesson);
						classesDao.putClass(lesson);
					}
				}
				todays_classes = getTodaysClasses();
				updateLessonsList();
				getTasks();
			}

			@Override
			public void onOffline() {
				classes = classesDao.getClasses();
				int diff = 0;
				for (int i = 0; i < classes.size(); i++) {
					if (classes.get(i - diff).getFlag() == Flag.DELETED) {
						classes.remove(i - diff);
						diff++;
					}
				}
				todays_classes = getTodaysClasses();
				updateLessonsList();
				getTasks();
			}
		});
	}

	public void getTasks() {
		tasks = new ArrayList<Task>();
		tasksDao = new TasksDao(getActivity());

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
				tasksDao.putTasks(tasks);
				getUpcoming();
			}

			@Override
			public void onOffline() {
				tasks = tasksDao.getTasks();
				getUpcoming();
			}
		});

	}

	public void getUpcoming() {
		upcoming = new ArrayList<Task>();
		Calendar cal;
		for (int i = 0; i < tasks.size(); i++) {
			Task t = tasks.get(i);
			cal = Calendar.getInstance();
			int day = cal.get(Calendar.DAY_OF_MONTH);
			int month = cal.get(Calendar.MONTH) + 1;
			int year = cal.get(Calendar.YEAR);
			if (t.getDeadline().getYear() == year) {
				if (day > 15) {
					// day 16..31
					if (t.getDeadline().getMonth() == month && t.getDeadline().getDay() <= 31) {
						upcoming.add(t);
					} else {
						if (t.getDeadline().getMonth() == (month + 1) && t.getDeadline().getDay() <= 15) {
							upcoming.add(t);
						}
					}
				} else {
					// day 1..15
					if (t.getDeadline().getMonth() == month && t.getDeadline().getDay() <= day + 15) {
						upcoming.add(t);
					}
				}
			}
		}
		updateUpcomingList();
	}

	public void updateLessonsList() {
		Lesson[] temp = todays_classes.toArray(new Lesson[todays_classes.size()]);
		Arrays.sort(temp);
		todays_classes = new ArrayList<Lesson>(Arrays.asList(temp));
		lessonsAdapter = new LessonsListAdapter(getActivity(), elv_lessons, todays_classes);
		elv_lessons.setAdapter(lessonsAdapter);
		elv_lessons.setScrollContainer(false);
		Utils.setListViewHeightBasedOnChildren3(elv_lessons);
	}

	public void updateAnnouncementsList() {
		Announcement[] temp = announcements.toArray(new Announcement[announcements.size()]);
		Arrays.sort(temp);
		announcements = new ArrayList<Announcement>(Arrays.asList(temp));
		announcementsAdapter = new AnnouncementsListAdapter(getActivity(), elv_announcements, announcements);
		elv_announcements.setAdapter(announcementsAdapter);
		elv_announcements.setScrollContainer(false);
		Utils.setListViewHeightBasedOnChildren(elv_announcements, true);
	}

	public void updateUpcomingList() {
		upcomingAdapter = new TasksListAdapter(inflater, elv_upcoming, upcoming);
		elv_upcoming.setAdapter(upcomingAdapter);
		elv_upcoming.setScrollContainer(false);
		Utils.setListViewHeightBasedOnChildren(elv_upcoming, true);
	}

	public void setActions() {
		elv_announcements.setDismissCallback(new EnhancedListView.OnDismissCallback() {

			@Override
			public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {

				final Announcement item = (Announcement) announcementsAdapter.getItem(position);
				announcementsAdapter.remove(position);
				Utils.setListViewHeightBasedOnChildren(elv_announcements, true);
				return new EnhancedListView.Undoable() {

					@Override
					public void undo() {
						announcementsAdapter.insert(position, item);
						Utils.setListViewHeightBasedOnChildren(elv_announcements, true);
					}

				};
			}
		});

		elv_announcements.enableSwipeToDismiss();
		elv_announcements.setUndoStyle(UndoStyle.MULTILEVEL_POPUP);
	}

	public ArrayList<Lesson> getTodaysClasses() {
		ArrayList<Lesson> c = new ArrayList<Lesson>();
		ArrayList<Day> days;

		Calendar cal = Calendar.getInstance();
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
		if (day_of_week == 1)
			day_of_week = 7;
		else
			day_of_week--;

		for (int i = 0; i < classes.size(); i++) {
			days = classes.get(i).getDays();
			for (int j = 0; j < days.size(); j++)
				if (day_of_week == days.get(j).getCode())
					c.add(classes.get(i));
		}
		return c;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.dashboard, menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
//		case R.id.action_add_task: {
//			Intent addTaskIntent = new Intent(getActivity(), TaskAddEditActivity.class);
//			getActivity().startActivityForResult(addTaskIntent, task_add_request_code);
//		}
//			break;
//		case R.id.action_add_note: {
//			Intent addNoteIntent = new Intent(getActivity(), NoteAddEditActivity.class);
//			getActivity().startActivityForResult(addNoteIntent, note_add_request_code);
//		}
//			break;
		case R.id.action_refresh: {
			setData();			
		} break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// ((DashboardActivity)
		// activity).onSectionAttached(getArguments().getInt(EXTRA_CLASSES));
	}
}