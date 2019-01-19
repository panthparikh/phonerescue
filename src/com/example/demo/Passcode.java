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

public class Passcode extends Activity{

	EditText et1,et2,et3;
	Button bt1;
	SQLiteDatabase db;
	public static String pass,use,passc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	setContentView(R.layout.passcode);
	et3=(EditText)findViewById(R.id.et3);
	et1=(EditText)findViewById(R.id.et1);
	et2=(EditText)findViewById(R.id.et2);
	
	bt1=(Button)findViewById(R.id.bt1);
	db=openOrCreateDatabase("test.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
	Cursor c = db.rawQuery("select * from passcodetbl", null);
	c.moveToNext();
	 use=c.getString(0);
	 et3.setText(use);
	 
	
		bt1.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			 pass=et1.getText().toString();
			 passc=et2.getText().toString();		
			if(pass != null && pass.equals(passc))
			{
			
				db.execSQL("update passcodetbl set passcode = '"+pass.trim()+"'");
			
			Toast.makeText(Passcode.this,"Passcode Changed!!", 1).show();
			stopService(new Intent(Passcode.this,BackService.class));
			startService(new Intent(Passcode.this,BackService.class));
      	Intent i= new Intent("android.intent.action.Home");
		startActivity(i);
			}
			else
			{
			
				Toast.makeText(Passcode.this,"Passcode and confirm passcode does not match!!", 2).show();
				et1.setText(" ");
				et2.setText(" ");
			
			}
			
			}
	});
	
	
	}
}
