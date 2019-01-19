package com.example.demo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Setpasscode extends Activity{
	
	TextView tv;
	EditText et;
	Button btn;
	
	SQLiteDatabase db;
	public static String pass;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.setpasscode);
	tv=(TextView)findViewById(R.id.tv);
	et=(EditText)findViewById(R.id.et);
	btn=(Button)findViewById(R.id.btn);
	db=openOrCreateDatabase("test.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
	db.execSQL("create table if not exists passcodetbl(passcode text)");
	
	db.execSQL("create table if not exists command(Command text, Usage text)");
	Cursor c = db.rawQuery("select count(*) from passcodetbl", null);
	c.moveToFirst();
	int icount=c.getInt(0);
	if(icount>0)
	{
		startService(new Intent(Setpasscode.this,BackService.class));
		Intent i2= new Intent("android.intent.action.Home");
		startActivity(i2);
	}
	db.execSQL("delete from command");
	
	
	db.execSQL("insert into command values('passcode all','Activate all services' )");
	db.execSQL("insert into command values('passcode msg','Activate message divert services' )");
	db.execSQL("insert into command values('passcode call','Activate call divert services' )");
	db.execSQL("insert into command values('passcode location','To get location')");
	db.execSQL("insert into command values('passcode all off','Deactivate all services')");
	db.execSQL("insert into command values('passcode msg off','Deactivate message divert services')");
	db.execSQL("insert into command values('passcode call off','Deactivate call divert services' )");

	btn.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			
			pass=et.getText().toString();
			db.execSQL("insert into passcodetbl values('"+pass+"')");
			Toast.makeText(Setpasscode.this,"Passcode registered",2).show();
			startService(new Intent(Setpasscode.this,BackService.class));
			
			Intent i= new Intent("android.intent.action.Home");
				startActivity(i);

				
			
			
		}
	});
	
	
	}

}
