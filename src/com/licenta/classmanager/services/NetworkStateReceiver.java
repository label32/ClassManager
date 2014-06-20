package com.licenta.classmanager.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

public abstract class NetworkStateReceiver extends BroadcastReceiver {
	private IntentFilter filter;
	private Context context;

	
	public NetworkStateReceiver(Context context) {
		this.context = context;
		filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		//filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
		context.registerReceiver(this, filter);
	
	}
	
	public void registerReceiver() {
		context.registerReceiver(this, filter);
	}
	
	public void unregisterReceiver() {
		context.unregisterReceiver(this);
	}
		
}

