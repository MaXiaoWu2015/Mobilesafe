package com.matingting.mobilesafe;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AdvancedToolsActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_advanced_tools);
	}

   public void EnterAddressSearch(View view)
   {
	   startActivity(new Intent(AdvancedToolsActivity.this,AddressSearchActivity.class));
   }
}
