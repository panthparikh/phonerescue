package com.example.demo;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

public class Command extends Activity{

	TextView c1,c2,c3,c4;
	SQLiteDatabase db;
	private static String[] data = null;
	ScrollView sv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
	setContentView(R.layout.command);
	sv=(ScrollView)findViewById(R.id.scrollView1);
	c3=(TextView)findViewById(R.id.c3);
	c1=(TextView)findViewById(R.id.c1);

		db=openOrCreateDatabase("test.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
	Cursor c = db.rawQuery("select * from command", null);
	 
	 int i=1;
	 c.moveToFirst();
	   //fetch all data one by one
	   do
	   {
	    //we can use c.getString(0) here
	    //or we can get data using column index
	    String command=c.getString(c.getColumnIndex("Command"));
	    String use=c.getString(c.getColumnIndex("Usage"));
	    //display on text view
	    c3.append(""+i+")"+command+" : "+use+"\n");
	    i++;
	    //move next position until end of the data
	   }while(c.moveToNext());
	  }
}