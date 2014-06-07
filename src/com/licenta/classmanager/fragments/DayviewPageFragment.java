package com.licenta.classmanager.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.licenta.classmanager.R;

public class DayviewPageFragment extends Fragment {

	public static final String PAGER_COUNT = "PAGER_COUNT";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_dayview_page, container, false);
		Bundle args = getArguments();
        ((TextView) rootView.findViewById(R.id.txt_pagerCount)).setText(
                Integer.toString(args.getInt(PAGER_COUNT)));

		return rootView;
	}

}
