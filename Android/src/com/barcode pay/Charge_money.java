package com.example.nam;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.controller.AppController;
import com.example.nam.R;
import com.helper.SQLiteHandler;
import com.helper.SessionManager;
import com.selected.food_buy;



public class Charge_money extends ActionBarActivity {
	
	 Handler handler = new Handler();  // ø‹∫Œæ≤∑πµÂ ø°º≠ ∏ﬁ¿Œ UI»≠∏È¿ª ±◊∏±∂ß ªÁøÎ
	    private SQLiteHandler db;
	    private SessionManager session;
	    private String barcode_url;
	    private static final String TAG = Charge_money.class.getSimpleName();
		private ProgressDialog pDialog;
	    
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.charge_money);
	        // ¿Œ≈Õ≥› ªÛ¿« ¿ÃπÃ¡ˆ ∫∏ø©¡÷±‚
	 
	        // 1. ±««—¿ª »πµÊ«—¥Ÿ (¿Œ≈Õ≥›ø° ¡¢±Ÿ«“ºˆ ¿÷¥¬ ±««—¿ª »πµÊ«—¥Ÿ)  - ∏ﬁ¥œ∆‰Ω∫∆Æ ∆ƒ¿œ
	        // 2. Thread ø°º≠ ¿•¿« ¿ÃπÃ¡ˆ∏¶ πﬁæ∆ø¬¥Ÿ - honeycomb(3.0) πˆ¡Ø ∫Œ≈Õ πŸ≤Ò
	        // 3. ø‹∫Œæ≤∑πµÂø°º≠ ∏ﬁ¿Œ UIø° ¡¢±Ÿ«œ∑¡∏È Handler ∏¶ ªÁøÎ«ÿæﬂ «—¥Ÿ.
	        
			pDialog = new ProgressDialog(this);
			pDialog.setCancelable(false);
			Button finish = (Button) findViewById(R.id.charge_finish);
	        
	        db = new SQLiteHandler(getApplicationContext());
			
	        // session manager
	        session = new SessionManager(getApplicationContext());
	       
	 
	        // Fetching user details from sqlite
	        HashMap<String, String> user = db.getUserDetails();
	        
	        final String number = user.get("number");
	        final String money = user.get("money");
	        final String point = user.get("point");
	        String barcode = user.get("barcode_url");
			
	        barcode_url = barcode;
	 
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
	        
	        //Thread t = new Thread(Runnable ∞¥√º∏¶ ∏∏µÁ¥Ÿ);
	        Thread t = new Thread(new Runnable() {
	            @Override
	            public void run() {    // ø¿∑° ∞≈∏± ¿€æ˜¿ª ±∏«ˆ«—¥Ÿ
	                // TODO Auto-generated method stub
	                try{
	                    // ∞¡ ø‹øÏ¥¬∞‘ ¡¡¥Ÿ -_-;
	                    final ImageView iv = (ImageView)findViewById(R.id.person_image);
	                    URL url = new URL(barcode_url);
	                    InputStream is = url.openStream();
	                    final Bitmap bm = BitmapFactory.decodeStream(is);
	                    handler.post(new Runnable() {
	 
	                        @Override
	                        public void run() {  // »≠∏Èø° ±◊∑¡¡Ÿ ¿€æ˜
	                            iv.setImageBitmap(bm);
	                        }
	                    });
	                    iv.setImageBitmap(bm); //∫Ò∆Æ∏  ∞¥√º∑Œ ∫∏ø©¡÷±‚
	                } catch(Exception e){
	 
	                }
	 
	            }
	        });
	 
	        t.start();
	        
	        finish.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					My_Status(number);
					
					
				}
			});
	        

	        
	    } // end onCreate
   
	    
private void My_Status(final String number){
		
		String tag_string_req = "My_status";
	
		StringRequest strReq = new StringRequest(Method.POST,"http://server/My_status.php"
													,new Response.Listener<String>() {
														@Override
														public void onResponse(
																String response) {
														/*	Log.d(TAG,"charge money :" + response.toString());*/
														
															
															try {
																JSONObject jObj = new JSONObject(response);
																boolean error = jObj.getBoolean("error");
																if(!error){

																			
																	
																			String money = jObj.getString("money");
																			String point = jObj.getString("point");
																			String number =jObj.getString("number");
																			
																			db.updateUsers(number, money, point);
																			
																			Intent intent = new Intent(
														                            Charge_money.this,
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
				/*Log.e(TAG, "Status Error: " + error.getMessage());*/
				Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
 
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
	    
	    
	    
	} // end class


