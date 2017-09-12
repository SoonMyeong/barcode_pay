package com.adapters;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import product_model.product;
import product_model.user_review;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.sax.StartElementListener;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Request.Method;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.controller.AppController;
import com.example.nam.LoginActivity;
import com.example.nam.MainActivity;
import com.example.nam.R;
import com.helper.SQLiteHandler;
import com.helper.SessionManager;
import com.selected.Product_Review_detail;
import com.selected.food_detail;

public class CustomReviewAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<user_review> productItems;

	
	public CustomReviewAdapter(Activity activity, List<user_review> productItems) {
		this.activity = activity;
		this.productItems = productItems;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return productItems.size();
	}

	@Override
	public Object getItem(int location) {
		// TODO Auto-generated method stub
		return productItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		if(inflater == null) {
			inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}
		if(convertView == null) {
			convertView = inflater.inflate(R.layout.review_row,null);
		}
		
		TextView user_name = (TextView) convertView.findViewById(R.id.user_name);
		TextView user_review = (TextView) convertView.findViewById(R.id.user_review);
		TextView created = (TextView) convertView.findViewById(R.id.user_create);
		TextView user_number = (TextView) convertView.findViewById(R.id.user_number);
		ImageView user_delete = (ImageView) convertView.findViewById(R.id.user_delete);
		final RatingBar user_score = (RatingBar) convertView.findViewById(R.id.user_score);
		
		
			
		
		user_review u = productItems.get(position);
		
		user_name.setText(u.getuser_name());
		
		user_review.setText(u.getuser_review());
		
		created.setText(u.getcreate());
		
		final int getscore = u.getuser_score();
		
		user_number.setText(u.getuser_number());
		
		String check_number = u.getuser_number();
		String check_getnumber = u.getcheck_user();
		final String delete_comm = u.getbarcode_num();
		
		
		user_score.setVisibility(android.view.View.VISIBLE);

		user_score.setRating(getscore);

		
		
		
		
		if(check_number.equals(check_getnumber)) { 
			
			user_delete.setVisibility(android.view.View.VISIBLE);
			
			
		}
		
		user_delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Delete_user_comment(delete_comm);
				Toast.makeText(activity, "¥Ò±€¿Ã ªË¡¶µ«æ˙Ω¿¥œ¥Ÿ.",
						Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent(activity,MainActivity.class);
				intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
				activity.startActivity(intent);
				activity.finish();

			}
		});
		
		

		return convertView;
	}
	
	private void Delete_user_comment(final String delete_comm) {
		String tag_string_req = "delete_comment";
		

		
		StringRequest strReq = new StringRequest(Method.POST, "http://114.200.13.66:11111/Delete_comment.php",
								new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								// TODO Auto-generated method stub
							
								try {										
										JSONObject user = new JSONObject();
										String barcode = user.getString("barcode_number");
	
								} catch (JSONException e) {
									e.printStackTrace();
								}					
							}
			
						} , new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								// TODO Auto-generated method stub
		            
							}
						}) {
			
								protected Map<String, String> getParams() {
									Map<String, String> params = new HashMap<String, String>();
					                params.put("barcode_number", delete_comm); 
					                return params;
								}
		};
				
		AppController.getInstance().addToRequestQueue(strReq,tag_string_req);
		
	}
	

}
