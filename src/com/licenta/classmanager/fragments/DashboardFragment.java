package com.licenta.classmanager.fragments;

import java.util.ArrayList;
import java.util.Calendar;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.licenta.classmanager.adapters.listadapters.UpcomingListAdapter;
import com.licenta.classmanager.dao.ClassesDao;
import com.licenta.classmanager.holders.Announcement;
import com.licenta.classmanager.holders.Date;
import com.licenta.classmanager.holders.Day;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.utils.Utils;

import de.timroes.android.listview.EnhancedListView;
import de.timroes.android.listview.EnhancedListView.UndoStyle;

public class DashboardFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";

	public static final int note_add_request_code = 104;
	public static final int task_add_request_code = 101;
	
	private TextView txt_announcements;
	private EnhancedListView elv_announcements;
	private EnhancedListView elv_lessons;
	private EnhancedListView elv_upcoming;
	private LayoutInflater inflater;
	private AnnouncementsListAdapter announcementsAdapter;
	private LessonsListAdapter lessonsAdapter;
	private UpcomingListAdapter upcomingAdapter;
	private ArrayList<Announcement> announcements;
	private ArrayList<Lesson> classes;
	private ArrayList<Lesson> todays_classes;
	private ClassesDao classesDao;

//	public void setData(int sectionNumber) {
//		Bundle args = new Bundle();
//		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//		this.setArguments(args);
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
		this.inflater = inflater;
		setHasOptionsMenu(true);
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
		announcements = new ArrayList<Announcement>();
		for(int i=0; i<5; i++) {
			announcements.add(new Announcement(0, "Announcement "+i, "some description", 0, 0, null));
		}
		if(announcements.size()==0) {
			txt_announcements.setVisibility(View.GONE);
		}
		
		classesDao = new ClassesDao(getActivity());
		classes = new ArrayList<Lesson>();
		classes = classesDao.getClasses();
		todays_classes = getTodaysClasses();
		
		announcementsAdapter = new AnnouncementsListAdapter(getActivity(), elv_announcements, announcements);
		lessonsAdapter = new LessonsListAdapter(getActivity(), elv_lessons, todays_classes);
		upcomingAdapter = new UpcomingListAdapter(getActivity(), elv_upcoming);
		
		announcementsAdapter.resetItems();
		upcomingAdapter.resetItems();
		
		elv_announcements.setAdapter(announcementsAdapter);
		elv_announcements.setScrollContainer(false);
		elv_lessons.setAdapter(lessonsAdapter);
		elv_lessons.setScrollContainer(false);
		elv_upcoming.setAdapter(upcomingAdapter);
		elv_upcoming.setScrollContainer(false);
		
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
		
		Utils.setListViewHeightBasedOnChildren(elv_announcements,true);
		Utils.setListViewHeightBasedOnChildren3(elv_lessons);
		Utils.setListViewHeightBasedOnChildren(elv_upcoming, true);

	}

	public void setActions() {
		
	}
	
	public ArrayList<Lesson> getTodaysClasses() {
		ArrayList<Lesson> c = new ArrayList<Lesson>();
		ArrayList<Day> days;
		
		Calendar cal = Calendar.getInstance();
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
		if(day_of_week == 1)
			day_of_week = 7;
		else
			day_of_week--;
		
		for(int i=0; i<classes.size(); i++) {
			days = classes.get(i).getDays();
			for(int j=0; j<days.size(); j++) 
				if(day_of_week==days.get(j).getCode())
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
		case R.id.action_add_task: {
			Intent addTaskIntent = new Intent(getActivity(), TaskAddEditActivity.class);
			getActivity().startActivityForResult(addTaskIntent, task_add_request_code);
		}
			break;
		case R.id.action_add_note: {
			Intent addNoteIntent = new Intent(getActivity(), NoteAddEditActivity.class);
			getActivity().startActivityForResult(addNoteIntent, note_add_request_code);
		}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//		((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}
}