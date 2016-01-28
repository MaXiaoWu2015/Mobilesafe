package com.matingting.mobilesafe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.matingting.mobilesafe.utils.StreamUtils;

public class SplashActivity extends Activity {
	
	private static final int FIND_NEW_VERSION=1;
	private static final int INTERNET_ERROR=2;
	private static final int ENTER_HOME_SCENE=3;
	private static final int CODE_JSON_ERROR=4;
	private  SharedPreferences mPres;
	
	private TextView tv_version;
	private TextView tv_loadProgress;
	private RelativeLayout rlSplash;
	private String mVersionName;
	private String mVersionDesciption;
	private Handler mHandler=new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			Toast.makeText(SplashActivity.this, mVersionName, Toast.LENGTH_SHORT);
			System.out.println("handler"+msg.what);
			switch(msg.what)
			{
			case FIND_NEW_VERSION:
				showDialog();
				break;
			case INTERNET_ERROR:
				enterHomeScene();
				Toast.makeText(SplashActivity.this, "网络异常", Toast.LENGTH_SHORT);
				break;
			case ENTER_HOME_SCENE:
				enterHomeScene();
			case CODE_JSON_ERROR:
				enterHomeScene();
				Toast.makeText(SplashActivity.this, "数据解析错误", Toast.LENGTH_SHORT);
			}
			
		}
		
	};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		tv_loadProgress=(TextView) findViewById(R.id.tv_loadProgress);
		tv_version = (TextView) findViewById(R.id.tv_version); 
		rlSplash=(RelativeLayout) findViewById(R.id.rl_splash);
		tv_version.setText("版本号:" + getVersionName());
		mPres=getSharedPreferences("config",MODE_PRIVATE);
		System.out.println(mPres.getBoolean("isAutoCheckUpdate", true));
		if(mPres.getBoolean("isAutoCheckUpdate", true))
		    {
		    	checkVersionInfo();
		    }
		 else{
		    	mHandler.sendEmptyMessageDelayed(ENTER_HOME_SCENE, 2000);//延迟两秒进入主页，如果此处用Thread.sleep会出现想不到bug
		    	
		    }
	    AlphaAnimation anim=new AlphaAnimation(0, 1);
	    anim.setDuration(2000);
	    rlSplash.startAnimation(anim);
	    copyAddressDB("address.db");
	    
	}

	private void enterHomeScene() {
		// TODO Auto-generated method stub
		Intent intent=new Intent(this,MainActivity.class);
		startActivity(intent);	
	}

	private String getVersionName() {
		String versionName = null;
		PackageManager packManager = getPackageManager();
		try {
			PackageInfo packInfo = packManager.getPackageInfo(getPackageName(),
					0);
			versionName = packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return versionName;
	}

	private void checkVersionInfo() {
		final long start=System.currentTimeMillis();
		new Thread() {
			Message msg=Message.obtain();
			HttpURLConnection conn=null;
			@Override
			public void run() {
				URL url;
				
				try {
					url = new URL("http://192.168.1.102:8080/versionInfo.json");
					conn = (HttpURLConnection) url
							.openConnection();
					conn.setRequestMethod("GET");
					conn.setConnectTimeout(5000);//设置连接超时
					conn.setReadTimeout(5000);//设置响应超时
					InputStream ins = conn.getInputStream();
					String versionInfo = StreamUtils.readFromStream(ins);
					JSONObject jsonObject = new JSONObject(versionInfo);
					jsonObject.getInt("versionCode");
					mVersionName = jsonObject.getString("versionName");
					mVersionDesciption = jsonObject.getString("versionDesciption");
					jsonObject.getString("downloadURL");
					System.out.println("hahaha:"+mVersionName);
					if (Float.parseFloat(mVersionName) > Float.parseFloat(getVersionName())) 
					{
						msg.what=FIND_NEW_VERSION;
						System.out.println("hahaha:"+getVersionName());
					}
					else{
						  msg.what=ENTER_HOME_SCENE;
					}
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					msg.what=INTERNET_ERROR;
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					long end=System.currentTimeMillis();
					if(end-start<2000)
					{
						try {
							Thread.sleep(2000-(end-start));
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					mHandler.sendMessage(msg);
					if(conn!=null)
					{
						conn.disconnect();
					}
				}
				       
			}
			
		}.start();
	}
	protected void download(){
		String url="http://192.168.1.102:8080/MoblieSafe52.apk";
		String target=Environment.getExternalStorageDirectory()+"/update.apk";
		if (Environment.MEDIA_MOUNTED.equals(Environment
				.getExternalStorageState()))
		{
			HttpUtils http=new HttpUtils();
		    HttpHandler<File> handler=http.download(url, target, new RequestCallBack<File>() {
			
		    
			@Override
				public void onLoading(long total, long current,
						boolean isUploading) {
					// TODO Auto-generated method stub
				    tv_loadProgress.setVisibility(View.VISIBLE);
				    System.out.println("download:"+"下载进度:"+current*100/total+"%");
				    tv_loadProgress.setText("下载进度:"+current*100/total+"%");
					super.onLoading(total, current, isUploading);
					
				}

			@Override
			public void onSuccess(ResponseInfo<File> arg0) {
				// TODO Auto-generated method stub
				tv_loadProgress.setVisibility(View.INVISIBLE);
				Intent intent=new Intent(Intent.ACTION_VIEW);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.setDataAndType(Uri.fromFile(arg0.result), "application/vnd.android.package-archive" );
				startActivityForResult(intent, 0);
			}
			
			@Override
			public void onFailure(HttpException arg0, String arg1) {
				// TODO Auto-generated method stub
				Toast.makeText(SplashActivity.this, "下载失败", Toast.LENGTH_LONG).show();
			}
		});
		}
		else{
			Toast.makeText(this, "找不到SD卡", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		enterHomeScene();
		super.onActivityResult(requestCode, resultCode, data);
	}

	private void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("发现新版本:" + mVersionName);
		builder.setMessage(mVersionDesciption);
		builder.setCancelable(false);//用户不能取消对话框
		builder.setPositiveButton(getResources().getText(R.string.UpdateNow),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						download();
					}
				});
		builder.setNegativeButton(getResources().getText(R.string.DelayNext),
				new OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						enterHomeScene();
					}
				});
		builder.show();
	}
	private void copyAddressDB(String DBName){
		File files=new File(getFilesDir(),DBName);
		FileOutputStream fos=null;
		InputStream  ins=null;
		try {
			 fos=new FileOutputStream(files);
			 ins=getAssets().open(DBName);
			byte[] buffer=new byte[1024];
			while((ins.read(buffer))!=-1)
			{
				fos.write(buffer);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(fos!=null)
			{
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(ins!=null)
			{
				try {
					ins.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
