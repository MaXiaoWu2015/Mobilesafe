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
			 //1.输入框晃动
			 Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
			 et_PhoneNumber.startAnimation(shake);
			//2.手机震动
			 vibrate();
		}
	}
	public void vibrate(){
		Vibrator vibrate=(Vibrator) getSystemService(VIBRATOR_SERVICE);
		vibrate.vibrate(new long[]{1000,2000,1000,3000}, -1);// 先等待1秒,再震动2秒,再等待1秒,再震动3秒,
		// 参2等于-1表示只执行一次,不循环,
		// 参2等于0表示从头循环,
		// 参2表示从第几个位置开始循环
//		vibrate.cancel();//取消震动
	}
}
