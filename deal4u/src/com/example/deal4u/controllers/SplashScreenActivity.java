package com.example.deal4u.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;

import com.example.deal4u.R;


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
	
}
