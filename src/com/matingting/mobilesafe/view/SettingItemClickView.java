package com.matingting.mobilesafe.view;

import com.matingting.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemClickView extends RelativeLayout {

	private TextView mtvAddressToastStyle;
	private TextView mtvTitle;
	public SettingItemClickView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView();
	}

	public SettingItemClickView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView();

	}

	public SettingItemClickView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	private void initView(){
		View.inflate(getContext(), R.layout.setting_item_click, this);
		
		
		mtvAddressToastStyle=(TextView) findViewById(R.id.tv_addressStyle);
		mtvTitle=(TextView)findViewById(R.id.tv_click);
	}
   public void setTitle(String title)
   {
	   mtvTitle.setText(title);
   }
   public void setDesc(String desc)
   {
	   mtvAddressToastStyle.setText(desc);
   }

}
