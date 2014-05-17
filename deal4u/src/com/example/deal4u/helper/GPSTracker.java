package com.example.deal4u.helper;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;


public class GPSTracker extends Service implements LocationListener {

	private static final String LOGTAG = "GPSTracker";
	
	private final Context mContext;

	// flag for GPS status
	boolean isGPSEnabled = false;

	// flag for network status
	boolean isNetworkEnabled = false;

	// flag for GPS status
	boolean canGetLocation = false;

	Location location; // location
	double latitude; // latitude
	double longitude; // longitude

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

	// Declaring a Location Manager
	protected LocationManager locationManager;

	public GPSTracker(Context context) {
		this.mContext = context;
		turnGPSOn();
		//get current location.
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				Location currentLocation = getLocation();		
				if (currentLocation!=null) {
					//implemented location listener.
					sendUpdateLocationService(currentLocation);
				}
				else
				{
					Log.d(LOGTAG, "location is empty at constructer GPSTracker");
					turnGPSOff();
				}
				
			}
		}, 3000);		
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager) mContext
					.getSystemService(LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager
					.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				Log.i(LOGTAG, "getLocation: Enable GPS by sending broadcast to ping gps application using intent ");
				// no network provider is enabled
			} else {
				this.canGetLocation = true;
				if (isNetworkEnabled) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					//getLastKnownLocation gets last location
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
						Log.i(LOGTAG, "getLocation: GPS is enabled get current location ");
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}else if(!isGPSEnabled)
				{
					locationManager.requestLocationUpdates(
							LocationManager.GPS_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					//getLastKnownLocation gets last location
					if (locationManager != null) {
						location = locationManager
								.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						Log.i(LOGTAG, "getLocation: GPS is enabled get current location ");
						if (location != null) {
							latitude = location.getLatitude();
							longitude = location.getLongitude();
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}
	
	/**
	 * Function to get latitude
	 * @param location 
	 * */
	public double getLatitude(Location location){
		if(location != null){
			latitude = location.getLatitude();
		}
		
		// return latitude
		return latitude;
	}
	
	/**
	 * Function to get longitude
	 * */
	public double getLongitude(){
		if(location != null){
			longitude = location.getLongitude();
		}
		
		// return longitude
		return longitude;
	}
	

	@Override
	public void onLocationChanged(Location location) {
		String s = new String();
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}

	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	// automatic turn on the gps
		public void turnGPSOn() {
			Log.i(LOGTAG, "turnGPSOn: Enable GPS by sending broadcast to ping gps application using intent ");
			// send broadcast to ping gps application to enable 
			 Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
	         intent.putExtra("enabled", true);
	         this.mContext.sendBroadcast(intent);
	         Log.i(LOGTAG, "turnGPSOn: GPS is now enable");
	         //can comment the below code works for few device and doesnt for few device.
			String provider = Settings.Secure.getString(mContext.getContentResolver(),
					Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
			if (!provider.contains("gps")) { // if gps is disabled
				final Intent poke = new Intent();
				poke.setClassName("com.android.settings",
						"com.android.settings.widget.SettingsAppWidgetProvider");
				poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
				poke.setData(Uri.parse("3"));
				this.mContext.sendBroadcast(poke);
			}
		}

		// automatic turn off the gps
		public void turnGPSOff() {
			Log.i(LOGTAG, "turnGPSOff: Disable GPS by sending broadcast to ping gps application using intent ");
			 Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
	         intent.putExtra("enabled", false);
	         this.mContext.sendBroadcast(intent);
	         //For some devices the above code doesnt work so the below code will disable GPS
			String provider = Settings.Secure.getString(mContext.getContentResolver(),
					Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
			if (provider.contains("gps")) { // if gps is enabled
				final Intent poke = new Intent();
				poke.setClassName("com.android.settings",
						"com.android.settings.widget.SettingsAppWidgetProvider");
				poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
				poke.setData(Uri.parse("3"));
				this.mContext.sendBroadcast(poke);
			}
		}
		
		public void sendUpdateLocationService(Location location)
		{
			// take latitude and longitude.
			double latitude = getLatitude(location);
			double longitude = getLatitude(location);
			//Service controller is wriiten by us
			//serviceControler serviceobj = new serviceControler(mContext);
			//String[] serviceparameters = {Double.toString(latitude),Double.toString(longitude),Constants.getCurrentTime()};
			//serviceobj.startService(Constants.UPDATE_LOCATION,serviceparameters);
			Log.i(LOGTAG, "sendUpdateLocationService: send current location to server");
			//turn GPS of so that battery does not go
			turnGPSOff();
		}
		
}
