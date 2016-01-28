package com.matingting.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.Telephony.Sms;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;

public class BootCompletedReceiver extends BroadcastReceiver {
    SharedPreferences mPres;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		System.out.println("BootCompletedReceiver");
		mPres=context.getSharedPreferences("config", context.MODE_PRIVATE);
		String SimNumSaved=mPres.getString("SimNum", null);
		TelephonyManager TM=(TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
		String SimNum=TM.getSubscriberId();
		SimNum="";
		if(SimNumSaved!=null)
		{
			if(SimNum.equals(SimNumSaved))
		    {
			   System.out.println("SIMδ�����仯");
		    }
		   else{
			   System.out.println("SIM�����仯��");
			   String num=mPres.getString("urgentContact", "");
			   SmsManager smsManager=SmsManager.getDefault();
			   smsManager.sendTextMessage("5554", null, "ɵ��", null, null);
		    }
		}
	}

}
