package com.licenta.classmanager.services;


import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;

public abstract class ServiceCallback extends BaseCallback {
	private Context context;
	private NetworkWorker net_work;
	public final static int WIFI_REQUEST_CODE = 256;

	public ServiceCallback(Context context) {
		this.context = context;
		super.setContext(context);
		net_work = new NetworkWorker();
	}

	public abstract void onSuccess(JSONObject result);

	public abstract void onOffline();

	@Override
	public void finish(String url, JSONObject result, String msg) {
		if (result != null) {
			if (result.optInt("result_id") == 0) {
				onSuccess(result);
			} else {
				onError(url, result, msg);
			}
		} else {
			onError(url, result, msg);
		}

	}

	private void onError(final String url, JSONObject result, final String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		if (result != null) {
			builder.setMessage(result.optString("result_message"));
			builder.setPositiveButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {

						}
					});
			AlertDialog alert = builder.create();
			alert.show();

		} else {

			if (NetworkWorker.checkConnection(context)) {
				builder.setMessage("Error: "+msg);
				builder.setPositiveButton("Retry",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								net_work.executeRequest(url,
										ServiceCallback.this);
							}
						});
				builder.setCancelable(true);
				builder.setNegativeButton("Continue offline",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								onOffline();
							}
						});
				builder.setOnCancelListener(new OnCancelListener() {
					
					@Override
					public void onCancel(DialogInterface dialog) {
						onOffline();
					}
				});
				AlertDialog alert = builder.create();
				alert.show();
			} else {
				onOffline();
			}

		}

	}

	@Override
	public Context getContext() {
		return context;
	}
}
