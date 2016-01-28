package com.matingting.mobilesafe;


import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class DragActivity extends Activity {
	SharedPreferences mPres;
	private ImageView mIvMove;
	private TextView mTvTop;
	private TextView mTvBottom;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag);
		mPres=getSharedPreferences("config", MODE_PRIVATE);
		initView();
	}
	private void initView()
	{
		mTvTop=(TextView) findViewById(R.id.tv_desc_top);
		mTvBottom=(TextView) findViewById(R.id.tv_desc_bottom);
		
		
		mIvMove=(ImageView) findViewById(R.id.Iv_move);
		RelativeLayout.LayoutParams rl_LayoutParams=(RelativeLayout.LayoutParams) mIvMove.getLayoutParams();
		rl_LayoutParams.leftMargin=mPres.getInt("DragActivity_x", getWindowManager().getDefaultDisplay().getWidth()/2);
		rl_LayoutParams.topMargin=mPres.getInt("DragActivity_y", getWindowManager().getDefaultDisplay().getHeight()/2);
		mIvMove.setLayoutParams(rl_LayoutParams);
		
		mIvMove.setOnTouchListener(new OnTouchListener() {
			// TODO Auto-generated method stub
			 int  preX=0;
			 int preY=0;
			 int  nowX=0;
			 int nowY=0;
			 int l=0;
			 int t=0;
			@Override
			public boolean onTouch(View v, MotionEvent event) {

				switch (event.getAction())
				{
				case MotionEvent.ACTION_DOWN:
					 preX = (int) event.getRawX();
					 preY=(int) event.getRawY();
					break;
				case MotionEvent.ACTION_MOVE:
					nowX=(int) event.getRawX();
					nowY=(int) event.getRawY();
					
					int dx=nowX-preX;
					int dy=nowY-preY;
					
					 l=mIvMove.getLeft()+dx;
					 t=mIvMove.getTop()+dy;
			
					 mIvMove.layout(l, t, l+mIvMove.getWidth(), t+mIvMove.getHeight());	
					 if(nowY<mTvTop.getHeight()+mTvTop.getPaddingBottom())
					 {
						 mTvTop.setVisibility(View.INVISIBLE);
						 mTvBottom.setVisibility(View.VISIBLE);
					 }
					 if(nowY>getWindowManager().getDefaultDisplay().getHeight()-mTvTop.getHeight())
					 {
						 mTvTop.setVisibility(View.VISIBLE);
						 mTvBottom.setVisibility(View.INVISIBLE);
					 }
					 
					 preX=nowX;
					 preY=nowY;
					break;
				case MotionEvent.ACTION_UP:
				    mPres.edit().putInt("DragActivity_x", l).commit();
				    mPres.edit().putInt("DragActivity_y", t).commit();
				    
				    break;
				}
				return true;
			}
		});
	}
}
