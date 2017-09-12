package com.selected;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import product_model.user_product;

import com.helper.SQLiteHandler;
import com.helper.SessionManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.controller.AppController;
import com.controller.JsonArrayPostRequest;
import com.example.nam.LoginActivity;
import com.example.nam.MainActivity;
import com.example.nam.R;
import com.example.nam.RegisterActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class food_buy extends ActionBarActivity {

    private SQLiteHandler db;
    private SessionManager session;
	private ProgressDialog pDialog;
	private static final String TAG = food_buy.class.getSimpleName();
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_buy);
		
		Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
		
		final TextView Check_money = (TextView) findViewById(R.id.check_money);
		final TextView Check_point = (TextView) findViewById(R.id.check_point);
		final TextView Buy_product = (TextView) findViewById(R.id.buy_product);
		final TextView Buy_product_price= (TextView) findViewById(R.id.buy_product_price);
		
		Button Buy_money = (Button) findViewById(R.id.buy_money);
		Button Buy_money_point = (Button) findViewById(R.id.buy_money_point);
		Button Use_check = (Button) findViewById(R.id.use_check);
		
		final EditText Use_money = (EditText) findViewById(R.id.use_money);
		final EditText Use_point = (EditText) findViewById(R.id.use_point);
		
		final LinearLayout Invisiblemenu = (LinearLayout) findViewById(R.id.invisible_menu);
		
		setSupportActionBar(toolbar);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		toolbar.setNavigationOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
		
		pDialog = new ProgressDialog(this);
		pDialog.setCancelable(false);
		
	       db = new SQLiteHandler(getApplicationContext());
			
	        // session manager
	        session = new SessionManager(getApplicationContext());
	       
	 
	        // Fetching user details from sqlite
	        HashMap<String, String> user = db.getUserDetails();
			
	        final String number = user.get("number");
	        final String money = user.get("money");
	        final String point = user.get("point"); 
	        
	        Check_money.setText(String.valueOf(money)+"ø¯");
	        Check_point.setText(String.valueOf(point)+"P"); 
	        
	        Intent intent = getIntent();
	        String image_url;
	        
	        Buy_product.setText(intent.getStringExtra("food_name"));
			Buy_product_price.setText(intent.getStringExtra("food_price"));
			Buy_money.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
				
					
					String buy_product = Buy_product.getText().toString().trim();
					String buy_product_price = Buy_product_price.getText().toString().trim();
					
					
					Buy_product(number, money, point, buy_product, buy_product_price);
					
					
					
				}
			});
			
			Buy_money_point.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Invisiblemenu.setVisibility(android.view.View.VISIBLE);
	
				}
			});
			
			Use_check.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					final String buy_product = Buy_product.getText().toString().trim();
					final String buy_product_price = Buy_product_price.getText().toString().trim();
					final String use_money =Use_money.getText().toString().trim();
					final String use_point =Use_point.getText().toString().trim();
					
					Buy_point_product(number, money, point, use_money, use_point, buy_product ,buy_product_price);
					
				}
			});
	}
	
	private void Buy_product(final String number, final String money, final String point, final String buy_product, final String buy_product_price){
		
		String tag_string_req = "buy_product";
		pDialog.setMessage("±∏¿‘¡ﬂ ...");
		showDialog();
	
		StringRequest strReq = new StringRequest(Method.POST,"http://114.200.13.66:11111/Buy_product.php"
													,new Response.Listener<String>() {
														@Override
														public void onResponse(
																String response) {
														/*	Log.d(TAG, "Buy_product Response: " + response.toString());*/
															hideDialog();
															
															try {
																JSONObject jObj = new JSONObject(response);
																boolean error = jObj.getBoolean("error");
																if(!error){
																	String money = jObj.getString("money");
																	String point = jObj.getString("point");
																			JSONObject user = jObj.getJSONObject("user");
																	
																			String barcode = user.getString("barcode");
																			
																			db.updateUsers(number, money, point);
																			
																			checkBarcode(barcode); //¿ÃπÃ¡ˆ∏∏µÂ¥¬∞≈
																	
																	
																	Toast.makeText(getApplicationContext(), "±∏∏≈∞° øœ∑·µ«æ˙Ω¿¥œ¥Ÿ. ∫∏∞¸«‘¿ª »Æ¿Œ«ÿ ¡÷ººø‰!",
																			Toast.LENGTH_LONG).show();
																	
																	Intent intent = new Intent(
												                             food_buy.this,
												                              MainActivity.class);
																	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
												                    startActivity(intent);
												                    finish();
																	
																} else {
																	String errorMsg = jObj.getString("error_msg");
																	Toast.makeText(getApplicationContext(),
											                                errorMsg, Toast.LENGTH_LONG).show();
																}
																
															} catch(JSONException e) {
																e.printStackTrace();
															}
														
														}
		
				
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				/*Log.e(TAG, "Buy_product Error: " + error.getMessage());*/
				Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
			}
		}) {
			
		
			
			protected Map<String, String> getParams() {
				Map<String,String> params = new HashMap<String,String>();
				params.put("number",number);
				params.put("money", money);
				params.put("point", point);
				params.put("fname", buy_product);
				params.put("fprice", buy_product_price);
				
				return params;
			}
			
		};
		AppController.getInstance().addToRequestQueue(strReq,tag_string_req);
	}
	
	
	private void checkBarcode(final String barcode) {
		
	String tag_string_req = "barcode_info";
	
	StringRequest strReq = new StringRequest(Method.POST, "http://114.200.13.66:11111/barcode/save_barcode/save_barcode.php",
							new Response.Listener<String>() {
						@Override
						public void onResponse(String response) {
							// TODO Auto-generated method stub

						}
		
					} , new Response.ErrorListener() {

						@Override
						public void onErrorResponse(VolleyError error) {
							// TODO Auto-generated method stub
						
							Toast.makeText(getApplicationContext(),
				                        error.getMessage(), Toast.LENGTH_LONG).show();
				            
						}
					}) {
		
							protected Map<String, String> getParams() {
								Map<String, String> params = new HashMap<String, String>();
				                params.put("barcode", barcode);
				 
				                return params;
							}
	};
			
	AppController.getInstance().addToRequestQueue(strReq,tag_string_req);
	
}
	
private void Buy_point_product(final String number, final String money, final String point,final String use_money, final String use_point, final String buy_product, final String buy_product_price){
		
		String tag_string_req = "buy_product";
		pDialog.setMessage("±∏¿‘¡ﬂ ...");
		showDialog();
	
		StringRequest strReq = new StringRequest(Method.POST,"http://server/Buy_point_product.php"
													,new Response.Listener<String>() {
														@Override
														public void onResponse(
																String response) {
														/*	Log.d(TAG, "Buy_product_point Response: " + response.toString());*/
															hideDialog();
															
															try {
																JSONObject jObj = new JSONObject(response);
																boolean error = jObj.getBoolean("error");
																if(!error){
																	String money = jObj.getString("money");
																	String point = jObj.getString("point");
																			JSONObject user = jObj.getJSONObject("user");
																	
																			String barcode = user.getString("barcode");
																			
																			db.updateUsers(number, money, point);
																			
																			checkBarcode(barcode); //¿ÃπÃ¡ˆ∏∏µÂ¥¬∞≈
																	
																	
																	Toast.makeText(getApplicationContext(), "±∏∏≈∞° øœ∑·µ«æ˙Ω¿¥œ¥Ÿ. ∫∏∞¸«‘¿ª »Æ¿Œ«ÿ ¡÷ººø‰!",
																			Toast.LENGTH_LONG).show();
																	
																	Intent intent = new Intent(
												                             food_buy.this,
												                              MainActivity.class);
																	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
												                    startActivity(intent);
												                    finish();
																	
																} else {
																	String errorMsg = jObj.getString("error_msg");
																	Toast.makeText(getApplicationContext(),
											                                errorMsg, Toast.LENGTH_LONG).show();
																}
																
															} catch(JSONException e) {
																e.printStackTrace();
															}
														
														}
		
				
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				/*Log.e(TAG, "Buy_point_product Error: " + error.getMessage());*/
				Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
			}
		}) {
			
		
			
			protected Map<String, String> getParams() {
				Map<String,String> params = new HashMap<String,String>();
				params.put("number",number);
				params.put("money", money);
				params.put("point", point);
				params.put("use_money", use_money);
				params.put("use_point", use_point);
				params.put("fname", buy_product);
				params.put("fprice", buy_product_price);
				
				return params;
			}
			
		};
		AppController.getInstance().addToRequestQueue(strReq,tag_string_req);
	}
	
	

	
	
	



	
	private void showDialog() {
		if(!pDialog.isShowing())
			pDialog.show();
	}
	
	private void hideDialog() {
		if(pDialog.isShowing())
			pDialog.dismiss();
	}
	
	
	
	
}
