package com.matingmating.mobilesafe.fragment;

import com.matingting.mobilesafe.R;
import com.matingting.mobilesafe.inter.FragmentCallback;
import com.matingting.mobilesafe.view.SettingItemCheckView;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Setting2Frag extends Fragment {
    private Button nextBtn;
    private Button preBtn;
    private FragmentTransaction transaction;
    private FragmentManager fm;
    private FragmentCallback mFragmentCallback=null;
    private SettingItemCheckView mSettingItemView;
    private SharedPreferences mPres;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mPres=getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
		View view=inflater.inflate(R.layout.frag_setting2, container, false);
//		fm=getActivity().getSupportFragmentManager();
		
		initView(view);
		return view;
	}
	private void initView(View view)
	{
		nextBtn=(Button) view.findViewById(R.id.frag_setting2_nextbtn);
		preBtn=(Button) view.findViewById(R.id.frag_setting2_prebtn);
		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				transaction =  fm.beginTransaction();
//				transaction.replace(R.id.frag_replace, new Setting3Frag());
//				transaction.commit();
				mFragmentCallback.toNextFragment(1);
			}
		});
		preBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				transaction =  fm.beginTransaction();
//				transaction.replace(R.id.frag_replace, new Setting1Frag());
//				transaction.commit();
				mFragmentCallback.toPreFragment(1);
			}
		});
		mSettingItemView=(SettingItemCheckView) view.findViewById(R.id.SIM_bind);
		mSettingItemView.setChecked((mPres.getBoolean("isBindSim", false)));
		mSettingItemView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mSettingItemView.isChecked())
				{
					mSettingItemView.setChecked(false);
					mPres.edit().putBoolean("isBindSim", false).commit();
					mPres.edit().putString("SimNum",  null).commit();

				}
				else{
					mSettingItemView.setChecked(true);
					mPres.edit().putBoolean("isBindSim", true).commit();
					mPres.edit().putString("SimNum",  getSimNum()).commit();
					System.out.println("SimNum“—∞Û∂®");
				}
			}
		});

	}
	private String getSimNum(){
		TelephonyManager TM=(TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
		String SimNum=TM.getSubscriberId();
		return SimNum;
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mFragmentCallback=(FragmentCallback) activity;
	}
	
}
