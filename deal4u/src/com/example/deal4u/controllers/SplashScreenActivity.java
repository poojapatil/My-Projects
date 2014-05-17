package com.example.deal4u.controllers;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import com.example.deal4u.R;
import com.example.deal4u.Service.LocationUpdateService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;


/** used the below link for animation **/
/** http://stackoverflow.com/questions/9294112/how-to-make-the-textview-blinking  **/

public class SplashScreenActivity extends Activity {
	/** Called when the activity is first created. */
	private static final String LOGTAG = "SplashScreenActivity";
	private long _splashTime = 3000;
	TextView splashTxt;
	// Animation
    Animation animFadeIn,animFadeOut;
    SharedPreferences sharedPreferences_put;
	SharedPreferences.Editor editor;
	private boolean HaveConnectedWifi = false;
	private boolean HaveConnectedMobile = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_screen);
		
		splashTxt = (TextView) findViewById(R.id.splash_textId);
		

		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(50); //You can manage the time of the blink with this parameter
		anim.setStartOffset(20);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		splashTxt.startAnimation(anim);

		
		//startService(new Intent(this, LocationUpdateService.class));
        // Check Internet availability
        //checks whether wifi or data is available any one connection should be available
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] netInfo = cm.getAllNetworkInfo();
		for (NetworkInfo ni : netInfo) {
			if (ni.getTypeName().equalsIgnoreCase("WIFI"))
				if (ni.isConnected())
					HaveConnectedWifi = true;
			if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
				if (ni.isConnected())
					HaveConnectedMobile = true;
		}
		if(HaveConnectedWifi || HaveConnectedMobile){
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				
				sharedPreferences_put = getSharedPreferences(Constants.User_Setting_file, Activity.MODE_PRIVATE);
				
				finish();
				
				Boolean isLogin = sharedPreferences_put.getBoolean("isLogin", false);
				Intent main_Intent = null;
				if (!isLogin)
					main_Intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
				else
					main_Intent = new Intent(SplashScreenActivity.this,HomeActivity.class);
				
				startActivity(main_Intent);
				// animating switching of 2 activities with fadein fadeout animation
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			}
		}, _splashTime);
		
		}
		else
		{
			Log.i(LOGTAG, "onCreate: Failure network connection (Wifi/Mobile data) not available.");
			Constants.showDialog(SplashScreenActivity.this,"Newtork error", "Please check your internet connection",true);
		}
		

	}
	
}
