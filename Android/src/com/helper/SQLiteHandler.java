package com.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
	 
import java.util.HashMap;
	 
	public class SQLiteHandler extends SQLiteOpenHelper {
	 
	    private static final String TAG = SQLiteHandler.class.getSimpleName();
	 
	    // All Static variables
	    // Database Version
	    private static final int DATABASE_VERSION = 1;
	 
	    // Database Name
	    private static final String DATABASE_NAME = "android_api";
	 
	    // Login table name
	    private static final String TABLE_USER = "users";
	 
	    // Login Table Columns names

	    private static final String KEY_ID = "id";
	    private static final String KEY_NAME = "name";
	    private static final String KEY_NUMBER = "number";
	    private static final String KEY_UID = "uid";
	    private static final String KEY_CREATED_AT = "created_at";
	    private static final String KEY_BARCODE = "barcode";
	    private static final String KEY_BARCODE_URL = "barcode_url";
	    private static final String KEY_MONEY = "money";
	    private static final String KEY_POINT = "point";
	 
	    public SQLiteHandler(Context context) {
	        super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    }
	 
	    // Creating Tables
	    @Override
	    public void onCreate(SQLiteDatabase db) {
	      String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_USER + "("
	                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
	                + KEY_NUMBER + " TEXT UNIQUE," + KEY_UID + " TEXT,"
	                + KEY_CREATED_AT + " TEXT," + KEY_BARCODE + " TEXT,"
	                + KEY_BARCODE_URL + " TEXT," + KEY_MONEY + " TEXT,"
	                + KEY_POINT + " TEXT" + ");";
	        db.execSQL(CREATE_LOGIN_TABLE);
	 
	     /*   Log.d(TAG, "Database tables created");*/
	    }
	 
	    // Upgrading database
	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	        // Drop older table if existed
	        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
	 
	        // Create tables again
	        onCreate(db);
	    }
	 
	    /**
	     * Storing user details in database
	     * */
	    public void addUser(String name, String number, String uid, String created_at, String barcode, String barcode_url, 
	    		String money,String point) {
	        SQLiteDatabase db = this.getWritableDatabase();
	 
	        ContentValues values = new ContentValues();
	        values.put(KEY_NAME, name); // Name
	        values.put(KEY_NUMBER, number); // number
	        values.put(KEY_UID, uid); 
	        values.put(KEY_CREATED_AT, created_at);
	        values.put(KEY_BARCODE, barcode); //barcode
	        values.put(KEY_BARCODE_URL, barcode_url);
	        values.put(KEY_MONEY, money);
	        values.put(KEY_POINT, point);
	        
	 
	        // Inserting Row
	        long id = db.insert(TABLE_USER, null, values);
	        db.close(); // Closing database connection
	 
	      /*  Log.d(TAG, "New user inserted into sqlite: " + id);*/
	    }
	 
	    /**
	     * Getting user data from database
	     * */
	    public HashMap<String, String> getUserDetails() {
	        HashMap<String, String> user = new HashMap<String, String>();
	        String selectQuery = "SELECT  * FROM " + TABLE_USER;
	 
	        SQLiteDatabase db = this.getReadableDatabase();
	        Cursor cursor = db.rawQuery(selectQuery, null);
	        // Move to first row
	        cursor.moveToFirst();
	        if (cursor!= null && cursor.getCount() > 0 && cursor.moveToFirst()) {
	            user.put("name", cursor.getString(1));
	            user.put("number", cursor.getString(2));
	            user.put("uid", cursor.getString(3));
	            user.put("created_at", cursor.getString(4));
	            user.put("barcode", cursor.getString(5));
	            user.put("barcode_url", cursor.getString(6));
	            user.put("money", cursor.getString(7));
	            user.put("point", cursor.getString(8));
	        } 
	        cursor.close();
	        db.close();
	        // return user
	     /*   Log.d(TAG, "Fetching user from Sqlite: " + user.toString());*/
	 
	        return user;
	    }
	 
	    /**
	     * Re crate database Delete all tables and create them again
	     * */
	    public void deleteUsers() {
	        SQLiteDatabase db = this.getWritableDatabase();
	        // Delete All Rows
	        db.delete(TABLE_USER, null, null);
	        db.close();
	 
	       /* Log.d(TAG, "Deleted all user info from sqlite");*/
	    }
	    
	    public void updateUsers(String num,String mon, String po) {
	    	SQLiteDatabase db = this.getWritableDatabase();
	    	
	    	 db.execSQL("UPDATE users SET money = "+ mon + ", point =" + po +" WHERE number =" + num);
	    	db.close();
	    }
	    
	    
	 
}
