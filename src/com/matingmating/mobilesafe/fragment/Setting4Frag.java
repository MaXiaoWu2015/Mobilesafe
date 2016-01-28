package com.matingmating.mobilesafe.fragment;

import com.matingting.mobilesafe.R;
import com.matingting.mobilesafe.inter.FragmentCallback;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Setting4Frag extends Fragment {
	 private Button nextBtn;
	    private Button preBtn;
	    private FragmentTransaction transaction;
	    private FragmentManager fm;
	    private SharedPreferences mPres;
	    private FragmentCallback mFragmentCallback=null;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.frag_setting4, container, false);
		mPres=getActivity().getSharedPreferences("config",Context.MODE_PRIVATE);
		fm=getActivity().getSupportFragmentManager();
		nextBtn=(Button) view.findViewById(R.id.frag_setting4_nextbtn);
		preBtn=(Button) view.findViewById(R.id.frag_setting4_prebtn);
		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mPres.edit().putBoolean("SecuritySetting", true).commit();
//				transaction =  fm.beginTransaction();
//				transaction.replace(R.id.frag_replace, new Setting5Frag());
//				transaction.commit();
				mFragmentCallback.toNextFragment(3);
			}
		});
		preBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				transaction =  fm.beginTransaction();
//				transaction.replace(R.id.frag_replace, new Setting4Frag());
//				transaction.commit();
				mFragmentCallback.toPreFragment(3);
			}
		});
		return view;
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mFragmentCallback=(FragmentCallback) activity;
	}
}
