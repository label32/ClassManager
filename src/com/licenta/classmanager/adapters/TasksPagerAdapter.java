package com.licenta.classmanager.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.licenta.classmanager.fragments.TasksListFragment;

public class TasksPagerAdapter extends FragmentPagerAdapter {

	public TasksPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = new TasksListFragment();
        Bundle args = new Bundle();
        args.putInt(TasksListFragment.PAGER_COUNT, i + 1);
        fragment.setArguments(args);
        return fragment;
	}

	@Override
	public int getCount() {
		return 2;
	}
	
	  @Override
	    public CharSequence getPageTitle(int position) {
		  	if(position == 0)
		  		return "Incomplete";
		  	else
		  		return "Complete";
	    }


}
