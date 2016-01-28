package com.matingting.mobilesafe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class SecuritySettingCompletedActivity extends Activity {
	SharedPreferences mPres;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_security_setting_completed);
		mPres=getSharedPreferences("config", MODE_PRIVATE);
		boolean isSecuritySetting=mPres.getBoolean("SecuritySetting", false);
		System.out.println("mPres:"+isSecuritySetting);
		
		if(!isSecuritySetting)
		{
			startActivity(new Intent(SecuritySettingCompletedActivity.this,PhoneSecurityActivity.class));
		}

	}
	public void reEnter(View view){
//		mPres.edit().putBoolean("SecuritySetting", false).commit();
		startActivity(new Intent(SecuritySettingCompletedActivity.this,PhoneSecurityActivity.class));
		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.security_setting_completed, menu);
		return true;
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
