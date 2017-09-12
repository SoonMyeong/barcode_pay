vpackage com.example.nam;



import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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



public class LoginActivity extends Activity {
	
	private static final String TAG = RegisterActivity.class.getSimpleName();
	private EditText inputNumber;
	private EditText inputpassword;
	private Button btnLogin;
	private Button btnLinkToRegister;
	private SQLiteHandler db;
	private SessionManager session;
	private ProgressDialog pDialog;
	
	String URL1="http://114.200.13.66/barcode/code_";
	String URL2=".png";
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		inputNumber = (EditText) findViewById(R.id.number);
		inputpassword = (EditText) findViewById(R.id.password);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		
		btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegister);
		
		pDialog = new ProgressDialog(this);
		pDialog.setCancelable(false);
		
		db= new SQLiteHandler(getApplicationContext());
		
		session = new SessionManager(getApplicationContext());
		
		if(session.isLoggedIn()) {
			Intent intent = new Intent(LoginActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
			}
		
		
		btnLogin.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				String number = inputNumber.getText().toString().trim();
				String password = inputpassword.getText().toString().trim();
				
				if(!number.isEmpty() && !password.isEmpty()) {
					checkLogin(number,password);
				} else {
					Toast.makeText(getApplicationContext(), "∏µŒ ¿‘∑¬«ÿ¡÷ººø‰!", Toast.LENGTH_LONG).show();
				}
				
			}
		});	
		
		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View view) {
			Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
			startActivity(i);
			finish();
				
			}
		});		
	}
	
	private void checkLogin(final String number, final String password) {
		
		String tag_string_req = "req_login";
		
		pDialog.setMessage("∑Œ±◊¿Œ ¡ﬂ ...");
		showDialog();
		
		StringRequest strReq = new StringRequest(Method.POST, "http://server/Login.php",
								new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								// TODO Auto-generated method stub
							/*	Log.d(TAG,"Login Response :" + response.toString());*/
								hideDialog();
								try {
									JSONObject jObj = new JSONObject(response);
									boolean error = jObj.getBoolean("error");
									
									
									if(!error) {
										session.setLogin(true);
										
										String uid = jObj.getString("uid");
										
										JSONObject user = jObj.getJSONObject("user");
										String name = user.getString("name");
										String number = user.getString("number");
										String created_at = user.getString("created_at");
										String barcode = user.getString("barcode");
										String barcode_url = user.getString("barcode_url");
										String money = user.getString("money");
										String point = user.getString("point");
										db.addUser(name, number,uid,created_at,barcode,barcode_url,money,point);
										
										Intent intent = new Intent(
					                             LoginActivity.this,
					                              MainActivity.class);
					                    startActivity(intent);
					                    finish();
										
									} else {
										String errorMsg = jObj.getString("error_msg");
										Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
		
									}
								} catch (JSONException e) {
									e.printStackTrace();
									Toast.makeText(getApplicationContext(),"Json error :",Toast.LENGTH_LONG).show();
	
								}
								
							}
			
						} , new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								// TODO Auto-generated method stub
							/*	Log.e(TAG,"Login Error:" + error.getMessage());*/
								Toast.makeText(getApplicationContext(),
					                        error.getMessage(), Toast.LENGTH_LONG).show();
					            
							}
						}) {
			
								protected Map<String, String> getParams() {
									Map<String, String> params = new HashMap<String, String>();
					                params.put("number", number);
					                params.put("password", password);
					 
					                return params;
								}
		};
				
		AppController.getInstance().addToRequestQueue(strReq,tag_string_req);
		
	}	
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
 
    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
}
