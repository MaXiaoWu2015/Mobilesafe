package com.matingting.mobilesafe.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class LocationService extends Service {
	SharedPreferences mPres;
	LocationManager mlocationManager;
	MyListener mListener;
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		System.out.println("service created");
		// TODO Auto-generated method stub
		mPres=getSharedPreferences("config", MODE_PRIVATE);
		mlocationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
//		if(!mlocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
//		{
//			Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//			startActivity(intent);
//		}
		Criteria criteria=new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setCostAllowed(true);
		String provider=mlocationManager.getBestProvider(criteria, true);//获取当前最佳的位置提供者
		mListener=new MyListener();
		mlocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mListener);
		
	}
  class MyListener implements LocationListener{
	//位置提供者状态发生变化
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			System.out.println("onStatusChanged");
		}
		// 用户打开gps
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			System.out.println("onProviderEnabled");
		}
		// 用户关闭gps
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			System.out.println("onProviderDisabled");
		}
		// 位置发生变化
		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			System.out.println("onLocationChanged");
			double mLongitude=location.getLongitude();
			double mLatitude=location.getLatitude();
			System.out.println();
			mPres.edit().putString("mLongitude", mLongitude+"").commit();
			mPres.edit().putString("mLatitude", mLatitude+"").commit();
			stopSelf();//即使关掉app，service也在运行
		}
	
  }
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		mlocationManager.removeUpdates(mListener);//因为会自己开service进行定位，即使app结束，定位仍在继续
	}
	

}
