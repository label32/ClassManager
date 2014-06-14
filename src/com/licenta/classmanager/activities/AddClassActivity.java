package com.licenta.classmanager.activities;

import net.margaritov.preference.colorpicker.ColorPickerDialog;
import net.margaritov.preference.colorpicker.ColorPickerDialog.OnColorChangedListener;
import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.licenta.classmanager.R;

public class AddClassActivity extends ActionBarActivity {
	
	public static final int request_code = 103;
	
	private TimePicker tp_start_time, tp_end_time;
	private Button btn_color;
	private TextView txt_pick;
	public static int color;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_class);
		linkUI();
		setData();
		setActions();
	}
	
	private void linkUI() {
		tp_start_time = (TimePicker) findViewById(R.id.tp_start_time);
		tp_end_time = (TimePicker) findViewById(R.id.tp_end_time);
		btn_color = (Button) findViewById(R.id.btn_color);
		txt_pick = (TextView) findViewById(R.id.txt_pick);
	}
	
	private void setData() {
		tp_start_time.setIs24HourView(true);
		tp_end_time.setIs24HourView(true);
	}
	
	private void setActions() {;
		btn_color.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ColorPickerDialog cpd = new ColorPickerDialog(AddClassActivity.this, 0);
				cpd.setAlphaSliderVisible(false);
				cpd.setHexValueEnabled(true);
				cpd.setOnColorChangedListener(new OnColorChangedListener() {
					
					@Override
					public void onColorChanged(int color) {
						AddClassActivity.color = 0xff000000 + Integer.parseInt(Integer.toHexString(color),16);
						Toast.makeText(AddClassActivity.this, "color:"+Integer.toHexString(color), Toast.LENGTH_LONG).show();
						txt_pick.setBackgroundColor(AddClassActivity.color);
					}
				});
				cpd.show();
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.add_menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}
}
