package com.licenta.classmanager.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.licenta.classmanager.fragments.DayviewPageFragment;
import com.licenta.classmanager.fragments.TasksPageFragment;

public class DayviewPagerAdapter extends FragmentPagerAdapter {

	public DayviewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = new DayviewPageFragment();
        Bundle args = new Bundle();
        args.putInt(TasksPageFragment.PAGER_COUNT, i + 1);
        fragment.setArguments(args);
        return fragment;
	}

	@Override
	public int getCount() {
		return 7;
	}
	
	@Override
	public CharSequence getPageTitle(int position) {
		CharSequence m = "";
	  	switch(position) {
		  	case 0: { m = "Monday"; } break;
		  	case 1: { m = "Tuesday"; } break;
		  	case 2: { m = "Wednesday"; } break;
		  	case 3: { m = "Thursday"; } break;
		  	case 4: { m = "Friday"; } break;
		  	case 5: { m = "Saturday"; } break;
		  	case 6: { m = "Sunday"; } break;
	  	}
	  	return m;
	}
}
