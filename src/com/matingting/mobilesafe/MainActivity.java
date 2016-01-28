package com.matingting.mobilesafe;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.matingting.mobilesafe.R;
public class MainActivity extends Activity {
	
	private SharedPreferences mPres;
	private GridView mGVList;
	private String[] mItemName=new String[]{ "手机防盗", "通讯卫士", "软件管理", "进程管理",
			"流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心" };
	private int[]  mItemIcon=new int[]{R.drawable.home_safe,R.drawable.home_callmsgsafe,R.drawable.home_apps,
			R.drawable.home_taskmanager,R.drawable.home_netmanager,R.drawable.home_trojan,R.drawable.home_sysoptimize,
			R.drawable.home_tools,R.drawable.home_settings};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPres=getSharedPreferences("config",MODE_PRIVATE);
		mGVList=(GridView) findViewById(R.id.gv_list);
		mGVList.setAdapter(new HomeAdapter());
		mGVList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				switch (position)
				{
				case 0:
					showPasswordDialog();break;
				case 7:
					startActivity(new Intent(MainActivity.this,AdvancedToolsActivity.class));break;
				case 8:
					startActivity(new Intent(MainActivity.this,SettingActivity.class));
					break;
				}
			}
		});
	}
	protected void showPasswordDialog() {
		// TODO Auto-generated method stub
	 String savedPassword=mPres.getString("password", null);
	 if(savedPassword==null)
		{
		 showSetPasswordDialog();
		}
	 else{
		 showInputPasswordDialog(savedPassword);
	    }
		
	}
	private void showInputPasswordDialog(final String savedPassword) {
		// TODO Auto-generated method stub

		 AlertDialog.Builder builder=new AlertDialog.Builder(this);
		    final AlertDialog inputPasswordDialog=builder.create();
		    
		    View view=View.inflate(this, R.layout.password_inputing, null);
		    inputPasswordDialog.setView(view,0,0,0, 0);
		    inputPasswordDialog.show();
//		    inputPasswordDialog.getWindow().setLayout(1000, 600);
		    Button confirm_btn=(Button)view.findViewById(R.id.confirm_input_btn);
		    Button cancel_btn=(Button)view.findViewById(R.id.cancel_input_btn);
		    final EditText password=(EditText)view.findViewById(R.id.et_input_password);
//		    final String passwordStr=password.getText().toString();//为什么在这获取，就获取不到EditText的值
		    confirm_btn.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String passwordStr=password.getText().toString();
					if(passwordStr.isEmpty())
					{
						Toast.makeText(MainActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
					}
					else 
					   {
						
						if(passwordStr.equals(savedPassword)){
							Toast.makeText(MainActivity.this, "成功登录", Toast.LENGTH_SHORT).show();
							inputPasswordDialog.dismiss();
							startActivity(new Intent(MainActivity.this, SecuritySettingCompletedActivity.class));
					     }
						else{
							Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
						}
						
					}
				}
			});
		    cancel_btn.setOnClickListener(new OnClickListener() {
		    	
		    	@Override
		    	public void onClick(View v) {
		    		// TODO Auto-generated method stub
		    		inputPasswordDialog.dismiss();
		    	}
		    });
		   
	}
	private void showSetPasswordDialog() {
		// TODO Auto-generated method stub
	    AlertDialog.Builder builder=new AlertDialog.Builder(this);
	    final AlertDialog setPasswordDialog=builder.create();
	    View view=View.inflate(this, R.layout.password_setting, null);
	    setPasswordDialog.setView(view,0,0,0,0);
	    Button confirm_btn=(Button)view.findViewById(R.id.confirm_btn);
	    Button cancel_btn=(Button)view.findViewById(R.id.cancel_btn);
	    final EditText password=(EditText) view.findViewById(R.id.et_password);
	   
	    final EditText confirm_password=(EditText)view.findViewById(R.id.et_confirm_password);
	   
//	    System.out.println("passwordStr:"+","+"confirm_passwordStr:"+confirm_passwordStr);
	    confirm_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 String passwordStr=password.getText().toString();
				 String confirm_passwordStr=confirm_password.getText().toString();
				 System.out.println("passwordStr:"+passwordStr+","+"confirm_passwordStr:"+confirm_passwordStr);
				if(TextUtils.isEmpty(passwordStr)||confirm_passwordStr.isEmpty())
				{
					Toast.makeText(MainActivity.this, "输入框不能为空", Toast.LENGTH_SHORT).show();
				}
				else 
				   {
					if(passwordStr.equals(confirm_passwordStr)){
						Toast.makeText(MainActivity.this, "密码设置成功", Toast.LENGTH_SHORT).show();
						mPres.edit().putString("password", passwordStr).commit();
						setPasswordDialog.dismiss();
						
				     }
					else{
						Toast.makeText(MainActivity.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();
					}
					
				}
			}
		});
	    cancel_btn.setOnClickListener(new OnClickListener() {
	    	
	    	@Override
	    	public void onClick(View v) {
	    		// TODO Auto-generated method stub
	    		setPasswordDialog.dismiss();
	    	}
	    });
	    setPasswordDialog.show();
	}
	class HomeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return mItemName.length;
		}

		@Override
		public Object getItem(int position) {
			return mItemName[position];
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(MainActivity.this,
					R.layout.main_gridview_item, null);
			ImageView ivItem = (ImageView) view.findViewById(R.id.Iv_item);
			TextView tvItem = (TextView) view.findViewById(R.id.tv_item);
			tvItem.setText(mItemName[position]);
			ivItem.setImageResource(mItemIcon[position]);
			return view;
		}

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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
