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
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.controller.AppController;
import com.controller.JsonArrayPostRequest;
import com.example.nam.LoginActivity;
import com.example.nam.MainActivity;
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

public class food_detail extends ActionBarActivity {
	
    private SQLiteHandler db;
    private SessionManager session;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	private String food_price, food_name;
	
	private static final String TAG = User_product_list.class.getSimpleName();
	
	private static final String url = "http://server/Product_reviewlist.php";
	private ProgressDialog pDialog;
	private List<user_review> productList = new ArrayList<user_review>();
	private ListView listview;
	private CustomReviewAdapter adapter;
	private TextView noreview,addcount;
	private ImageView userdelete,all_add;
	private LinearLayout all_add_lay;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_detail);
		
		
		listview = (ListView) findViewById(R.id.review_check);
		adapter = new CustomReviewAdapter(this,productList);
		listview.setAdapter(adapter);
		noreview = (TextView) findViewById(R.id.no_review);
		userdelete = (ImageView) findViewById(R.id.user_delete);
		all_add_lay = (LinearLayout) findViewById(R.id.all_add_lay);
		all_add = (ImageView) findViewById(R.id.all_add);
		addcount = (TextView) findViewById(R.id.add_count);
		
		Button buybtn = (Button) findViewById(R.id.buy_pro);
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
		final TextView text_one = (TextView)findViewById(R.id.food_name);
		final TextView text_two = (TextView)findViewById(R.id.food_price);
		final TextView review_number = (TextView) findViewById(R.id.user_number);
		
		Intent intent = getIntent();
		int intprice;
		
		image.setImageUrl(intent.getStringExtra("food_image"),imageLoader);
		text_one.setText(intent.getStringExtra("food_name"));
		intprice = intent.getIntExtra("food_price", 0);
		text_two.setText(Integer.toString(intprice)+"ø¯");
		
		
		final String fname = text_one.getText().toString().trim(); //parameter

		 db = new SQLiteHandler(getApplicationContext());
			
	        // session manager
	        session = new SessionManager(getApplicationContext());
	       
	 
	        // Fetching user details from sqlite
	        HashMap<String, String> user = db.getUserDetails();
			
	        final String check_number = user.get("number");

		
		
		
		
		buybtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stu
				
				Intent intent = new Intent(food_detail.this,food_buy.class);
				
				intent.putExtra("food_image", image.getImageURL());
				intent.putExtra("food_name", text_one.getText().toString());
				intent.putExtra("food_price", text_two.getText().toString());
				
				startActivity(intent);
				
			}
		});
		
		all_add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(food_detail.this,all_add_comment.class);
				intent.putExtra("food_name", text_one.getText().toString());
				
				startActivity(intent);			
			}
		});
		
		
		count_add(fname);
		reviewlist(fname,check_number);
		
		
	}
		
	private void count_add(final String fname) {
		

		
		StringRequest strReq = new StringRequest(Method.POST, "http://114.200.13.66:11111/count_add.php",
								new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								// TODO Auto-generated method stub

								try {
									
										JSONObject jObj = new JSONObject(response);
													
										String add = jObj.getString("COUNT(*)");
										
										addcount.setText(add);
					
								}
									
								 catch (JSONException e) {
									e.printStackTrace();
									Toast.makeText(getApplicationContext(),"Json error :",Toast.LENGTH_LONG).show();
	
								}
								
							}
			
						} , new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								// TODO Auto-generated method stub
								/*Log.e(TAG,"Login Error:" + error.getMessage());*/
								Toast.makeText(getApplicationContext(),
					                        error.getMessage(), Toast.LENGTH_LONG).show();
							}
					            
							})	
								{
			
								protected Map<String, String> getParams() {
									Map<String, String> params = new HashMap<String, String>();
					                params.put("fname", fname);
					 
					                return params;
								}
						 
		};
				
		AppController.getInstance().addToRequestQueue(strReq);
		
	}	


	
	
	private void reviewlist(final String fname, final String check_number) {
		pDialog = new ProgressDialog(this);
		
		pDialog.setMessage("Loading ...");
		pDialog.show();
		
	    Map<String, String> params = new HashMap<String, String>();
	    params.put("fname", fname);
	    params.put("number",check_number);
	    
	    // Creating volley request array
	    JsonArrayPostRequest jsonArrRequest = new JsonArrayPostRequest(url,
	            new Response.Listener<JSONArray>() {
	                @Override
	                public void onResponse(JSONArray response) {
	                	
	                	hidePDialog();
	                		
	                	if(response.length() == 0) {
	                		noreview.setVisibility(android.view.View.VISIBLE);
	                		
	                	}
	                	else {
	                		
	                		noreview.setVisibility(android.view.View.GONE);
	                		listview.setVisibility(android.view.View.VISIBLE);
	                		all_add_lay.setVisibility(android.view.View.VISIBLE);
	                	
	                        for (int i = 0; i < response.length(); i++) {
	                        	
	                        	 try {
	                			JSONObject obj = response.getJSONObject(i);

	    						user_review u = new user_review();
	    						
	    						u.setuser_name(obj.getString("name"));
	    						u.setuser_review(obj.getString("food_review"));
	    						u.setcreate(obj.getString("created_at"));
	    						u.setuser_number(obj.getString("number"));
	    						u.setbarcode_num(obj.getString("barcode_number"));
	    						u.setcheck_user(check_number);
	    						u.setuser_score(obj.getInt("score"));
	    					
	    						
	    						productList.add(u);
	                		
	    						
	    						
	    							
	                    } catch (JSONException e) {
	                        e.printStackTrace();
	                    }
	                        
	                        } //for
	                          
	                        
	                        } //else
	                adapter.notifyDataSetChanged();
	                }
	            }, new Response.ErrorListener() {
	            @Override
	            public void onErrorResponse(VolleyError error) {
	              /*  Log.d(TAG, "Error: " + error.getMessage());*/
	                Toast.makeText(getApplicationContext(),
	                    error.getMessage(), Toast.LENGTH_SHORT).show();
	                hidePDialog();
	            }
	        }, params);
	    // Adding request to request queue
	    AppController.getInstance().addToRequestQueue(jsonArrRequest);
	}	

	
	
	
	private void hidePDialog() {
		if(pDialog != null) {
			pDialog.dismiss();
			pDialog = null;
		}
	}
	private void showDialog() {
		if(!pDialog.isShowing())
			pDialog.show();
	}
		
	
	
	
public boolean onOptionsItemSelected(MenuItem item) 
{    
   switch (item.getItemId()) 
   {        
      case android.R.id.home: 
    	  finish();
         return true;        
      default:            
         return super.onOptionsItemSelected(item);    
   }
}
}
