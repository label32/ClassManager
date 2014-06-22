package com.licenta.classmanager.fragments;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.licenta.classmanager.R;
import com.licenta.classmanager.activities.NoteAddEditActivity;
import com.licenta.classmanager.activities.TaskAddEditActivity;
import com.licenta.classmanager.adapters.listadapters.AnnouncementsListAdapter;
import com.licenta.classmanager.adapters.listadapters.LessonsListAdapter;
import com.licenta.classmanager.adapters.listadapters.UpcomingListAdapter;
import com.licenta.classmanager.holders.Announcement;
import com.licenta.classmanager.holders.Day;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.holders.Time;
import com.licenta.classmanager.utils.Utils;

import de.timroes.android.listview.EnhancedListView;
import de.timroes.android.listview.EnhancedListView.UndoStyle;

public class DashboardFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";

	public static final int note_add_request_code = 104;
	public static final int task_add_request_code = 101;
	
	private EnhancedListView elv_announcements;
	private EnhancedListView elv_lessons;
	private EnhancedListView elv_upcoming;
	private AnnouncementsListAdapter announcementsAdapter;
	private LessonsListAdapter lessonsAdapter;
	private UpcomingListAdapter upcomingAdapter;
	private ArrayList<Announcement> announcements;
	private ArrayList<Lesson> lessons;

//	public void setData(int sectionNumber) {
//		Bundle args = new Bundle();
//		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//		this.setArguments(args);
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
//		TextView textView = (TextView) rootView.findViewById(R.id.section_label);
//		textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
		setHasOptionsMenu(true);
		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		linkUI();
		setData();
		setActions();
	}

	public void linkUI() {
		elv_announcements = (EnhancedListView) getActivity().findViewById(R.id.elv_announcements);
		elv_lessons = (EnhancedListView) getActivity().findViewById(R.id.elv_lessons);
		elv_upcoming = (EnhancedListView) getActivity().findViewById(R.id.elv_upcoming);
	}

	public void setData() {
		announcements = new ArrayList<Announcement>();
		for(int i=0; i<5; i++) {
			announcements.add(new Announcement(0, "Announcement "+i, "some description", 0, 0, new Date()));
		}
		
		lessons = new ArrayList<Lesson>();
		ArrayList<Day> days;
		int color;
		for(int i=0; i<5; i++) {
			days = new ArrayList<Day>();
			if(i%2==0) {
				days.add(Day.Monday);
				days.add(Day.Wednesday);
				days.add(Day.Friday);
				color = Color.RED;
			}
			else {
				days.add(Day.Tuesday);
				days.add(Day.Thursday);
				color = Color.BLUE;
			}
			lessons.add(new Lesson(0,"Lesson "+i,"Some details",""+i, days, new Time(12,30), new Time(14,30), color));
		}
		
		announcementsAdapter = new AnnouncementsListAdapter(getActivity(), elv_announcements, announcements);
		lessonsAdapter = new LessonsListAdapter(getActivity(), elv_lessons, lessons);
		upcomingAdapter = new UpcomingListAdapter(getActivity(), elv_upcoming);
		
		announcementsAdapter.resetItems();
		lessonsAdapter.resetItems();
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
		
		Utils.setListViewHeightBasedOnChildren(elv_announcements, true);
		Utils.setListViewHeightBasedOnChildren(elv_lessons, false);
		Utils.setListViewHeightBasedOnChildren(elv_upcoming, true);

	}

	public void setActions() {
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