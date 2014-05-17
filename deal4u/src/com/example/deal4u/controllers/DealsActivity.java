package com.example.deal4u.controllers;

import com.example.deal4u.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;

public class DealsActivity extends Activity {
	
	ListView dealsList;
	EditText searchEdit;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.deals_screen);
		
		initUI();
		
	}

	private void initUI() {
		// TODO Auto-generated method stub
		
		dealsList = (ListView) findViewById(R.id.deals_listviewID);
		searchEdit = (EditText) findViewById(R.id.deals_searchEditID);
	}

}
