package com.example.demo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

public class Home extends Activity {

	Button bh1,bh2,bh3;
	
	TextView t1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.home);
		bh1=(Button)findViewById(R.id.bh1);
		bh2=(Button)findViewById(R.id.bh2);
		bh3=(Button)findViewById(R.id.bh3);
		
		t1 = (TextView)findViewById(R.id.t1);
		
		try
		{
			if(BackService.status)
			{
				t1.setVisibility(View.VISIBLE);
				bh1.setVisibility(View.VISIBLE);
			}
			else
			{
				t1.setVisibility(View.GONE);
				bh1.setVisibility(View.VISIBLE);
			}
		}
		catch(Exception e)
		{
			t1.setVisibility(View.GONE);
			bh1.setVisibility(View.VISIBLE);
		}
		
		bh1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(BackService.call)
				{
					String callForwardString = "##21#";   
					Intent intentCallForward = new Intent(Intent.ACTION_CALL);
					intentCallForward.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
					intentCallForward.setData(uri2);                                
					startActivity(intentCallForward);

				}
				
				stopService(new Intent(Home.this,BackService.class));
				startService(new Intent(Home.this,BackService.class));
				
				Toast.makeText(Home.this, "All Services Stopped !!", 2).show();
				t1.setVisibility(View.GONE);
				bh1.setVisibility(View.VISIBLE);
				
			}
		});
		
		bh2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent i=new Intent("android.intent.action.Passcode");
				startActivity(i);

				
				// TODO Auto-generated method stub
				
			}
		});
		bh3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i1=new Intent("android.intent.action.Command");
				startActivity(i1);
			
			}
		});
		}
	
	
	
}
