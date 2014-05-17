package com.example.deal4u.Service;

import com.example.deal4u.helper.GPSTracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class LocationUpdateService extends Service {
	protected static final String TAG = "LocationUpdateService";
	private boolean isServiceRunning = true;
	Context context;
	Thread thread;

	public void onCreate() {
		super.onCreate();
		Log.i(TAG, "Starting Service...");
		context = getApplicationContext();
		
		  thread = new Thread(){
			    public void run(){
			      System.out.println("Thread Running");
			      Log.d(TAG, "Creating thread...");
					while (true) {
						if (!LocationUpdateService.this.isServiceRunning)
							return;

						Log.d(TAG, "Sleep...");
						try {
							Log.d(TAG, "Getting location updates...");
							handler.sendEmptyMessage(0);
							sleep(300000);
						} catch (InterruptedException localInterruptedException) {
							Log.d(TAG,
									"Error encountered in update location service");
						}
					}
			    }
			  };thread.start();
			  
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Log.d(TAG, "in handler");
			// LOCATION UPDATES
			new GPSTracker(LocationUpdateService.this);
		}
	};

	public void onDestroy() {
		Log.i(TAG, "Stop the Service and and interupt the current thread");
		this.isServiceRunning = false;
		Thread.currentThread().interrupt();
		this.stopSelf();
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}