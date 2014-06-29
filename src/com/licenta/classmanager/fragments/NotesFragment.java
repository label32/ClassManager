package com.licenta.classmanager.fragments;

import java.util.ArrayList;
import java.util.Arrays;

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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import com.licenta.classmanager.R;
import com.licenta.classmanager.activities.NoteAddEditActivity;
import com.licenta.classmanager.activities.NoteDetailsActivity;
import com.licenta.classmanager.adapters.CustomSpinnerAdapter;
import com.licenta.classmanager.adapters.listadapters.NotesListAdapter;
import com.licenta.classmanager.dao.NotesDao;
import com.licenta.classmanager.holders.Note;

import de.timroes.android.listview.EnhancedListView;

public class NotesFragment extends BaseFragment {

	private static final String ARG_SECTION_NUMBER = "section_number";

	private ArrayList<Note> notes;
	private EnhancedListView elv_notes;
	private NotesListAdapter notesAdapter;
	private NotesDao dao;
	private LayoutInflater inflater;
	
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
		this.inflater = inflater;
		setHasOptionsMenu(true);
		linkUI(rootView);
		setData();
		setActions();
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
//		((MainActivity) activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
	}
	
	private void linkUI(View rootView) {
		elv_notes = (EnhancedListView) rootView.findViewById(R.id.elv_notes); 
	}
	
	private void setData() {
		dao = new NotesDao(getActivity());
		notes = dao.getNotes();
		notesAdapter = new NotesListAdapter(inflater, elv_notes, notes);
		notesAdapter.resetItems();
		elv_notes.setAdapter(notesAdapter);		
		
	}
	
	private void setActions() {
		elv_notes.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), NoteDetailsActivity.class);
				intent.putExtra(NoteDetailsActivity.EXTRA_NOTE, notes.get(position));
				intent.putExtra(NoteDetailsActivity.EXTRA_NOTE_POSITION, position);
				startActivityForResult(intent, NoteDetailsActivity.request_code);
			}
			
		});
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
//				Toast.makeText(getActivity(), "notes spinner position = " + itemPosition, Toast.LENGTH_SHORT).show();
				return false;
			}
		};

		getActivity().getActionBar().setListNavigationCallbacks(mCustomSpinnerAdapter, mSpinnerOnNavigationListener);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.action_add_note) {
			Intent addNoteIntent = new Intent(getActivity(), NoteAddEditActivity.class);
			startActivityForResult(addNoteIntent, NoteAddEditActivity.add_request_code);
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
		case NoteDetailsActivity.request_code: {
			if(resultCode == Activity.RESULT_OK) {
				Note note = (Note) data.getSerializableExtra(NoteDetailsActivity.EXTRA_NOTE);
				int position = data.getIntExtra(NoteDetailsActivity.EXTRA_NOTE_POSITION, -1);
				if(data.getBooleanExtra(NoteDetailsActivity.EXTRA_NOTE_DELETED, false)) {
					deleteNote(note, position);
				} else {
					updateNote(note, position);
				}
			}
		} break;
		case NoteAddEditActivity.add_request_code: {
			if(resultCode == Activity.RESULT_OK) {
				Note note = (Note) data.getSerializableExtra(NoteAddEditActivity.EXTRA_NOTE);
				addNote(note);
			}
		} break;
		}
	}
	
	public void addNote(Note note) {
		dao.putNote(note);
		notes.add(note);
		updateUI();
	}
	
	public void updateNote(Note note, int position) {
		dao.deleteNote(note);
		dao.putNote(note);
		notes.set(position, note);
		updateUI();
	}
	
	public void deleteNote(Note note, int position) {
		dao.deleteNote(note);
		notes.remove(position);
		updateUI();
	}
	
	public void updateUI() {
		Note[] temp = notes.toArray(new Note[notes.size()]);
		Arrays.sort(temp);
		notes = new ArrayList<Note>(Arrays.asList(temp));
		notesAdapter = new NotesListAdapter(inflater, elv_notes, notes);
		elv_notes.setAdapter(notesAdapter);
	}
}
