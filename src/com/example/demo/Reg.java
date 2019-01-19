package com.example.demo;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Reg extends Activity{
	
	TextView t1,t2,t3,t4,t5;
	EditText re1,re2,re3,re4,re5;
	Button bt;
	ScrollView sv;
	public static String name;
	public static String uid;
	public static String pass;
	public static String nbr;
	public static String eid;
	SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

	setContentView(R.layout.reg);
	sv=(ScrollView)findViewById(R.id.scrollView1);
	t1=(TextView)findViewById(R.id.t1);
	t2=(TextView)findViewById(R.id.t2);
	t3=(TextView)findViewById(R.id.t3);
	t4=(TextView)findViewById(R.id.t4);
	t5=(TextView)findViewById(R.id.t5);
	re1=(EditText)findViewById(R.id.re1);
	re2=(EditText)findViewById(R.id.re2);
	re3=(EditText)findViewById(R.id.re3);
	re4=(EditText)findViewById(R.id.re4);
	re5=(EditText)findViewById(R.id.re5);
	bt=(Button)findViewById(R.id.bt);
	db=openOrCreateDatabase("test.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
	db.execSQL("create table if not exists reg(Name text, Uid text primary key, Password text, Number text, Email_id text)");

	bt.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			name=re1.getText().toString();
			uid=re2.getText().toString();
			pass=re3.getText().toString();
			nbr=re4.getText().toString();
			eid=re5.getText().toString();
			db.execSQL("insert into reg values('"+name+"','"+uid+"','"+pass+"','"+nbr+"','"+eid+"')");
			Toast.makeText(Reg.this,"User registered",2).show();
			//Intent i=new Intent("android.intent.action.MAIN");
			//startActivity(i);
		}
	});
	}	
}
