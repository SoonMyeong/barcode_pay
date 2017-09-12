package com.example.nam;

import java.util.Iterator;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
 
import com.example.nam.R;
import com.google.android.gcm.GCMBaseIntentService;

 
 
	import java.util.Iterator;

	import android.content.Context;
	import android.content.Intent;
	import android.os.Bundle;
	import android.os.Vibrator;
	import android.util.Log;

	import com.google.android.gcm.GCMBaseIntentService;

public class GCMIntentService extends GCMBaseIntentService{
	private static void generateNotification(Context context, String message) {
		 
		int icon = R.drawable.paypay;
		long when = System.currentTimeMillis();
		 
		 
		NotificationManager notificationManager = (NotificationManager) context
		.getSystemService(Context.NOTIFICATION_SERVICE);
		 
		Notification notification = new Notification(icon, message, when);
		 
		String title = context.getString(R.string.app_name);
		 
		Intent notificationIntent = new Intent(context, MainActivity.class);
		 
		notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP 
		        | Intent.FLAG_ACTIVITY_SINGLE_TOP);
		PendingIntent intent = PendingIntent.getActivity(context, 0,
		notificationIntent, 0);
		 
		 
		 
		notification.setLatestEventInfo(context, title, message, intent);
		 
		notification.flags |= Notification.FLAG_AUTO_CANCEL;
		 
		notificationManager.notify(0, notification);
		 
		}	
	
	
	
	
		public GCMIntentService(){} 
		
		// registration or unregistration ø°∑Ø∞° ≥Ø ∞ÊøÏ »£√‚
		@Override
		protected void onError(Context context, String errorId) {
			/*Log.d("jiho", "errorId : "+errorId);*/
			
		}

		@Override
		protected void onMessage(Context arg0, Intent intent) {
			
			Vibrator vi = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
		    vi.vibrate(500);
			
		    Bundle bundle = intent.getExtras();
			 
			Iterator <String> iterator = bundle.keySet().iterator();
			while ( iterator.hasNext() ){
				String key = iterator.next();
				String value = bundle.get(key).toString();
			/*	Log.d("jiho", "key : "+key+", value : "+value);*/
			}


			String msg = intent.getStringExtra("msg");
			/*Log.e("getmessage", "getmessage:" + msg);*/
			generateNotification(arg0,msg);
		
			
		}

		@Override
		protected void onRegistered(Context arg0, String regId) {
			/*Log.d("jiho", "onRegistered - regId : "+regId);*/
			
		}

		@Override
		protected void onUnregistered(Context arg0, String arg1) {
			// TODO Auto-generated method stub
			
		}

	}
