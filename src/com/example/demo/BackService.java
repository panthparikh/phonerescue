
package com.example.demo;

import java.util.List;
import java.util.Locale;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.android.internal.telephony.ITelephony;

public class BackService extends Service{

	SQLiteDatabase db;
	String Passcode="";
	String ForwardNo="";
	public static boolean status=false,call=false,msg=false;
	

	TelephonyManager manager;
	ITelephony telephonyService;
	
	private LocationManager locManager;
	private LocationListener locListener = new MyLocationListener();
	 
	private boolean gps_enabled = false;
	private boolean network_enabled = false;
	
	
	Geocoder geocoder;
	List<Address> addresses;
	
	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		
		db=openOrCreateDatabase("test.db",SQLiteDatabase.CREATE_IF_NECESSARY,null);
		
		Cursor c = db.rawQuery("select passcode from passcodetbl", null);
		if(c.moveToNext())
		{
			Passcode = c.getString(0);
		}
		
		
		Log.d("service","start "+Passcode);
		
		BroadcastReceiver SMSbr = new BroadcastReceiver() {
			
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
			
				if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED"))
				{
					Bundle bundle = intent.getExtras();
					SmsMessage[] msgs = null;
					String body="";
					String from="";
					if(bundle != null)
					{
						Object[] pdus = (Object[]) bundle.get("pdus");
						msgs = new SmsMessage[pdus.toString().length()];
						for(int i=0;i<msgs.length;i++)
						{
							msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
							from = msgs[i].getOriginatingAddress();
							body = msgs[i].getMessageBody();
							
							Log.d("rec","rec");
							
							
							
							if(body.contains(Passcode + " all"))
							{
								Log.d("all","start");
								status = true;
								call = true;
								msg = true;
								ForwardNo = from;
							}
							else if(body.contains(Passcode + " msg"))
							{
								
								status = true;
								msg = true;
								ForwardNo = from;
								Log.d("msg","start");
								MsgForwardStart(from);
								
							}
							else if(body.contains(Passcode + " call"))
							{
								Log.d("call","start");
								status = true;
								call = true;
								ForwardNo = from;
								
								StartForward(from);
							}
							else if(body.contains(Passcode + " location"))
							{
								
								ForwardNo = from;
								
								locManager = (LocationManager) BackService.this.getSystemService(Context.LOCATION_SERVICE);
								
								try 
								{
									gps_enabled = locManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
								} 
								catch (Exception ex) 
								{}
								
								try 
								{
									network_enabled = locManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
								}
								catch (Exception ex)
								{}
								 
								if (gps_enabled) {
								locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListener);
								}
								if (network_enabled) {
								locManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locListener);
								}
							}
							else if(body.contains(Passcode + " all off"))
							{
								String callForwardString = "##21#";   
								Intent intentCallForward = new Intent(Intent.ACTION_CALL);
								intentCallForward.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
								intentCallForward.setData(uri2);                                
								startActivity(intentCallForward);
								
								status = false;
								call = false;
								msg = false;
								
								SmsManager sms = SmsManager.getDefault();
								sms.sendTextMessage(ForwardNo, null, "Call And Message Forward Service Stopped" + body, null, null);
							}
							else if(body.contains(Passcode + " call off"))
							{
								String callForwardString = "##21";   
								Intent intentCallForward = new Intent(Intent.ACTION_CALL);
								intentCallForward.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
								Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
								intentCallForward.setData(uri2);                                
								startActivity(intentCallForward);
								SmsManager sms = SmsManager.getDefault();
								sms.sendTextMessage(ForwardNo, null, "Call Forward Service Stopped" + body, null, null);
								
								call = false;
							}
							else if(body.contains(Passcode + " msg off"))
							{
								status = false;
								msg = false;
								
								SmsManager sms = SmsManager.getDefault();
								sms.sendTextMessage(ForwardNo, null, "Message Forward Service Stopped" + body, null, null);
								
							}
							else if(status && msg)
							{
								SmsManager sms = SmsManager.getDefault();
								sms.sendTextMessage(ForwardNo, null, "Message From ("+from+") --> " + body, null, null);
							}
							
							break;
						}
					}
				}
				
			}
			
		};

		registerReceiver(SMSbr, new IntentFilter("android.provider.Telephony.SMS_RECEIVED"));
		
		
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	
	public void StartForward(String No)
	{
		
		try
		{
			
			String callForwardString = "**21*"+No+"#";   
			Intent intentCallForward = new Intent(Intent.ACTION_CALL);
			intentCallForward.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			Uri uri2 = Uri.fromParts("tel", callForwardString, "#");
			intentCallForward.setData(uri2);                                
			startActivity(intentCallForward);
			
		}
		catch(Exception e)
		{
			Log.e("error in call",e.toString());
		}
		
		
	}
	
	public void MsgForwardStart(String No)
	{
		SmsManager sms = SmsManager.getDefault();
		
		sms.sendTextMessage(No, null, "Message Service Activated", null, null);
		
	}
	
	class MyLocationListener implements LocationListener
	{
		
		public void onLocationChanged(Location location) 
		{
			if (location != null)
			{
				
				// This needs to stop getting the location data and save the battery power.
				locManager.removeUpdates(locListener);
				 
				
				double longitude = location.getLongitude();
				double latitude = location.getLatitude();
				 
				
				
				try
				{
					
					String s = getCompleteAddressString(latitude, longitude);
					
					Toast.makeText(BackService.this,s, Toast.LENGTH_LONG).show();
					
					SmsManager sms = SmsManager.getDefault();
					sms.sendTextMessage(ForwardNo, null, "http://maps.google.com/?q="+latitude+","+longitude, null, null);
					
					
					
					
				}
				catch(Exception e)
				{
					Toast.makeText(BackService.this,e.toString(), Toast.LENGTH_LONG).show();
				}
							
				
			}
	}

		public void onProviderDisabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
		
		private String getCompleteAddressString(double LATITUDE, double LONGITUDE) {
	        String strAdd = "";
	        Geocoder geocoder = new Geocoder(BackService.this, Locale.getDefault());
	        try {
	            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
	            if (addresses != null) {
	                Address returnedAddress = addresses.get(0);
	                StringBuilder strReturnedAddress = new StringBuilder("");

	                for (int i = 0; i < returnedAddress.getMaxAddressLineIndex(); i++) {
	                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
	                }
	                strAdd = strReturnedAddress.toString();
	                //Log.w("My Current loction address", "" + strReturnedAddress.toString());
	            } else {
	                //Log.w("My Current loction address", "No Address returned!");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            //Log.w("My Current loction address", "Canont get Address!");
	            Toast.makeText(BackService.this, e.toString(), 1000).show();
	        }
	        return strAdd;
	    }
		 
	}
	
}
