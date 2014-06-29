package com.licenta.classmanager.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.licenta.classmanager.R;
import com.licenta.classmanager.dao.NotesDao;
import com.licenta.classmanager.dao.TasksDao;
import com.licenta.classmanager.fragments.ClassesFragment;
import com.licenta.classmanager.fragments.DashboardFragment;
import com.licenta.classmanager.fragments.DayviewFragment;
import com.licenta.classmanager.fragments.MonthviewFragment;
import com.licenta.classmanager.fragments.NavigationDrawerFragment;
import com.licenta.classmanager.fragments.NotesFragment;
import com.licenta.classmanager.fragments.SettingsFragment;
import com.licenta.classmanager.fragments.TasksFragment;
import com.licenta.classmanager.holders.Note;
import com.licenta.classmanager.holders.Task;
import com.licenta.classmanager.holders.User;
import com.licenta.classmanager.services.NetworkWorker;
import com.licenta.classmanager.services.ServiceCallback;

public class MainActivity extends ActionBarActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

	public final static String OFFLINE = "com.licenta.classmanager.OFFLINE";
	public final static String REQUEST_CODE = "com.licenta.classmanager.REQUEST_CODE";
	public final static String EXTRA_USER = "com.licenta.classmanager.EXTRA_USER";
	public final static String EXTRA_USER_ID = "com.licenta.classmanager.EXTRA_USER_ID";
	public final static String EXTRA_USER_TYPE = "com.licenta.classmanager.EXTRA_USER_TYPE";
	private static final String ARG_USERID = "com.licenta.classmanager.USERID";

	private NavigationDrawerFragment mNavigationDrawerFragment;
	private CharSequence mTitle;
	public boolean offline;
	private int user_id;
	private SharedPreferences sharedPref;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager().findFragmentById(
				R.id.navigation_drawer);
		mTitle = getTitle();
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
		loadData();		
	}

	private void loadData() {
		Intent intent = getIntent();
		User u = (User) intent.getSerializableExtra(EXTRA_USER);
		if (u != null) {
			user_id = u.getId();
		} else {
			sharedPref = getSharedPreferences(getString(R.string.preference_file), Context.MODE_PRIVATE);
			user_id = sharedPref.getInt(EXTRA_USER_ID, -1);
			if(user_id==-1 && NetworkWorker.checkConnection(this)) {
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
				builder.setMessage("Internet connection is available, do you want to login?");
				builder.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								Intent intent = new Intent(MainActivity.this, LoginActivity.class);
								startActivity(intent);
								MainActivity.this.finish();
							}
						});
				builder.setCancelable(true);
				builder.setNegativeButton("No",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
							}
						});
				builder.setOnCancelListener(new OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			}
		}
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {

		FragmentManager fragmentManager = getSupportFragmentManager();

		switch (position) {

		/* Dashboard */
		case 0: {
			DashboardFragment dashboardFragment = new DashboardFragment();
			Bundle data = new Bundle();
			dashboardFragment.setArguments(data);
			fragmentManager.beginTransaction().replace(R.id.container, dashboardFragment).commit();
		}
			break;

		/* Calendar */
		case 1:
			// CalendarFragment calendarFragment = new CalendarFragment();
			mTitle = getString(R.string.title_calendar);
			DayviewFragment calendarFragment = new DayviewFragment();
			calendarFragment.setData(position + 1);
			fragmentManager.beginTransaction().replace(R.id.container, calendarFragment).commit();
			break;

		case 2: /* Tasks */
			TasksFragment tasksFragment = new TasksFragment();
			// tasksFragment.setData(position + 1);
			fragmentManager.beginTransaction().replace(R.id.container, tasksFragment).commit();
			break;

		case 3: /* Notes */
			NotesFragment notesFragment = new NotesFragment();
			// notesFragment.setData(position + 1);
			fragmentManager.beginTransaction().replace(R.id.container, notesFragment).commit();
			break;

		case 4: /* Classes */
			ClassesFragment classesFragment = new ClassesFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_USERID, user_id);
			classesFragment.setArguments(args);
			fragmentManager.beginTransaction().replace(R.id.container, classesFragment).commit();
			break;

		case 5: /* Settings */
			SettingsFragment settingsFragment = new SettingsFragment();
			// settingsFragment.setData(position + 1);
			fragmentManager.beginTransaction().replace(R.id.container, settingsFragment).commit();
			break;
		}

	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			mTitle = getString(R.string.title_dashboard);
			break;
		case 2:
			mTitle = getString(R.string.title_calendar);
			break;
		case 3:
			mTitle = getString(R.string.title_tasks);
			break;
		case 4:
			mTitle = getString(R.string.title_notes);
			break;
		case 5:
			mTitle = getString(R.string.title_classes);
			break;
		case 6:
			mTitle = getString(R.string.title_settings);
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		FragmentManager fragmentManager = getSupportFragmentManager();
		switch (requestCode) {
		case DashboardFragment.task_add_request_code: {
			Task task = (Task) data.getSerializableExtra(TaskAddEditActivity.EXTRA_TASK);
			TasksDao dao = new TasksDao(this);
			dao.putTask(task);
			TasksFragment tasksFragment = new TasksFragment();
			fragmentManager.beginTransaction().replace(R.id.container, tasksFragment).commit();
		}
			break;
		case DashboardFragment.note_add_request_code: {
			Note note = (Note) data.getSerializableExtra(NoteAddEditActivity.EXTRA_NOTE);
			NotesDao dao = new NotesDao(this);
			dao.putNote(note);
			NotesFragment notesFragment = new NotesFragment();
			fragmentManager.beginTransaction().replace(R.id.container, notesFragment).commit();
		}
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
		actionBar.setTitle(mTitle);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.

			// getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		case R.id.action_dayview: {
			mTitle = getString(R.string.title_calendar);
			FragmentManager fragmentManager = getSupportFragmentManager();
			DayviewFragment calendarFragment = new DayviewFragment();
			calendarFragment.setData(1 + 1);
			fragmentManager.beginTransaction().replace(R.id.container, calendarFragment).commit();
		}
			break;
		case R.id.action_monthview: {
			mTitle = getString(R.string.title_calendar);
			FragmentManager fragmentManager = getSupportFragmentManager();
			MonthviewFragment calendarFragment = new MonthviewFragment();
			fragmentManager.beginTransaction().replace(R.id.container, calendarFragment).commit();
		}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	// public void updateMenuItems() {
	// MenuItem item_dayview = menu.findItem(R.id.action_dayview);
	// MenuItem item_monthview = menu.findItem(R.id.action_monthview);
	//
	// if (initial) {
	// item_dayview.setChecked(false);
	// item_monthview.setChecked(true);
	// initial = false;
	// } else {
	// item_dayview.setChecked(true);
	// item_monthview.setChecked(false);
	// initial = true;
	// }
	// }

}
