package com.licenta.classmanager.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ActionBar.OnNavigationListener;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.licenta.classmanager.R;
import com.licenta.classmanager.activities.ClassAddEditActivity;
import com.licenta.classmanager.activities.ClassDetailsActivity;
import com.licenta.classmanager.activities.MainActivity;
import com.licenta.classmanager.adapters.CustomSpinnerAdapter;
import com.licenta.classmanager.adapters.listadapters.LessonsListAdapter;
import com.licenta.classmanager.dao.ClassesDao;
import com.licenta.classmanager.holders.Flag;
import com.licenta.classmanager.holders.Lesson;
import com.licenta.classmanager.services.DataService;
import com.licenta.classmanager.services.DaysCallback;
import com.licenta.classmanager.services.JsonParser;
import com.licenta.classmanager.services.NetworkWorker;
import com.licenta.classmanager.services.ServiceCallback;
import com.licenta.classmanager.services.SyncCallback;

import de.timroes.android.listview.EnhancedListView;

public class ClassesFragment extends BaseFragment {

	private static final String ARG_USERID = "com.licenta.classmanager.USERID";

	private ArrayList<Lesson> classes;
	private EnhancedListView elv_classes;
	private LessonsListAdapter classesAdapter;
	private ClassesDao dao;
	private DataService dataService;
	private JsonParser jsonParser;
	private int user_id;

	public ClassesFragment() {

	}

	// public void setData(int sectionNumber) {
	// Bundle args = new Bundle();
	// args.putInt(ARG_SECTION_NUMBER, sectionNumber);
	// this.setArguments(args);
	// }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_classes, container, false);
		setHasOptionsMenu(true);
		return rootView;
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		// ((MainActivity)
		// activity).onSectionAttached(getArguments().getInt(ARG_SECTION_NUMBER));
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
		user_id = getArguments().getInt(ARG_USERID);
		classes = new ArrayList<Lesson>();
		jsonParser = new JsonParser(getActivity());
		dao = new ClassesDao(getActivity());
		dataService = new DataService(getActivity());
		if(!classes_synced && NetworkWorker.checkConnection(getActivity())) {
			dataService.syncClasses(user_id, new SyncCallback() {
								
				@Override
				public void onSuccess(JSONObject result) {
					Toast.makeText(getActivity(), "Classes synced!", Toast.LENGTH_SHORT).show();
					getClasses();				
				}
				
			});
		} else {
			getClasses();			
		}
		
	}
	
	private void getClasses() {
		dataService.getUserClasses(user_id, new ServiceCallback(getActivity()) {

			@Override
			public void onSuccess(JSONObject result) {
				JSONArray json_classes = result.optJSONArray("classes");
				if (json_classes.length() == 0)
					return;
				for (int i = 0; i < json_classes.length(); i++) {
					final int last = json_classes.length() - 1;
					final int count = i;
					JSONObject json_class = json_classes.optJSONObject(i);
					jsonParser.getLessonFromJSON(json_class, new DaysCallback() {

						@Override
						public void onDaysFinish(JSONObject json_days, Lesson lesson) {
							if (lesson != null) {
								lesson.setDays(jsonParser.getDaysFromJSON(json_days));
								classes.add(lesson);
								if (count == last) {
									dao.putClasses(classes);
									updateUI();
								}
									
							}
						}
					});
				}
			}

			@Override
			public void onOffline() {
				classes = dao.getClasses();
				updateUI();
			}
		});
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
		switch (requestCode) {
		case ClassDetailsActivity.request_code: {
			if (resultCode == Activity.RESULT_OK) {
				int position = data.getIntExtra(ClassDetailsActivity.EXTRA_CLASS_POSITION, -1);
				Lesson lesson = (Lesson) data.getSerializableExtra(ClassDetailsActivity.EXTRA_CLASS);
				if (data.getBooleanExtra(ClassDetailsActivity.CLASS_DELETED, false)) {
					deleteClass(lesson);
				} else {
					updateClass(lesson, position);
				}
			}
		}
			break;
		case ClassAddEditActivity.add_request_code: {
			if (resultCode == Activity.RESULT_OK) {
				Lesson lesson = (Lesson) data.getSerializableExtra(ClassAddEditActivity.EXTRA_CLASS);
				addClass(lesson);
			}
		}
			break;
		}
	}

	public void addClass(final Lesson lesson) {
		dataService.addClass(user_id, lesson, new ServiceCallback(getActivity()) {

			@Override
			public void onSuccess(JSONObject result) {
				lesson.setId(result.optInt("classid"));
				dao.putClass(lesson);
				classes_modified = true;
				classes.add(lesson);
				updateUI();
			}

			@Override
			public void onOffline() {
				classes_synced = false;
				classes_modified = true;
				lesson.setFlag(Flag.ADDED);
				dao.putClass(lesson);
				classes.add(lesson);
				updateUI();
			}
		});
	}

	public void updateClass(final Lesson lesson, final int position) {
		dataService.updateClass(lesson, new ServiceCallback(getActivity()) {

			@Override
			public void onSuccess(JSONObject result) {
				dao.deleteClass(lesson);
				dao.putClass(lesson);
				classes.set(position, lesson);
				updateUI();
				classes_modified = true;
			}

			@Override
			public void onOffline() {
				if (lesson.getFlag() != Flag.ADDED) {
					lesson.setFlag(Flag.MODIFIED);
				}
				dao.deleteClass(lesson);
				dao.putClass(lesson);
				classes.set(position, lesson);
				updateUI();
				classes_modified = true;
				classes_synced = false;
			}
		});

	}

	public void deleteClass(final Lesson lesson) {
		if (lesson.getId() == 0) {
			dao.deleteClass(lesson);
			classes.remove(lesson);
			classes_modified = true;
			updateUI();
		} else {
			dataService.deleteClass(lesson.getId(), new ServiceCallback(getActivity()) {

				@Override
				public void onSuccess(JSONObject result) {
					dao.deleteClass(lesson);
					classes.remove(lesson);
					classes_modified = true;
					updateUI();
				}

				@Override
				public void onOffline() {
					int position = classes.indexOf(lesson);
					if (classes.get(position).getFlag() == Flag.ADDED) {
						dao.deleteClass(classes.get(position));
					} else {
						classes.get(position).setFlag(Flag.DELETED);
						dao.putClass(classes.get(position));
					}
					classes_modified = true;
					classes_synced = false;
					classes.remove(position);
					updateUI();
				}
			});
		}
	}

	public void updateUI() {
		Lesson[] temp = classes.toArray(new Lesson[classes.size()]);
		Arrays.sort(temp);
		classes = new ArrayList<Lesson>(Arrays.asList(temp));
		classesAdapter = new LessonsListAdapter(getActivity(), elv_classes, classes);
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
				// Toast.makeText(getActivity(), "classes spinner position = " +
				// itemPosition, Toast.LENGTH_SHORT).show();
				return false;
			}
		};

		getActivity().getActionBar().setListNavigationCallbacks(mCustomSpinnerAdapter, mSpinnerOnNavigationListener);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_add_class) {
			Intent intent = new Intent(getActivity(), ClassAddEditActivity.class);
			intent.putExtra(MainActivity.REQUEST_CODE, ClassAddEditActivity.add_request_code);
			startActivityForResult(intent, ClassAddEditActivity.add_request_code);
		}
		return super.onOptionsItemSelected(item);
	}
}
