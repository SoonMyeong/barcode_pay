package com.selected;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.controller.AppController;
import com.example.nam.MainActivity;
import com.example.nam.R;
import com.helper.SQLiteHandler;
import com.helper.SessionManager;

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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


public class Product_Review_detail extends ActionBarActivity {

	private TextView food_name,barcode_num;
	private EditText review;
	private Button review_send;
	private RatingBar user_score;
	private SQLiteHandler db;
   private SessionManager session;
   ImageLoader imageLoader = AppController.getInstance().getImageLoader();
   private ProgressDialog pDialog;
   private float score;

   
   @Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.review_detail);
	
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
	
	pDialog = new ProgressDialog(this);
	pDialog.setCancelable(false);
	
	  // SqLite database handler
    db = new SQLiteHandler(getApplicationContext());
	
    // session manager
    session = new SessionManager(getApplicationContext());
   

    // Fetching user details from sqlite
    HashMap<String, String> user = db.getUserDetails();
	
	
	food_name = (TextView) findViewById(R.id.food_name);
	barcode_num = (TextView) findViewById(R.id.barcode_number);
	review = (EditText) findViewById(R.id.review_write);
	review_send = (Button) findViewById(R.id.review_send);
	user_score = (RatingBar) findViewById(R.id.user_score);
	final NetworkImageView image = (NetworkImageView)findViewById(R.id.food_image);
	
	
	
	
	Intent intent = getIntent();
	
	image.setImageUrl(intent.getStringExtra("food_image"),imageLoader);
	food_name.setText(intent.getStringExtra("food_name"));
	barcode_num.setText(intent.getStringExtra("barcode_number"));
	
	final String user_number = user.get("number"); //user_number
    final String user_name = user.get("name"); //user_name
    final String fname = food_name.getText().toString().trim(); //food_name 
    final String barcode = barcode_num.getText().toString().trim(); //barcode_number
    
    
    
   review_send.setOnClickListener(new OnClickListener() {
	
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		final String write_review = review.getText().toString();
		
		RatingBar rating = (RatingBar) findViewById(R.id.user_score);
		final String user_score = String.valueOf(rating.getRating());
		
		if(write_review.length()>=5 && write_review.length() <= 50) {
			Send_review(write_review, user_name, fname, barcode, user_number,user_score);
		} else {
			Toast.makeText(getApplicationContext(), "»ƒ±‚∏¶ 5±€¿⁄ ¿ÃªÛ  50±€¿⁄ ¿Ã«œ∑Œ ¿‘∑¬«ÿ¡÷ººø‰.", Toast.LENGTH_LONG).show();
		}
			
	}
});
    

}
	
   
   private void Send_review(final String write, final String user_name, final String fname,
		   final String barcode, final String user_number,final String user_score){
		
		String tag_string_req = "send_review";
		pDialog.setMessage("»ƒ±‚ µÓ∑œ¡ﬂ ...");
		showDialog();
	
		StringRequest strReq = new StringRequest(Method.POST,"http://server/Send_review.php"
													,new Response.Listener<String>() {
														@Override
														public void onResponse(
																String response) {
															
															hideDialog();
															
								
																	Toast.makeText(getApplicationContext(), "»ƒ±‚∞° µÓ∑œµ«æ˙Ω¿¥œ¥Ÿ. ∞®ªÁ«’¥œ¥Ÿ.",
																			Toast.LENGTH_LONG).show();
																	
																	Intent intent = new Intent(
												                             Product_Review_detail.this,
												                              MainActivity.class);
																	intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
												                    startActivity(intent);
												                    finish();
										
														}
		
				
		}, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
				Toast.makeText(getApplicationContext(),
                       error.getMessage(), Toast.LENGTH_LONG).show();
               hideDialog();
			}
		}) {
			
		
			
			protected Map<String, String> getParams() {
				Map<String,String> params = new HashMap<String,String>();
				params.put("write",write);
				params.put("user_name", user_name);
				params.put("fname", fname);
				params.put("barcode", barcode);
				params.put("user_number", user_number);
				params.put("user_score", user_score);

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
