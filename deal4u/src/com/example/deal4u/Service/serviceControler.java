package com.example.deal4u.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.util.HashMap;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.example.deal4u.controllers.Constants;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

public class serviceControler {
	private static final String LOGTAG = "serviceControler";
	private Context context;
	JSONObject jsonObject;
	SharedPreferences sharedPreferences_put;
	SharedPreferences.Editor editor;

	public serviceControler(Context context) {
		// super();
		this.context = context;
		sharedPreferences_put = context.getSharedPreferences(Constants.User_Setting_file,
				Activity.MODE_PRIVATE);
		editor = sharedPreferences_put.edit();
	}

	public serviceControler() {

	}

	public void startService(String methodName, String[] serviceparameters) {
		// Web task is a AsyncTask written.
		WebTask webtaskSocket = new WebTask(methodName, serviceparameters);
		webtaskSocket.execute();
	}
	

	class WebTask extends AsyncTask<Void, Void, String> {

		public String methodname;
		HashMap<String, Object> paramHashMap = null;
		String[] serviceparameters;

		private ProgressDialog pDialog;
		
		private Handler handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				Log.d(LOGTAG, "in handler service controler");
				Toast.makeText((LocationUpdateService)context, "Updating Location every 5 mins", Toast.LENGTH_SHORT).show();
				
			}
		};

		public WebTask() {

		}

		public WebTask(String methodname) {
			this.methodname = methodname;
		}

		public WebTask(String methodName, HashMap<String, Object> paramHashmap) {
			this.paramHashMap = paramHashmap;
			this.methodname = methodName;
		}

		public WebTask(String methodName, String[] serviceparameters) {
			this.methodname = methodName;
			this.serviceparameters = serviceparameters;
		}

		// Progressbar in pre execute
		// Set message depending on service hit.
		@Override
		protected void onPreExecute() {

			pDialog = new ProgressDialog(context);

			if (context instanceof LocationUpdateService) {
				
				Log.d(LOGTAG, "context -> "+context.getApplicationContext());
				handler.sendEmptyMessage(0);
			}
			else
			{
				pDialog.show();
			}
			
		}

		@Override
		protected String doInBackground(Void... params) {
			InputStream inputStream = null;
			String response = "";
			String jsonString = "";
			
			try {
				DefaultHttpClient httpclient = new DefaultHttpClient();
				// Hit main url to connect to server.
				HttpPost httpPost = new HttpPost(Constants.Main_URL);

				JSONObject jsonObject = new JSONObject();
				
				//jsonString = jsonObject.toString();
				StringEntity se = new StringEntity("hello");
				httpPost.setEntity(se);
				httpPost.setHeader("Accept", "application/text");
				httpPost.setHeader("Content-type", "application/text");
				// we hit service and get the response
				HttpResponse httpResponse = httpclient.execute(httpPost);
				inputStream = httpResponse.getEntity().getContent();

				BufferedReader reader = new BufferedReader(new InputStreamReader(
						inputStream, "UTF-8"));
				StringBuilder sb = new StringBuilder();
				String line = null;
				while ((line = reader.readLine()) != null) {
					sb.append(line + "\n");
				}
				response = sb.toString();

				httpclient = null;
				httpPost = null;
				httpResponse = null;
				inputStream = null;
				reader = null;
				Log.i(LOGTAG,
						"updateLocation: Response for UPDATE_LOCATION successfully retrived from server");
				return response;

			} catch (UnknownHostException e) {
				Log.i(LOGTAG,
						"updateLocation: ERROR to retriving response for UPDATE_LOCATION from server");
				((Activity) context).runOnUiThread(new Runnable() {
					public void run() {
						// runs on UI thread
						showDialog("Newtork error",
								"Please check your internet connection");
					}
				});

				return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

		}

		@Override
		protected void onPostExecute(String result) {

			pDialog.dismiss();
		}
	}

	/*
	 * Update Location Service
	 */
	private String updateLocation(String[] serviceparameters) {
		Log.i(LOGTAG, "updateLocation: send UPDATE_LOCATION request to server");
		InputStream inputStream = null;
		String response = "";
		String jsonString = "";
		jsonObject = new JSONObject();
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			// Hit main url to connect to server.
			HttpPost httpPost = new HttpPost(Constants.Main_URL);

			JSONObject jsonObject = new JSONObject();
			
			jsonString = jsonObject.toString();
			StringEntity se = new StringEntity(jsonString);
			httpPost.setEntity(se);
			httpPost.setHeader("Accept", "application/text");
			httpPost.setHeader("Content-type", "application/text");
			// we hit service and get the response
			HttpResponse httpResponse = httpclient.execute(httpPost);
			inputStream = httpResponse.getEntity().getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					inputStream, "UTF-8"));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			response = sb.toString();

			httpclient = null;
			httpPost = null;
			httpResponse = null;
			inputStream = null;
			reader = null;
			Log.i(LOGTAG,
					"updateLocation: Response for UPDATE_LOCATION successfully retrived from server");
			return response;

		} catch (UnknownHostException e) {
			Log.i(LOGTAG,
					"updateLocation: ERROR to retriving response for UPDATE_LOCATION from server");
			((Activity) context).runOnUiThread(new Runnable() {
				public void run() {
					// runs on UI thread
					showDialog("Newtork error",
							"Please check your internet connection");
				}
			});

			return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * This method displays the dialog box with the title and message
	 * 
	 * @param title
	 * @param message
	 */
	public void showDialog(String title, String message) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

				((Activity) context).finish();

			}
		});

		builder.show();
	}
}