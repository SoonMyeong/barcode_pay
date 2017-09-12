package com.adapters;


import java.util.List;

import product_model.user_product;

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

public class CustomGrid_SaveAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<user_product> productItems;
	ImageLoader imageLoader = AppController.getInstance().getImageLoader();
	
	public CustomGrid_SaveAdapter(Activity activity, List<user_product> productItems) {
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
			convertView = inflater.inflate(R.layout.grid_save_row, null);
		}
		if(imageLoader == null) {
			imageLoader = AppController.getInstance().getImageLoader();
		}
		
		NetworkImageView food_image = (NetworkImageView) convertView.findViewById(R.id.food_image);
		NetworkImageView food_barcode_image = (NetworkImageView) convertView.findViewById(R.id.barcode_image);
		TextView food_name = (TextView) convertView.findViewById(R.id.food_name);
		TextView food_barcode_number = (TextView) convertView.findViewById(R.id.barcode_number);
		TextView price = (TextView) convertView.findViewById(R.id.food_price);
		
		
		user_product p = productItems.get(position);
		
		food_image.setImageUrl(p.getfood_image(),imageLoader);
		
		food_barcode_image.setImageUrl(p.getbarcode_image(), imageLoader);
		
		food_name.setText(p.getfood_name());
		
		food_barcode_number.setText(p.getbarcode_number());
		
		price.setText(String.valueOf(p.getfood_price())+"ø¯");
		
		return convertView;
	}
	
	

}
