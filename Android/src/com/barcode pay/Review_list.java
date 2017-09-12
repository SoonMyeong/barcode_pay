package com.example.nam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import product_model.user_product;




import com.adapters.CustomGrid_SaveAdapter;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.controller.AppController;
import com.controller.JsonArrayPostRequest;
import com.helper.SQLiteHandler;
import com.helper.SessionManager;
import com.selected.Product_Review_detail;
import com.selected.food_detail;
import com.selected.grid_save_detail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Review_list extends AppCompatActivity {

	

	
	
	private static final String TAG = Review_list.class.getSimpleName();
	
	private static final String url = "http://server/My_save_review.php";
	private ProgressDialog pDialog;
	private List<user_product> productList = new ArrayList<user_product>();
	private GridView gridView;
	private CustomGrid_SaveAdapter adapter;
    private SQLiteHandler db;
    private SessionManager session;
    private TextView hidetext;
 
	

	
	
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.grid_review_list);
  
		gridView = (GridView) findViewById(R.id.list_main);
		hidetext = (TextView) findViewById(R.id.hide_text);
		adapter = new CustomGrid_SaveAdapter(this,productList);
		gridView.setAdapter(adapter);
		
		
		
		

		
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
		
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(Review_list.this,Product_Review_detail.class);
				
				intent.putExtra("food_image", productList.get(position).getfood_image());
				intent.putExtra("food_name", productList.get(position).getfood_name());
				intent.putExtra("barcode_number", productList.get(position).getbarcode_number());
				
				startActivity(intent);
				
			}
		});
		
		
		  // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
		
        // session manager
        session = new SessionManager(getApplicationContext());
       
 
 
        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
        
        final String number = user.get("number");
		
		/*Userproductlist(number);*/
        userLocation(number);
        

        

        
	}
	
	
	
	
	/* Ω∫∆Æ∏µ∏Æƒ˘Ω∫∆Æ∑Œ πﬁ¿∏¥œ±Ó ≥ π´¥¿∑¡º≠ æ»æµ øπ¡§ ±◊≥… ¿Ã∑∏∞‘µµ πﬁ¿ªº¯ ¿÷¥Ÿ∑Œ æÀæ∆µŒ±‚*/
	
/*	private void Userproductlist(final String number) {
		pDialog = new ProgressDialog(this);
		String tag_string_req = "req_register";
		pDialog.setMessage("Lodaing...");
		showDialog();
		
	
		StringRequest strReq = new StringRequest(Method.POST,"http://server/My_save_product.php"
													,new Response.Listener<String>() {
														@Override
														public void onResponse(
																String response) {
															Log.d(TAG, "Register Response: " + response.toString());
															
															
																try {
																JSONArray jAry = new JSONArray(response);
		
																//Parsing json
																for(int i=0; i<response.length(); i++) {
																	try{
																		JSONObject obj = jAry.getJSONObject(i);
																		user_product p = new user_product();
																		p.setfood_name(obj.getString("food_name"));
																		p.setfood_image(obj.getString("food_image"));
																		p.setfood_price(((Number) obj.get("food_price")).intValue());
																		p.setbarcode_image(obj.getString("barcode_image"));
																		p.setbarcode_number(obj.getString("barcode_number"));

																		productList.add(p);
																	
															} catch(JSONException e) {
																e.printStackTrace();
															}
																
																}
																} catch(JSONException e) {
																	e.printStackTrace();
																}
																adapter.notifyDataSetChanged();
																hidePDialog();
														}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Log.e(TAG, "Registration Error: " + error.getMessage());
				hidePDialog();

			}
		}) {
			
		
			
			protected Map<String, String> getParams() {
				Map<String,String> params = new HashMap<String,String>();
				params.put("number",number);			
				return params;
			}
			
		};
		AppController.getInstance().addToRequestQueue(strReq,tag_string_req);
	}
	
	*/
	
	
	
/*	private void registerUser1(final String number) {
		pDialog = new ProgressDialog(this);
		
		pDialog.setMessage("Loading ...");
		pDialog.show();

		JsonArrayRequest productReq = new JsonArrayRequest(Method.POST,url,new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				// TODO Auto-generated method stub
				Log.d(TAG,response.toString());
				hidePDialog();
				
				//Parsing json
				for(int i=0; i<response.length(); i++) {
					try{
						JSONObject obj = response.getJSONObject(i);
						user_product p = new user_product();
						p.setfood_name(obj.getString("food_name"));
						p.setfood_image(obj.getString("food_image"));
						p.setfood_price(((Number) obj.get("food_price")).intValue());
						p.setbarcode_image(obj.getString("barcode_image"));
						p.setbarcode_number(obj.getString("barcode_number"));

						productList.add(p);
					} catch(JSONException e) {
						e.printStackTrace();
					}
				}
				
				adapter.notifyDataSetChanged();
			}
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				VolleyLog.d(TAG,"Error: "+ error.getMessage());
				hidePDialog();
			}
			


	
		});
		
		AppController.getInstance().addToRequestQueue(productReq);

	} */
	
	/* jsonarrayrequestø° post∏¶ «“ºˆ ¿÷¥¬ ƒøΩ∫≈“«ÿ≥Ì ¿⁄∑·∏¶ Ω¿µÊ ^^! ≥™µµ ∫–ºÆ«ÿº≠ ƒøΩ∫≈“«ÿ∫¡æﬂ¡ˆ */
	
	private void userLocation(final String number) {
		pDialog = new ProgressDialog(this);
		
		pDialog.setMessage("Loading ...");
		pDialog.show();
		
        Map<String, String> params = new HashMap<String, String>();
        params.put("number", number);
        
        // Creating volley request array
        JsonArrayPostRequest jsonArrRequest = new JsonArrayPostRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                    	
                    	hidePDialog();
                    	
                    	if(response.length() == 0) {
                    		hidetext.setVisibility(android.view.View.VISIBLE);
                    	}
                    	else {
                    		
                    		gridView.setVisibility(android.view.View.VISIBLE);
                    		
                            for (int i = 0; i < response.length(); i++) {
                            	
                            
                            	 try {
                    			JSONObject obj = response.getJSONObject(i);
        						user_product p = new user_product();
        						p.setfood_name(obj.getString("food_name"));
        						p.setfood_image(obj.getString("food_image"));
        						p.setfood_price(((Number) obj.get("food_price")).intValue());
        						p.setbarcode_image(obj.getString("barcode_image"));
        						p.setbarcode_number(obj.getString("barcode_number"));

        						productList.add(p);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                            
                            } // for
                            } // else
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*--------------------------------------------------------------------*/
	
	public void onDestory() {
		super.onDestroy();
		hidePDialog();
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
		


}
