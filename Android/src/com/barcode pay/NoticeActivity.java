package com.example.nam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import product_model.notice;
import product_model.product;
import product_model.user_product;
import product_model.user_review;

import com.adapters.CustomGridAdapter;
import com.adapters.CustomNoticeAdapter;
import com.adapters.CustomReviewAdapter;
import com.adapters.viewpageradapter_chaeum;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.controller.AppController;
import com.controller.JsonArrayPostRequest;
import com.selected.Notice_detail;
import com.selected.food_detail;
import com.slidingtabs.SlidingTabLayout;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v4.view.ViewPager;

public class NoticeActivity extends ActionBarActivity {

	
	private TextView notice_select;
	private TextView notice_title;
	private TextView notice_detail;
	private TextView notice_day;
	private ListView listview;
	private static final String url = "http://server/notice.php";
	private List<notice> productList = new ArrayList<notice>();
	private CustomNoticeAdapter adapter;
	public String call = "1";

	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.notice_list);
  
	
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
		
		
		 listview =(ListView)findViewById(R.id.notice_listview);
	        adapter = new CustomNoticeAdapter(this,productList);
	        listview.setAdapter(adapter);
	        
			listview.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position,
						long id) {
					// TODO Auto-generated method stub
					
					Intent intent = new Intent(NoticeActivity.this,Notice_detail.class);
					
					intent.putExtra("notice_select", productList.get(position).getnotice_select());
					intent.putExtra("notice_title", productList.get(position).getnotice_title());
					intent.putExtra("notice_detail", productList.get(position).getnotice_detail());
					intent.putExtra("notice_day",productList.get(position).getnotice_day());
					startActivity(intent);
					
				}
			});
		

			get_notice(call);
	
	}
	
	private void get_notice(final String call) {
		
        Map<String, String> params = new HashMap<String, String>();
        params.put("call", call);
        
        // Creating volley request array
        JsonArrayPostRequest jsonArrRequest = new JsonArrayPostRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                    	
                    		
                            for (int i = 0; i < response.length(); i++) {
                        
                            
                            	 try {
                            		 JSONObject obj = response.getJSONObject(i);
                 					notice n = new notice();
                 					n.setnotice_select(obj.getString("notice_select"));
                 					n.setnotice_title(obj.getString("notice_title"));
                 					n.setnotice_detail(obj.getString("notice_detail"));
                 					n.setnotice_day(obj.getString("notice_day"));
                 					productList.add(n);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                            
                            } //for
                    adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
  
                }
            }, params);
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonArrRequest);
    }
	
	
	
}


