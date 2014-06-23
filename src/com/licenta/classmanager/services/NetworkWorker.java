package com.licenta.classmanager.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

public class NetworkWorker {

	public BaseCallback callback;
	private String url;
	private String error_msg="Connection error";

	public NetworkWorker() {
	}

	public static boolean checkConnection(Context context) {
		ConnectivityManager connMgr = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isConnected());
	}

	public void executeRequest(String url, BaseCallback callback) {
		this.callback = callback;
		this.url = url;
		DownloadDataTask ddt = new DownloadDataTask();
		ddt.execute(url);
	}

	private class DownloadDataTask extends AsyncTask<String, Void, JSONObject> {
		
		ProgressDialog progress;

		public DownloadDataTask() {
		}

		@Override
		protected void onPreExecute() {
			progress = ProgressDialog.show(callback.getContext(), "Loading data",
					"Please wait...");
			super.onPreExecute();
		}

		@Override
		protected JSONObject doInBackground(String... urls) {
			try {
				return downloadData(urls[0]);
			} catch (Exception e) {
				Log.e("DOWNLOAD_TASK", e.toString());
				return null;
			}
		}

		@Override
		protected void onPostExecute(JSONObject result) {
			super.onPostExecute(result);
			progress.dismiss();
			callback.finish(url, result, error_msg);
		}
	}

	private JSONObject downloadData(String message_url) {
		InputStream is = null;
		JSONObject result = null;
		try {
			URL url = new URL(message_url);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(5000);
			conn.setConnectTimeout(10000);
			conn.setRequestMethod("GET");
			conn.setDoInput(true);

			conn.connect();
			int response = conn.getResponseCode();
			Log.d("CONNECTION_DEBUG", "The response code is " + response);
			is = conn.getInputStream();

			result = readJSON(is);
			return result;

		} catch (ProtocolException e) {
			e.printStackTrace();
			Log.e("DOWNLOAD_ERROR", e.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
			Log.e("DOWNLOAD_ERROR", e.toString());
		} catch (IOException e) {
			e.printStackTrace();
			Log.e("DOWNLOAD_ERROR", e.toString());
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
					Log.e("DOWNLOAD_ERROR", e.toString());
				}
			}
		}
		return result;
	}

	public JSONObject readJSON(InputStream is) {
		try {
			BufferedReader streamReader = new BufferedReader(
					new InputStreamReader(is, "UTF-8"));
			StringBuilder responseStrBuilder = new StringBuilder();
			String inputStr;
			while ((inputStr = streamReader.readLine()) != null)
				responseStrBuilder.append(inputStr);
			return new JSONObject(responseStrBuilder.toString());
		} catch (Exception e) {
			error_msg = "Incorrect data!";
			Log.e("READ_JSON_ERROR", e.toString());
			return null;
		}
	}
}
