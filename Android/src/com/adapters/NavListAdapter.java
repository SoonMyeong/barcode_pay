package com.adapters;

import com.example.nam.R;
import com.slidingmenu.NatItem;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class NavListAdapter extends ArrayAdapter<NatItem> {
	
	Context context;
	int resLayout;
	List<NatItem> listNavItems;
	
	public NavListAdapter(Context context, int resLayout, List<NatItem> listNavItems) {
		super(context,resLayout,listNavItems);
		this.context = context;
		this.resLayout= resLayout;
		this.listNavItems = listNavItems;
		
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v = View.inflate(context, resLayout, null);
		
		TextView tvTitle = (TextView) v.findViewById(R.id.title);
		ImageView navIcon = (ImageView) v.findViewById(R.id.nav_icon);

		NatItem navItem = listNavItems.get(position);
		
		tvTitle.setText(navItem.getTitle());
		navIcon.setImageResource(navItem.getIcon());
		
		return v;
	}
	

}
