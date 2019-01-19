package com.example.demo;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class Editprofile extends Activity {

	ScrollView sv1;
	EditText re1,re2,re3,re4,re5;
	TextView t1,t2,t3,t4,t5;
	Button b1;
	SQLiteDatabase db;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.editprofile);
		sv1=(ScrollView)findViewById(R.id.sv1);
		re1 = (EditText)findViewById(R.id.re1);
		re2 = (EditText)findViewById(R.id.re2);
		re3 = (EditText)findViewById(R.id.re3);
		re4= (EditText)findViewById(R.id.re4);
		re5 = (EditText)findViewById(R.id.re5);
		t1=(TextView)findViewById(R.id.t1);
		t2=(TextView)findViewById(R.id.t2);
		t3=(TextView)findViewById(R.id.t3);
		t4=(TextView)findViewById(R.id.t4);
		t5=(TextView)findViewById(R.id.t5);
		b1=(Button)findViewById(R.id.b1);
		
		db = openOrCreateDatabase("test.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
		
		Cursor c = db.rawQuery("select * from reg where Uid = '"+Reg.uid+"'", null);
		if(c.moveToNext())
		{
			re1.setText(c.getString(0));
			re2.setText(c.getString(1));
			re3.setText(c.getString(2));
			re4.setText(c.getString(3));
			re5.setText(c.getString(4));
		}
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String name = re1.getText().toString();
				String u=re2.getText().toString();
				String pwd = re3.getText().toString();
				String phone = re4.getText().toString();
				String email = re5.getText().toString();
		
				db.execSQL("update reg set Name = '"+name+"',Password='"+pwd+"',Number='"+phone+"',Email_id='"+email+"' where Uid = '"+re2.getText()+"'");
			
				Toast.makeText(Editprofile.this,"Data Inserted !!", 1).show();
				
			}
		});
	}
	
}
