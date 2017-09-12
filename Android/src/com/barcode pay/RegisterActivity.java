package com.example.nam;



import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.StringRequest;
import com.controller.AppController;
import com.helper.SQLiteHandler;
import com.helper.SessionManager;

public class RegisterActivity extends Activity {
	private static final String TAG = RegisterActivity.class.getSimpleName();
	private Button btnRegister;
	private Button btnLinkToLogin;
	private EditText inputFullName;
	private EditText inputNumber;
	private EditText inputPassword;
	private ProgressDialog pDialog;
	private SessionManager session;
	private SQLiteHandler db;
	


	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

		inputFullName = (EditText) findViewById(R.id.name);
		inputNumber = (EditText) findViewById(R.id.number);
		inputPassword = (EditText) findViewById(R.id.password);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLogin);
        
		
        InputFilter filterKor = new InputFilter() {
			@Override
			public CharSequence filter(CharSequence source, int start, int end,
					Spanned dest, int dstart, int dend) {
				Pattern ps = Pattern.compile("^[§°-§”∞°-∆R]*$");
                if (!ps.matcher(source).matches()) { 
                    return ""; 
                } 
				return null;
			} 
        }; 
        
        InputFilter filterNum = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern ps = Pattern.compile("^[0-9]*$"); 
                if (!ps.matcher(source).matches()) { 
                    return ""; 
                } 
                return null; 
            } 
        }; 
        
        inputFullName.setFilters(new InputFilter[]{filterKor}); //«—±€∏∏ ¿‘∑¬πﬁµµ∑œ ¿˚øÎ
        inputNumber.setFilters(new InputFilter[] {filterNum}); //º˝¿⁄∏∏ ¿‘∑¬πﬁµµ∑œ ¿˚øÎ

        
        
		pDialog = new ProgressDialog(this);
		pDialog.setCancelable(false);
		
		db = new SQLiteHandler(getApplicationContext());
		
		session = new SessionManager(getApplicationContext());
		
		if(session.isLoggedIn()) {
			Intent intent = new Intent(RegisterActivity.this,
					MainActivity.class);
			startActivity(intent);
			finish();
		}
		
		btnRegister.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				String name = inputFullName.getText().toString().trim();
				String number = inputNumber.getText().toString().trim();
				String password = inputPassword.getText().toString().trim();
				
				
				if(!name.isEmpty() && !number.isEmpty() && !password.isEmpty()) {
					registerUser(name,number,password);
				} else {
					Toast.makeText(getApplicationContext(), "∏µŒ ¿‘∑¬«ÿ¡÷ººø‰!", Toast.LENGTH_LONG).show();
					
				}

				
			}
		});
		
		 btnLinkToLogin.setOnClickListener(new View.OnClickListener() {
			 
	            public void onClick(View view) {
	                Intent i = new Intent(getApplicationContext(),
	                        LoginActivity.class);
	                startActivity(i);
	                finish();
	            }
	        });
	}
	

		
	private void registerUser(final String name, final String number, final String password) {
		
		String tag_string_req = "req_register";
		pDialog.setMessage("»∏ø¯∞°¿‘¡ﬂ ...");
		showDialog();
	
		StringRequest strReq = new StringRequest(Method.POST,"http://server/REGISTER.php"
													,new Response.Listener<String>() {
														@Override
														public void onResponse(
																String response) {
														/*	Log.d(TAG, "Register Response: " + response.toString());*/
															hideDialog();
															
															try {
																JSONObject jObj = new JSONObject(response);
																boolean error = jObj.getBoolean("error");
																if(!error) {
																	String uid = jObj.getString("uid");
																	
																	JSONObject user = jObj.getJSONObject("user");
																	String name = user.getString("name");
																	String number = user.getString("number");
																	String created_at = user.getString("created_at");
																	String barcode = user.getString("barcode");
																	String barcode_url = user.getString("barcode_url");
																	String money = user.getString("money");
																	String point = user.getString("point");
																	
																	checkBarcode(barcode); //¿ÃπÃ¡ˆ∏∏µÂ¥¬∞≈
																	
														db.addUser(name, number,uid,created_at,barcode,barcode_url,money,point);
																	
																	
																	
																	Toast.makeText(getApplicationContext(), "»∏ø¯∞°¿‘ º∫∞¯¿‘¥œ¥Ÿ ∑Œ±◊¿Œ«œººø‰!",
																			Toast.LENGTH_LONG).show();
																	
																	Intent intent = new Intent(
												                             RegisterActivity.this,
												                              LoginActivity.class);
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
			/*	Log.e(TAG, "Registration Error: " + error.getMessage());*/
				Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_LONG).show();
                hideDialog();
			}
		}) {
			
		
			
			protected Map<String, String> getParams() {
				Map<String,String> params = new HashMap<String,String>();
				params.put("name", name);
				params.put("number",number);
				params.put("password", password);
				
				return params;
			}
			
		};
		AppController.getInstance().addToRequestQueue(strReq,tag_string_req);
	}
	
	
	/*
	 * 
	 */
	private void checkBarcode(final String barcode) {
	
	String tag_string_req = "barcode_info";
	
	StringRequest strReq = new StringRequest(Method.POST, "http://server/barcode/personal_barcode/barcode.php",
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
	
	
	
	
	private void showDialog() {
		if(!pDialog.isShowing())
			pDialog.show();
	}
	
	private void hideDialog() {
		if(pDialog.isShowing())
			pDialog.dismiss();
	}
}
