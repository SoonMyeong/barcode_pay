package com.selected;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import product_model.user_product;
import product_model.user_review;

import com.adapters.CustomGrid_SaveAdapter;
import com.adapters.CustomReviewAdapter;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.controller.AppController;
import com.controller.JsonArrayPostRequest;
import com.example.nam.R;
import com.example.nam.StudentActivity;
import com.example.nam.User_product_list;
import com.helper.SQLiteHandler;
import com.helper.SessionManager;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Notification.Action;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Notice_detail extends ActionBarActivity {
	
	private TextView notice_select, notice_title, notice_detail, notice_day;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_detail);
		
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
		
		notice_select = (TextView) findViewById(R.id.notice_select);
		notice_title = (TextView) findViewById(R.id.notice_title);
		notice_detail = (TextView) findViewById(R.id.notice_detail);
		notice_day = (TextView) findViewById(R.id.notice_day);
		
		
		
		Intent intent = getIntent();
		
		notice_select.setText(intent.getStringExtra("notice_select"));
		notice_title.setText(intent.getStringExtra("notice_title"));
		notice_detail.setText(intent.getStringExtra("notice_detail"));
		notice_day.setText(intent.getStringExtra("notice_day"));
		
		

		
		
	}
}
