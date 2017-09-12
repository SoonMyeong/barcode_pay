package com.selected;

import java.util.HashMap;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.controller.AppController;
import com.example.nam.R;
import com.example.nam.StudentActivity;
import com.helper.SQLiteHandler;
import com.helper.SessionManager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification.Action;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class grid_save_detail extends ActionBarActivity {
	
    private SQLiteHandler db;
    private SessionManager session;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private String food_price, food_name, barcode_number;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_save_detail);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
		setSupportActionBar(toolbar);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
		
		final NetworkImageView image = (NetworkImageView)findViewById(R.id.food_image);
		final NetworkImageView image_barcode = (NetworkImageView)findViewById(R.id.barcode_image);
		final TextView text_one = (TextView)findViewById(R.id.food_name);
		final TextView text_two = (TextView)findViewById(R.id.food_price);
		final TextView text_three = (TextView)findViewById(R.id.barcode_number);
		
		

		
		
		Intent intent = getIntent();
		int intprice;
		
		image.setImageUrl(intent.getStringExtra("food_image"),imageLoader);
		image_barcode.setImageUrl(intent.getStringExtra("barcode_image"), imageLoader);
		text_one.setText(intent.getStringExtra("food_name"));
		intprice = intent.getIntExtra("food_price", 0);
		text_two.setText(Integer.toString(intprice)+"ø¯");
		text_three.setText(intent.getStringExtra("barcode_number"));
	
	}

}
