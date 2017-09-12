package com.adapters;


import java.util.List;

import product_model.notice;
import product_model.product;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.controller.AppController;
import com.example.nam.R;

public class CustomNoticeAdapter extends BaseAdapter {
	private Activity activity;
	private LayoutInflater inflater;
	private List<notice> productItems;
	
	public CustomNoticeAdapter(Activity activity, List<notice> productItems) {
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
			convertView = inflater.inflate(R.layout.notice_listview_row, null);
		}
	
		TextView notice_select = (TextView)convertView.findViewById(R.id.notice_select);
		TextView notice_title =(TextView)convertView.findViewById(R.id.notice_title);
		TextView notice_detail =(TextView)convertView.findViewById(R.id.notice_detail);
		TextView notice_day =(TextView)convertView.findViewById(R.id.notice_day);
		
		notice n = productItems.get(position);
		
		notice_select.setText(n.getnotice_select());
		notice_title.setText(n.getnotice_title());
		notice_detail.setText(n.getnotice_detail());
		notice_day.setText(n.getnotice_day());
		

		
		return convertView;
	}
	
	

}
