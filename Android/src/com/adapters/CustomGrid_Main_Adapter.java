package com.adapters;


import java.util.List;

import product_model.product;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.controller.AppController;
import com.example.nam.R;

public class CustomGrid_Main_Adapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<product> productItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	public CustomGrid_Main_Adapter(Activity activity, List<product> productItems) {
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
			convertView = inflater.inflate(R.layout.grid_main_row, null);
		}
		if(imageLoader == null) {
			imageLoader = AppController.getInstance().getImageLoader();
		}
		
		NetworkImageView food_image = (NetworkImageView) convertView.findViewById(R.id.food_image);
		TextView food_name = (TextView) convertView.findViewById(R.id.food_name);
		TextView price = (TextView) convertView.findViewById(R.id.food_price);
		
		product p = productItems.get(position);
		
		food_image.setImageUrl(p.getfood_image(),imageLoader);
		
		food_name.setText(p.getfood_name());
		
		price.setText(String.valueOf(p.getfood_price())+"ø¯");
		
		return convertView;
	}
	
	

}
