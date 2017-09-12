package com.slidingmenu;

public class NatItem {
	
	private String title;
	private int icon;
	// boolean to set visiblity of the counter
	
	public NatItem(){}

	public NatItem(String title, int icon){
		super();
		this.title = title;
		this.icon = icon;
	}
	
	public String getTitle(){
		return title;
	}
	
	public int getIcon(){
		return icon;
	}
	
	
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setIcon(int icon){
		this.icon = icon;
	}
	
}

