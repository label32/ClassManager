package com.licenta.classmanager.adapters;

import java.util.Locale;

import com.licenta.classmanager.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {

	private Context mContext;
	private String[] options;

	public CustomSpinnerAdapter(Context context, int resource, String[] objects) {
		super(context, resource, objects);
		mContext = context;
		options = objects;
	}

//	@Override
//	public View getDropDownView(int position, View convertView, ViewGroup parent) {
//		return getCustomView(position, convertView, parent, true);
//	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return getCustomView(position, convertView, parent, false);
	}

	public View getCustomView(int position, View convertView, ViewGroup parent, boolean dropdown) {
		// return super.getView(position, convertView, parent);

		LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		ViewHolder holder;
		
		holder = new ViewHolder();
		
		if (convertView == null) {
			if(dropdown) {
				convertView = inflater.inflate(R.layout.spinner_dropdown_layout, null);
				
				holder.txt_title = (TextView) convertView.findViewById(R.id.spinner_dropdown_title);
				holder.txt_title.setText(options[position]);
				
			} else {
				convertView = inflater.inflate(R.layout.spinner_layout, null);
				
				holder.txt_title = (TextView) convertView.findViewById(R.id.spinner_item_title);
				holder.txt_subtitle = (TextView) convertView.findViewById(R.id.spinner_item_subtitle);
				
				holder.txt_title.setText("Tasks");
				holder.txt_subtitle.setText(options[position].toUpperCase(Locale.getDefault()));
			}

			convertView.setTag(holder);

		} else {

			holder = (ViewHolder) convertView.getTag();
		}		

		return convertView;
	}

	class ViewHolder {
		TextView txt_title;
		TextView txt_subtitle;
	}
}
