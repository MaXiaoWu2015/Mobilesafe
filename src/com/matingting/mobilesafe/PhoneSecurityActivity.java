package com.matingting.mobilesafe;

import java.util.ArrayList;

import com.matingmating.mobilesafe.fragment.Setting1Frag;
import com.matingmating.mobilesafe.fragment.Setting2Frag;
import com.matingmating.mobilesafe.fragment.Setting3Frag;
import com.matingmating.mobilesafe.fragment.Setting4Frag;


import com.matingting.mobilesafe.inter.FragmentCallback;


import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;


public class PhoneSecurityActivity extends FragmentActivity implements FragmentCallback{
	private SharedPreferences mPres;
    private ArrayList<Fragment> mSettingList=new ArrayList<Fragment>();
    private ViewPager mViewPager;
    private FragmentPagerAdapter mPagerAdapter;
    private Fragment mFrag1=new Setting1Frag(); 
    private Fragment mFrag2=new Setting2Frag(); 
    private Fragment mFrag3=new Setting3Frag(); 
    private Fragment mFrag4=new Setting4Frag(); 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_phone_security);
//		mPres=getSharedPreferences("config",MODE_PRIVATE);
//		boolean isSecuritySetting=mPres.getBoolean("SecuritySetting", false);
//		System.out.println("mPres:"+isSecuritySetting);
//		
//		if(isSecuritySetting)
//		{
//			startActivity(new Intent(PhoneSecurityActivity.this,SecuritySettingCompletedActivity.class));
//		}
//		else{
//			
//			
//		}
		initAdapter();
	}
	private void initAdapter(){
		
		mSettingList.add(mFrag1);
		mSettingList.add(mFrag2);
		mSettingList.add(mFrag3);
		mSettingList.add(mFrag4);
		mPagerAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mSettingList.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return mSettingList.get(arg0);
			}
		};
		mViewPager=(ViewPager) findViewById(R.id.frag_replace);
		mViewPager.setAdapter(mPagerAdapter);	
	}
	@Override
	public void toNextFragment(int curFrag) {
		// TODO Auto-generated method stub
		if(curFrag==3)
		{
			System.out.println("curFrag=="+curFrag);
			startActivity(new Intent(PhoneSecurityActivity.this,SecuritySettingCompletedActivity.class));
		}else{
		  mViewPager.setCurrentItem(curFrag+1);
		}
	}
	@Override
	public void toPreFragment(int curFrag) {
		// TODO Auto-generated method stub
		mViewPager.setCurrentItem(curFrag-1);
	}
}
