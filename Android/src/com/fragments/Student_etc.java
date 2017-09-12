package com.choose.fragments;



import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import product_model.product;

import com.adapters.CustomGridAdapter;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.controller.AppController;
import com.example.nam.MainActivity;
import com.example.nam.R;
import com.example.nam.StudentActivity;
import com.selected.food_detail;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class Student_etc extends Fragment {
	private static final String TAG = StudentActivity.class.getSimpleName();
	private static final String url = "http://server/product_student_etc.json";
	private ProgressDialog pDialog;
	private GridView gridView;
	private CustomGridAdapter adapter;
	private List<product> productList = new ArrayList<product>();
	

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_chaeum_korea,container, false);
         
        gridView =(GridView) rootView.findViewById(R.id.list_main);
        adapter = new CustomGridAdapter(this.getActivity(),productList);
        gridView.setAdapter(adapter);
        
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				
				Intent intent = new Intent(getActivity(),food_detail.class);
				
				intent.putExtra("food_image", productList.get(position).getfood_image());
				intent.putExtra("food_name", productList.get(position).getfood_name());
				intent.putExtra("food_price", productList.get(position).getfood_price());
				
				startActivity(intent);
				
			}
		});
        

        
		
		JsonArrayRequest productReq = new JsonArrayRequest(url,new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
				// TODO Auto-generated method stub
				/*Log.d(TAG,response.toString());*/
			
				
				//Parsing json
				for(int i=0; i<response.length(); i++) {
					try{
						JSONObject obj = response.getJSONObject(i);
						product p = new product();
						p.setfood_name(obj.getString("food_name"));
						p.setfood_image(obj.getString("food_image"));
						p.setfood_price(((Number) obj.get("price")).intValue());

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
			/*	VolleyLog.d(TAG,"Error: "+ error.getMessage());*/
				
			}
			
		});
		
		AppController.getInstance().addToRequestQueue(productReq);
        
        
        return rootView;
    }
    
    
}
