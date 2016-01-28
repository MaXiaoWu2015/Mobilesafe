package com.matingting.mobilesafe.receiver;

import com.matingting.mobilesafe.dao.PhoneAddressDao;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;

public class CallListenReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if(intent.getAction().equals(Intent.ACTION_NEW_OUTGOING_CALL))
		{
			
		}
		else{
			TelephonyManager tm=(TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
			tm.listen(new MyPhoneStateListener(context), PhoneStateListener.LISTEN_CALL_STATE);
		}
	}
    class MyPhoneStateListener extends PhoneStateListener
    {
    	private Context mContext;
		public MyPhoneStateListener(Context context) {
			super();
			// TODO Auto-generated constructor stub
			mContext=context;
		}

		@Override
		public void onCallStateChanged(int state, String incomingNumber) {
			// TODO Auto-generated method stub
			
			switch (state)
			{
			case TelephonyManager.CALL_STATE_RINGING:
				String location=PhoneAddressDao.QueryPhoneAddress(incomingNumber);
				Toast.makeText(mContext, location, Toast.LENGTH_LONG).show();
				System.out.println("CallLstenService"+location);break;
			}
			super.onCallStateChanged(state, incomingNumber);
		}
    	
    }
    
}
