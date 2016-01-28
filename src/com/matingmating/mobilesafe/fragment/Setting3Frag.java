package com.matingmating.mobilesafe.fragment;

import com.matingting.mobilesafe.R;
import com.matingting.mobilesafe.inter.FragmentCallback;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
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
import android.widget.EditText;
import android.widget.Toast;

public class Setting3Frag extends Fragment {
	    private Button nextBtn;
	    private Button preBtn;
	    private FragmentTransaction transaction;
	    private FragmentManager fm;
	    private FragmentCallback mFragmentCallback=null;
	    private Button mSelectContact;
	    private EditText mEdit;
	    private SharedPreferences mPres;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view=inflater.inflate(R.layout.frag_setting3, container, false);
		mPres=getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
//		fm=getActivity().getSupportFragmentManager();
		initView(view);
		return view;
	}
	private void initView(View view){
		mEdit=(EditText) view.findViewById(R.id.et_show_num);
		mEdit.setText(mPres.getString("urgentContact", ""));
		
		nextBtn=(Button) view.findViewById(R.id.frag_setting3_nextbtn);
		preBtn=(Button) view.findViewById(R.id.frag_setting3_prebtn);
		mSelectContact=(Button) view.findViewById(R.id.select_contacts);
		mSelectContact.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(Intent.ACTION_PICK);
				intent.setType("vnd.android.cursor.dir/phone");
				startActivityForResult(intent, 0);
				
			}
		});
		nextBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				transaction =  fm.beginTransaction();
//				transaction.replace(R.id.frag_replace, new Setting4Frag());
//				transaction.commit();
				mFragmentCallback.toNextFragment(2);
			}
		});
		preBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				transaction =  fm.beginTransaction();
//				transaction.replace(R.id.frag_replace, new Setting2Frag());
//				transaction.commit();
				mFragmentCallback.toPreFragment(2);
			}
		});
	}
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		mFragmentCallback=(FragmentCallback)activity;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println("onActivityResult!!!");
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode)
		{
		case 0:
			if(data==null)
			{
				System.out.println("没获取到");
				Toast.makeText(getActivity(), "没有获取到数据", Toast.LENGTH_LONG);
			}
			else{
				Uri uri=data.getData();
				Cursor data_cursor=getActivity().getContentResolver().query(uri, null, null, null, null);
				data_cursor.moveToFirst();
				String number=data_cursor.getString(data_cursor.getColumnIndexOrThrow(android.provider.Contacts.Phones.NUMBER));
				String name=data_cursor.getString(data_cursor.getColumnIndexOrThrow(android.provider.Contacts.Phones.NAME));
				mEdit.setText(number);
				mPres.edit().putString("urgentContact", number).commit();
			}
			break;
		}
	
	}
	
}
