package com.example.deal4u.controllers;

import com.example.deal4u.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DealDetailActivity extends Activity implements OnClickListener {
	
	TextView titleText, addressText, distanceText, descriptionText, validityText, dealText, showMapText;
	Button okBtn;
	ImageView dealImage;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deal_detail_screen);
		
		initUI();
	}

	private void initUI() {
		// TODO Auto-generated method stub
		
		titleText = (TextView) findViewById(R.id.dealDetail_TitleTextID);
		addressText = (TextView) findViewById(R.id.dealDetail_AddressTextID);
		distanceText = (TextView) findViewById(R.id.dealDetail_DistanceTextID);
		descriptionText = (TextView) findViewById(R.id.dealDetail_DescriptionTextID);
		validityText = (TextView) findViewById(R.id.dealDetail_ValidityTextID);
		dealText = (TextView) findViewById(R.id.dealDetail_dealTextID);
		showMapText = (TextView) findViewById(R.id.dealDetail_showMapTextID);
		dealImage = (ImageView) findViewById(R.id.dealDetail_ImageID);
		
		okBtn = (Button) findViewById(R.id.dealDetail_okBtnID);
		
		okBtn.setOnClickListener(this);	
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		if (v ==okBtn) {
			
			DealDetailActivity.this.finish();
			
		}
		if (v == showMapText) {
			
			Intent main_Intent = new Intent(DealDetailActivity.this,DestinationMapActivity.class);
			startActivity(main_Intent);
			// animating switching of 2 activities with fadein fadeout animation
			overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
			
		}
		
	}

}
