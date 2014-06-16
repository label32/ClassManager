package com.licenta.classmanager.utils;

import android.support.v7.app.ActionBar.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class Utils {
	
	public static int hash(String s) {
		int hash=7;
		for (int i=0; i < s.length(); i++) {
		    hash = hash*31+s.charAt(i);
		}
		return hash;
	}
	
	public static void setListViewHeightBasedOnChildren(ListView listView, boolean special) {
		int listItem_height = 0;
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = listView.getPaddingTop() + listView.getPaddingBottom();
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			if (listItem instanceof ViewGroup) {
				listItem.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
			}
			listItem.measure(0, 0);
			listItem_height = listItem.getMeasuredHeight();
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		int count = listAdapter.getCount()-3;
		if(special) {
			listItem_height += listItem_height/2+5;
			count+=3;
		}
		params.height = totalHeight + (listView.getDividerHeight() * (count)) + listItem_height;
		listView.setLayoutParams(params);
	}
}
