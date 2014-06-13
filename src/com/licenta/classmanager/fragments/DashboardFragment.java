package com.licenta.classmanager.fragments;

import java.util.ArrayList;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.licenta.classmanager.R;
import com.licenta.classmanager.activities.AddNoteActivity;
import com.licenta.classmanager.activities.AddTaskActivity;
import com.licenta.classmanager.adapters.AnnouncementsListAdapter;
import com.licenta.classmanager.adapters.LessonsListAdapter;
import com.licenta.classmanager.adapters.UpcomingListAdapter;
import com.licenta.classmanager.holders.Announcement;
import com.licenta.classmanager.utils.Utils;

import de.timroes.android.listview.EnhancedListView;
import de.timroes.android.listview.EnhancedListView.UndoStyle;

public class DashboardFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";

	private EnhancedListView elv_announcements;
	private EnhancedListView elv_lessons;
	private EnhancedListView elv_upcoming;
	private AnnouncementsListAdapter announcementsAdapter;
	private LessonsListAdapter lessonsAdapter;
	private UpcomingListAdapter upcomingAdapter;
	private ArrayList<Announcement> announcements;

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
		
		announcementsAdapter = new AnnouncementsListAdapter(getActivity(), elv_announcements, announcements);
		lessonsAdapter = new LessonsListAdapter(getActivity(), elv_lessons);
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
				Utils.setListViewHeightBasedOnChildren(elv_announcements);
				return new EnhancedListView.Undoable() {

					@Override
					public void undo() {
						announcementsAdapter.insert(position, item);
						Utils.setListViewHeightBasedOnChildren(elv_announcements);
					}

				};
			}
		});
		
//		elv_lessons.setDismissCallback(new EnhancedListView.OnDismissCallback() {
//
//			@Override
//			public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
//
//				final String item = (String) lessonsAdapter.getItem(position);
//				lessonsAdapter.remove(position);
//				return new EnhancedListView.Undoable() {
//
//					@Override
//					public void undo() {
//						lessonsAdapter.insert(position, item);
//					}
//
//				};
//			}
//		});
//		
//		elv_upcoming.setDismissCallback(new EnhancedListView.OnDismissCallback() {
//
//			@Override
//			public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {
//
//				final String item = (String) upcomingAdapter.getItem(position);
//				upcomingAdapter.remove(position);
//				return new EnhancedListView.Undoable() {
//
//					@Override
//					public void undo() {
//						upcomingAdapter.insert(position, item);
//					}
//
//				};
//			}
//		});
		
		elv_announcements.enableSwipeToDismiss();
		elv_announcements.setUndoStyle(UndoStyle.MULTILEVEL_POPUP);
		
		Utils.setListViewHeightBasedOnChildren(elv_announcements);
		Utils.setListViewHeightBasedOnChildren(elv_lessons);
		Utils.setListViewHeightBasedOnChildren(elv_upcoming);

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
			Intent addTaskIntent = new Intent(getActivity(), AddTaskActivity.class);
			startActivityForResult(addTaskIntent, AddTaskActivity.request_code);
		}
			break;
		case R.id.action_add_note: {
			Intent addNoteIntent = new Intent(getActivity(), AddNoteActivity.class);
			startActivityForResult(addNoteIntent, AddNoteActivity.request_code);
		}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case AddTaskActivity.request_code: {
			// do stuff
		}
			break;
		}
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//		((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}
}