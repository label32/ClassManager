package com.licenta.classmanager.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.licenta.classmanager.fragments.TasksPageFragment;

import de.timroes.android.listview.EnhancedListView;

public class TasksPagerAdapter extends FragmentStatePagerAdapter {

	private Context context;
	private EnhancedListView elv;
	
	public TasksPagerAdapter(FragmentManager fm, Context context) {
		super(fm);
		this.context = context;
	}

	@Override
	public Fragment getItem(int i) {
		Fragment fragment = new TasksPageFragment();
        Bundle args = new Bundle();
        args.putInt(TasksPageFragment.PAGER_COUNT, i + 1);
        fragment.setArguments(args);
        return fragment;
	}

	@Override
	public int getCount() {
		return 2;
	}
	
	@Override
	public int getItemPosition(Object object) {
		return POSITION_NONE;
	}
	
//	@Override
//	public Object instantiateItem(ViewGroup container, int position) {
//		LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
//
//		  elv = (EnhancedListView) layoutInflater.inflate(R.layout.listview1, null);
//
//		  String[] listData = null;
//		  MyArrayAdapter dataAdapter;
//
//		if (position == 0) {
//		    listData = context.getResources().getStringArray(R.array.list1);
//		    dataAdapter = new MyArrayAdapter((Activity) context,
//		        R.layout.rowlayout, listData);
//		  } else if (position == 1) {
//		    listData = context.getResources().getStringArray(R.array.list2);
//		    dataAdapter = new MyArrayAdapter((Activity) context,
//		        R.layout.rowlayout, listData);
//		  } else {
//		    listData = context.getResources().getStringArray(R.array.list3);
//		    dataAdapter = new MyArrayAdapter((Activity) context,
//		        R.layout.rowlayout, listData);
//		  }
//
//		  listView1.setAdapter(dataAdapter);
//		  listView1.setOnItemClickListener(new OnItemClickListener() {
//		    @Override
//		    public void onItemClick(AdapterView<?> adapter, View view,
//		        int position, long arg3) {
//		      Toast.makeText(context,
//		          adapter.getAdapter().getItem(position).toString(),
//		          Toast.LENGTH_LONG).show();
//		    }
//		  });
//
//		  ((ViewPager) collection).addView(listView1, 0);
//
//		  return listView1;
//	}
	
	  @Override
	    public CharSequence getPageTitle(int position) {
		  	if(position == 0)
		  		return "Incomplete";
		  	else
		  		return "Complete";
	    }


}
