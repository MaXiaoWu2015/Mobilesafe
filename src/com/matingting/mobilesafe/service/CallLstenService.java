package com.matingting.mobilesafe.service;

import com.matingting.mobilesafe.R;
import com.matingting.mobilesafe.dao.PhoneAddressDao;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class CallLstenService extends Service {
     private TelephonyManager TM;
     private MyPhoneStateListener mMyPhoneStateListener;
     SharedPreferences mPres;
     private OutGoingListenReceiver mReceiver;
	private WindowManager mWindowManager;
	private View mAddressView;
	private final int[] styles=new int[]{ R.drawable.call_locate_white,
			R.drawable.call_locate_orange, R.drawable.call_locate_blue,
			R.drawable.call_locate_gray, R.drawable.call_locate_green };
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		mPres=getSharedPreferences("config", MODE_PRIVATE);
		TM=(TelephonyManager) getSystemService(TELEPHONY_SERVICE);
		mMyPhoneStateListener=new MyPhoneStateListener();
		TM.listen(mMyPhoneStateListener, PhoneStateListener.LISTEN_CALL_STATE);
		mReceiver=new OutGoingListenReceiver();
		registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL));
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		TM.listen(mMyPhoneStateListener, TelephonyManager.CALL_STATE_IDLE);// Í£Ö¹À´µç¼àÌý
		unregisterReceiver(mReceiver);
//		if(mPres!=null)
//		  {
//			mPres.edit().putBoolean("isCallAdressShow", false);
//		  }
	}
	class OutGoingListenReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String number=getResultData();
			String location=PhoneAddressDao.QueryPhoneAddress(number);
			Toast.makeText(CallLstenService.this, location, Toast.LENGTH_LONG).show();
		}
		
	}
	class MyPhoneStateListener extends PhoneStateListener{

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			switch(state)
			{
			case TelephonyManager.CALL_STATE_RINGING:
				String location=PhoneAddressDao.QueryPhoneAddress(incomingNumber);
				showToast(CallLstenService.this,location);
				System.out.println("CallLstenService"+location);break;
			case TelephonyManager.CALL_STATE_IDLE:
				System.out.println("CALL_STATE_IDLE");
				if(mWindowManager!=null&&mAddressView!=null)
				  {
					mWindowManager.removeView(mAddressView);
					mAddressView=null;
				  }break;
			}
		
			super.onCallStateChanged(state, incomingNumber);
		}	
	}
	public void showToast(Context context, CharSequence text){
         mWindowManager=(WindowManager) context.getSystemService(context.WINDOW_SERVICE);		
		WindowManager.LayoutParams params = new WindowManager.LayoutParams();
         params.height = WindowManager.LayoutParams.WRAP_CONTENT;
         params.width = WindowManager.LayoutParams.WRAP_CONTENT;
         params.format = PixelFormat.TRANSLUCENT;
         params.type = WindowManager.LayoutParams.TYPE_TOAST;
         params.setTitle("Toast");
         params.flags = WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                 | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                 | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
         params.setTitle("Toast");
         
		mAddressView=View.inflate(context, R.layout.address_window, null);
		int style=mPres.getInt("addressToastStyle", 2);
		mAddressView.setBackgroundResource(styles[style]);
		TextView toastWin=(TextView) mAddressView.findViewById(R.id.tv_toastWin);
		toastWin.setText(text);
		
//		ViewGroup p=(ViewGroup) view.getParent();
//		if(p!=null)
//		{
//			p.removeAllViewsInLayout();
//		}
		mWindowManager.addView(mAddressView, params);
	}
}
