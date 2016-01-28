package com.matingmating.mobilesafe.fragment;

import com.matingting.mobilesafe.R;
import com.matingting.mobilesafe.inter.FragmentCallback;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class Setting1Frag extends Fragment {
	private Button nextBtn;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private FragmentCallback mFragmentCallback=null;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.frag_setting1, container, false);
		fm=getActivity().getSupportFragmentManager();
		nextBtn=(Button) view.findViewById(R.id.frag_setting1_nextbtn);
		nextBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				transaction =  fm.beginTransaction();
////				transaction.replace(R.id.frag_replace, new Setting2Frag());
//				transaction.commit();
				mFragmentCallback.toNextFragment(0);
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
