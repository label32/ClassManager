package com.licenta.classmanager.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.support.v7.app.ActionBar.LayoutParams;
import android.view.View;
import android.view.View.MeasureSpec;
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
	
	public static final String md5(final String s) {
	    final String MD5 = "MD5";
	    try {
	        // Create MD5 Hash
	        MessageDigest digest = java.security.MessageDigest
	                .getInstance(MD5);
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();

	        // Create Hex String
	        StringBuilder hexString = new StringBuilder();
	        for (byte aMessageDigest : messageDigest) {
	            String h = Integer.toHexString(0xFF & aMessageDigest);
	            while (h.length() < 2)
	                h = "0" + h;
	            hexString.append(h);
	        }
	        return hexString.toString();

	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";
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
	
	 public static void setListViewHeightBasedOnChildren2(ListView listView) {
	        ListAdapter listAdapter = listView.getAdapter();
	        if (listAdapter == null) {
	            // pre-condition
	            return;
	        }

	        int totalHeight = 0;
	        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
	        for (int i = 0; i < listAdapter.getCount(); i++) {
	            View listItem = listAdapter.getView(i, null, listView);
	            listItem.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
	            totalHeight += listItem.getMeasuredHeight();
	        }

	        ViewGroup.LayoutParams params = listView.getLayoutParams();
	        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
	        listView.setLayoutParams(params);
	        listView.requestLayout();
	    }
	 
	 public static void setListViewHeightBasedOnChildren3(ListView listView) {
		    ListAdapter listAdapter = listView.getAdapter();
		    if (listAdapter == null)
		        return;

		    int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.UNSPECIFIED);
		    int totalHeight = 0;
		    View view = null;
		    for (int i = 0; i < listAdapter.getCount(); i++) {
		        view = listAdapter.getView(i, view, listView);
		        if (i == 0)
		            view.setLayoutParams(new ViewGroup.LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));

		        view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
		        totalHeight += view.getMeasuredHeight();
		    }
		    ViewGroup.LayoutParams params = listView.getLayoutParams();
		    params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		    listView.setLayoutParams(params);
		    listView.requestLayout();
		}
}
