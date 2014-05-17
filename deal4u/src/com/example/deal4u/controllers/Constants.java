package com.example.deal4u.controllers;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;

public class Constants {

	private static final String LOGTAG = "Deals4U";
	// Main Server URL
	public static final String Main_URL = "http://1-dot-hypnotic-camp-570.appspot.com/javaserver";
	public static final String User_Setting_file = "setting_file";

	public static void turnGPSOff(Context context) {
		Log.i(LOGTAG,
				"turnGPSOff: Disable GPS by sending broadcast to ping gps application using intent ");
		Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		intent.putExtra("enabled", false);
		context.sendBroadcast(intent);
	}

	public void turnGPSOn(Context context) {
		Log.i(LOGTAG,
				"turnGPSOn: Enable GPS by sending broadcast to ping gps application using intent ");
		Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
		intent.putExtra("enabled", true);
		context.sendBroadcast(intent);
		Log.i(LOGTAG, "turnGPSOn: GPS is now enable");
	}
	
	/**
	 * This function is used to validate the user entered email
	 * @param target
	 * @return
	 */
	public final static boolean isValidEmail(CharSequence target) {
        if (target == null) {
        return false;
        } else {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    } 
	
	/**
	 * This method displays the dialog box with the title and message 
	 * @param title
	 * @param message
	 */
	public static void showDialog(final Context context, String title, String message, final Boolean isFinish) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle(title);
		builder.setMessage(message);
		builder.setPositiveButton("OK", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
				if (isFinish) {
					((Activity) context).finish();
				}
				
				
			}
		});
		
		builder.show();
	}

}
