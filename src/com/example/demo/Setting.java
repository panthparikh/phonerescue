package com.example.demo;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Setting extends Activity{
	
	TextView t1,t2,t3,t4,t5;
	EditText e1,e2,e3;
	Button b1,b2,b3;
	SQLiteDatabase db;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		t1=(TextView)findViewById(R.id.t1);
		t2=(TextView)findViewById(R.id.t2);
		t3=(TextView)findViewById(R.id.t3);
		t4=(TextView)findViewById(R.id.t4);
		t5=(TextView)findViewById(R.id.t5);
		e1=(EditText)findViewById(R.id.e1);
		e2=(EditText)findViewById(R.id.e2);
		e3=(EditText)findViewById(R.id.e3);
		b1=(Button)findViewById(R.id.b1);
		b2=(Button)findViewById(R.id.b2);
		b3=(Button)findViewById(R.id.b3);
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent("android.intent.action.Home");
				startActivity(i);
			}
		});
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i1=new Intent("android.intent.action.Editprofile");
				startActivity(i1);
				
			}
		});
		
b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i1=new Intent("android.intent.action.Editprofile");
				startActivity(i1);
				
			}
		});
	}
}
