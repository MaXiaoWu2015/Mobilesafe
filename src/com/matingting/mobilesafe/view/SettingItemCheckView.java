package com.matingting.mobilesafe.view;

import com.matingting.mobilesafe.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SettingItemCheckView extends RelativeLayout {

	private CheckBox mCheckBox;
	private TextView mtvIsUpdateCheck;
	private String mTitle;
	private String mDescOn;
	private String mDescOff;
	private TextView mtvTitle;
	private final static String NAMESPACE="http://schemas.android.com/apk/res/com.matingting.mobilesafe";
	public SettingItemCheckView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView();
	}

	public SettingItemCheckView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mTitle=attrs.getAttributeValue(NAMESPACE, "Title");
		mDescOn=attrs.getAttributeValue(NAMESPACE, "DescOn");
		mDescOff=attrs.getAttributeValue(NAMESPACE, "DescOff");
		initView();

	}

	public SettingItemCheckView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView();
	}
	private void initView(){
		View.inflate(getContext(), R.layout.setting_item_check, this);
		
		mCheckBox=(CheckBox) findViewById(R.id.cb_checkbox);
		mtvIsUpdateCheck=(TextView) findViewById(R.id.tv_IsUpdateCheck);
		mtvTitle=(TextView)findViewById(R.id.tv_check);
		mtvTitle.setText(mTitle);
		mtvIsUpdateCheck.setText(mDescOn);
	}
	public boolean isChecked()
	{
		return mCheckBox.isChecked();
	}

	public void setChecked(boolean b) {
		// TODO Auto-generated method stub
		mCheckBox.setChecked(b);
		if(b)
		{
			mtvIsUpdateCheck.setText(mDescOn);
		}
		else{
			mtvIsUpdateCheck.setText(mDescOff);
		}
	}

}
