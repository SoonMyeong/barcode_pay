package com.example.nam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;


public class IntroActivity extends Activity {

	
	
	/* @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_splash);

	  
	        
	        
	        Handler handler = new Handler();
	        handler.postDelayed(new Runnable() {
	            public void run() {
	                Intent intent = new Intent(IntroActivity.this, LoginActivity.class);
	                startActivity(intent);
	                finish();
	            }
	        }, 2000);   
	    }
	}*/
	
	private static final int SPLASH_TIME = 1 * 1000;// 3 seconds delay

	@Override
	protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_splash);
	    ImageView iv= (ImageView) findViewById(R.id.splash_icon);
	    iv.setBackgroundResource(R.drawable.barcode_pay);
	    try {
	           new Handler().postDelayed(new Runnable() {

	        public void run() {

	            Intent intent = new Intent(IntroActivity.this,
	                LoginActivity.class);
	            startActivity(intent);

	            IntroActivity.this.finish();
	        }     
	    }, SPLASH_TIME);
	        }catch(Exception e){}
	}
}
