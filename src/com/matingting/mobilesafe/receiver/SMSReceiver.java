package com.matingting.mobilesafe.receiver;

import com.matingting.mobilesafe.R;
import com.matingting.mobilesafe.service.LocationService;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.sax.StartElementListener;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {
	SharedPreferences mPres;
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		mPres=context.getSharedPreferences("config",context.MODE_PRIVATE);
		Object[] objects=(Object[]) intent.getExtras().get("pdus");
		for(Object obj:objects)
		{
			SmsMessage smsMessage=SmsMessage.createFromPdu((byte[]) obj);
			String number=smsMessage.getOriginatingAddress();
			String content=smsMessage.getMessageBody();
			System.out.println(number+"::"+content);
			if(content.equals("#*alarm*#"))
			{
				MediaPlayer player= MediaPlayer.create(context, R.raw.ylzs);
				player.setVolume(1.0f, 1.0f);
				player.setLooping(true);
				player.start();
				abortBroadcast();//中止广播  // 中断短信的传递, 从而系统短信app就收不到内容了
			}else if(content.equals("#*location*#"))
			{
				context.startService(new Intent(context,LocationService.class));
				System.out.println("location"+mPres.getString("mLongitude", "")+"::::"+mPres.getString("mLatitude", ""));
				abortBroadcast();//中止广播  // 中断短信的传递, 从而系统短信app就收不到内容了
			}
		}
	}
}
