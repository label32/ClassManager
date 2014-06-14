package com.licenta.classmanager.fragments;

import java.util.ArrayList;

import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.licenta.classmanager.R;
import com.licenta.classmanager.activities.AddNoteActivity;
import com.licenta.classmanager.activities.MainActivity;
import com.licenta.classmanager.adapters.CustomSpinnerAdapter;
import com.licenta.classmanager.adapters.listadapters.ClassesListAdapter;
import com.licenta.classmanager.holders.Lesson;

import de.timroes.android.listview.EnhancedListView;

public class NotesFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";

	private ArrayList<Lesson> notes;
	private EnhancedListView elv_notes;
	private ClassesListAdapter notesAdapter;
	
	public NotesFragment() {

	}

//	public void setData(int sectionNumber) {
//		Bundle args = new Bundle();
//		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//		this.setArguments(args);
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_notes, container, false);
		setHasOptionsMenu(true);
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//		((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		linkUI();
		setData();
		setActions();
	}
	
	private void linkUI() {
		elv_notes = (EnhancedListView) getActivity().findViewById(R.id.elv_notes); 
	}
	
	private void setData() {
		notesAdapter = new ClassesListAdapter(getActivity(), elv_notes);
		notesAdapter.resetItems();
		elv_notes.setAdapter(notesAdapter);		
		
	}
	
	private void setActions() {
		
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.notes, menu);

		getActivity().getActionBar().setDisplayShowTitleEnabled(false);
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		CustomSpinnerAdapter mCustomSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.spinner_layout,
				getResources().getStringArray(R.array.notes_spinner_items), "Notes");
		mCustomSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);

		OnNavigationListener mSpinnerOnNavigationListener = new OnNavigationListener() {

			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				Toast.makeText(getActivity(), "notes spinner position = " + itemPosition, Toast.LENGTH_SHORT).show();
				return false;
			}
		};

		getActivity().getActionBar().setListNavigationCallbacks(mCustomSpinnerAdapter, mSpinnerOnNavigationListener);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.action_add_note) {
			Intent addNoteIntent = new Intent(getActivity(), AddNoteActivity.class);
			startActivityForResult(addNoteIntent, AddNoteActivity.request_code);
		}
		return super.onOptionsItemSelected(item);
	}
}
