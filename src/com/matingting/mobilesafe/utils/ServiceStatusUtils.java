package com.matingting.mobilesafe.utils;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;

public class ServiceStatusUtils {
	public static boolean isServiceRunning(Context context,String serviceClassName){
		ActivityManager am=(ActivityManager) context.getSystemService(context.ACTIVITY_SERVICE);
		List<RunningServiceInfo> serviceList=am.getRunningServices(100);
		boolean isRunning=false;
		for(RunningServiceInfo serviceInfo:serviceList)
		{
			String serviceName=serviceInfo.service.getClassName();
			if(serviceName.equals(serviceClassName))
			{
				isRunning=true;
				break;
			}
		}
		return isRunning;
	}
}
