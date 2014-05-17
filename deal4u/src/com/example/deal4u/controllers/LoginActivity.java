package com.example.deal4u.controllers;

import com.example.deal4u.R;
import com.example.deal4u.Service.serviceControler;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

public class LoginActivity extends Activity implements OnClickListener {
	
	LinearLayout loginBtnLayout, emailLinearLayoutID;
	Button emailLogin, FacebookLogin, loginBtn, showoptionBtn;
	EditText emailEditText, passwordEditTextID;
	SharedPreferences sharedPreferences_put;
	SharedPreferences.Editor editor;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_screen);
		
		initUI();
		handleRequest();
	}

	private void handleRequest() {
		// TODO Auto-generated method stub
		serviceControler serviceobj = new serviceControler(LoginActivity.this);
		String[] serviceparameters = {};
		serviceobj.startService("", serviceparameters);
	}

	private void initUI() {
		// TODO Auto-generated method stub
		
		loginBtnLayout = (LinearLayout) findViewById(R.id.login_loginBtnLinearID);
		emailLinearLayoutID = (LinearLayout) findViewById(R.id.login_emailLinearID);
		
		loginBtnLayout.setVisibility(View.VISIBLE);
		emailLinearLayoutID.setVisibility(View.GONE);
		
		emailLogin = (Button) findViewById(R.id.login_emailBtnId);
		FacebookLogin = (Button) findViewById(R.id.login_facebookBtnId);
		loginBtn = (Button) findViewById(R.id.login_loginBtnId);
		showoptionBtn = (Button) findViewById(R.id.login_showOptionBtnId);
		
		emailEditText = (EditText) findViewById(R.id.login_emailEditTxtID);
		passwordEditTextID = (EditText) findViewById(R.id.login_passwordEditTxtID);
		
		
		emailLogin.setOnClickListener(this);
		FacebookLogin.setOnClickListener(this);
		loginBtn.setOnClickListener(this);
		showoptionBtn.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if (v == emailLogin) {
			
			loginBtnLayout.setVisibility(View.GONE);
			emailLinearLayoutID.setVisibility(View.VISIBLE);
			
		}
		if (v == FacebookLogin) {
			
			// execute fb login here and hit login service
			
		}
		if (v == loginBtn) {
			
			if (Constants.isValidEmail(emailEditText.getText().toString())) {
				
				String emailStr = emailEditText.getText().toString();
				String passwordStr = passwordEditTextID.getText().toString();
				
				// login web service here
				// whne login is success, set shared preference isLogin = true 
				//so that user dosent have to login evey time he opens the application
				
				Intent main_Intent = new Intent(LoginActivity.this,HomeActivity.class);
				startActivity(main_Intent);
				// animating switching of 2 activities with fadein fadeout animation
				overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
				
			}
			else
			{
				Constants.showDialog(LoginActivity.this, "Input Error", "Please enter a valid email", false);
			}
			
		}
		if (v == showoptionBtn) {
			
			loginBtnLayout.setVisibility(View.VISIBLE);
			emailLinearLayoutID.setVisibility(View.GONE);
	
		}
		
	}
	
}
