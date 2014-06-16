package com.licenta.classmanager.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.licenta.classmanager.R;
import com.licenta.classmanager.holders.Lesson;

public class TaskClassSpinnerAdapter extends ArrayAdapter<String> {

	private Context context;
	private ArrayList<Lesson> classes;
	private String title;
	
	public TaskClassSpinnerAdapter(Context context, int resource, ArrayList<Lesson> classes, String title) {
		super(context, resource, makeArray(classes));
		this.context = context;
		this.title = title;
	}
	
	private static String[] makeArray(ArrayList<Lesson> classes) {
		ArrayList<String> c = new ArrayList<String>();
		for(int i=0; i<classes.size(); i++) 
			c.add(classes.get(i).getName());
		return (String[]) c.toArray(new String[c.size()]);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent, false);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent, boolean dropdown) {

		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		ViewHolder holder;
		holder = new ViewHolder();
		
		if (convertView == null) {
			if(dropdown) {
				convertView = inflater.inflate(android.R.layout.simple_spinner_dropdown_item, null);
				holder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
				holder.text1.setText(classes.get(position).getName());
				
			} else {
				convertView = inflater.inflate(android.R.layout.simple_spinner_item, null);
				holder.text1 = (TextView) convertView.findViewById(android.R.id.text1);
				holder.text1.setText(title);
			}
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}		
		return convertView;
	}
	
	class ViewHolder {
		TextView text1;
		Lesson lesson;
	}

}
