package com.matingting.mobilesafe;


import com.matingting.mobilesafe.dao.PhoneAddressDao;

import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.app.Activity;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.TextView;

public class AddressSearchActivity extends Activity {
	private EditText et_PhoneNumber;
	private TextView tv_address;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_address_search);
		et_PhoneNumber=(EditText) findViewById(R.id.et_PhoneNumber);
		tv_address=(TextView) findViewById(R.id.tv_address);
		et_PhoneNumber.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				String number=et_PhoneNumber.getText().toString().trim();
				String location=PhoneAddressDao.QueryPhoneAddress(number);
				tv_address.setText(location);
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	public void Search(View view)
	{
		
		String number=et_PhoneNumber.getText().toString().trim();
		if(!TextUtils.isEmpty(number))
		 {
			String location=PhoneAddressDao.QueryPhoneAddress(number);
		    tv_address.setText(location);
		 }
		else{
			 //1.�����ζ�
			 Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			 et_PhoneNumber.startAnimation(shake);
			//2.�ֻ���
			 vibrate();
		}
	}
	public void vibrate(){
		Vibrator vibrate=(Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibrate.vibrate(new long[]{1000,2000,1000,3000}, -1);// �ȵȴ�1��,����2��,�ٵȴ�1��,����3��,
		// ��2����-1��ʾִֻ��һ��,��ѭ��,
		// ��2����0��ʾ��ͷѭ��,
		// ��2��ʾ�ӵڼ���λ�ÿ�ʼѭ��
//		vibrate.cancel();//ȡ����
	}
}
