package com.example.deal4u.controllers;

import com.example.deal4u.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class HomeActivity extends Activity implements OnClickListener {
	
	Button movieBtn, foodBtn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.home_screen);
		
		initUI();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		
		
		
	}
	
	public void foodDeals(View v){

		Intent main_Intent = new Intent(HomeActivity.this,DealsActivity.class);
		startActivity(main_Intent);
		// animating switching of 2 activities with fadein fadeout animation
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		
	}
	
	public void movieDeals(View v){
		Intent main_Intent = new Intent(HomeActivity.this,DealsActivity.class);
		startActivity(main_Intent);
		// animating switching of 2 activities with fadein fadeout animation
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if (v == movieBtn) {
			
			
			
		}
		if (v == foodBtn) {
			
			
		}
		
	}

}
