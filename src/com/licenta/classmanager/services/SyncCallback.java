package com.licenta.classmanager.services;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;


public abstract class SyncCallback extends BaseCallback {

	public abstract void onSuccess(JSONObject result);

	@Override
	public void finish(String url, JSONObject result, String msg) {
		if (result != null) {
				onSuccess(result);
		} else {
			onError(url, result);
		}

	}

	public void onError(final String url, JSONObject result) {
		if(result == null) {
			Toast.makeText(context, "Sync error: no internet connection", Toast.LENGTH_SHORT).show();
		} else {
			onSuccess(result);
		}
		
	}
	
}
