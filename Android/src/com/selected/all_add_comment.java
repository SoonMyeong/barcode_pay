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
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.adapters.CustomGrid_SaveAdapter;
import com.adapters.CustomReviewAdapter;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.controller.AppController;
import com.controller.JsonArrayPostRequest;
import com.example.nam.R;
import com.example.nam.User_product_list;
import com.helper.SQLiteHandler;
import com.helper.SessionManager;

public class all_add_comment extends ActionBarActivity {
	private static final String TAG = all_add_comment.class.getSimpleName();
	
	private static final String url = "http://server/Product_reviewlist_all.php";
	private ProgressDialog pDialog;
	private List<user_review> productList = new ArrayList<user_review>();
	private CustomReviewAdapter adapter;
	private ListView review_all;
	
    private SQLiteHandler db;
    private SessionManager session;
 
	
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.review_list_all);
		
		review_all = (ListView) findViewById(R.id.review_all_list);
		adapter = new CustomReviewAdapter(this, productList);
		review_all.setAdapter(adapter);
		
		
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
		
		
		
		Intent intent = getIntent();

		String fname = intent.getStringExtra("food_name");
		
		 db = new SQLiteHandler(getApplicationContext());
			
	        // session manager
	        session = new SessionManager(getApplicationContext());
	       
	 
	        // Fetching user details from sqlite
	        HashMap<String, String> user = db.getUserDetails();
			
	        final String check_number = user.get("number");
	        
	        userLocation(fname,check_number);

		
		
		
		
	}
	private void userLocation(final String fname,final String check_number) {
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

                    adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                   /* Log.d(TAG, "Error: " + error.getMessage());*/
                    Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
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

	
}
