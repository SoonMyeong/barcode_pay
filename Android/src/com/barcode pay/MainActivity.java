package com.example.nam;

import com.google.android.gcm.GCMRegistrar;
import com.handler.BackPressCloseHandler;
import com.helper.SQLiteHandler;
import com.helper.SessionManager;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.json.JSONException;
import org.json.JSONObject;

import com.adapters.NavListAdapter;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.best_fragment.Best_Chaeum_Food;
import com.best_fragment.Best_Student_Food;
import com.choose.fragments.Chaeum_korea;
import com.choose.fragments.Student_korea;
import com.controller.AppController;
import com.slidingmenu.NatItem;
import com.slidingtabs.SlidingTabLayout;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabContentFactory;
import android.widget.HorizontalScrollView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
 

public class MainActivity extends ActionBarActivity{
 
    // Declaring Your View and Variables
 
    Toolbar toolbar;


    DrawerLayout drawerLayout;
    RelativeLayout drawerPane;
    ListView lvNav;
    
    List<NatItem> listNavItems;
    List<Fragment> listFragments;
    
    ActionBarDrawerToggle actionBarDrawerToggle;
    BackPressCloseHandler backPressCloseHandler;


    private SQLiteHandler db;
    private SessionManager session;
    
    private TextView txtName;
    private TextView txtMoney;
    private TextView txtPoint;
    private ProgressDialog pDialog;

 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        registerGcm();

		txtName=(TextView) findViewById(R.id.profile_name_user);
		txtMoney=(TextView) findViewById(R.id.profile_money_user);
		txtPoint=(TextView) findViewById(R.id.profile_point_user);
        
		
		pDialog = new ProgressDialog(this);
		pDialog.setCancelable(false);
		
		
        // SqLite database handler
        db = new SQLiteHandler(getApplicationContext());
		
        // session manager
        session = new SessionManager(getApplicationContext());
       
 
 
        // Fetching user details from sqlite
        HashMap<String, String> user = db.getUserDetails();
		
        String name = user.get("name");
        String money = user.get("money");
        String point = user.get("point");
        
        txtName.setText(String.valueOf(name));
        txtMoney.setText(String.valueOf(money)+"ø¯");
        txtPoint.setText(String.valueOf(point)+"P"); 
        
        
        
        
        
        // Creating The Toolbar and setting it as the Toolbar for the activity
 
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        backPressCloseHandler = new BackPressCloseHandler(this);
        

        
        drawerLayout = (DrawerLayout) findViewById(R.id.DrawerLayout);
        drawerPane = (RelativeLayout) findViewById(R.id.drawer_pane);
        lvNav = (ListView) findViewById(R.id.nav_list);
        
        
 
        
        listNavItems = new ArrayList<NatItem>();
        listNavItems.add(new NatItem("ø¿¥√¿« √ﬂ√µ∏ﬁ¥∫",R.drawable.ic_thumb_up));
        listNavItems.add(new NatItem("∞¯¡ˆªÁ«◊",R.drawable.ic_star));
        listNavItems.add(new NatItem("«–ª˝»∏∞¸",R.drawable.ic_restaurant));
        listNavItems.add(new NatItem("√§øÚ",R.drawable.ic_restaurant));
        listNavItems.add(new NatItem("∫∏∞¸«‘",R.drawable.ic_shopping_cart));
        listNavItems.add(new NatItem("»ƒ±‚¿€º∫",R.drawable.ic_create));
        listNavItems.add(new NatItem("√Ê¿¸",R.drawable.ic_money));
        listNavItems.add(new NatItem("∑Œ±◊æ∆øÙ",R.drawable.ic_logout));
        

        
        NavListAdapter navListAdapter = new NavListAdapter(getApplicationContext(), R.layout.nav_item_list, listNavItems);
        
        lvNav.setAdapter(navListAdapter);
        
        listFragments = new ArrayList<Fragment>();
        listFragments.add(new Best_Student_Food());
        listFragments.add(new Student_korea()); //¿œ¥‹ µ–¥Ÿ.
        listFragments.add(new Chaeum_korea()); //¿œ¥‹ µ–¥Ÿ.
        listFragments.add(new Best_Student_Food());
        listFragments.add(new Best_Student_Food());
        listFragments.add(new Best_Chaeum_Food());
        
        //load first fragment as default
        
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main,listFragments.get(0)).commit();
  
        
        setTitle(listNavItems.get(0).getTitle());
        lvNav.setItemChecked(0, true);
        drawerLayout.closeDrawer(drawerPane);
        
        //set listener for navigation items 
        
        lvNav.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				FragmentManager fragmentManager = getSupportFragmentManager();
				

				           switch (position) {
				               case 0:
					   		        fragmentManager.beginTransaction().replace(R.id.main,listFragments.get(0)).commit(); 
							        setTitle(listNavItems.get(0).getTitle());
							        lvNav.setItemChecked(0, true);
							        drawerLayout.closeDrawer(drawerPane);
			
				                   break;
				                   
				               case 1:
				            	   Intent intent1 = new Intent(MainActivity.this,NoticeActivity.class);
				            	   startActivity(intent1);
				            	   
				            	   break;
				            	   
				               case 2:
									Intent intent2 = new Intent(MainActivity.this, StudentActivity.class);
									startActivity(intent2);
							
				                   break;
				               case 3:
									Intent intent3 = new Intent(MainActivity.this, ChaeumActivity.class);
									startActivity(intent3);
						
				                   break;
				               case 4:
				            	   Intent intent4 = new Intent(MainActivity.this,User_product_list.class);
				            	   startActivity(intent4);
				                   break;
				               case 5:
				            	   Intent intent5 = new Intent(MainActivity.this,Review_list.class);
				            	   startActivity(intent5);
				            	   break;
				               case 6:
				            	   Intent intent6 = new Intent(MainActivity.this, Charge_money.class);
									startActivity(intent6);
						        break;
						        
				               case 7:
				            	   logoutUser();
						        
		
				           }
			}
		});
        
        	//create Listener for drawer layout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed) {
        	
        	@Override
        	public void onDrawerOpened(View drawerView) {
        		// TODO Auto-generated method stub
        		invalidateOptionsMenu();
        		super.onDrawerOpened(drawerView);
        	}
        	@Override
        	public void onDrawerClosed(View drawerView) {
        		// TODO Auto-generated method stub
        		invalidateOptionsMenu();
        		super.onDrawerClosed(drawerView);
        	}
        	
        };
        
        drawerLayout.setDrawerListener(actionBarDrawerToggle); 
        
        
 
    };
    
    
 

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
 
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
    	
    	if(actionBarDrawerToggle.onOptionsItemSelected(item))
    		return true;
    	

 
        return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
    	// TODO Auto-generated method stub
    	super.onPostCreate(savedInstanceState);
    	actionBarDrawerToggle.syncState();
    }
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(drawerPane)) {
            drawerLayout.closeDrawer(drawerPane);
        }
        backPressCloseHandler.onBackPressed();
    }

    public boolean onOptionsItemSelected1(MenuItem item) 
    {    
       switch (item.getItemId()) 
       {        
          case android.R.id.home: 
        	  drawerLayout.openDrawer(drawerPane);
             return true;        
          default:            
             return super.onOptionsItemSelected(item);    
       }
    }
    private void logoutUser() {
        session.setLogin(false);
        db.deleteUsers();

        // Launching the login activity
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        
    }		
    
    public void registerGcm() {
    	GCMRegistrar.checkDevice(this);
		GCMRegistrar.checkManifest(this);
		final String regId = GCMRegistrar.getRegistrationId(this);
		if ( regId.equals("") ){
			GCMRegistrar.register(this, "xxxxxxxx");
			Postregid(regId);
		}else{
			Postregid(regId);
			/*Log.d("jiho", "oncreated regId = "+regId);*/
			
		} 
    	 
    	}
private void Postregid(final String regId) {
		
		
		StringRequest strReq = new StringRequest(Method.POST, "http://server/push.php",
								new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								// TODO Auto-generated method stub
							
								
							}
			
						} , new Response.ErrorListener() {

							@Override
							public void onErrorResponse(VolleyError error) {
								// TODO Auto-generated method stub
					            
							}
						}) {
			
								protected Map<String, String> getParams() {
									Map<String, String> params = new HashMap<String, String>();
					                params.put("id", regId);
					 
					                return params;
								}
		};
				
		AppController.getInstance().addToRequestQueue(strReq);
		
	}	
    
    
    

}
    
    

