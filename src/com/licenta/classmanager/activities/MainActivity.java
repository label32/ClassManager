package com.licenta.classmanager.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.licenta.classmanager.R;
import com.licenta.classmanager.fragments.CalendarFragment;
import com.licenta.classmanager.fragments.ClassesFragment;
import com.licenta.classmanager.fragments.DashboardFragment;
import com.licenta.classmanager.fragments.NavigationDrawerFragment;
import com.licenta.classmanager.fragments.NotesFragment;
import com.licenta.classmanager.fragments.SettingsFragment;
import com.licenta.classmanager.fragments.TasksFragment;

public class MainActivity extends ActionBarActivity implements
		NavigationDrawerFragment.NavigationDrawerCallbacks {

	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;

	/**
	 * Used to store the last screen title. For use in
	 * {@link #restoreActionBar()}.
	 */
	private CharSequence mTitle;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNavigationDrawerFragment = (NavigationDrawerFragment) getSupportFragmentManager()
				.findFragmentById(R.id.navigation_drawer);
		mTitle = getTitle();

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
	}

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		
		FragmentManager fragmentManager = getSupportFragmentManager();
		
		switch(position) {
		
			/* Dashboard */
		case 0: 
		{
			DashboardFragment dashboardFragment = new DashboardFragment();
			dashboardFragment.setData(position + 1);
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							dashboardFragment).commit();
		}
			break;
		
			/* Calendar */
		case 1:
			CalendarFragment calendarFragment = new CalendarFragment();
			calendarFragment.setData(position + 1);
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							calendarFragment).commit();
			break;
			
		case 2: /* Tasks */
			TasksFragment tasksFragment = new TasksFragment();
			tasksFragment.setData(position + 1);
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							tasksFragment).commit();
			break;
			
		case 3: /* Notes */
			NotesFragment notesFragment = new NotesFragment();
			notesFragment.setData(position + 1);
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							notesFragment).commit();
			break;
			
		case 4: /* Classes */
			ClassesFragment classesFragment = new ClassesFragment();
			classesFragment.setData(position + 1);
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							classesFragment).commit();
			break;
			
		case 5: /* Settings */
			SettingsFragment settingsFragment = new SettingsFragment();
			settingsFragment.setData(position + 1);
			fragmentManager
					.beginTransaction()
					.replace(R.id.container,
							settingsFragment).commit();
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
			getMenuInflater().inflate(R.menu.main, menu);
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
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	

}
