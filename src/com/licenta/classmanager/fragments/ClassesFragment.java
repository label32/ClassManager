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
import com.licenta.classmanager.activities.ClassAddEditActivity;
import com.licenta.classmanager.activities.ClassDetailsActivity;
import com.licenta.classmanager.activities.DashboardActivity;
import com.licenta.classmanager.adapters.CustomSpinnerAdapter;
import com.licenta.classmanager.adapters.listadapters.ClassesListAdapter;
import com.licenta.classmanager.dao.ClassesDao;
import com.licenta.classmanager.holders.Lesson;

import de.timroes.android.listview.EnhancedListView;

public class ClassesFragment extends Fragment {

	private static final String ARG_SECTION_NUMBER = "section_number";
	
	private ArrayList<Lesson> classes;
	private EnhancedListView elv_classes;
	private ClassesListAdapter classesAdapter;
	private ClassesDao dao;
	
	public ClassesFragment() {

	}

//	public void setData(int sectionNumber) {
//		Bundle args = new Bundle();
//		args.putInt(ARG_SECTION_NUMBER, sectionNumber);
//		this.setArguments(args);
//	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_classes, container, false);
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
		elv_classes = (EnhancedListView) getActivity().findViewById(R.id.elv_classes); 
	}
	
	private void setData() {
		dao = new ClassesDao(getActivity());
		classes = dao.getClasses();
		if(classes == null)
			classes = new ArrayList<Lesson>();
		classesAdapter = new ClassesListAdapter(getActivity(), elv_classes, classes);
		elv_classes.setAdapter(classesAdapter);		
		
	}
	
	private void setActions() {
		elv_classes.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(getActivity(), ClassDetailsActivity.class);
				intent.putExtra(ClassDetailsActivity.EXTRA_CLASS, classes.get(position));
				intent.putExtra(ClassDetailsActivity.EXTRA_CLASS_POSITION, position);
				startActivityForResult(intent, ClassDetailsActivity.request_code);
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch(requestCode) {
		case ClassDetailsActivity.request_code: {
			if(resultCode == Activity.RESULT_OK) {
				Lesson lesson = (Lesson) data.getSerializableExtra(ClassDetailsActivity.EXTRA_CLASS);
				int position = data.getIntExtra(ClassDetailsActivity.EXTRA_CLASS_POSITION, -1);
				updateClass(lesson, position);
			}
		} break;
		case ClassAddEditActivity.add_request_code: {
			if(resultCode == Activity.RESULT_OK) {
				Lesson lesson = (Lesson) data.getSerializableExtra(ClassAddEditActivity.EXTRA_CLASS);
				addClass(lesson);
			}
		} break;
		}
	}
	
	public void addClass(Lesson lesson) {
		dao.putClass(lesson);
		classes.add(lesson);
		updateUI();
	}
	
	public void updateClass(Lesson lesson, int position) {
		dao.deleteClass(lesson);
		dao.putClass(lesson);
		classes.set(position, lesson);
		updateUI();
	}
	
	public void deleteClass(Lesson lesson) {
		dao.deleteClass(lesson);
		classes.remove(lesson);
		updateUI();
	}
	
	public void updateUI() {
		Lesson[] temp = classes.toArray(new Lesson[classes.size()]);
		Arrays.sort(temp);
		classes = new ArrayList<Lesson>(Arrays.asList(temp));
		classesAdapter = new ClassesListAdapter(getActivity(), elv_classes, classes);
		elv_classes.setAdapter(classesAdapter);
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.classes, menu);

		getActivity().getActionBar().setDisplayShowTitleEnabled(false);
		getActivity().getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		CustomSpinnerAdapter mCustomSpinnerAdapter = new CustomSpinnerAdapter(getActivity(), R.layout.spinner_layout,
				getResources().getStringArray(R.array.classes_spinner_items), "Classes");
		mCustomSpinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);

		OnNavigationListener mSpinnerOnNavigationListener = new OnNavigationListener() {

			@Override
			public boolean onNavigationItemSelected(int itemPosition, long itemId) {
				Toast.makeText(getActivity(), "classes spinner position = " + itemPosition, Toast.LENGTH_SHORT).show();
				return false;
			}
		};

		getActivity().getActionBar().setListNavigationCallbacks(mCustomSpinnerAdapter, mSpinnerOnNavigationListener);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if(id == R.id.action_add_class) {
			Intent intent = new Intent(getActivity(), ClassAddEditActivity.class);
			intent.putExtra(DashboardActivity.REQUEST_CODE, ClassAddEditActivity.add_request_code);
			startActivityForResult(intent, ClassAddEditActivity.add_request_code);
		}
		return super.onOptionsItemSelected(item);
	}
}
