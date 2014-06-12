package com.licenta.classmanager.fragments;

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
import android.widget.TextView;

import com.licenta.classmanager.R;
import com.licenta.classmanager.activities.AddNoteActivity;
import com.licenta.classmanager.activities.AddTaskActivity;
import com.licenta.classmanager.activities.MainActivity;
import com.licenta.classmanager.adapters.EnhancedListAdapter;

import de.timroes.android.listview.EnhancedListView;
import de.timroes.android.listview.EnhancedListView.Undoable;

public class DashboardFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";

	private EnhancedListView elv_announcements;
	private EnhancedListAdapter elvAdapter;

	public void setData(int sectionNumber) {
		Bundle args = new Bundle();
		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
		this.setArguments(args);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);
		TextView textView = (TextView) rootView.findViewById(R.id.section_label);
		textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
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
	}

	public void setData() {
		elvAdapter = new EnhancedListAdapter(getActivity(), elv_announcements);
		elvAdapter.resetItems();
		elv_announcements.setAdapter(elvAdapter);
		elv_announcements.setDismissCallback(new EnhancedListView.OnDismissCallback() {

			@Override
			public EnhancedListView.Undoable onDismiss(EnhancedListView listView, final int position) {

				final String item = (String) elvAdapter.getItem(position);
				elvAdapter.remove(position);
				return new EnhancedListView.Undoable() {

					@Override
					public void undo() {
						elvAdapter.insert(position, item);
					}

				};
			}
		});
		
		elv_announcements.enableSwipeToDismiss();

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
		((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}
}