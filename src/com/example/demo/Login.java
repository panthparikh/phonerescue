package com.example.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;


public class Login extends Activity {
	
	TextView tv1,tv2,tv3;
	EditText e1,e2;
	Button b1,b2;
	public static String pass,uid,u;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		b1=(Button)findViewById(R.id.b1);
		b2=(Button)findViewById(R.id.b2);
		tv1=(TextView)findViewById(R.id.tv1);
		tv2=(TextView)findViewById(R.id.tv2);
		e1=(EditText)findViewById(R.id.e1);
		e2=(EditText)findViewById(R.id.e2);
		tv3=(TextView)findViewById(R.id.tv3);
		u=e1.getText().toString();
		
		db=openOrCreateDatabase("test.db", SQLiteDatabase.CREATE_IF_NECESSARY,null);
		db.execSQL("create table if not exists passcodetbl(Passcode text)");
		db.execSQL("create table if not exists command(Command text, Usage text)");
		db.execSQL("delete from command");
		db.execSQL("insert into command values('#passcodeall','Activate all services' )");
		db.execSQL("insert into command values('#passcodemsg','Activate message divert services' )");
		db.execSQL("insert into command values('#passcodecall','Activate call divert services' )");
		db.execSQL("insert into command values('#passcodelocation','To get location')");
		tv3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent("android.intent.action.Reg");
				startActivity(i);
				
			}
		
	});
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
		public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				uid=e1.getText().toString();
				pass=e2.getText().toString();
				
				
			 Cursor c=db.rawQuery("select * from reg where Uid='"+uid+"'and Password='"+pass+"'", null);
				if(c.moveToNext())
				{
					Toast.makeText(Login.this,"Logged in successfully",2).show();
					
					startService(new Intent(Login.this,BackService.class));
					
					Intent i1= new Intent("android.intent.action.Home");
					startActivity(i1);	
					
				}
				else
				{

					Toast.makeText(Login.this,"invalid userid/password",2).show();
				}
						}
			});
}
}

