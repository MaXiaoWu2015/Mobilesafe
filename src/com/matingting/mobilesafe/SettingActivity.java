package com.matingting.mobilesafe;

import com.matingting.mobilesafe.service.CallLstenService;
import com.matingting.mobilesafe.utils.ServiceStatusUtils;
import com.matingting.mobilesafe.view.SettingItemCheckView;
import com.matingting.mobilesafe.view.SettingItemClickView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

public class SettingActivity extends Activity {
	private  SharedPreferences mPres;
	private SettingItemCheckView mRlItem1;
	private SettingItemCheckView mRlItem2;
	private SettingItemClickView mRlItem3;
	private SettingItemClickView mRlItem4;
	private final String[]  mAddressToastSytleItems=new String[] { "半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		mPres=getSharedPreferences("config",MODE_PRIVATE);
		initView();
		
	}
	
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mRlItem2Update();
	}
    //若用户手动关闭服务，同步checkBox
	private void mRlItem2Update()
    {
    	boolean isServiceRunning=ServiceStatusUtils.isServiceRunning(this, "com.matingting.mobilesafe.service.CallLstenService");
    	if(isServiceRunning)
    	   {
    		mRlItem2.setChecked(true);
//    		mPres.edit().putBoolean("isAutoCheckUpdate", false).commit();
    	   }
    	else{
    		mRlItem2.setChecked(false);
//    		mPres.edit().putBoolean("isAutoCheckUpdate", false).commit();
    	}
    		
    }
	private void initView()
	{
		mRlItem1=(SettingItemCheckView) findViewById(R.id.rl_SettingItem1);
		mRlItem1.setChecked(mPres.getBoolean("isAutoCheckUpdate", true));
		mRlItem1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mRlItem1.isChecked())
				{
					mRlItem1.setChecked(false);
					mPres.edit().putBoolean("isAutoCheckUpdate", false).commit();
				}
				else{
					mRlItem1.setChecked(true);
					mPres.edit().putBoolean("isAutoCheckUpdate", true).commit();

				}
			}
		});
		
		mRlItem2=(SettingItemCheckView) findViewById(R.id.rl_SettingItem2);
		mRlItem2.setChecked(mPres.getBoolean("isCallAdressShow", false));
		mRlItem2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mRlItem2.isChecked())
				{
					mRlItem2.setChecked(false);
					stopService(new Intent(SettingActivity.this,CallLstenService.class));

//					mPres.edit().putBoolean("isCallAdressShow", false).commit();
				}
				else{
					mRlItem2.setChecked(true);
					startService(new Intent(SettingActivity.this,CallLstenService.class));
//					mPres.edit().putBoolean("isCallAdressShow", true).commit();
				}
			}
		});
		
		
		mRlItem3=(SettingItemClickView) findViewById(R.id.rl_SettingItem3);
		int addressToastStyle=mPres.getInt("addressToastStyle", 2);
		mRlItem3.setTitle("设置来电提醒样式");
		mRlItem3.setDesc(mAddressToastSytleItems[addressToastStyle]);
		mRlItem3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showAdressStyleChoicesDialog();
			}
		});
		
		mRlItem4=(SettingItemClickView) findViewById(R.id.rl_SettingItem4);
		mRlItem4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(SettingActivity.this, DragActivity.class);
				startActivity(intent);
			}
		});
		
		
		
		
	}
	
	protected void showAdressStyleChoicesDialog() {
		// TODO Auto-generated method stub
		int addressToastStyle=mPres.getInt("addressToastStyle", 2);
		AlertDialog.Builder builder=new AlertDialog.Builder(this);
		builder.setTitle("请选择一种风格");
		builder.setSingleChoiceItems(mAddressToastSytleItems, addressToastStyle, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				mRlItem3.setDesc(mAddressToastSytleItems[which]);
				mPres.edit().putInt("addressToastStyle", which).commit();	
				dialog.dismiss();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		builder.show();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
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
